<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
<link rel="stylesheet" href="/css/list.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/egjs-jquery-transform/2.0.0/transform.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>
<style type="text/css">
/* =====================네비게이션================================== */
.nav{
	align-items: center;
	word-break:break-all;
	box-sizing: border-box;
	background:white;
	position: fixed;
 	top: 0;
  	width: 100%;
  	left: 0;
 	right: 0;
	padding: 10px;
	border-bottom: 1px solid #ccc;
	display: flex;
	justify-content: space-between;
	box-shadow: 1px 0px 7px #EAEAEA;
	z-index: 2;
}

.nav-logout{
	cursor: pointer;
	padding :5px 10px;
	border-radius: 5px;
}

.nav-logout:hover{
	background: #EAEAEA;
	transition:0.5s;
}

.nav-list{
	font-size: 1rem;
	display: flex;
}

.nav-list > div{
	cursor:pointer;
	border-radius:5px;
	padding: 5px 10px;
	margin: 0px 10px;
}

.my-board{
	display: none;
}

.login{
	border-radius:5px;
	padding: 5px 10px;
	font-size: 1rem;
	cursor:pointer;
}

.login:hover{
	background: #EAEAEA;
	transition:0.5s;
}

.nav-list > div:hover{
	background: #EAEAEA;
	transition:0.5s;
}

/* =====================네비 사이드 메뉴================================ */
.side-menu{
	z-index: 3;
	width : 19rem;
	border-right: 1px solid #ccc;
	background: white;
	height: 100%;
	position: fixed;
	top:0;
	left: -19rem;
	box-shadow: 0px 1px 7px #EAEAEA;
}

.side-item{
	padding: 5px 10px;
	text-align:center;
	font-size: 1em;
	border-bottom: 1px solid #ccc;
}

.side-item:hover{
	cursor:pointer;
	background: #EAEAEA;
	transition: 0.3s;
}

.side-close{
	color: #ccc;
	cursor:pointer;
	padding: 5px 10px;
	position: absolute;
	top: -1px;
	right: 10px;
}

.side-close:hover {
	color: black;
	transition:0.5s;
}
</style>
<style type="text/css">
/* =================라이브 상태=================== */

.live-status{
	width: 20rem;
	position:fixed;
	bottom:20%;
	right:10%;
	font-size:0.9rem;
}

.live-status > div{
	border:0.1rem solid #ccc;
	border-radius:5px;
	padding: 0.2rem 0.4rem;
	background: white;
	opacity: 0;
	margin-bottom: 0.2rem;
	cursor: pointer;
	transform: translateY(150px);
}

/* ======================탑 콘테이너 =====================  */

/* =================라이브 댓글 채팅 영역=================== */
.live-comment-chat{
	background: white;
	display : none;
	box-sizing:border-box;
	padding:5px 10px;
	max-width: 500px;
	margin: 0px auto;	
	border: 0.1rem solid #ccc;
	border-radius: 5px;
}

.live-comment-insert{
	display: flex;
}

.live-comment-area{
	border:0.1rem solid #ccc;
	border-radius: 5px;
	margin-bottom: 0.3rem;
	height: 8rem ;
	word-break: break-all;
	overflow: scroll ;
	overflow-x: hidden;
	padding: 3px 5px;
}
.live-comment-title{
	font-size: 0.7rem;
	display: flex;
	justify-content: space-between;
	padding: 3px 5px;
	border-bottom: 0.1rem solid #ccc;
}

.live-comment-list{
	display: flex;
	font-size: 0.7rem;
	padding: 5px 0px;
	word-break: break-all;
}

.live-comment-area::-webkit-scrollbar {
    width: 0.5rem;
   
 }
 
.live-comment-area::-webkit-scrollbar-thumb {
    background-color: #ccc;
    border-radius: 10px;
    background-clip: padding-box;
    border: 2px solid transparent;
}

.live-comment-content{
	padding: 3px 4px;
	border: 1px solid #ccc;
    border-radius: 4px;
    background: white;
    box-shadow: 2px 2px 2px 1px #ccc;
    margin: 0px 5px 0px 0px;
}

.live-comment-writer{
	padding: 3px 5px;
	flex: 0 0 8em;
}

.live-append-comment{
	opacity: 0;
	transform: translateX(-150px);
	border: 0.1rem solid #ccc;
	border-radius: 5px; 
	margin-bottom: 0.3rem;
	background: white;
	cursor: pointer;
}

.live-append-comment:hover{
	transition: 0.5s;
	background: #F6F6F6;
}

.live-comment-insert-title{
	font-size: 0.7rem;
    padding: 5px;   
}

.live-my-comment{
	opacity: 0;
	transform: translateX(-150px);
	border: 0.1rem solid #ccc;
    border-radius: 5px;
    margin-bottom: 0.3rem;
}

.live-my-comment-title{
	font-size: 0.7rem;
	padding: 5px;
    border-bottom: 0.1rem solid #ccc;
}

.live-my-comment-content{
    word-break: break-all;
	font-size: 0.7rem;
	display:flex;
	padding: 5px;
}

.live-my-comment-content > div:first-child{
	padding: 3px 0px;
	flex: 0 0 8em;
}
.note-modal-backdrop{
 z-index:3;
}

