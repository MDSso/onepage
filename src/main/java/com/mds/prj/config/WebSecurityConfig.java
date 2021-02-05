package com.mds.prj.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustumAuthFailureHandler custumAuthFailureHandler; 
	
	
	@ModelAttribute("cp")
	public String getContextPath(HttpServletRequest request) {
		return request.getContextPath();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/css/**","/img/**","/join","/capt","/captOK","/joinOK","/loginForm","/cerror","/idduplicate","/list","/boardForm","/js/**","/test"
					,"/nav","/boardlist","/getcomments","/nameduplicate","/boardimg/**","/nextboard","/nextcomments","/member/check","/captimg/**").permitAll()
			.antMatchers("/member/**").hasAnyRole("USER","ADMIN")
			.anyRequest().authenticated();
		
		http.formLogin()
			.passwordParameter("memPw")
			.usernameParameter("memId")
			.loginPage("/loginForm")
			.loginProcessingUrl("/login")
			.defaultSuccessUrl("/list")
			.failureHandler(custumAuthFailureHandler)
			.permitAll();
			
		http.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/")
			.permitAll();
		http
        .csrf().disable();
		
	}
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
