<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="loginName" value="${sessionScope.loginName}" />

<!-- Navbar: Start -->
    <nav class="layout-navbar shadow-none py-0">
      <div class="container">
        <div class="navbar navbar-expand-lg landing-navbar px-3 px-md-4">
          <!-- Menu logo wrapper: Start -->
          <div class="navbar-brand app-brand demo d-flex py-0 me-4">
           
            <a href='<c:url value="/home.do"/>' class="app-brand-link">
              <span class="app-brand-logo demo">
              <!-- 로고-->
              <img src="/resources/images/heart.png" style="width: 45%;"></span>
              <span class="app-brand-text demo menu-text fw-bold ms-2 ps-1">도란도란</span>
            </a>
          </div>
          <!-- Menu logo wrapper: End -->
          <!-- Menu wrapper: Start -->
          <div class="collapse navbar-collapse landing-nav-menu" id="navbarSupportedContent">
            <button
              class="navbar-toggler border-0 text-heading position-absolute end-0 top-0 scaleX-n1-rtl"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent"
              aria-expanded="false"
              aria-label="Toggle navigation">
              <i class="tf-icons bx bx-x bx-sm"></i>
            </button>
            <ul class="navbar-nav me-auto">
              <li class="nav-item">
                <a class="nav-link fw-medium" aria-current="page" href="landing-page.html#landingHero">Home</a>
              </li>
              <c:if test="${not empty loginName }">
              	<c:if test="${loginName eq 'admin'}">
	              <li class="nav-item">
	                <a class="nav-link fw-medium" aria-current="page" href="landing-page.html#landingHero">안녕하세요 관리자님</a>
	              </li>
              	</c:if>
              	
              	<c:if test="${loginName ne 'admin'}">
	              <li class="nav-item">
	                <a class="nav-link fw-medium" aria-current="page" href="landing-page.html#landingHero">안녕하세요 ${loginName}님</a>
	              </li>
              	</c:if>
              </c:if>
            </ul>
          </div>
          <div class="landing-menu-overlay d-lg-none"></div>
          <!-- Menu wrapper: End -->
          <!-- Toolbar: Start -->
          <ul class="navbar-nav flex-row align-items-center ms-auto">
            <!-- Style Switcher -->
            <li class="nav-item dropdown-style-switcher dropdown me-2 me-xl-0">
              <a class="nav-link dropdown-toggle hide-arrow" href="javascript:void(0);" data-bs-toggle="dropdown">
                <i class="bx bx-sm"></i>
              </a>
              <ul class="dropdown-menu dropdown-menu-end dropdown-styles">
                <li>
                  <a class="dropdown-item" href="javascript:void(0);" data-theme="light">
                    <span class="align-middle"><i class="bx bx-sun me-2"></i>Light</span>
                  </a>
                </li>
                <li>
                  <a class="dropdown-item" href="javascript:void(0);" data-theme="dark">
                    <span class="align-middle"><i class="bx bx-moon me-2"></i>Dark</span>
                  </a>
                </li>
                <li>
                  <a class="dropdown-item" href="javascript:void(0);" data-theme="system">
                    <span class="align-middle"><i class="bx bx-desktop me-2"></i>System</span>
                  </a>
                </li>
              </ul>
            </li>
            <!-- / Style Switcher-->

            <!-- navbar button: Start -->
            <c:if test="${empty loginName}">
	            <li>
	              <a href='<c:url value="/login"></c:url>' class="btn btn-primary" >
	              <span class="tf-icons bx bx-user me-md-1"></span>
	              <span class="d-none d-md-block">Login</span></a>
	            </li>
	            <!-- navbar button: End -->
            </c:if>
            <c:if test="${not empty loginName}">
	            <li>
	              <a href='<c:url value="/login/logout"></c:url>' class="btn btn-primary" >
	              <span class="tf-icons bx bx-user me-md-1"></span>
	              <span class="d-none d-md-block">Logout</span></a>
	            </li>
	            <!-- navbar button: End -->
            </c:if>
          </ul>
          <!-- Toolbar: End -->
        </div>
      </div>
    </nav>
    <!-- Navbar: End -->