</style>
</head>
<script type="text/javascript">
/*============================ 게시글 작성 영역 ================================*/
$(document).ready(function() {
	//여기 아래 부분
	$('#summernote').summernote({
		height: 300,                 // 에디터 높이
		minHeight: 300,             // 최소 높이
		maxHeight: 300,             // 최대 높이
		focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부	
		placeholder: '최대 1500자까지 쓸 수 있습니다',	//placeholder 설정
		tabDisable: true,
		spellCheck: false,
		fontname:"'Noto Sans KR',sans-serif",
		toolbar: [
			['insert', ['picture', 'video']]	          
		],
		callbacks : { 
			onImageUpload : function(files, editor, welEditable) {       
				for (var i = 0; i < files.length; i++) {
					$.sendFile(files[i], this);
				}
			},
			onKeydown: function(e) {
		        if ($(".note-editable").text().length >= 1500){
		        	if (e.keyCode != 8 && !(e.keyCode >=37 && e.keyCode <=40) && e.keyCode != 46 && !(e.keyCode == 88 && e.ctrlKey) && !(e.keyCode == 67 && e.ctrlKey)){
                        e.preventDefault(); }
                    } 
		        }    
			}
		});


	$.sendFile = function(file, el) {
		var form_data = new FormData();
		form_data.append('file', file);
		$.ajax({                                                             
			data : form_data,
			type : "post",
			url : "/saveimg",
			cache : false,
			contentType : false,
			enctype : "multipart/form-data",                                  
			processData : false,
			success : function(url) {                                         
				$(el).summernote('insertImage', url, function ($image) {
					  $image.css('max-width', "100%");
					}); 
			}
		});
	};

	$('.writer-floating').click(function(){
		$(".btn-area > div").attr("class","board-insert-confirm");
		$(".btn-area > div").text("등록");
		$("#board-insert-form").attr("action","/board/insert");
		$(".board-title").val("");
		$('#summernote').summernote('reset');
		$('.modal').css("display","block");
		$('#modal-back').css("display","block");
	});
	
	$('.close_btn').click(function(){
		$('.modal').css("display","none");
		$('#modal-back').css("display","none"); 
	});
	
});
</script>
<script type="text/javascript">
//초기 페이지
var nowPage = 1;
var idxSet;
var commentIdxSet;
var category;
var boardCategory;
var ws;
var checkl;
var myb;

/* ======================================================= 소켓 ======================================================================*/
function wsOpen(){
	ws = new WebSocket("ws://localhost:8085/live");
	wsEvt();
}
	
function wsEvt() {
	ws.onopen = function(data){
		
	}
	
	ws.onmessage = function(data) {
		var msg = data.data;
		var d = JSON.parse(msg);
		if(msg != null && msg.trim() != ''){
			$(".live-status").prepend('<div class="live-comment live-comment'+ d.Idx + '" data-Idx="'  + d.Idx + '">' + '새 댓글 : ' + d.content + '</div>'
			);
			$(".live-comment" + d.Idx).animate({
				opacity : '1',
				transform: 'translateY(0px)'
			},600);
			setTimeout(function() {
				$(".live-status>div:last-child").remove();
				}, 5000);
			$(".live-comment-area").append('<div class="live-append-comment" data-boardIdx="' +  d.Idx  + '" data-title="' +  d.title  + '">' +
					'<div class="live-comment-title">' + 
					'<div>내 게시글 제목 : ' + d.title + '</div>' +
					'<div>새 댓글</div>' + 
				'</div>' + 
				'<div class="live-comment-list">' + 
					'<div class="live-comment-writer"><i class="fas fa-user"></i> ' + d.memName + '</div>' +
					'<div class="live-comment-content">' + d.content + '</div>' +
				'</div>' +
			'</div>');
			$(".live-comment-area").scrollTop($(".live-comment-area")[0].scrollHeight);
			$(".live-append-comment").animate({
				opacity : '1',
				transform: 'translateX(0)'
			},500);
		}
	}

}

/* ======================================================= 라이브 댓글 ======================================================================*/
 
//알람 선택
$(document).on("click",".live-append-comment",function(){
	var title = $(this).attr("data-title");
	var boardIdx = $(this).attr("data-boardIdx");
	$('.live-comment-insert-title').text('게시글 제목 : ' + title);
	$('.live-comment-send-btn').attr("data-boardIdx",boardIdx);
	$('.live-comment-send-btn').attr("data-title",title);
	$('live-comment-textarea').css('placeholder',"댓글을 입력해 주세요.");
});

$(document).on("click",".live-comment-send-btn",function(){
	var boardIdx = $(this).attr("data-boardIdx");
	var title = $(this).attr("data-title");
	var cContents = $('.live-comment-textarea').val()
	if(boardIdx == null) alert('알람을 선택 후 댓글을 입력 해 주세요.');
 	else{
		$.ajax({
			url : "/insertcomment",
			type : "post",
			data : { 'boardIdx' : boardIdx,
					 'cContents' : cContents
			},
			dataType: 'text',
			success: function(data){
				$('.live-comment-textarea').val('');
				$(".live-comment-area").append('<div class="live-my-comment">' +
					'<div class="live-my-comment-title">내 게시글 제목 : ' + title + '</div>' +
					'<div class="live-my-comment-content">' +
						 '<div>내 댓글</div>' +
						 '<div class="live-comment-content">' + cContents + '</div>' +
					'</div>' +
				'</div>');
				$(".live-comment-area").scrollTop($(".live-comment-area")[0].scrollHeight);
				$(".live-my-comment").animate({
					opacity : '1',
					transform: 'translateX(0)'
				},500);
			}
		});
	}	
});

$(document).on('click','.live-comment',function(){
	$('html').scrollTop(0);
});
/* ==================================================== 초기 댓글 리스트 수신 =========================================================*/

