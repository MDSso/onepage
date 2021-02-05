package com.mds.prj.dto;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class BoardListDto {
	private String idx;
	private String memName;
	private String memId;
	@Pattern(regexp="^.{2,200}$",message="제목은 2~200글자까지 입력해주세요.")
	private String title;
	@Pattern(regexp="^.{0,1500}$",message="내용은 1500자까지 입력 가능합니다.")
	private String allContent;
	private String recomRate;
	private String notRecomRate;
	private String cTime;
	private String category;
	private String ownerCheck;
}
