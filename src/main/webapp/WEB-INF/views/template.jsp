<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html class="light-style layout-navbar-fixed layout-wide" data-template="front-pages" data-theme="theme-default" dir="ltr" >
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />
		
		<title>도란도란</title>
		
		<meta name="description" content="" />
		<meta name="keywords"
			content="dashboard, bootstrap 5 dashboard, bootstrap 5 design, bootstrap 5">
		
		<tiles:insertAttribute name="preScript" />
		<tiles:insertAttribute name="cssScript" />
				
		<style>
			
		</style>
		
		</head>
		<body  data-context-path="${pageContext.request.contextPath }" style="background-color: #F5F5F9;">
		<script src="/resources/assets/vendor/js/dropdown-hover.js"></script>
    	<script src="/resources/assets/vendor/js/mega-dropdown.js"></script>
			<tiles:insertAttribute name="headerMenu" />
			
			<div data-bs-spy="scroll" class="scrollspy-example" style="background-color: #F5F5F9;">
				<section id="hero-animation">
			        <div id="landingHero" class="section-py landing-hero position-relative">
			          <div class="container">
			            <div class="hero-text-box text-center">
			              <h1 class="text-primary hero-title display-4 fw-bold">Tell me your story</h1>
			            	<div class="input-wrapper my-3 input-group input-group-lg input-group-merge position-relative mx-auto">
						      <span class="input-group-text" id="basic-addon1"><i class="bx bx-search bx-sm"></i></span>
						      <input type="text" class="form-control" placeholder="Search" aria-label="Search" aria-describedby="basic-addon1">
						    </div>
			            </div>
			          </div>
			        </div>
			      </section>
				</div>
	    		<!-- Sections:Start -->
	
	   			<div class="container-xxl flex-grow-1 container-p-y" >
					<!-- Main Content Area start -->
					<tiles:insertAttribute name="content" />
					<!-- Main Content Area end -->
				</div>
	
	    		<!-- / Sections:End -->
   			<tiles:insertAttribute name="footer" />
		</body>
		<tiles:insertAttribute name="postScript" />
</html>
