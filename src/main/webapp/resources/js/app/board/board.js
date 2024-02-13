/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2024. 1. 24.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2024. 1. 24.  김보영      최초작성
 * Copyright (c) 2024 by DDIT All right reserved
 * </pre>
 */ 


//▶▷▶▷▶▷▶▷▶▷[ 이벤트 ] ▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷

//1.댓글 엔터키 이벤트
/*
	keydown:키보드를 눌렀을때
	keyCode == 13 - > 엔터키
	event.preventDefault(); ->form제출방지
*/
$(document).ready(function() {
    $('#commCn').keydown(function(event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            fn_insertComm();
        }
    });
});


//2.test용 더미
function fn_fillDummy(){
	document.getElementById('boNick').value = '테스형';
	document.getElementById('boPw').value = '123456';
	document.getElementById('boPwCheck').value = '123456';
	document.getElementById('boSj').value = 'test';
	
	var dummyContent = 'test하는 테스형';

    // CKEditor의 인스턴스를 가져오기
    var editor = CKEDITOR.instances.boCn;

    // CKEditor에 더미 데이터 설정
    if (editor) {
        editor.setData(dummyContent);
    }
}



//▶▷▶▷▶▷▶▷▶▷[ 게시글 삭제 & 수정 ] ▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷

//1.게시판 수정

function fn_updateBoard(boNo){

	dropzone.enqueueFiles(dropzone.files);
	dropzone.processQueue();
	
	if(validate()){

		fn_swalConfirm("게시글을 수정하시겠습니까?", function(){
			//비동기
			var updateForm = new FormData($("#formBoard")[0]);
			var boCn = CKEDITOR.instances.boCn.getData();
			updateForm.set("boCn", boCn);
			
			//게시판번호
			updateForm.set("boNo", boNo);
			
			
			//랜덤파일코드도 포함
			var fileCode = $('#fileCode').val();
			updateForm.set("fileCode", fileCode);
			
			$.ajax({
				type: "PUT",
				url: "/board/update/"+boNo,
				data: updateForm,
				processData: false,
				contentType: false,
				cache: false,
				success: function (data) {
					var text;
					var icon;
					if (data.success == "Y") {
						text = "게시글 수정이 완료되었습니다.";
						icon = "success";
					} else {
						text = "게시글 수정이 실패되었습니다.";
						icon = "warning";
					}
					//모달창열기
					fn_swalComplete(text, icon, "/board/detail/"+data.boNo, data.success);
				},
				error: function () {
					fn_swalError();
				}
			});
		});
	}
}


//2. 게시글 삭제하기
function fn_deleteBoard(boNo){

	fn_swalConfirm("게시글을 삭제하시겠습니까?", function(){
		//비동기
		fn_inputPasswordWhenDelete(boNo);
	});
}


//▶▷▶▷▶▷▶▷▶▷[ 페이지 이동 ] ▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷

//게시글 수정페이지로 이동
function fn_boardEdit(boNo){
	
	fn_inputPassword(boNo,'/board/edit/');
}


//게시글 목록이동
function fn_homeList(){
	location.href='/home.do';
}


//게시글 상세보기로 이동
function fn_boardDetail(boNo){
	location.href = '/board/detail/'+boNo;
}


//▶▷▶▷▶▷▶▷▶▷[ 댓글 ] ▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷

//상세보기페이지 댓글 목록
$(function(){

  fn_commList();

});