$.getComments = function(boardIdx){
	var commentDelete = "";
	$.ajax({
		url : "/getcomments",
		type: "get",
		data: { "boardIdx" : boardIdx,
				"nowPage" : 1
		},
		dataType:"json",
		success : function(data) {
			if(data.length > 0) $(".comment-idx-set" + boardIdx).attr("id",data[0].commentIdx);
			$("#comment-area"+boardIdx).html("");
			for(var i=0 in data){
				if(data[i].ownerCheck == "owner") {
					 commentDelete = '<div class="update-comment">' +
						'<span class="comment-delete-button" data-commentIdx="' + data[i].commentIdx + '" data-boardIdx="' +  boardIdx  + '">' +
							'<i class="far fa-trash-alt"></i>' +
						'</span>' +
						'</div>' ;
				}
				else  commentDelete = "";
				$("#comment-area"+boardIdx).append(
					'<div>' +
						'<div class="comment-writer">' +
							'<span><i class="fas fa-user"></i></span>' +
							'<span>' + data[i].memName + '</span>' +
						'</div>' +
						'<div>' +
							'<div class="comment-contents">' +
								'<div>' + data[i].cContents + '</div>' +
							'</div>' +
						'</div>' + commentDelete +
					'</div>'
				);
			}
			if(data.length > 4){
				$(".more-area" + boardIdx).html(
					'<div class="refresh-comment" id="' +  boardIdx   + '">' + '댓글 새로고침</div>' +	
					'<div class="comment-more" id="' +  boardIdx   + '" data-nowPage="2" data-commentIdx="' + data[0].commentIdx +'">' + '댓글 더보기</div>'
				);
			}
			else{
				$(".more-area" + boardIdx).html(
						'<div class="refresh-comment" id="' +  boardIdx   + '">' + '댓글 새로고침</div>'
				);
			}
		}
	});
};

/* ==================================================== 댓글 더보기 =========================================================*/

$(document).on("click",".comment-more",function(){
	var commentDelete = "";
	var boardIdx = $(this).attr("id");
	var nowPage = $(this).attr("data-nowPage");
	var commentIdx= $(this).attr("data-commentIdx");
	$.ajax({
		url : "/nextcomments",
		type: "get",
		data: { "boardIdx" : boardIdx,
				"commentIdx" : commentIdx,
				"nowPage" : nowPage
		},
		dataType:"json",
		success : function(data) {
			for(var i=0 in data){
				if(data[i].ownerCheck == "owner") {
					 commentDelete = '<div class="update-comment">' +
					 '<span class="comment-delete-button" data-commentIdx="' + data[i].commentIdx + '" data-boardIdx="' +  boardIdx  + '">' +
							'<i class="far fa-trash-alt"></i>' +
						'</span>' +
						'</div>' ;
				}
				else  commentDelete = "";
				$("#comment-area"+boardIdx).append(
					'<div>' +
						'<div class="comment-writer">' +
							'<span><i class="fas fa-user"></i></span>' +
							'<span>' + data[i].memName + '</span>' +
						'</div>' +
						'<div>' +
							'<div class="comment-contents">' +
								'<div>' + data[i].cContents + '</div>' +
							'</div>' +
						'</div>' + commentDelete +
					'</div>' 
				);
			}
			nowPage = Number(nowPage) + 1;
			if(data.length > 4){
				$(".more-area" + boardIdx).html(
					'<div class="refresh-comment" id="' +  boardIdx   + '">' + '댓글 새로고침</div>' +	
					'<div class="comment-more" id="' +  boardIdx   + '" data-nowPage="' + nowPage  + '" data-commentIdx="' + commentIdx +'">' + '댓글 더보기</div>'
				);
			}
			else{
				$(".more-area" + boardIdx).html(
						'<div class="refresh-comment" id="' +  boardIdx   + '">' + '댓글 새로고침</div>'
				);
			}
		}
	});
});

/* ==================================================== 댓글 새로고침 =========================================================*/

$(document).on("click",".refresh-comment",function(){
	var boarIdx = $(this).attr("id");
	$.getComments(boarIdx);
});


/* ==================================================== 초기 보드 리스트 수신 =========================================================*/

