package com.mds.prj.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mds.prj.dto.MemberDetails;

@Mapper
public interface IMemberDao {
	ArrayList<MemberDetails> list();
	MemberDetails findById(@Param("memId") String memId);
	MemberDetails findByName(@Param("memName") String memName);
	int joinMember(@Param("memberDt") MemberDetails memberDt);
}
