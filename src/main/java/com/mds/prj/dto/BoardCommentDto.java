package com.mds.prj.dto;

import lombok.Data;

@Data
public class BoardCommentDto {
	private String boardIdx;
	private String commentIdx;
	private String cContents;
	private String memName;
	private String memId;
	private String ownerCheck;
}