$.getList = function(Page,category){
	var content = "";
	var titleBottom = "";
	var listButton = "";
	$('.no-more > div').text("");
	$('.no-more').hide();
	$.ajax({
		url : "/boardlist",
		type: "get",
		data : {	"nowPage" : Page,
					"category" : category
		},
		dataType:"json",
		success : function(data) {
			$(".list-area").html("");
			idxSet = data[0].idx;
			for(var i=0 in data){
				if(data[i].ownerCheck == 'owner'){
					 listButton = '<div class="info-name">' +
						'<div class="board-update-button" data-boardIdx="' + data[i].idx + '"><i class="fas fa-ellipsis-h"></i></div>' +
						'</div>' ;
				} 
				else listButton = "";
				if(data[i].allContent == null) {
					content = "";
					titleBottom = "";
				}
				else {
					content = data[i].allContent ;
					titleBottom = '<div class="unfold" id="' +  data[i].idx  + '">본문보기</div>';
				}
				
				$(".list-area").append(
						'<div class="list1">' +
					'<div class="area title-area">' +
						'<div class="info">' +
							'<div class="info-name">' + 
								'<span><i class="fas fa-user"></i></span>' +
								'<span>' + data[i].memName + '</span>' +
							'</div>' +
							'<div class="info-name">' +
								'<span><i class="fas fa-clock"></i></span>' +
								'<span>' + data[i].cTime + '</span>' +
							'</div>' +
							'<div class="info-name">' +
								'<span><i class="far fa-thumbs-up"></i></span>' +
								'<span>' + data[i].recomRate + '</span>' +
							'</div>' +
							listButton + 
						'</div>' + 
						'<div class="title">' + data[i].title + '</div>' +
						'<div class="title-bottom">' +
							titleBottom + 
						'</div>' +
					'</div>' +
					'<div class="area content-area" id="content' +  data[i].idx  + '">' + 
						'<div>' + content +
						'</div>' +
						'<div class="button-area">' +
							'<div class= "recommend" id="'   +  data[i].idx    + '">' +
								'<span><i class="far fa-thumbs-up"></i></span>' +
								'<span>추천</span>' +
								'<span id="recom-rate'+ data[i].idx + '">' + data[i].recomRate + '</span>' +
							'</div>' +
							'<div class= "not-recommend" id="'   +  data[i].idx    + '">' +
								'<span><i class="far fa-thumbs-down"></i></span>' +
								'<span>비추천</span>' +
								'<span id="notrecom-rate'+ data[i].idx + '">' + data[i].notRecomRate + '</span>' +
							'</div>' +
						'</div>' +
					'</div>' +
					'<div class="area commentinput-area">' +
						'<div>' +
							'<div class="comment-input">' +
								'<textarea placeholder="댓글을 입력해 주세요." id="textarea' +  data[i].idx  + '"></textarea>' +
							'</div>' +
							'<div class="comment-send">' +
								'<div class="comment-send-btn" id="' +  data[i].idx  + '">' +
									'<i class="far fa-comment-dots"></i>' +
								'</div>' +
							'</div>' +
						'</div>' +
					'</div>' +
					'<div class="area commentlist-area" id="comment-area' +  data[i].idx  +  '">' +
					'</div>' +
					'<div class="more more-area' +   data[i].idx   + '">'  + 
					'<span class="cis comment-idx-set' + data[i].idx  + '">' + '</span>' +
					'</div>' +
				'</div>');
				$.getComments(data[i].idx);
				
			}
			nowPage=1;

		},
		error: function(){
			$(".list-area").html("표시할 항목이 없습니다.");
		}
	});
};

/* ==================================================== 다음 보드 리스트 불러오기 =========================================================*/
$.nextList = function(nowPage,idxSet,category){
	var content = "";
	var titleBottom = "";
	var listButton = "";
	$.ajax({
		url : "/nextboard",
		type: "get",
		data : {"nowPage" : nowPage,
				"idxSet" : idxSet,
				"category" : category
		},
		dataType:"json",
		success : function(data) {
			for(var i=0 in data){
				if(data[i].ownerCheck == 'owner'){
					 listButton = '<div class="info-name">' +
						'<div class="board-update-button" data-boardIdx="' + data[i].idx + '"><i class="fas fa-ellipsis-h"></i></div>' +
						'</div>' ;
				} 
				else listButton = "";
				if(data[i].allContent == null) {
					content = "";
					titleBottom = "";
				}
				else {
					content = data[i].allContent ;
					titleBottom = '<div class="unfold" id="' +  data[i].idx  + '">본문보기</div>';
				}
				$(".list-area").append(
						'<div class="list1">' +
					'<div class="area title-area">' +
						'<div class="info">' +
							'<div class="info-name">' + 
								'<span><i class="fas fa-user"></i></span>' +
								'<span>' + data[i].memName + '</span>' +
							'</div>' +
							'<div class="info-name">' +
								'<span><i class="fas fa-clock"></i></span>' +
								'<span>' + data[i].cTime + '</span>' +
							'</div>' +
							'<div class="info-name">' +
								'<span><i class="far fa-thumbs-up"></i></span>' +
								'<span>' + data[i].recomRate + '</span>' +
							'</div>' +
							listButton +
						'</div>' + 
						'<div class="title">' + data[i].title + '</div>' +
						'<div class="title-bottom">' +
							titleBottom + 
						'</div>' +
					'</div>' +
					'<div class="area content-area" id="content' +  data[i].idx  + '">' + 
						'<div>' + content +
						'</div>' +
						'<div class="button-area">' +
							'<div class= "recommend" id="'   +  data[i].idx    + '">' +
								'<span><i class="far fa-thumbs-up"></i></span>' +
								'<span>추천</span>' +
								'<span id="recom-rate'+ data[i].idx + '">' + data[i].recomRate + '</span>' +
							'</div>' +
							'<div class= "not-recommend" id="'   +  data[i].idx    + '">' +
								'<span><i class="far fa-thumbs-down"></i></span>' +
								'<span>비추천</span>' +
								'<span id="notrecom-rate'+ data[i].idx + '">' + data[i].notRecomRate + '</span>' +
							'</div>' +
						'</div>' +
					'</div>' +
					'<div class="area commentinput-area">' +
						'<div>' +
							'<div class="comment-input">' +
								'<textarea placeholder="댓글을 입력해 주세요." id="textarea' +  data[i].idx  + '"></textarea>' +
							'</div>' +
							'<div class="comment-send">' +
								'<div class="comment-send-btn" id="' +  data[i].idx  + '">' +
									'<i class="far fa-comment-dots"></i>' +
								'</div>' +
							'</div>' +
						'</div>' +
					'</div>' +
					'<div class="area commentlist-area" id="comment-area' +  data[i].idx  +  '">' +
					'</div>' +
					'<div class="more more-area' +   data[i].idx   + '">'  + 
					'<span class="cis comment-idx-set' + data[i].idx  + '">' + '</span>' +
					'</div>' +
				'</div>');
				$.getComments(data[i].idx);
				
			}

		},
		error : function(error){
			$('.no-more > div').text("더이상 불러올 항목이 없습니다.");
			$('.no-more').show();
		}
	});
};
/* ==================================================== 댓글 입력 구현부 =========================================================*/