//1.댓글목록
function fn_commList(){
	
	var boNo = $('#boNo').val();
	let settings = {
		url : '/board/commList',
		contentType : 'application/json',
		method : 'get',
		data : {
			boNo : boNo
		},
		dataType: 'json'
	};
	
	let divTag='';
	$.ajax(settings).done(function (resp){
		var commList = resp.commList;
		if(commList.length != 0){
			$.each(commList , function(i, v){
				if(v.commNo != null){
					divTag += `
						<div class="card mt-3">
					      <div class="card-body">
					        <div class="d-flex justify-content-between mb-2 ">
					          <h8 class="fw-normal">${v.commRdate}</h8>
					        </div>
					        <div class="d-flex justify-content-between align-items-end">
					          <div class="role-heading" >
					            <h5 class="mb-1">${v.commCn}</h5>
					          </div>
					        </div>
					      </div>
					    </div>
					`;
				}
			})
		}else{
			divTag += `
				<div class="card mt-3">
			      <div class="card-body">
			        <div class="d-flex justify-content-between align-items-end">
			          <div class="role-heading" >
			            <h5 class="mb-1">작성된 댓글이 없습니다.</h5>
			          </div>
			        </div>
			      </div>
			    </div>
			`;
		}
		$("#commArea").html(divTag);
	})
}

//2.댓글 등록

function fn_insertComm(){
	
	fn_swalConfirm("댓글을 등록하시겠습니까?", function(){
		//비동기
		var insertComm = new FormData($("#insertComm")[0]);
		
		$.ajax({
			type: "POST",
			enctype: 'multipart/form-data',
			url: "/board/insertComm",
			data: insertComm,
			processData: false,
			contentType: false,
			cache: false,
			success: function (data) {
				var text;
				var icon;
				if (data.success == "Y") {
					text = "댓글 등록이 완료되었습니다.";
					icon = "success";
				} else {
					text = "댓글 등록이 실패되었습니다.";
					icon = "warning";
				}
				fn_swalComplete(text, icon, "/board/detail/"+data.boNo, data.success);
			},
			error: function () {
				fn_swalError();
			}
		});
	});
}


//▶▷▶▷▶▷▶▷▶▷[ 게시판 입력값검증 & 등록 ] ▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷

//글 등록하기
//1.등록폼으로 이동

function fn_boardForm(){
	location.href='/board/form';
}

//2.등록하기

//입력값검증

function validateInput(inputId, message, minLength, maxLength,name) {
    var inputValue = $(inputId).val();
    var emptyLabel = $(message);
	
    // 빈값을 확인
    if (inputValue.trim() == '') {
        emptyLabel.text(name+'을 입력하세요.');
        emptyLabel.show();
        $(inputId).focus();
        return false;
    }

    var inputLength = inputValue.length;
    //길이를 확인
    if (inputLength < minLength || inputLength > maxLength) {
        emptyLabel.text(`${minLength}~ ${maxLength}자 이내로 입력하세요.`);
        emptyLabel.show();
        $(inputId).focus();
        return false;
    }
    emptyLabel.hide();
    return true;
}


function validate(){
	
	if (!validateInput('#boNick', '#nickNameEmpty', 2, 10, '닉네임')) return false;
    if (!validateInput('#boSj', '#boSjEmpty',2, 20 ,'제목')) return false;
    if (!validateInput('#boPw', '#passwordEmpty',6,15,'비밀번호')) return false;
    
    var boCn = CKEDITOR.instances.boCn.getData();
    if(boCn.trim() == ''){
	    if (!validateInput('#boCn', '#boCnEmpty',1,4000,'내용')) return false;
    }
    
    return true;
}


//비밀번호 확인검증
function validatePassword() {
    var boPw = $('#boPw').val();
    var boPwCheck = $('#boPwCheck').val();
    var mismatchLabel = $('#passwordMismatch');

    if (boPw != boPwCheck) {
        mismatchLabel.show();
        $('#boPwCheck').focus();
        return false;
    } else {
        mismatchLabel.hide();
        return true;
    }
   
}


