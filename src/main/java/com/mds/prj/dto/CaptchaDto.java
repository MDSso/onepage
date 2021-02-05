package com.mds.prj.dto;

import lombok.Data;

@Data
public class CaptchaDto {
	private String capKey;
	private String capInput;
}