$(document).on('keyup','.comment-input > textarea',function(){
	if($(this).val().length > 100){
		$(this).val($(this).val().substring(0,100));
	}
});

$(document).on("click",".comment-send-btn",function(){
	var boardIdx = $(this).attr("id");
	$.ajax({
		url : "/insertcomment",
		type:"post",
		data: { "cContents" :  $("#textarea"+boardIdx).val() ,
				"boardIdx" : boardIdx
		},
		dataType:"text",
		success : function(data) {
			console.log(data)
			$.getComments(boardIdx);	
			var jdata = {Idx : boardIdx,
						 content : $("#textarea"+boardIdx).val()
				};		
				ws.send(JSON.stringify(jdata));
				$("#textarea"+boardIdx).val("");
		},
		error : function(error) {
			console.log(error)
	        alert("에러 발생");
			$("#textarea"+boardIdx).val("");
	    }
	});
});



/* ==================================================== 추천 비추천 =========================================================*/
 
$(document).on("click",".recommend",function(){
	var idx = $(this).attr("id");
	$.ajax({
		url : "/recommend",
		type:"post",
		data : "idx=" + idx,
		dataType:"text",
		success : function(data) {
			console.log(data);
			$("#recom-rate"+idx).text(data);
		},
		error : function(error) {
	        alert("이미 추천 했습니다.");
	    }
	});
});

$(document).on("click",".not-recommend",function(){
	var idx = $(this).attr("id");
	$.ajax({
		url : "/notrecommend",
		type:"post",
		data : "idx=" + idx,
		dataType:"text",
		success : function(data) {
			console.log(data);
			$("#notrecom-rate"+idx).text(data);
		},
		error : function(error) {
	        alert("이미 비추천 했습니다.");
	    }
	});
});
/* ==================================================== 본문보기/접기 =========================================================*/

$(document).on("click",".unfold",function(){
	var idx = $(this).attr("id");
	if($("#content"+idx).css("display") == "none") {
		$("#content"+idx).show();
		$(this).text("접기");
	}
	else {
		$("#content"+idx).hide();
		$(this).text("본문보기");
	}
});


/* ==================================================== 페이지 진입 시 동작 =========================================================*/

$(document).ready(function() {


	/* ================== 게시판 타이틀 설정 ========================= */
	if(sessionStorage.getItem("category") == null){ 
		sessionStorage.setItem("category", "free");		
		sessionStorage.setItem("title", "자유 게시판");
	}
	$('.nav-board-title').text(sessionStorage.getItem('title'));
	category = sessionStorage.getItem('category');
	$(".board-category").val(sessionStorage.getItem('category'));


	/*================== 사이드 메뉴 아이템 선택시 동작 ================  */
	$(".side-item").click(function(){
		var setCategory = $(this).attr('data-category');
		var setTitle = $(this).attr('data-title');
		sessionStorage.setItem("category", setCategory);
		sessionStorage.setItem("title", setTitle);
		$('.nav-board-title').text(sessionStorage.getItem('title'));
		category = sessionStorage.getItem("category");
		$.getList(1,category);
		nowPage = 1;
		$('.writer-floating').show();
		$(".board-category").val(category);
	});

	/*=================== 로그인 체크 후 화면 셋팅 =============================== */
	$.ajax({                                                             
		url : "/member/check",
		type : 'get',                             
		dataType : "text",
		success : function(data) {
			wsOpen();
			$('.my-board').show();
			$('.login').html("<span class='memName-area'>" + data + "님</span>" + "<span> 로그아웃</span>").attr('id','nav-logout');
			$('.live-comment-chat').show();
			if(sessionStorage.getItem('category') != 'myBoard') $('.writer-floating').show();
		
		},
		error : function(error) {
			checkl = false;
			$('.my-board').hide();
			$('.login').html("<span>로그인</sapn>").attr('id','nav-login');
			$('.live-comment-chat').hide();
			$('.writer-floating').hide();
	    }	
	});
	
	/*================== 게시글 수신 =================================*/
	if(sessionStorage.getItem('category') != 'myBoard') $.getList(nowPage,category);
	else $.getMyList();


	
});

/* ==================================================== 로그인/아웃 버튼=========================================================*/
$(document).on("click","#nav-login",function(){
	$(location).attr('href','/');
});

$(document).on("click","#nav-logout",function(){
	$(location).attr('href','/logout');
});

/* ==================================================== 내 게시글 버튼=========================================================*/
$(document).on('click','.my-board',function(){
	var setCategory = $(this).attr('data-category');
	var setTitle = $(this).attr('data-title');
	sessionStorage.setItem("category", setCategory);
	sessionStorage.setItem("title", setTitle);
	$('.nav-board-title').text(sessionStorage.getItem('title'));
	$('.writer-floating').hide();
	$.getMyList();
});

/* ==================================================== 스크롤 이벤트 =========================================================*/
$(window).scroll(function() {
	if(sessionStorage.getItem('category') != 'myBoard'){
	   if($(window).scrollTop() + $(window).height() == $(document).height()) {
		   nowPage = nowPage +1; 
		   $.nextList(nowPage,idxSet,category);
	   }
	}
	else{
		if($(window).scrollTop() + $(window).height() == $(document).height()) {
			nowPage = nowPage +1; 
			$.getNextMyList(nowPage)
		}
	}
	});

