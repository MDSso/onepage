package com.mds.prj.dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class MemberDetails implements UserDetails{
	
	@Pattern(regexp="^[a-zA-Z0-9]{3,15}$",message="¾ÆÀÌµð ÀÔ·Â Á¤º¸¸¦ È®ÀÎÇØ ÁÖ¼¼¿ä.")
	private String memId;
	
	@Pattern(regexp="^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",message="ºñ¹Ð¹øÈ£ ÀÔ·Â Á¤º¸¸¦ È®ÀÎÇØ ÁÖ¼¼¿ä.")
	private String memPw;
	
	@Pattern(regexp = "[°¡-ÆRa-zA-Z0-9]{3,7}$")
	private String memName;
	
	private String AUTHORITY;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(AUTHORITY));
        return auth;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.memPw;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.memId;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getMemId() {
		return memId;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}

	public String getMemPw() {
		return memPw;
	}

	public void setMemPw(String memPw) {
		this.memPw = memPw;
	}

	public String getAUTHORITY() {
		return AUTHORITY;
	}

	public void setAUTHORITY(String aUTHORITY) {
		AUTHORITY = aUTHORITY;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

}
