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
			logger.info("게시글 작성 성공");
			return "redirect:/list";
		}
		else {
			logger.info("게시글 작성 실패");
			return "redirect:/cerror";
		}
	}
	
	@PostMapping("/saveimg")
	public ResponseEntity<?> imgUpload(@RequestParam("file") MultipartFile file){
		logger.info("이미지 저장 접수");
		try {
			BoardImgDto imgdto = service.uploadImg(file);
			return ResponseEntity.ok().body("/boardimg/" + imgdto.getId());

		}catch (Exception e) {
			logger.info("파일 업로드 과정에서 문제가 발생하였음");
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/boardimg/{id}")
	public ResponseEntity<?> imgload(@PathVariable String id){
		try {
			BoardImgDto imgDto = service.loadImg(id);
			Resource resource = resourceLoader.getResource("file:" + imgDto.getFilePath());
			logger.info("이미지 요청 응답 성공");
			return ResponseEntity.ok().body(resource);
		} catch (Exception e) {
			logger.info("이미지 요청 응답 실패");
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}		
	}
	
	
	@PostMapping("/board/delete")
	public ResponseEntity<?> deleteBoard(@RequestParam("boardIdx") String boardIdx,@AuthenticationPrincipal MemberDetails member){
		logger.info("게시글 삭제 요청 memId : " + member.getMemId());
		logger.info("게시글 삭제 요청 boardIdx : " + boardIdx);
		int result = service.deleteBoard(member.getMemId(), boardIdx);
		if(result == 1) {
			logger.info("게시글 삭제 성공");
			return ResponseEntity.ok().body("success");
		}
		else {
			logger.info("게시글 삭제 실패");
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/board/update")
	public ResponseEntity<?> updateBoard(@RequestParam("boardIdx") String boardIdx,@AuthenticationPrincipal MemberDetails member){
		logger.info("게시글 수정 요청(정보취득) memId : " + member.getMemId());
		logger.info("게시글 수정 요청(정보취득) boardIdx : " + boardIdx);
		BoardListDto board = service.updateBoard(boardIdx, member.getMemId());
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String result =  gson.toJson(board);
		if(board!=null) {
			logger.info("게시글 수정 요청(정보취득) 성공");
			return ResponseEntity.ok().body(result);
		}
		else {
			logger.info("게시글 수정 요청(정보취득) 실패");
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/board/updateOK")
	public String updateBoardOK(BoardListDto board, @AuthenticationPrincipal MemberDetails member) {
		logger.info("게시글 수정 요청(수정적용) memId : " + member.getMemId());
		logger.info("게시글 수정 요청(수정적용) boardIdx : " + board.getIdx());
		int result = service.updateBoardOK(board, member.getMemId());
		if(result == 1) {
			logger.info("게시글 수정 요청(수정적용) 성공");
			return "redirect:/list";
		}
		else {
			logger.info("게시글 수정 요청(수정적용) 실패");
			return "redirect:/cerror";
		}
	}
	
	
	@RequestMapping("/boardlist")
	public ResponseEntity<?> getboardList(@RequestParam("nowPage") String nowPage, @RequestParam("category") String category, @AuthenticationPrincipal MemberDetails member){
		logger.info("보드 리스트 요청 nowPage : " + nowPage);
		logger.info("보드 리스트 요청 category : " + category);
		PagingVO paging = new PagingVO(nowPage);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<BoardListDto> bdDto= service.boardList(paging,category,member);
		String result = gson.toJson(bdDto); 
		if(bdDto.size() == 0) {
			logger.info("불러올 보드 리스트가 없음");
			return ResponseEntity.badRequest().build();
		}
		else {
			logger.info("보드 리스트 응답 성공");
			return ResponseEntity.ok().body(result);
		}
	}
	
	@RequestMapping("/nextboard")
	public  ResponseEntity<?> nextBoard(@RequestParam("nowPage") String nowPage,@RequestParam("idxSet") String idxSet,@RequestParam("category") String category, @AuthenticationPrincipal MemberDetails member){
		logger.info("다음 보드 리스트 요청 nowPage : " + nowPage);
		logger.info("다음 보드 리스트 요청 category : " + category);
		PagingVO paging = new PagingVO(nowPage);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<BoardListDto> bdDto= service.nextBoard(paging, idxSet, category, member);
		String result = gson.toJson(bdDto);
		if(bdDto.size() == 0) {
			logger.info("불러올 다음 보드 리스트가 없음");
			return ResponseEntity.badRequest().build();
		}
		else {
			logger.info("다음 보드 리스트 응답 성공");
			return ResponseEntity.ok().body(result);
		}
		
	}
	
	@PostMapping("/insertcomment")
	public ResponseEntity<?> insertComment(@AuthenticationPrincipal MemberDetails member,BoardCommentDto commentDto) {
		try {
			logger.info("댓글 입력 시도 memId : " + member.getMemId());
			commentDto.setMemId(member.getMemId());
			commentDto.setMemName(member.getMemName());
			service.insertComment(commentDto);
			return ResponseEntity.ok().body("success");
		} catch (Exception e) {
			logger.info("댓글 입력 실패");
			return ResponseEntity.badRequest().build();
		}
	}
	
	@RequestMapping("/getcomments")
	public ResponseEntity<?> getComments(@RequestParam("boardIdx") String boardIdx,@RequestParam("nowPage") String nowPage,@AuthenticationPrincipal MemberDetails member){
		logger.info("댓글 불러오기 boardIdx : " + boardIdx);
		PagingVO paging = new PagingVO(nowPage);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<BoardCommentDto> comments = service.getComments(boardIdx,paging,member);
		String result = gson.toJson(comments);
		return ResponseEntity.ok().body(result);
	}
	
	@RequestMapping("/nextcomments")
	public ResponseEntity<?> nextComments(@RequestParam("boardIdx") String boardIdx,@RequestParam("nowPage") String nowPage, @RequestParam("commentIdx") String commentIdx,@AuthenticationPrincipal MemberDetails member){
		logger.info("다음 댓글 불러오기 boardIdx : " + boardIdx);
		PagingVO paging = new PagingVO(nowPage);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<BoardCommentDto> comments = service.nextComments(boardIdx, commentIdx, paging, member);
		String result = gson.toJson(comments);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("/recommend")
	public ResponseEntity<?> recommend(@RequestParam("idx") String idx, @AuthenticationPrincipal MemberDetails member){
		logger.info("게시글 추천 idx : " + idx);
		logger.info("게시글 추천 user : " + member.getMemId());
		String result;	
		try {
			result = service.recommend(idx, member);
			logger.info("게시글을 추천완료");
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			logger.info("이미 해당 게시글을 추천함"); 
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/notrecommend")
	public ResponseEntity<?> notRecommend(@RequestParam("idx") String idx, @AuthenticationPrincipal MemberDetails member){
		logger.info("게시글 비추천 idx : " + idx);
		logger.info("게시글 비추천 user : " + member.getMemId());
		String result;
		
		try {
			result = service.notRecommend(idx, member);
			logger.info("게시글을 추천완료");
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("이미 해당 게시글을 추천함"); 
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/comment/delete")
	public ResponseEntity<?> deleteComment(@RequestParam("commentIdx") String commentIdx,@AuthenticationPrincipal MemberDetails member){
		logger.info("댓글 삭제 요청 memId : " + member.getMemId());
		logger.info("댓글 삭제 요청 commentIdx : " + commentIdx);
		int result = service.deleteComment(commentIdx, member);
		if(result == 1) return ResponseEntity.ok().body("success");
		else return ResponseEntity.badRequest().build();
	}
	
	@RequestMapping("/list/myboard")
	public ResponseEntity<?> myBoard(@RequestParam("nowPage") String nowPage,@AuthenticationPrincipal MemberDetails member){
		logger.info("내 게시글 리스트 요청 요청자 : " + member.getMemId());
		logger.info("내 게시글 리스트 요청 page : " + nowPage);
		PagingVO paging = new PagingVO(nowPage);
		List<BoardListDto> board = service.myBoard(paging, member.getMemId());
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String result = gson.toJson(board);
		if(board.size() != 0) return ResponseEntity.ok().body(result);
		else {
			logger.info("더이상 불러올 내 게시글 리스트가 없음");
			return ResponseEntity.badRequest().build();
		}
	}
}