/* ==================================================== 댓글 삭제=========================================================*/
$(document).on("click",".comment-delete-button",function(){
	$(".comment-modal-info").attr("data-commentIdx",$(this).attr("data-commentIdx"));
	$(".comment-modal-info").attr("data-boardIdx",$(this).attr("data-boardIdx"));
	$(".modal-title").text("댓글 삭제");
	$(".comment-modal-contents").text("댓글을 삭제 하시겠습니까?");
	$(".modal-confirm-button").attr("id","comment-delete-confirm");
	$(".comment-modal").show();
	$("#modal-back").show();
});

$(document).on("click",".comment-modal-delete",function(){
	$(".comment-modal").hide();
	$("#modal-back").hide();
});

$(document).on("click","#comment-delete-confirm",function(){
	var boardIdx = $(".comment-modal-info").attr("data-boardIdx");
	var commentIdx = $(".comment-modal-info").attr("data-commentIdx");
	$.ajax({                                                             
		data : {
			"commentIdx" : commentIdx,
		},
		type : "post",
		url : "/comment/delete",                                  
		dataType : "text",
		success : function(data) {                
			$.getComments(boardIdx);
			$(".comment-modal").hide();
			$("#modal-back").hide();
		},
		error : function(error) {
	        alert("댓글 삭제에 실패했습니다.");
	        $(".comment-modal").hide();
			$("#modal-back").hide();
	    }
		
	});
});

/* ==================================================== 리스트 서브 메뉴 =========================================================*/
$(document).on("click",".board-update-button",function(){
	$(".board-modal-info").attr("data-boardIdx",$(this).attr("data-boardIdx"));
    var p = $(this).offset();
    $(".board-sub-menu").css('top',p.top+23);
    $(".board-sub-menu").css('left',p.left-70);
   	$(".board-sub-menu").show();
});

$(document).mouseup(function (e){
	var cel = $(e.target);
	if(cel.attr('class') != 'board-sub-button' ){ 
		$(".board-sub-menu").hide(); }
	else $(".board-sub-menu").show();
});

$(window).resize(function() {
	var leftset = $(".board-update-button").offset().left;
	$(".board-sub-menu").css('left',leftset-8);
});

$(document).on("click",".board-delete-button",function(){
	$(".modal-title").text("게시글 삭제");
	$(".comment-modal-contents").text("게시글을 삭제 하시겠습니까?");
	$(".modal-confirm-button").attr("id","board-delete-confirm");
	$(".board-sub-menu").hide();
	$(".comment-modal").show();
	$("#modal-back").show();
});

$(document).on("click","#board-delete-confirm",function(){
	var boardIdx = $(".board-modal-info").attr("data-boardIdx");
	$.ajax({                                                             
		data : {
			"boardIdx" : boardIdx,
		},
		type : "post",
		url : "/board/delete",                                  
		dataType : "text",
		success : function(data) {                
			$.getList(1,category);
			$(".comment-modal").hide();
			$("#modal-back").hide();
		},
		error : function(error) {
	        alert("게시글 삭제에 실패했습니다.");
	        $(".comment-modal").hide();
			$("#modal-back").hide();
	    }
		
	});
});

/* ==================================================== 게시글 등록/수정 =========================================================*/



$(document).on("click",".board-insert-confirm",function(){
	$('#board-insert-form').submit();
});

$(document).on('keyup','.board-title',function(){
	if($(this).val().length > 200){
		$(this).val($(this).val().substring(0,200));
	}
});



$(document).on("click",".board-modified-button",function(){
	var boardIdx = $(".board-modal-info").attr("data-boardIdx");
	$.ajax({                                                             
		data : {
			"boardIdx" : boardIdx,
		},
		type : "post",
		url : "/board/update",                                  
		dataType : "json",
		success : function(data) {
			$("#board-insert-form").attr("action","/board/updateOK");
			$(".board-update-idx").val(boardIdx);
			$(".btn-area > div").attr("class","board-update-confirm");
			$(".btn-area > div").text("수정");
			$(".board-title").val(data.title);
			$('#summernote').summernote('reset');
			$("#summernote").summernote('code', data.allContent);
			$("#modal-back").show();
			$(".insert-area").show();
		},
		error : function(error) {
	        alert("게시글 정보 취득에 실패했습니다.");
	    }
		
	});
});

$(document).on("click",".board-update-confirm",function(){
	$('#board-insert-form').submit();
});

</script>
<script type="text/javascript">
/* ==========================게시판 사이드 메뉴 ========================================= */
$(document).on("click",".board-list-select",function(){
	$('.side-menu').css("transition",'0.3s');
	$('.side-menu').css("left",'0');
	
});

$(document).on("click",".side-close",function(){
	$('.side-menu').css("transition",'0.3s');
	$('.side-menu').css("left",'-19rem');
	
});