function fn_insertBoard(){

	//파일드랍 
	//수동으로 큐에 올리기위함
	dropzone.enqueueFiles(dropzone.files);
	dropzone.processQueue();
	
	if(validate()){
	
		if(validatePassword()){
		
			fn_swalConfirm("게시글을 등록하시겠습니까?", function(){
			
				
				//비동기
				var insertForm = new FormData($("#formBoard")[0]);
				var boCn = CKEDITOR.instances.boCn.getData();
				insertForm.set("boCn", boCn);
				
				//랜덤파일코드도 포함
				var fileCode = $('#fileCode').val();
				insertForm.set("fileCode", fileCode);
				
				$.ajax({
					type: "POST",
					url: "/board/insert",
					data: insertForm,
					processData: false,
					contentType: false,
					cache: false,
					success: function (data) {
						var text;
						var icon;
						if (data.success == "Y") {
							text = "게시글 등록이 완료되었습니다.";
							icon = "success";
						} else {
							text = "게시글 등록이 실패되었습니다.";
							icon = "warning";
						}
						//모달창열기
						fn_swalComplete(text, icon, "/board/detail/"+data.boNo, data.success);
					},
					error: function () {
						fn_swalError();
					}
				});
			});
		}
	
	}
}





//▶▷▶▷▶▷▶▷▶▷[ 페이징 ] ▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷


//게시판 처음접속 리스트 비동기

$(function(){

  fn_boardPaging();

});

//1.페이징 버튼 눌렀을때
function fn_paging(page){

	let option = $('#option').val();
		
	if(option == 'list'){
		searchForm.page.value = page ;
		fn_boardPaging();
	
	}else if(option == 'album'){
		searchFormAlbum.page.value = page ;
		fn_albumList();

	}

}

//2.키워드 검색

$(document).on('click','#searchBtn', function(){
  	fn_boardPaging();
})

$(document).on('click','#searchAlbumBtn', function(){
 	fn_albumList();
})

function fn_listChange(listNum){
	searchForm.listNum.value = listNum ;
  	fn_paging(1);
}

//3.리스트형 페이징

function fn_boardPaging(){

	$('#option').val('list');

	$('#boardTableList').children().show();
	$('#listSelectValue').children().show();
	$('#albumList').children().hide();
	
	let formData = $('#searchForm').serialize();

	let settings = {
		url : '/board/list/list',
		contentType : 'application/json',
		method : 'get',
		data : formData,
		dataType: 'json'
	};
	
	let trTag='';
	
	$.ajax(settings).done(function (resp){
		
		let pagingBoardList = resp.paging.dataList;
		let simpleCondition = resp.paging.simpleCondition;
		
		if(pagingBoardList[0] != null){
			
			$.each(pagingBoardList, function(i,v){

		        if(v.boNo != null){
		          trTag +=`
		            <tr onclick="fn_boardDetail('${v.boNo}')" style="cursor: pointer;">
		              <td>${v.boNo}</td>
		              <td>${v.boSj}</td>
		              <td>${v.boNick}</td>
		              <td>${v.boRdate}</td>
		              <td>${v.boRdcnt}</td>
		            </tr> 
		          `;
		        }else{
		          trTag +=`
		            <tr>
		              <td colspan='5'>내역 없음</td>
		            </tr>  
		          `;
		        }

        	$('#boardList').html(trTag);
      	});

      	trTag = `
				<tr>
					<td colspan="6">
							<hr class="my-0 mb-3 mt-2">
						${resp.paging.pagingHTML}
						<form id="searchForm">
							<div id ="searchUI" class="row g-3 d-flex justify-content-center">
								<input type="hidden" name="page" readonly="readonly"/>
								<input type="hidden" name="listNum" readonly="readonly" value="${resp.paging.listNum}"/>
								<input type="hidden" name="option" readonly="readonly" value="${resp.paging.option}" id="option"/>
								<div class="col-auto">
									<select name="searchType" class="form-select"> 
										<option value="" >전체</option>
										<option value="title" ${simpleCondition.searchType == "title" ? 'selected' : ''} >제목</option>
										<option value="writer" ${simpleCondition.searchType == "writer" ? 'selected' : ''}>작성자</option>
									</select>
								</div>
								<div class="col-auto">
									<input name="searchWord" placeholder="입력하세요" class="form-control" 
										value="${simpleCondition.searchWord != null ? simpleCondition.searchWord :''}"/>
								</div>
								<div class="col-auto">
									<input type="button" value="검색" id="searchBtn" class="btn btn-primary" />
								</div>
							</div>
						</form>
					</td>
				</tr>
				`;
        $('#boardPaging').html(trTag);
		}
		
	});
}


