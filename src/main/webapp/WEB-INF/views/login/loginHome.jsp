<%--
* [[개정이력(Modification Information)]]
* 수정일       수정자        수정내용
* ----------  ---------  -----------------
* 2024. 2. 18.      김보영        최초작성
* Copyright (c) 2024 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


 
<div class="container-xxl" style="width: 30%;">
      <div class="authentication-wrapper authentication-basic container-p-y">
        <div class="authentication-inner">
          <!-- Register Card -->
          <div class="card">
            <div class="card-body">
              <!-- Logo -->
              <div class="app-brand justify-content-center">
                <a href="index.html" class="app-brand-link gap-2">
                  <span class="app-brand-logo demo">
                  </span>
                  <span class="app-brand-text demo text-body fw-bold">로그인</span>
                </a>
              </div>
              <!-- /Logo -->

              <form id="formAuthentication" class="mb-3 fv-plugins-bootstrap5 fv-plugins-framework" >
                <div class="mb-3 fv-plugins-icon-container">
                  <label for="username" class="form-label">아이디</label>
                  <input type="text" class="form-control" id="username" name="username" placeholder="아이디" >
                <div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div></div>
               <div class="mb-3 form-password-toggle fv-plugins-icon-container">
                  <label class="form-label" for="password">비밀번호</label>
                  <div class="input-group input-group-merge has-validation">
                    <input type="password" id="password" class="form-control" name="password" placeholder="············" aria-describedby="password">
                    <span class="input-group-text cursor-pointer"><i class="bx bx-hide"></i></span>
                  </div><div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
                </div>

                <button type="button" class="btn btn-primary d-grid w-100" onclick="fn_login()">로그인</button>
              <input type="hidden">
              </form>

            </div>
          </div>
          <!-- Register Card -->
        </div>
      </div>
    </div>
    
<script>


//로그인
function fn_login(){
	
	var dataForm = new FormData($("#formAuthentication")[0]);
	
	
	$.ajax({
		type: "POST",
		url: "/login/auth",
		data: dataForm,
		processData: false,
		contentType: false,
		cache: false,
		success: function (data) {
			
			if (data.success == "Y") {
				location.href='/home.do';
				
			}else if(data.success == "N"){
				fn_swalLoginFail();
			}
			
		},
		error: function () {
			fn_swalError();
		}
	});
	
}




</script>  
    