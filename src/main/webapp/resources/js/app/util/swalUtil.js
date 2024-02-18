/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2024. 1. 22.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        	수정자       수정내용
 * --------     	--------    ----------------------
 * 2024. 1. 22.      작성자명       최초작성
 * Copyright (c) 2024 by DDIT All right reserved
 * </pre>
 */ 

//값 입력
function fn_inputPasswordWhenDelete(boNo){
	
	Swal.fire({
	  title: "비밀번호를 입력하세요.",
	  input: "password",
	  inputAttributes: {
	    autocapitalize: "off"
	  },
	  showCancelButton: true,
	  confirmButtonText: "확인",
	  showLoaderOnConfirm: true,
	  preConfirm: async (boPwCheck) => {
	    try {
	    	const response = await $.ajax({
	            url: "/board/pwCheck",  // 컨트롤러 경로
	            method: "POST",
	            data: { 
	           		boPwCheck: boPwCheck
	           		, boNo : boNo },
	            dataType: "json"
	        });
	     
	      if (response.success != "Y") {
	         throw new Error("비밀번호가 일치하지 않습니다.");
	      }else{
		      	$.ajax({
					type: "POST",
					dataType : "json",
					url: "/board/delete",
					data:{
						"boNo" : boNo
					},
					success: function (data) {
						var text;
						var icon;
						if (data.success == "Y") {
							text = "게시글 삭제가 완료되었습니다.";
							icon = "success";
						} else {
							text = "게시글 삭제가 실패되었습니다.";
							icon = "warning";
						}
						//모달창열기
						fn_swalComplete(text, icon, "/home.do", data.success);
					},
					error: function () {
						fn_swalError();
					}
				});
	      }
	      //return response.json();
	    } catch (error) {
	    	Swal.fire({
				text: error.message,
				icon: "error",
				//showCancelButton: true,
				confirmButtonText: '확인',
				customClass: {
					confirmButton: 'btn btn-primary me-2',
					//cancelButton: 'btn btn-label-secondary'
				},
				buttonsStyling: false
			})
	      //Swal.showValidationMessage("잠시 후 다시 시도하세요.");
	    }
	  },
	  allowOutsideClick: () => !Swal.isLoading()
	})
}


//값 입력
function fn_inputPassword(boNo,uri){
	
	Swal.fire({
	  title: "비밀번호를 입력하세요.",
	  input: "password",
	  inputAttributes: {
	    autocapitalize: "off"
	  },
	  showCancelButton: true,
	  confirmButtonText: "확인",
	  showLoaderOnConfirm: true,
	  preConfirm: async (boPwCheck) => {
	    try {
	    	const response = await $.ajax({
	            url: "/board/pwCheck",  // 컨트롤러 경로
	            method: "POST",
	            data: { 
	           		boPwCheck: boPwCheck
	           		, boNo : boNo },
	            dataType: "json"
	        });
	     
	      if (response.success != "Y") {
	         throw new Error("비밀번호가 일치하지 않습니다.");
	      }else{
	      	var formData = $('#goEditForm');
	      	formData.attr("action",uri+"/"+boNo);
	      	$('#formId').val(boPwCheck);
	      	formData.submit();
	      }
	      //return response.json();
	    } catch (error) {
	    	Swal.fire({
				text: error.message,
				icon: "error",
				//showCancelButton: true,
				confirmButtonText: '확인',
				customClass: {
					confirmButton: 'btn btn-primary me-2',
					//cancelButton: 'btn btn-label-secondary'
				},
				buttonsStyling: false
			})
	      //Swal.showValidationMessage("잠시 후 다시 시도하세요.");
	    }
	  },
	  allowOutsideClick: () => !Swal.isLoading()
	}).then((result) => {
	  
	});
}


//에러창
function fn_swalError(){
	Swal.fire({
		text: "처리 중 에러가 발생했습니다. 다시 시도하세요.",
		icon: "error",
		//showCancelButton: true,
		confirmButtonText: '확인',
		customClass: {
			confirmButton: 'btn btn-primary me-2',
			//cancelButton: 'btn btn-label-secondary'
		},
		buttonsStyling: false
	})
}

//에러창
function fn_swalLoginFail(){
	Swal.fire({
		text: "비밀번호가 일치하지 않습니다.",
		icon: "error",
		//showCancelButton: true,
		confirmButtonText: '확인',
		customClass: {
			confirmButton: 'btn btn-primary me-2',
			//cancelButton: 'btn btn-label-secondary'
		},
		buttonsStyling: false
	})
}

/**
 * 알림창
 * text  : 문구
 * icon  : 아이콘
 */
function fn_swalAlert(text, icon){
	Swal.fire({
		text: text,
		icon: icon,
		//showCancelButton: true,
		confirmButtonText: '확인',
		customClass: {
			confirmButton: 'btn btn-primary me-2',
			//cancelButton: 'btn btn-label-secondary'
		},
		buttonsStyling: false
	})
}

/**
 * 완료 시 알림창
 * text  : 문구
 * icon  : 아이콘
 * moveUrl  : 버튼 클릭 시 이동할 주소
 * succeccYn  : 성공여부
 */
function fn_swalComplete(text, icon, moveUrl, succeccYn){
	
	Swal.fire({
		text: text,
		icon: icon,
		confirmButtonText: '확인',
		customClass: {
			confirmButton: 'btn btn-primary me-2',
		},
		buttonsStyling: false
	}).then((result) => {
		if (succeccYn != "N") {
			if (result.isConfirmed) {
				location.href = moveUrl;
			}
		}
	});
}

/**
 * confirm창
 * text  : 문구
 * handler  : 버튼클릭 시 실행할 함수
 */
function fn_swalConfirm(text, handler){
	
	Swal.fire({
		text: text,
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: '확인',
		customClass: {
			confirmButton: 'btn btn-primary me-2',
			cancelButton: 'btn btn-label-secondary'
		},
		buttonsStyling: false
	}).then((result) => {
		if (result.isConfirmed) {
			handler();
		}
	});
}