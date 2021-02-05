package com.mds.prj.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class BoardImgDto {
	
	private String id;
	private String contentType;
	private String originalName;
	private String saveName;
	private String filePath;
	private String fileSize;
	private Date cDate;
	
}
