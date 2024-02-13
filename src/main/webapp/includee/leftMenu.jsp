<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="currUri" value='<%=request.getAttribute("javax.servlet.forward.request_uri") %>'/>
<!-- Menu -->
<aside id="layout-menu" class="layout-menu menu-vertical menu bg-menu-theme">
  <div class="app-brand demo">
    <a 
    	href='<c:if test="${realUser.username   ne 'admin'}">/dashBoard/${realUser.username}</c:if>' 
    	href='<c:if test="${realUser.username   eq 'admin'}">#</c:if>'   class="app-brand-link">
      <span class="app-brand-logo demo">
        <img src="/resources/images/heart.png" style="width: 45%;"></span>
      <span class="app-brand-text demo menu-text fw-bold ms-n4">mannaduo</span>
    </a> <a href="javascript:void(0);" class="layout-menu-toggle menu-link text-large ms-auto"> <i
        class="bx bx-chevron-left bx-sm align-middle"></i>
    </a>
  </div>
  <div class="menu-inner-shadow"></div>

  <c:if test="${realUser.username   ne 'admin'}">

    <ul class="menu-inner py-1 ps ps--active-y">
      <!-- Dashboards -->
      <li class="menu-item  <c:if test='${fn:startsWith(currUri, "/dashBoard")}'>active</c:if>" >
        <a href="/dashBoard/${realUser.username}" class="menu-link ">
          <i class="menu-icon tf-icons bx bx-home-circle"></i>
          <div class="text-truncate">Home</div>
        </a>
      </li>
      <!-- 테스트 -->
      <li class="menu-item  <c:if test='${fn:startsWith(currUri, "/dashBoard")}'>active</c:if>" >
        <a href="/dashBoard/${realUser.username}" class="menu-link ">
          <i class='menu-icon bx bxs-heart bx-tada' ></i>
          <div class="text-truncate">러브테스트</div>
        </a>
      </li>

      <!-- 매칭 -->
      <li class="menu-header small text-uppercase">
        <span class="menu-header-text">MATCHING</span>
      </li>
      
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/notice") or fn:startsWith(currUri, "/event") or fn:startsWith(currUri, "/자유게시판") or fn:startsWith(currUri, "/faq")}'>active open</c:if>">
        <a class="menu-link menu-toggle">
          <i class='menu-icon bx bx-donate-heart'></i>
          <div class="text-truncate">서비스</div>
        </a>
        <ul class="menu-sub">
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/notice")}'>active</c:if>">
            <a href="<c:url value='/notice' />" class="menu-link">
              <div class="text-truncate">회원현황</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/event")}'>active</c:if>">
            <a href="<c:url value='/event/home'/>" class="menu-link">
              <div class="text-truncate">채팅</div>
            </a>
          </li>
        </ul>
      </li>
      <!-- 게시판 -->
      <li class="menu-header small text-uppercase">
        <span class="menu-header-text">BOARD</span>
      </li>
      
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/notice") or fn:startsWith(currUri, "/event") or fn:startsWith(currUri, "/자유게시판") or fn:startsWith(currUri, "/faq")}'>active open</c:if>">
        <a class="menu-link menu-toggle">
          <i class="menu-icon tf-icons bx bx-clipboard"></i>
          <div class="text-truncate">게시판</div>
        </a>
        <ul class="menu-sub">
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/notice")}'>active</c:if>">
            <a href="<c:url value='/notice' />" class="menu-link">
              <div class="text-truncate">맛집추천</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/event")}'>active</c:if>">
            <a href="<c:url value='/event/home'/>" class="menu-link">
              <div class="text-truncate">데이트장소추천</div>
            </a>
          </li>
        </ul>
      </li>
      <!-- user -->
      <li class="menu-header small text-uppercase <c:if test='${fn:startsWith(currUri, "/org") or fn:startsWith(currUri, "/mypage")}'>active</c:if>">
        <span class="menu-header-text">USER</span>
      </li>
      <!-- 조직도 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/org")}'>active</c:if>">
        <a href="<c:url value='/org'/>" class="menu-link">
          <i class='menu-icon bx bx-user-pin'></i>
          <div class="text-truncate">마이페이지</div>
        </a>
      </li>
      <!-- 조직도 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/org")}'>active</c:if>">
        <a href="<c:url value='/org'/>" class="menu-link">
          <i class='menu-icon bx bx-coin' ></i>
          <div class="text-truncate">멤버쉽</div>
        </a>
      </li>
    </ul>
  </c:if>
</aside>
<!-- / Menu -->