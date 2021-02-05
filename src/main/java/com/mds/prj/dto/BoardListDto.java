package com.mds.prj.dto;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class BoardListDto {
	private String idx;
	private String memName;
	private String memId;
	@Pattern(regexp="^.{2,200}$",message="������ 2~200���ڱ��� �Է����ּ���.")
	private String title;
	@Pattern(regexp="^.{0,1500}$",message="������ 1500�ڱ��� �Է� �����մϴ�.")
	private String allContent;
	private String recomRate;
	private String notRecomRate;
	private String cTime;
	private String category;
	private String ownerCheck;
}