//4.앨범형리스트

function fn_albumList(){
	
	
	$('#boardTableList').children().hide();  //목록형 숨기기
	$('#listSelectValue').children().hide();  //리스트갯수옵션 숨기기

	let formData = $('#searchFormAlbum').serialize();

	let settings = {
		url : '/board/list/album',
		contentType : 'application/json',
		method : 'get',
		data : formData,
		dataType: 'json'
	};

	let divTag='';
	
	$.ajax(settings).done(function (resp){
		
		let pagingBoardList = resp.paging.dataList;
		let simpleCondition = resp.paging.simpleCondition;
		
		if(pagingBoardList[0] != null){
		
			let classIndex = 0;
		
			divTag += `
				<section id="landingTeam" class="section-py landing-team">
  					<div class="container">
   						<div class="row gy-5">
			`;
			$.each(pagingBoardList, function(i,v){
			
				const rndColor = ['label-primary','label-info', 'label-danger','label-success' ]; 
				let divClass = rndColor[classIndex];
				classIndex = (classIndex + 1) % rndColor.length;
				
		        if(v.boNo != null){
			          divTag +=`
			          	<div class="col-lg-3 col-sm-6" style=" margin-bottom: 90px;"  onclick="fn_boardDetail('${v.boNo}')" style="cursor: pointer;">
					        <div class="card mt-3 mt-lg-0 shadow-none">
					          <div class="bg-${divClass} position-relative team-image-box">
					            <img src=" ${v.boImage == null ? '/resources/images/noImage.png/' : v.boImage}" class="position-absolute card-img-position bottom-0 start-50 scaleX-n1-rtl img-fluid" alt="human image">
					          </div>
					          <div class="card-body border border-${divClass} border-top-0 text-center">
					            <h5 class="card-title mb-0">${v.boSj}</h5>
					            <p class="text-muted mb-0">${v.boNick}</p>
					          </div>
					        </div>
					      </div>
			          `;
				}else{
					divTag +=`
						<h5 class="card-title mb-0">내역없음</h5>
					`;
				}
			})
			divTag +=`
				    </div>
				  </div>
				</section>
				
				 <table class="table card-table">
		      		<tfoot id="boardAlbumPaging">
      				</tfoot>
    		  </table>
			`;
			 $('#albumList').html(divTag);
			 
			 divTag = `
				<tr>
					<td colspan="6">
							<hr class="my-0 mb-3 mt-2">
						${resp.paging.pagingHTML}
						<form id="searchFormAlbum">
							<div id ="searchUI" class="row g-3 d-flex justify-content-center">
								<input type="hidden" name="page" readonly="readonly"/>
								<input type="hidden" name="listNum" readonly="readonly" value="${resp.paging.listNum}"/>
								<input type="hidden" name="option" readonly="readonly" value="${resp.paging.option}" id="option"/>
								<div class="col-auto">
									<select name="searchType" class="form-select"> 
										<option value="" >전체</option>
										<option value="title" ${simpleCondition.searchType == "title" ? 'selected' : ''} >제목</option>
										<option value="writer" ${simpleCondition.searchType == "writer" ? 'selected' : ''}>작성자</option>
									</select>
								</div>
								<div class="col-auto">
									<input name="searchWord" placeholder="입력하세요" class="form-control" 
										value="${simpleCondition.searchWord != null ? simpleCondition.searchWord :''}"/>
								</div>
								<div class="col-auto">
									<input type="button" value="검색" id="searchAlbumBtn" class="btn btn-primary" />
								</div>
							</div>
						</form>
					</td>
				</tr>
				`;
        $('#boardAlbumPaging').html(divTag);
		}
	})
}



