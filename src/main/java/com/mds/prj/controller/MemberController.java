package com.mds.prj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mds.prj.config.ApiExamCaptchaImage;
import com.mds.prj.config.ApiExamCaptchaNkey;
import com.mds.prj.config.ApiExamCaptchaNkeyResult;
import com.mds.prj.dao.IMemberDao;
import com.mds.prj.dto.CaptchaDto;
import com.mds.prj.dto.MemberDetails;
import com.mds.prj.service.BoardService;
import com.mds.prj.service.MemberDetailsService;

@Controller
public class MemberController {
	
	Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	IMemberDao dao;
	
	@Autowired
	MemberDetailsService service;
	
	@Autowired
	BoardService service2;
	
	@Autowired
	ApiExamCaptchaNkey apikey;
	
	@Autowired
	ApiExamCaptchaImage apiimg;
	
	@Autowired
	ApiExamCaptchaNkeyResult keyresult;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@ModelAttribute("cp")
	public String getContextPath(HttpServletRequest request) {
		return request.getContextPath();
	}
	
	@RequestMapping("/")
	public String root(@AuthenticationPrincipal MemberDetails member) {
		if(member != null) {
			return "redirect:/list";
		}
		
		return "/member/loginForm";
	}
	
	@RequestMapping("/nav")
	public String memberlist() {		
		
		return "/common/navigation";
	}
	
	@RequestMapping("/cerror")
	public @ResponseBody String cerror() {
		
		return "�߸��� �����Դϴ�.";
	}
	
	@RequestMapping("/join")
	public String joinForm() {
		
		return "/member/joinForm";
	}
	
	@RequestMapping("/capt")
	public @ResponseBody String capt() {
		
		apiimg.setKey(apikey.getKey());
		String imgpath = apiimg.getimg();
		
		return imgpath;
	}
	
	@RequestMapping("/captOK")
	public @ResponseBody String captOK(CaptchaDto captdto,HttpSession session) {
		
		String result = keyresult.getResult(captdto.getCapKey(),captdto.getCapInput());
		if(result != null) {logger.info("���� ��ū �߱� : " + result); session.setAttribute(result, result);}
		else logger.info("���� ��ū �߱� ����");
		
		return result;
		
	}
	
	@GetMapping("/captimg/{id}")
	public ResponseEntity<?> imgload(@PathVariable String id){
		System.out.println(id);
		try {
			Resource resource = resourceLoader.getResource("file:" +  "C:\\boardimg\\" + id);
			logger.info("�̹��� ��û ���� ����");
			return ResponseEntity.ok().body(resource);
		} catch (Exception e) {
			logger.info("�̹��� ��û ���� ����");
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}		
	}
	
	@RequestMapping("/idduplicate")
	public @ResponseBody String duplicateCheck(@RequestParam String memId) {
		logger.info("���̵� �ߺ�üũ ��û : " + memId);
		MemberDetails mem = dao.findById(memId);
		if(mem != null) return "duplicate";
		else return "success";
	}
	
	@RequestMapping("/nameduplicate")
	public @ResponseBody String duplicateCheckNmae(@RequestParam String memName) {
		logger.info("�г��� �ߺ�üũ ��û : " + memName);
		MemberDetails mem = dao.findByName(memName);
		if(mem != null) return "duplicate";
		else return "success";
	}
	
	@PostMapping("/joinOK")
	public String joinOK(@Valid MemberDetails memberDt, Errors error,HttpSession session,@RequestParam(value="token") String token) {
		
		logger.info("�Էµ� ��ū �� : " + token);
		logger.info("��� �� ��ū ��: " + session.getAttribute(token));
		if(session.getAttribute(token) == null) {logger.info("��ū���� ���Կ�û"); return "redirect:/cerror";} 
		else session.removeAttribute(token);
		if(session.getAttribute(token) == null) logger.info("��� �� ��ū �� ���� Ȯ�� : " + session.getAttribute(token));
		MemberDetails mem1 = dao.findById(memberDt.getMemId());
		MemberDetails mem2 = dao.findByName(memberDt.getMemName());
		
		if(mem1 != null) {
			logger.info("�ߺ��� ���̵�� ���Կ�û");
			return "redirect:/cerror";
		}
		
		if(mem2 != null) {
			logger.info("�ߺ��� �г������� ���Կ�û");
			return "redirect:/cerror";
		}
		
		if(error.hasErrors()) {
			logger.info("���Խİ� ��ġ���� �ʴ� �������� ���Կ�û");
			return "redirect:/cerror";
		}
		
		int result = service.joinMember(memberDt);
		if(result == 1) logger.info("ȸ������ ����");
		return "redirect:/";
	}
	
	@RequestMapping("/member/check")
	public ResponseEntity<?> insertComment(@AuthenticationPrincipal MemberDetails member) {
		if(member == null) {
			logger.info("����Ʈ ������ ���� : �α��� ���� ����");
			return ResponseEntity.badRequest().build();
		}
		else {
			logger.info("����Ʈ ������ ���� : �α���");
			return ResponseEntity.ok().body(member.getMemName());
		}
	}
}
