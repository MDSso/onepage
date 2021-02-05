$(document).ready(function() {
	//여기 아래 부분
	$('#summernote').summernote({
		height: 300,                 // 에디터 높이
		minHeight: 300,             // 최소 높이
		maxHeight: 300,             // 최대 높이
		focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부					// 한글 설정
		placeholder: '최대 2048자까지 쓸 수 있습니다',	//placeholder 설정
		tabDisable: true,
		spellCheck: false,
		toolbar: [
			['insert', ['picture', 'video']]	          
		],
		callbacks : { 
					onImageUpload : function(files, editor, welEditable) {       
						for (var i = 0; i < files.length; i++) {
							sendFile(files[i], this);
						}
					}
				}
		
	});
	
	function sendFile(file, el) {
			var form_data = new FormData();
			form_data.append('file', file);
			$.ajax({                                                             
				data : form_data,
				type : "POST",
				url : '/board/saveimg',
				cache : false,
				contentType : false,
				enctype : 'multipart/form-data',                                  
				processData : false,
				success : function(url) {                                         
					$(el).summernote('insertImage', url, function($image) {
						$image.css('width', "100%");
					});
				}
			});

	}
	
	$('#text').click(function(){
		$('.modal').css("display","block");
		$('#modal-back').css("display","block");
	});
	
	$('.close_btn').click(function(){
		$('.modal').css("display","none");
		$('#modal-back').css("display","none");
	});
});