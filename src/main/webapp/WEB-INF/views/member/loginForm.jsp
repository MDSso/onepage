<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
<link rel="stylesheet" type="text/css" href="/css/loginForm.css" />
</head>
<body>
<div class="container">
	<form action="/login" method="POST">
		<div class="title"><img src="/img/logo_img.png"></div>
		<div class="inputcontainer">
			<div class="inputbox">
				<input name="memId" placeholder="아이디" >
				<div class="highlight"></div>
			</div>
		</div>
		
		<div class="inputcontainer">
			<div class="inputbox">
				<input name="memPw" placeholder="비밀번호" >
				<div class="highlight"></div>
			</div>
		</div>
		
		<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
		<div class="button-area">
			<button>로그인</button>
		</div>
	</form>
	<div class="join"><a href="/join">가입하기</a></div>
	<c:if test="${not empty errorMessage}">
		<div class="errorMessage"> ${errorMessage} </div>
	</c:if>
</div>
</body>
</html>