$(document).on("click",".side-item",function(){
	$('.side-menu').css("transition",'0.3s');
	$('.side-menu').css("left",'-19rem');
	
});
</script>
<script type="text/javascript">
/* ============================================내 게시글 불러오기 ========================================================== */
$.getMyList = function(){
	var content = "";
	var titleBottom = "";
	var listButton = "";
	myb = true;
	$('.no-more > div').text("");
	$('.no-more').hide();
	$.ajax({
		url : "/list/myboard",
		type: "get",
		data : {	"nowPage" : '1'
		},
		dataType:"json",
		success : function(data) {
			$(".list-area").html("");
			for(var i=0 in data){
				listButton = '<div class="info-name">' +
						'<div class="board-update-button" data-boardIdx="' + data[i].idx + '"><i class="fas fa-ellipsis-h"></i></div>' +
						'</div>' ;
				if(data[i].allContent == null) {
					content = "";
					titleBottom = "";
				}
				else {
					content = data[i].allContent ;
					titleBottom = '<div class="unfold" id="' +  data[i].idx  + '">본문보기</div>';
				}
				
				$(".list-area").append(
						'<div class="list1">' +
					'<div class="area title-area">' +
						'<div class="info">' +
							'<div class="info-name">' + 
								'<span><i class="fas fa-user"></i></span>' +
								'<span>' + data[i].memName + '</span>' +
							'</div>' +
							'<div class="info-name">' +
								'<span><i class="fas fa-clock"></i></span>' +
								'<span>' + data[i].cTime + '</span>' +
							'</div>' +
							'<div class="info-name">' +
								'<span><i class="far fa-thumbs-up"></i></span>' +
								'<span>' + data[i].recomRate + '</span>' +
							'</div>' +
							listButton + 
						'</div>' + 
						'<div class="title">' + data[i].title + '</div>' +
						'<div class="title-bottom">' +
							titleBottom + 
						'</div>' +
					'</div>' +
					'<div class="area content-area" id="content' +  data[i].idx  + '">' + 
						'<div>' + content +
						'</div>' +
						'<div class="button-area">' +
							'<div class= "recommend" id="'   +  data[i].idx    + '">' +
								'<span><i class="far fa-thumbs-up"></i></span>' +
								'<span>추천</span>' +
								'<span id="recom-rate'+ data[i].idx + '">' + data[i].recomRate + '</span>' +
							'</div>' +
							'<div class= "not-recommend" id="'   +  data[i].idx    + '">' +
								'<span><i class="far fa-thumbs-down"></i></span>' +
								'<span>비추천</span>' +
								'<span id="notrecom-rate'+ data[i].idx + '">' + data[i].notRecomRate + '</span>' +
							'</div>' +
						'</div>' +
					'</div>' +
					'<div class="area commentinput-area">' +
						'<div>' +
							'<div class="comment-input">' +
								'<textarea placeholder="댓글을 입력해 주세요." id="textarea' +  data[i].idx  + '"></textarea>' +
							'</div>' +
							'<div class="comment-send">' +
								'<div class="comment-send-btn" id="' +  data[i].idx  + '">' +
									'<i class="far fa-comment-dots"></i>' +
								'</div>' +
							'</div>' +
						'</div>' +
					'</div>' +
					'<div class="area commentlist-area" id="comment-area' +  data[i].idx  +  '">' +
					'</div>' +
					'<div class="more more-area' +   data[i].idx   + '">'  + 
					'<span class="cis comment-idx-set' + data[i].idx  + '">' + '</span>' +
					'</div>' +
				'</div>');
				$.getComments(data[i].idx);
				
			}
			nowPage=1;
		},
		error: function(){
			$(".list-area").html("표시할 항목이 없습니다.");
		}
	});
};

$.getNextMyList = function(nowPage){
	var content = "";
	var titleBottom = "";
	var listButton = "";
	$.ajax({
		url : "/list/myboard",
		type: "get",
		data : {	"nowPage" : nowPage
		},
		dataType:"json",
		success : function(data) {
			for(var i=0 in data){
				listButton = '<div class="info-name">' +
						'<div class="board-update-button" data-boardIdx="' + data[i].idx + '"><i class="fas fa-ellipsis-h"></i></div>' +
						'</div>' ;
				if(data[i].allContent == null) {
					content = "";
					titleBottom = "";
				}
				else {
					content = data[i].allContent ;
					titleBottom = '<div class="unfold" id="' +  data[i].idx  + '">본문보기</div>';
				}
				
				$(".list-area").append(
						'<div class="list1">' +
					'<div class="area title-area">' +
						'<div class="info">' +
							'<div class="info-name">' + 
								'<span><i class="fas fa-user"></i></span>' +
								'<span>' + data[i].memName + '</span>' +
							'</div>' +
							'<div class="info-name">' +
								'<span><i class="fas fa-clock"></i></span>' +
								'<span>' + data[i].cTime + '</span>' +
							'</div>' +
							'<div class="info-name">' +
								'<span><i class="far fa-thumbs-up"></i></span>' +
								'<span>' + data[i].recomRate + '</span>' +
							'</div>' +
							listButton + 
						'</div>' + 
						'<div class="title">' + data[i].title + '</div>' +
						'<div class="title-bottom">' +
							titleBottom + 
						'</div>' +
					'</div>' +
					'<div class="area content-area" id="content' +  data[i].idx  + '">' + 
						'<div>' + content +
						'</div>' +
						'<div class="button-area">' +
							'<div class= "recommend" id="'   +  data[i].idx    + '">' +
								'<span><i class="far fa-thumbs-up"></i></span>' +
								'<span>추천</span>' +
								'<span id="recom-rate'+ data[i].idx + '">' + data[i].recomRate + '</span>' +
							'</div>' +
							'<div class= "not-recommend" id="'   +  data[i].idx    + '">' +
								'<span><i class="far fa-thumbs-down"></i></span>' +
								'<span>비추천</span>' +
								'<span id="notrecom-rate'+ data[i].idx + '">' + data[i].notRecomRate + '</span>' +
							'</div>' +
						'</div>' +
					'</div>' +
					'<div class="area commentinput-area">' +
						'<div>' +
							'<div class="comment-input">' +
								'<textarea placeholder="댓글을 입력해 주세요." id="textarea' +  data[i].idx  + '"></textarea>' +
							'</div>' +
							'<div class="comment-send">' +
								'<div class="comment-send-btn" id="' +  data[i].idx  + '">' +
									'<i class="far fa-comment-dots"></i>' +
								'</div>' +
							'</div>' +
						'</div>' +
					'</div>' +
					'<div class="area commentlist-area" id="comment-area' +  data[i].idx  +  '">' +
					'</div>' +
					'<div class="more more-area' +   data[i].idx   + '">'  + 
					'<span class="cis comment-idx-set' + data[i].idx  + '">' + '</span>' +
					'</div>' +
				'</div>');
				$.getComments(data[i].idx);
				
			}
			nowPage=1;
		},
		error: function(){
			$('.no-more > div').text("더이상 불러올 항목이 없습니다.");
			$('.no-more').show();
		}
	});
};

