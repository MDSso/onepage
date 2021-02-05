package com.mds.prj.dto;

import lombok.Data;

@Data
public class PagingVO {
	private String nowPage;
	private int start;
	private int end;
	
	public PagingVO() {}
	
	public PagingVO(String nowPage) {
		calcStartEnd(nowPage);
	}
	
	public void calcStartEnd(String nowPage) {
		int page = Integer.parseInt(nowPage);
		if(page==1) {
			setStart(0); 
			setEnd(10); }
		else {
			setStart((page - 1)*10);
			setEnd(page*10);
		}
	}
	
}
