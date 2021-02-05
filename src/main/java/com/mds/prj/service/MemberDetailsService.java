package com.mds.prj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mds.prj.dao.IMemberDao;
import com.mds.prj.dto.MemberDetails;

@Service
public class MemberDetailsService implements UserDetailsService{

	@Autowired
	IMemberDao dao;
	
	public int joinMember(MemberDetails memberDt){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		memberDt.setAUTHORITY("USER");
		memberDt.setMemPw(passwordEncoder.encode(memberDt.getMemPw()));
		
		return dao.joinMember(memberDt); 
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String memId) throws UsernameNotFoundException {
		MemberDetails member = dao.findById(memId);
		if(member == null) {
			throw new UsernameNotFoundException("User " + memId + " Not Found!");
		}
	
		return member;
	}

}
