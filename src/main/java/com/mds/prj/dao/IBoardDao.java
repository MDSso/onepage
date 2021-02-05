package com.mds.prj.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mds.prj.dto.BoardCommentDto;
import com.mds.prj.dto.BoardImgDto;
import com.mds.prj.dto.BoardListDto;

@Mapper
public interface IBoardDao {
	List<BoardListDto> boardList(HashMap<String, Object> map);
	List<BoardListDto> nextBoard(HashMap<String, Object> map);
	int deleteBoard(@Param("memId") String memId,@Param("boardIdx") String boardIdx);
	
	List<BoardListDto> myBoard(HashMap<String, Object> map);
	
	int insertBoard(@Param("boardListDto") BoardListDto boardListDto);
	int insertBoardImg(@Param("ImgDto") BoardImgDto boardImgDto);
	BoardImgDto findByFileId(@Param("id") String fileId);
	int insertComment(@Param("commentDto") BoardCommentDto commentDto);
	BoardListDto findByIdx(@Param("idx") String boardIdx);
	
	BoardListDto updateBoard(@Param("boardIdx") String boardIdx,@Param("memId") String memId);
	int updateBoardOK(HashMap<String, Object> map);
	
	// 초기 댓글 불러오기/새로고침	
	List<BoardCommentDto> findComments(HashMap<String, Object> map);
	// 다음 댓글 불러오기
	List<BoardCommentDto> nextComments(HashMap<String, Object> map);
	// 댓글 삭제
	int deleteComment(@Param("memId") String memId,@Param("commentIdx") String commentIdx);
	
	/* 추천 */
	int checkRecom(HashMap<String, Object> map);
	int insertRecom(HashMap<String, Object> map);
	int updateRecom(String idx);
	String getRecom(String idx);
	
	/* 비추천 */
	int checkNotRecom(HashMap<String, Object> map);
	int insertNotRecom(HashMap<String, Object> map);
	int updateNotRecom(String idx);
	String getNotRecom(String idx);
}