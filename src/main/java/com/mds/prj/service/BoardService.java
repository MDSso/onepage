package com.mds.prj.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mds.prj.dao.IBoardDao;
import com.mds.prj.dto.BoardCommentDto;
import com.mds.prj.dto.BoardImgDto;
import com.mds.prj.dto.BoardListDto;
import com.mds.prj.dto.MemberDetails;
import com.mds.prj.dto.PagingVO;

@Service
public class BoardService {
	Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	@Autowired
	IBoardDao dao;
	
	public int insertBoard(BoardListDto boardListDto){
		
		return dao.insertBoard(boardListDto);
		
	}
	
	public BoardImgDto uploadImg(MultipartFile file) throws Exception{
		if(file.isEmpty()) logger.info("썸머노트 파일이 첨부되지 않았음");
		UUID uuid = UUID.randomUUID();
		BoardImgDto returnfile = new BoardImgDto();
		String path = "C:\\boardimg\\" + file.getOriginalFilename();
		returnfile.setId(uuid.toString());
		returnfile.setOriginalName(file.getOriginalFilename());
		returnfile.setSaveName(uuid.toString() + file.getOriginalFilename());
		returnfile.setFileSize(file.getBytes().toString());
		returnfile.setContentType(file.getContentType());
		returnfile.setFilePath(path);
		
		File save = new File(path);
		FileCopyUtils.copy(file.getBytes(),save);
		
		
		int result = dao.insertBoardImg(returnfile);
		if(result==1) logger.info("썸머노트 파일정보 저장 완료");
		else logger.info("썸머노트 파일정보 저장 실패");
		
		
		return returnfile;
	}
	
	public BoardImgDto loadImg(String fileid) {
		BoardImgDto imgDto = dao.findByFileId(fileid);
		
		return imgDto;
	}
	
	public List<BoardListDto> boardList(PagingVO paging,String category, MemberDetails member) {
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("paging", paging);
		map.put("category", category);
		List<BoardListDto> bdDto = dao.boardList(map);
		if(member != null) {
			for(int i=0; i<bdDto.size(); i++) {
				String memId = bdDto.get(i).getMemId();
				if(member.getMemId().equals(memId)) bdDto.get(i).setOwnerCheck("owner");
				else bdDto.get(i).setOwnerCheck("notowner");	
			}
		}
		for(int i=0; i<bdDto.size(); i++) {
			bdDto.get(i).setMemId("");
		}
	
		return bdDto;
	}
	
	public List<BoardListDto> nextBoard(PagingVO paging,String idxSet, String category, MemberDetails member) {
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("paging", paging);
		map.put("idxSet", idxSet);
		map.put("category", category);
		List<BoardListDto> bdDto = dao.nextBoard(map);
		if(member != null) {
			for(int i=0; i<bdDto.size(); i++) {
				String memId = bdDto.get(i).getMemId();
				if(member.getMemId().equals(memId)) bdDto.get(i).setOwnerCheck("owner");
				else bdDto.get(i).setOwnerCheck("notowner");
			}
		}
		
		for(int i=0; i<bdDto.size(); i++) {
			bdDto.get(i).setMemId("");
		}
		
		return bdDto;
	}
	
	public int deleteBoard(String memId,String boardIdx) {
		int result = dao.deleteBoard(memId, boardIdx);
		
		return result;
	}
	
	public int insertComment(BoardCommentDto commentDto) {
		int result = dao.insertComment(commentDto);
		if(result == 1) logger.info("댓글 정보 저장 성공");
		else logger.info("댓글 정보 저장 실패");
		return result;
	}
	
	public List<BoardCommentDto> getComments(String boardIdx,PagingVO paging,MemberDetails member){
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("paging", paging);
		map.put("boardIdx", boardIdx);
		logger.info("댓글 리스트 불러오기 boardIdx : " + boardIdx);
		List<BoardCommentDto> comments = dao.findComments(map);
		
		if(member != null) {
			for(int i=0; i < comments.size(); i++) {
				String memId = comments.get(i).getMemId();
				if(member.getMemId().equals(memId)) comments.get(i).setOwnerCheck("owner");
				else comments.get(i).setOwnerCheck("notowner");
				comments.get(i).setMemId("");
			}
		}

		return comments;
	}
	
	public List<BoardCommentDto> nextComments(String boardIdx,String commentIdx,PagingVO paging,MemberDetails member){
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("paging", paging);
		map.put("boardIdx", boardIdx);
		map.put("commentIdx", commentIdx);
		List<BoardCommentDto> comments = dao.nextComments(map);
		
		if(member != null) {
			for(int i=0; i < comments.size(); i++) {
				String memId = comments.get(i).getMemId();
				if(member.getMemId().equals(memId)) comments.get(i).setOwnerCheck("owner");
				else comments.get(i).setOwnerCheck("notowner");
				comments.get(i).setMemId("");
			}
		}
		
		return comments;
	}
	
	public String recommend(String idx,MemberDetails member) throws Exception {
		HashMap<String, Object> map = new HashMap<String,Object>();
		String result;
		map.put("idx", idx);
		map.put("member", member);
		int check = dao.checkRecom(map);
		if(check > 0) throw new Exception("이미 추천한 사용자");
		else {
			dao.insertRecom(map);
			dao.updateRecom(idx);
			result = dao.getRecom(idx);
		}
		
		return result;
	}
	
	public String notRecommend(String idx,MemberDetails member) throws Exception {
		HashMap<String, Object> map = new HashMap<String,Object>();
		String result;
		map.put("idx", idx);
		map.put("member", member);
		int check = dao.checkNotRecom(map);
		if(check > 0) throw new Exception("이미 비추천한 사용자");
		else {
			dao.insertNotRecom(map);
			dao.updateNotRecom(idx);
			result = dao.getNotRecom(idx);
		}
		
		return result;
	}
	
	public int deleteComment(String commentIdx,MemberDetails member) {
		int result = dao.deleteComment(member.getMemId(), commentIdx);
		return result;
	}
	
	public BoardListDto updateBoard(String boardIdx,String memId) {
		BoardListDto board = dao.updateBoard(boardIdx, memId);
		return board;
	}
	
	public int updateBoardOK(BoardListDto board,String memId) {
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("board", board);
		map.put("memId", memId);
		int result = dao.updateBoardOK(map);
		
		return result;
	}
	
	public List<BoardListDto> myBoard(PagingVO paging, String memId){
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("paging", paging);
		map.put("memId", memId);
		List<BoardListDto> board = dao.myBoard(map);
		return board;
	}


}
