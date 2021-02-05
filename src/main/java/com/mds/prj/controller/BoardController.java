package com.mds.prj.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mds.prj.dto.BoardCommentDto;
import com.mds.prj.dto.BoardImgDto;
import com.mds.prj.dto.BoardListDto;
import com.mds.prj.dto.MemberDetails;
import com.mds.prj.dto.PagingVO;
import com.mds.prj.service.BoardService;

@Controller
public class BoardController {
	
	Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	BoardService service;
	
	@Autowired
	ResourceLoader resourceLoader;

	@RequestMapping("/list")
	public String boardList() {
		
		return "/board/list";
	}
	
	@RequestMapping("/boardForm")
	public String boardForm() {
		return "/board/boardForm";
	}
	
	@RequestMapping("/board/insert")
	public String boardInsert(@Valid BoardListDto boardListDto,@AuthenticationPrincipal MemberDetails member) {
		boardListDto.setMemName(member.getMemName());
		boardListDto.setMemId(member.getMemId());
		int result = service.insertBoard(boardListDto);
		if(result == 1) {
			logger.info("�Խñ� �ۼ� ����");
			return "redirect:/list";
		}
		else {
			logger.info("�Խñ� �ۼ� ����");
			return "redirect:/cerror";
		}
	}
	
	@PostMapping("/saveimg")
	public ResponseEntity<?> imgUpload(@RequestParam("file") MultipartFile file){
		logger.info("�̹��� ���� ����");
		try {
			BoardImgDto imgdto = service.uploadImg(file);
			return ResponseEntity.ok().body("/boardimg/" + imgdto.getId());

		}catch (Exception e) {
			logger.info("���� ���ε� �������� ������ �߻��Ͽ���");
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/boardimg/{id}")
	public ResponseEntity<?> imgload(@PathVariable String id){
		try {
			BoardImgDto imgDto = service.loadImg(id);
			Resource resource = resourceLoader.getResource("file:" + imgDto.getFilePath());
			logger.info("�̹��� ��û ���� ����");
			return ResponseEntity.ok().body(resource);
		} catch (Exception e) {
			logger.info("�̹��� ��û ���� ����");
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}		
	}
	
	
	@PostMapping("/board/delete")
	public ResponseEntity<?> deleteBoard(@RequestParam("boardIdx") String boardIdx,@AuthenticationPrincipal MemberDetails member){
		logger.info("�Խñ� ���� ��û memId : " + member.getMemId());
		logger.info("�Խñ� ���� ��û boardIdx : " + boardIdx);
		int result = service.deleteBoard(member.getMemId(), boardIdx);
		if(result == 1) {
			logger.info("�Խñ� ���� ����");
			return ResponseEntity.ok().body("success");
		}
		else {
			logger.info("�Խñ� ���� ����");
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/board/update")
	public ResponseEntity<?> updateBoard(@RequestParam("boardIdx") String boardIdx,@AuthenticationPrincipal MemberDetails member){
		logger.info("�Խñ� ���� ��û(�������) memId : " + member.getMemId());
		logger.info("�Խñ� ���� ��û(�������) boardIdx : " + boardIdx);
		BoardListDto board = service.updateBoard(boardIdx, member.getMemId());
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String result =  gson.toJson(board);
		if(board!=null) {
			logger.info("�Խñ� ���� ��û(�������) ����");
			return ResponseEntity.ok().body(result);
		}
		else {
			logger.info("�Խñ� ���� ��û(�������) ����");
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/board/updateOK")
	public String updateBoardOK(BoardListDto board, @AuthenticationPrincipal MemberDetails member) {
		logger.info("�Խñ� ���� ��û(��������) memId : " + member.getMemId());
		logger.info("�Խñ� ���� ��û(��������) boardIdx : " + board.getIdx());
		int result = service.updateBoardOK(board, member.getMemId());
		if(result == 1) {
			logger.info("�Խñ� ���� ��û(��������) ����");
			return "redirect:/list";
		}
		else {
			logger.info("�Խñ� ���� ��û(��������) ����");
			return "redirect:/cerror";
		}
	}
	
	
	@RequestMapping("/boardlist")
	public ResponseEntity<?> getboardList(@RequestParam("nowPage") String nowPage, @RequestParam("category") String category, @AuthenticationPrincipal MemberDetails member){
		logger.info("���� ����Ʈ ��û nowPage : " + nowPage);
		logger.info("���� ����Ʈ ��û category : " + category);
		PagingVO paging = new PagingVO(nowPage);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<BoardListDto> bdDto= service.boardList(paging,category,member);
		String result = gson.toJson(bdDto); 
		if(bdDto.size() == 0) {
			logger.info("�ҷ��� ���� ����Ʈ�� ����");
			return ResponseEntity.badRequest().build();
		}
		else {
			logger.info("���� ����Ʈ ���� ����");
			return ResponseEntity.ok().body(result);
		}
	}
	
	@RequestMapping("/nextboard")
	public  ResponseEntity<?> nextBoard(@RequestParam("nowPage") String nowPage,@RequestParam("idxSet") String idxSet,@RequestParam("category") String category, @AuthenticationPrincipal MemberDetails member){
		logger.info("���� ���� ����Ʈ ��û nowPage : " + nowPage);
		logger.info("���� ���� ����Ʈ ��û category : " + category);
		PagingVO paging = new PagingVO(nowPage);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<BoardListDto> bdDto= service.nextBoard(paging, idxSet, category, member);
		String result = gson.toJson(bdDto);
		if(bdDto.size() == 0) {
			logger.info("�ҷ��� ���� ���� ����Ʈ�� ����");
			return ResponseEntity.badRequest().build();
		}
		else {
			logger.info("���� ���� ����Ʈ ���� ����");
			return ResponseEntity.ok().body(result);
		}
		
	}
	
	@PostMapping("/insertcomment")
	public ResponseEntity<?> insertComment(@AuthenticationPrincipal MemberDetails member,BoardCommentDto commentDto) {
		try {
			logger.info("��� �Է� �õ� memId : " + member.getMemId());
			commentDto.setMemId(member.getMemId());
			commentDto.setMemName(member.getMemName());
			service.insertComment(commentDto);
			return ResponseEntity.ok().body("success");
		} catch (Exception e) {
			logger.info("��� �Է� ����");
			return ResponseEntity.badRequest().build();
		}
	}
	
	@RequestMapping("/getcomments")
	public ResponseEntity<?> getComments(@RequestParam("boardIdx") String boardIdx,@RequestParam("nowPage") String nowPage,@AuthenticationPrincipal MemberDetails member){
		logger.info("��� �ҷ����� boardIdx : " + boardIdx);
		PagingVO paging = new PagingVO(nowPage);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<BoardCommentDto> comments = service.getComments(boardIdx,paging,member);
		String result = gson.toJson(comments);
		return ResponseEntity.ok().body(result);
	}
	
	@RequestMapping("/nextcomments")
	public ResponseEntity<?> nextComments(@RequestParam("boardIdx") String boardIdx,@RequestParam("nowPage") String nowPage, @RequestParam("commentIdx") String commentIdx,@AuthenticationPrincipal MemberDetails member){
		logger.info("���� ��� �ҷ����� boardIdx : " + boardIdx);
		PagingVO paging = new PagingVO(nowPage);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<BoardCommentDto> comments = service.nextComments(boardIdx, commentIdx, paging, member);
		String result = gson.toJson(comments);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/recommend")
	public ResponseEntity<?> recommend(@RequestParam("idx") String idx, @AuthenticationPrincipal MemberDetails member){
		logger.info("�Խñ� ��õ idx : " + idx);
		logger.info("�Խñ� ��õ user : " + member.getMemId());
		String result;	
		try {
			result = service.recommend(idx, member);
			logger.info("�Խñ��� ��õ�Ϸ�");
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			logger.info("�̹� �ش� �Խñ��� ��õ��"); 
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/notrecommend")
	public ResponseEntity<?> notRecommend(@RequestParam("idx") String idx, @AuthenticationPrincipal MemberDetails member){
		logger.info("�Խñ� ����õ idx : " + idx);
		logger.info("�Խñ� ����õ user : " + member.getMemId());
		String result;
		
		try {
			result = service.notRecommend(idx, member);
			logger.info("�Խñ��� ��õ�Ϸ�");
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("�̹� �ش� �Խñ��� ��õ��"); 
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/comment/delete")
	public ResponseEntity<?> deleteComment(@RequestParam("commentIdx") String commentIdx,@AuthenticationPrincipal MemberDetails member){
		logger.info("��� ���� ��û memId : " + member.getMemId());
		logger.info("��� ���� ��û commentIdx : " + commentIdx);
		int result = service.deleteComment(commentIdx, member);
		if(result == 1) return ResponseEntity.ok().body("success");
		else return ResponseEntity.badRequest().build();
	}
	
	@RequestMapping("/list/myboard")
	public ResponseEntity<?> myBoard(@RequestParam("nowPage") String nowPage,@AuthenticationPrincipal MemberDetails member){
		logger.info("�� �Խñ� ����Ʈ ��û ��û�� : " + member.getMemId());
		logger.info("�� �Խñ� ����Ʈ ��û page : " + nowPage);
		PagingVO paging = new PagingVO(nowPage);
		List<BoardListDto> board = service.myBoard(paging, member.getMemId());
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String result = gson.toJson(board);
		if(board.size() != 0) return ResponseEntity.ok().body(result);
		else {
			logger.info("���̻� �ҷ��� �� �Խñ� ����Ʈ�� ����");
			return ResponseEntity.badRequest().build();
		}
	}
}
