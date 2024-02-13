<%--
* [[개정이력(Modification Information)]]
* 수정일       		수정자        수정내용
* ----------  		---------  -----------------
* 2024. 1. 25.      김보영        최초작성
* Copyright (c) 2024 by DDIT All right reserved
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>



<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
  <div class="layout-container">
    <!-- Layout container -->
    <div class="layout-page">
      <!-- Content wrapper -->
       <!-- Content -->
      <div>
        <div class="col-xl-12 col-md-8 col-12 mb-md-0 mb-4">
          <div class="card invoice-preview-card">
            <div class="card-body">
              <div class="d-flex justify-content-between flex-xl-row flex-md-column flex-sm-row flex-column p-sm-3 p-0">
                <div class="mb-xl-0 mb-4">
                  <div class="d-flex svg-illustration mb-3 gap-2">
                    <span class="app-brand-text demo text-body fw-bold">${detail.boSj}</span>
                  </div>
                  <p class="mb-1">${detail.boNick}</p>
                  <p class="mb-1">${detail.boRdate}</p>
                </div>
                <div>
                  <h4>조회 ${detail.boRdcnt}</h4>
                  <div class="mb-2">
                    <span class="me-1"><i class='bx bx-message-rounded-dots'></i>댓글수</span>
                  </div>
                  <div class="mb-2">
                    <span class="fw-medium"><i class='bx bx-like'></i>좋아요수</span>
                  </div>
                </div>
              </div>
            </div>
            <hr class="my-0">
            <div class="card-body">
             	${detail.boCn}
            </div>
			<hr class="my-0">
            <div class="card-body">
              <div class="row">
                <div class="col-12">
	                <span class="fw-medium">첨부파일　</span>
                	<c:forEach items="${detail.fileList }" var="file">
	                  <a href="/board//download?fileNo=${file.fileNo}">
	                    <i class="bx bx-check"></i><span class="fw-medium mx-2 fs-5">${file.fileName}</span>
	                    <span>${file.fileFancysize}</span>　
	                  </a>
                	</c:forEach>	
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
	 <div id="commArea">
      <!-- 댓글비동기-->
	 </div>
	 
    <!--  -->
    <form id="goEditForm" method="post">
		<input type="hidden"  value="" id="formId"  name="boPwCheck"/>
    </form>

      <div class="col-xl-12 col-md-8 col-12 mb-md-0 mb-4 mt-3" >
          <div class="card ">
            <div class="card-body">
               <form id="insertComm" class="form-send-message d-flex justify-content-between align-items-center">
                 <input id="commCn" name="commCn"  class="form-control message-input border-0 me-3 shadow-none" placeholder="댓글을 남겨보세요">
                 <input type="hidden" value="${detail.boNo}" name="boNo" id="boNo"/>
                 <div>
                   <button  type="button" class="btn btn-primary d-flex send-msg-btn" style="width: 110%;"
                   	onclick="fn_insertComm()">
                     <i class="bx bx-paper-plane "></i>
                     <span class="align-middle d-md-inline-block d-none">등록</span>
                   </button>
                 </div>
               </form>
            </div>
          </div>
      </div>
      
      <div class="demo-inline-spacing">
         <button type="button" class="btn btn-label-info" onclick="fn_boardForm()">글쓰기</button>
         <button type="button" class="btn btn-label-warning" onclick="fn_boardEdit(${detail.boNo})">수정</button>
         <button type="button" class="btn btn-label-danger" onclick="fn_deleteBoard(${detail.boNo})">삭제</button>
         <button type="button" class="btn btn-label-dark" onclick="fn_homeList()">목록</button>
       </div>
    <!-- /Content -->
    </div>
  </div>
  <!-- /Content wrapper -->
</div>
<!-- /Layout container --> 

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app/board/board.js"></script>
