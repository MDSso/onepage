package com.mds.prj.config;

import java.security.Principal;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.mds.prj.dao.IBoardDao;
import com.mds.prj.dao.IMemberDao;
import com.mds.prj.dto.BoardListDto;
import com.mds.prj.dto.MemberDetails;
import com.mds.prj.util.Xss;

@Component
public class SocketHandler extends TextWebSocketHandler{
	
	Logger logger = LoggerFactory.getLogger(SocketHandler.class);
	HashMap<String, WebSocketSession> sessionMap = new HashMap<>(); 
	
	@Autowired
	IBoardDao dao;
	@Autowired
	IMemberDao dao2;
	
	@SuppressWarnings("unchecked")
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		//메시지 발송
		String msg = message.getPayload();
		//xss필터
		Xss mxss = new Xss();
		//메시지 발송자의 로그인 세션 취득 및 객체 저장
		Principal principal = session.getPrincipal();
		MemberDetails member = dao2.findById(principal.getName());
		
		JSONObject obj = jsonToObjectParser(msg);
		//게시글의 Idx로 게시글 생성자 찾음
		BoardListDto board = dao.findByIdx(obj.get("Idx").toString());
		//게시글의 제목
		obj.put("title", board.getTitle());
		//댓글 입력 유저의 닉네임
		obj.put("memName", member.getMemName());
		
		String content = mxss.Xssf(obj.get("content").toString());
		System.out.println(content);
		obj.replace("content", content);
		System.out.println(obj.toJSONString());
		if(!board.getMemId().equals(member.getMemId())) {
			WebSocketSession wss = sessionMap.get(board.getMemId());
			try {
				wss.sendMessage(new TextMessage(obj.toJSONString()));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Principal principal = session.getPrincipal();
        String id = principal.getName(); // 로그인 아이디
        logger.info("소켓연결 사용자 ID : " + id);
		logger.info("소켓연결 세션 ID : " + session.getId());
		super.afterConnectionEstablished(session);
		sessionMap.put(id, session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		//소켓 종료
		Principal principal = session.getPrincipal();
        String id = principal.getName(); 
		logger.info("소켓 종료 사용자 ID : " + id);
		sessionMap.remove(id);
		super.afterConnectionClosed(session, status);
	}
	
	private static JSONObject jsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return obj;
	}

}
