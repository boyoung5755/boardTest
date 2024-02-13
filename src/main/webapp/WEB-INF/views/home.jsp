<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
  <div class="layout-container">
    <!-- Layout container -->
    <div class="layout-page">
      <!-- Content wrapper -->
      <div>
        <!-- Content -->
        <!--일감테이블-->
        <div class="card">
          <div class="table-responsive text-nowrap card-body">
            <h5 class="card-title"><i class='bx bx-book-heart bx-md mb-1'></i>자유게시판</h5>
            <hr>
            <div class="row mx-2">
            	<div class="col-md-2 " id="listSelectValue" >
            		<div class="me-3">
            			<div class="dataTables_length" id="DataTables_Table_0_length">
            				<label>
            					<select name="DataTables_Table_0_length" aria-controls="DataTables_Table_0" class="form-select" 
            						onclick="fn_listChange(this.value)"
            					>
            						<option disabled="disabled" selected="selected">선택</option>
            						<option value="5">5개씩</option>
            						<option value="10">10개씩</option>
            						<option value="20">20개씩</option>
            					</select>
            				</label>
            			</div>
            		</div>
            	</div>
            	<div class="col-md-10">
	            	<div class="dt-action-buttons text-xl-end text-lg-start text-md-end text-start d-flex align-items-center justify-content-end flex-md-row flex-column mb-3 mb-md-0">
		            	<div class="dt-buttons"> 
			            	<button class="dt-button buttons-collection  btn btn-label-secondary " onclick="fn_albumList()">
				            	<span><i class="bx bx-grid-alt fs-4"></i></span>
			            	</button>
			            	<button class="dt-button buttons-collection  btn btn-label-secondary " onclick="fn_boardPaging()">
				            	<span><i class="bx bx-spreadsheet fs-4"></i></span>
			            	</button>
			            	<button class="dt-button add-new btn btn-primary" onclick="fn_boardForm()">
			            		<span><i class="bx bx-plus me-0 me-sm-1"></i>
			            			<span class="d-none d-sm-inline-block">글쓰기</span>
			            		</span>
			            	</button>
		            	</div>
            		</div>
            	</div>
            </div>
            <hr>
            <div id="albumList"></div>
            <div id="boardTableList">
	            <table class="table card-table">
	              <colgroup>
				   	<col width="10%" />
				   	<col width="45%" />
				   	<col width="10%" />
				   	<col width="20%" />
				   	<col width="15%" />
	 	  		</colgroup>
	              <thead>
	                <tr>
	                  <th>NO</th>
	                  <th>제목</th>
	                  <th>작성자</th>
	                  <th>작성일</th>
	                  <th>조회수</th>
	                </tr>
	              </thead>
	              <tbody id="boardList" class="table-border-bottom-0">
	                <!-- 게시판 비동기 -->
	              </tbody>
	              <tfoot id="boardPaging">
	              </tfoot>
	            </table>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- /Content -->
  </div>
  <!-- /Content wrapper -->
</div>
<!-- /Layout container -->

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app/board/board.js"></script>