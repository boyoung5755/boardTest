<%--
* [[개정이력(Modification Information)]]
* 수정일       수정자        수정내용
* ----------  ---------  -----------------
* 2024. 1. 24.      김보영        최초작성
* Copyright (c) 2024 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<style>

.fileBody{
    border-style: dashed;
    border-width: 2px;
    border-radius: 10px;
    text-align: center;
}

.valid{
	color : red;
	display : none;
}

</style>

<script src="<c:url value='/resources/js/ckeditor/ckeditor.js'/>"></script>

<div class="row">
<!-- Form Separator -->
<input type="hidden" value="${rndStr}" id="fileCode" name="fileCode">
<!-- Form Label Alignment -->
<div class="col-xxl">
  <div class="card mb-4">
    <h5 class="card-header">자유게시판 등록</h5>
    <hr>
    <form class="card-body" id="formBoard">
      <h6>1. 사용자 정보</h6>
      <div class="row mb-3">
        <label class="col-sm-3 col-form-label text-sm-end" for="boNick">닉네임</label>
        <div class="col-sm-9">
          <input type="text" id="boNick" name="boNick" class="form-control" placeholder="닉네임을 입력하세요.">
        	<div><label id="nickNameEmpty" class="valid">닉네임을 입력하세요</label></div>
        </div>
      </div>
      <div class="row mb-3">
        <label class="col-sm-3 col-form-label text-sm-end" for="boPw">비밀번호</label>
        <div class="col-sm-5">
          <input type="password" id="boPw" name="boPw" class="form-control" placeholder="비밀번호를 입력하세요.">
        	<div><label id="passwordMismatch" class="valid">비밀번호가 다릅니다.</label></div>
        	<div><label id="passwordEmpty" class="valid"></label></div>
        </div>
        <div class="col-sm-4">
          <input type="password" id="boPwCheck" name="boPwCheck" class="form-control" placeholder="비밀번호를 한번 더 입력하세요.">
        </div>
      </div>
      <hr class="my-4 mx-n4">
      <h6>2. 게시글 내용</h6>
      <div class="row mb-3">
        <label class="col-sm-3 col-form-label text-sm-end" for="boSj">제목</label>
        <div class="col-sm-9">
          <input type="text" id="boSj" name="boSj" class="form-control" placeholder="제목을 입력하세요.">
       		<div><label id="boSjEmpty" class="valid"></label></div>
        </div>
      </div>
      <div class="row mb-3">
        <label class="col-sm-3 col-form-label text-sm-end" for="boCn">내용</label>
        <div class="col-sm-9">
        	<textarea name="boCn"  id="boCn" rows="5" cols="10" class="form-control"></textarea>
       		<div><label id="boCnEmpty" class="valid"></label></div>
        </div>
      </div>
<!--       <div class="row"> -->
<!--         <label class="col-sm-3 col-form-label text-sm-end" for="boCn">파일첨부</label> -->
<!--         <div class="col-sm-9"> -->
<!--         	<input type="file" class="form-control" id="insertBoardFile"  multiple> -->
<!--         </div> -->
<!--       </div> -->
      </form>
      <div class="card-body">
	      <div class="row mb-3">
	      	<label class="col-sm-3 col-form-label text-sm-end" for="boCn"></label>
	        <div class="col-sm-9 ">
	        	<div class="">
			        <form action="" enctype="multipart/form-data" class="dropzone fileBody">
		    		</form>
		         </div>
	        </div>
	      </div>
	      <div class="pt-4">
	        <div class="row justify-content-end">
	          <div class="col-sm-9">
	            <button type="button" class="btn btn-primary me-sm-2 me-1" onclick="fn_insertBoard()">등록</button>
	            <button type="button" class="btn btn-label-info" onclick="fn_fillDummy()">TEST용</button>
	            <button type="button" class="btn btn-label-secondary" onclick="location.href='<c:url value='/home.do' />'">취소</button>
	          </div>
	        </div>
	      </div>
      </div>
    </div>
  </div>
</div>



<script src="${pageContext.request.contextPath}/resources/js/app/board/board.js"></script>

<script>

CKEDITOR.replace('boCn',{
    filebrowserImageUploadUrl:`<c:url value='/board/image?type=image'/>`
});


Dropzone.autoDiscover = false; // deprecated 된 옵션. false로 해놓는걸 공식문서에서 명시

var fileCode=$('#fileCode').val();

//Dropzone 인스턴스 생성
var dropzone = new Dropzone(".dropzone", {
    url: "/board/fileUpload/"+fileCode, // 파일 업로드를 처리할 서버의 URL을 입력하세요.
    method:"post",
    paramName: "boardFile", // 서버에서 해당 이름으로 파일을 받을 수 있도록 지정
    addRemoveLinks: true, // 각 파일 옆에 삭제 링크 표시
    maxFilesize: 100, // 파일 크기 제한 (MB 단위)
    parallelUploads: 10, // 동시에 업로드할 파일 수
    uploadMultiple:true, //한번의 요청으로 여러 파일을 보낼지 여부
    createImageThumbnails: true, //이미지 썸네일생성여부
    autoQueue: false,
    dictDefaultMessage: "파일을 드래그하여 업로드하세요",
    // 다양한 Dropzone 옵션들을 추가할 수 있습니다.
});


</script>