</script>
<body>
<%@ include file="../common/navigation.jsp"%>
<!-- ================ 라이브 댓글 채팅  ===================== -->
<div class="live-comment-chat">
	<div class="live-comment-area">
	
	</div>
	<div class="live-comment-insert-title"></div>
	<div class="live-comment-insert">
			<div class="comment-input">
				<textarea class="live-comment-textarea" placeholder="알람을 선택 후 댓글을 입력해 주세요."></textarea>
			</div>
			<div class="comment-send">
				<div class="live-comment-send-btn">
					<i class="far fa-comment-dots"></i>
				</div>
			</div>
	</div>
</div>
<!-- ================ 소켓 테스트 영역 ===================== -->
<div class="live-status">
	
</div>
<!-- ================ 게시글 작성 노출 영역 ===================== -->
<div id="modal-back"></div>
<div class="insert-area modal">
	<form id="board-insert-form" action="#" method="post">
		<input type="hidden" name="idx" class="board-update-idx">
		<input type="hidden" name="category" class="board-category">
		<div class="title-insert">
			<div><input class="board-title" name="title" placeholder="제목을 입력해 주세요. 200자까지 입력 가능" ></div>
			<div><span class="close_btn"><i class="fas fa-times"></i></span></div>
		</div>
		<div>
			<textarea id="summernote" name="allContent" style="resize: none;"></textarea>
		</div>
		<div class="btn-area">
			<div></div>
		</div>
	</form>
</div>
<!-- ================ 댓글/게시글 삭제 확인 팝업 ===================== -->
<div class="comment-modal">
<div class="comment-modal-info modal-info"></div>
<div class="board-modal-info modal-info"></div>
	<div class="comment-modal-title">
		<div class="modal-title"></div>
		<div>
			<div class="comment-modal-delete">
				<i class="fas fa-times"></i>
			</div>
		</div>
	</div>
	<div class="comment-modal-contents">
		<div>이 댓글을 삭제하시겠습니까?</div>
	</div>
	<div class="comment-modal-button">
		<div class="modal-confirm-button">삭제</div>
	</div>
</div>

<!-- ================ 리스트 서브 메뉴  ===================== -->
<div class="board-sub-menu">
<div class="sub-menu-info"></div>
<div class="board-sub-button board-delete-button">
게시글 삭제
</div>
<div class="board-sub-button board-modified-button">
게시글 수정
</div>
</div>
<!-- ================ 게시글 리스트 노출 영역 ===================== -->
<div class="list-area">
	<div class="list1">
		<div class="area title-area">
			<div class="info">
				<div class="info-name">
					<span><i class="fas fa-user"></i></span>
					<span>배추잠자리</span>
				</div>
				<div class="info-name">
					<span><i class="fas fa-clock"></i></span>
					<span>2020/12/31</span>
				</div>
				<div class="info-name">
					<span><i class="far fa-thumbs-up"></i></span>
					<span>352</span>
				</div>
				<div class="info-name">
						<div class="list-update-button"><i class="fas fa-ellipsis-h"></i></div>
				</div>
			</div>
			<div class="title">오버워치 1028시간 달성했다 사실상 3000시간쯤됨</div>
			<div class="title-bottom">
				<div class="unfold">본문보기</div>
			</div>
		</div>
		<div class="area content-area">
			<div>
				
			</div>
			<div class="button-area">
				<div>
					<span><i class="far fa-thumbs-up"></i></span>
					<span>추천</span>
					<span>353</span>
				</div>
				<div>
					<span><i class="far fa-thumbs-down"></i></span>
					<span>비추천</span>
					<span>224</span>
				</div>
			</div>		
		</div>
		<div class="area commentinput-area">
			<div>
				<div class="comment-input">
					<textarea placeholder="댓글을 입력해 주세요."></textarea>
				</div>
				<div class="comment-send">
					<div>
						<i class="fas fa-reply"></i>
					</div>
				</div>
			</div>
		</div>
		<div class="area commentlist-area">
			<div>
				<div class="comment-writer">
					<span><i class="fas fa-user"></i></span>
					<span>user1</span>
				</div>
				<div>
					<div class="comment-contents">
						<div>호에에ㅔㅇ에에에ㅔ?호에에ㅔㅇ에에에ㅔ?호에에ㅔㅇ에에에ㅔ?호에에ㅔㅇ에에에ㅔ?</div>
					</div>
				</div>
				<div class="update-comment">
					<span><i class="far fa-trash-alt"></i></span>
				</div>
			</div>
			<div>
				<div class="comment-writer">
					<span><i class="fas fa-user"></i></span>
					<span>스린이</span>
				</div>
				<div>
					<div class="comment-contents">
						<div>선생님...</div>
					</div>
				</div>
			</div>
			<div class="more-area">
				<div class="comment-more">댓글 더보기</div>
			</div>
		</div>
	</div>
</div>
<div class="no-more"><div></div></div>
<div class="writer-floating"><div><i class="far fa-edit"></i></div></div>
</body>
</html>