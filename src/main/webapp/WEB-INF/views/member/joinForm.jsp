<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/css/joinForm.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<script type="text/javascript">
	$(document).ready(function() {
		
		$.getCapImg = function(){
		$.ajax({
			url : "/capt",
			dataType:"json",
			success : function(data) {
				$("#key").val(data.key);
				$("#test01").html("<img id='capimg' src='/captimg/"+data.imgname+"'>");
			}
		});
		};
		$.getCapImg();
		
		$("#joinOK").on("click",function(){
			var inputCaptcha = $("#captchaform").serialize();
			$.ajax({
				url : "/captOK",
				data : inputCaptcha,
				dataType:"text",
				success : function(data) {
					if(data != ""){
						console.log(data);
						$("#captoken").val(data);
						$("#joinform").submit();
					}
					else {
						$(".caperror").text("자동가입 방지 코드를 정확히 입력해 주세요.");
						$.getCapImg();
					};
				}
			});
		});
		

		$("#capref").click(function(){
			$.getCapImg();
		});

		var checkId = false;
		var checkName = false;
		var checkPw = false;
		var checkPwc = false;
		var re = /^.*(?=^.{8,20}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
		var re1 = /^[a-zA-Z0-9]{3,15}$/;
		var re2 = /^[가-힣a-zA-Z0-9]{3,7}$/;
		
		$.checkVal = function(){
			if(checkId==true && checkPw==true && checkPwc==true && checkName==true) $("#joinOK").prop("disabled", false).css("background-color","#A6A6A6").css("cursor","pointer");
			else $("#joinOK").prop("disabled", true).css("background","#EAEAEA").css("cursor","");
		};

		$("#memId").blur(function(){
			$.ajax({
				url : "/idduplicate",
				type:"get",
				data: "memId=" + $("#memId").val() ,
				dataType:"text",
				success : function(data) {
					console.log(data);
					if(data == "duplicate") {
						$("#duplicate").css("color","red").text("이미 사용중인 아이디 입니다.");
						$("#Idicon").attr("class","far fa-times-circle");
						checkId = false;
					}
					else if(data == "success" && re1.test($("#memId").val())){
						 $("#duplicate").css("color","#6799FF").text("사용 가능한 아이디 입니다.");
						 $("#Idicon").attr("class","far fa-check-circle");
						 checkId= true;
					}
				}
			});
			
			if(!re1.test($("#memId").val())){
				$("#duplicate").css("color","red").text("영문or숫자 3자 이상 입력해 주세요.");
				$("#Idicon").attr("class","far fa-times-circle");
				checkId = false;
			}
			$.checkVal();
		});

		$("#memName").blur(function(){
			$.ajax({
				url : "/nameduplicate",
				type:"get",
				data: "memName=" + $("#memName").val() ,
				dataType:"text",
				success : function(data) {
					console.log(data);
					if(data == "duplicate") {
						$("#duplicate2").css("color","red").text("이미 사용중인 닉네임 입니다.");
						$("#Nameicon").attr("class","far fa-times-circle");
						checkName = false;
					}
					else if(data == "success" && re2.test($("#memName").val())){
						 $("#duplicate2").css("color","#6799FF").text("사용 가능한 닉네임 입니다.");
						 $("#Nameicon").attr("class","far fa-check-circle");
						 checkName = true;
					}
				}
			});
			
			if(!re2.test($("#memName").val())){
				$("#duplicate2").css("color","red").text("영문,숫자,한글 3~6자로 입력해 주세요.");
				$("#Nameicon").attr("class","far fa-times-circle");
				checkName = false;
			}
			$.checkVal();
		});

		

		$("#memPw").keyup(function(){
			if(!re.test($("#memPw").val())) {
				$("#Pwicon").attr("class","far fa-times-circle"); 
				checkPw = false;
			}
			if(re.test($("#memPw").val())){
				$("#Pwicon").attr("class","far fa-check-circle");
				checkPw = true;
			}
			if(!re.test($("#memPwc").val()) || $("#memPw").val() != $("#memPwc").val()){
				$("#Pwiconc").attr("class","far fa-times-circle"); 
				checkPwc = false;
			}
			if(re.test($("#memPwc").val()) && $("#memPw").val() == $("#memPwc").val()){
				$("#Pwiconc").attr("class","far fa-check-circle");
				checkPwc = true;
			}
			$.checkVal();
			});

		$("#memPwc").keyup(function(){
			if($("#memPw").val() != $("#memPwc").val()) {
				$("#Pwiconc").attr("class","far fa-times-circle"); 
				checkPwc = false;
			}
			if($("#memPw").val() == $("#memPwc").val() && re.test($("#memPwc").val())) {
				$("#Pwiconc").attr("class","far fa-check-circle");
				checkPwc = true;
			}
			$.checkVal();
			}); 
	});
</script>
<body>
<div class="container">
<form action="/joinOK" id="joinform" method="POST" >
	<div class="title"><img src="/img/logo_img.png"></div>
	
	<div class="inputcontainer">
		<div class="inputbox">
			<input id="memId" name="memId" placeholder="아이디 3자이상 영문 or 숫자" >
			<div class="checkIcon"><i id="Idicon"></i></div>
			<div class="highlight"></div>
		</div>
		<div class="duplicate" id="duplicate"></div>
	</div>
	
	<div class="inputcontainer">
		<div class="inputbox">
			<input id="memName" name="memName" placeholder="닉네임 3~6자 한글,영문,숫자만 가능" >
			<div class="checkIcon"><i id="Nameicon"></i></div>
			<div class="highlight"></div>
		</div>
		<div class="duplicate" id="duplicate2"></div>
	</div>
	
	<div class="inputcontainer">
		<div class="inputbox">
			<input type="password" id="memPw" name="memPw" placeholder="비밀번호 특수문자 + 숫자 + 문자 포함 8~20자">
			<div class="checkIcon"><i id="Pwicon"></i></div>
			<div class="highlight"></div>
		</div>
	</div>
	
	<div class="inputcontainer">
		<div class="inputbox">
			<input type="password" id="memPwc" placeholder="비밀번호 확인">
			<div class="checkIcon"><i id="Pwiconc"></i></div>
			<div class="highlight"></div>
		</div>
	</div>
	<input id="captoken" name="token" type="hidden">
</form>
<form id="captchaform">
	<div class="inputcontainer">
		<div class="inputbox">
				<div id="test01">	</div>
				<input type="hidden" id="key" name="capKey">
				<input name="capInput" placeholder="자동가입 방지 코드 입력">
				<div class="checkIcon" id="capref"><i id="Idicon" class="fas fa-redo-alt"></i></div>
				<div class="highlight"></div>
		</div>
		<span id="error" class="caperror"></span>
	</div>
	<div class="inputcontainer">
		<button type="button" id="joinOK" disabled="disabled">가입하기</button>
	</div>
</form>
</div>
</body>
</html>