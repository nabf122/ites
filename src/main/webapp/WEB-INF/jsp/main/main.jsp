<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="sysYear">
	<fmt:formatDate value="${now}" pattern="yyyy" />
</c:set>
<c:set var="sysMonth">
	<fmt:formatDate value="${now}" pattern="MM" />
</c:set>
<c:set var="sysdate">
	<fmt:formatDate value="${now}" pattern="yyyyMMdd" />
</c:set>
<c:set var="sysdateTime">
	<fmt:formatDate value="${now}" pattern="yyyyMMddHHmm" />
</c:set>

<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>

<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script>

	// 사용자 조회
	function findUserInfo() {
		
		if($("#orgName").val().trim() === "") {
			alert("부서명은 필수 값입니다.");
			return;
		}
		
		if($("#userName").val().trim() === "") {
			alert("사용자명은 필수 값입니다.");
			return;
		}
		
	    $.get("/user/find", {
	        orgName: $("#orgName").val(),
	        userName: $("#userName").val()
	    }, function(res) {
	        if (!res.userId) {
	            alert("사용자를 찾을 수 없습니다.");
	            return;
	        }
	        alert("사용자 정보를 불러왔습니다.");
	        $("#userFlag").val("Y");
	        $("#userId").val(res.userId);
	        
	        loadEquipment(res.userId);
	    });
	}
	
	// 사용자의 장비현황 조회
	function loadEquipment(userId) {
	    $.get("/equipment/list", { userId }, function(list) {
	    	
	    	const tbody = document.getElementById("equipmentBody");
	    	tbody.replaceChildren();
	    	
	        list.forEach((e) => {
	        	tbody.appendChild(makeRow(e));
	        });
	    });
	}

	// row 만들기
	function makeRow(e = {}) {
		
		const template = document.getElementById('equipment-row-template');
	    const row = template.content.cloneNode(true);

	    row.querySelector('.equipmentId').value = e.equipmentId ?? '';
	    row.querySelector('.networkType').value = e.networkType ?? 'INT';
	    row.querySelector('.equipmentType').value = e.equipmentType ?? 'DESKTOP';
	    row.querySelector('.serialNumber').value = e.serialNumber ?? '';
	    row.querySelector('.modelName').value = e.modelName ?? '';
	    row.querySelector('.acquisitionCost').value = e.acquisitionCost ?? '';
	    row.querySelector('.acquisitionDate').value = e.acquisitionDate ?? '';
	    row.querySelector('.status').value = e.status ?? 'USE';
	    row.querySelector('.remark').value = e.remark ?? '';
	    
	    return row;
	}

	// 행 추가
	function addRow() {
		
		$("#emptyRow").remove();
		$("#equipmentBody").append(makeRow({}));
	}

	// 장비 저장(생성/수정/삭제)에 대한 처리
	function saveEquipment() {
		
		// Validation 체크
		if($("#orgName").val().trim() === "") {
			alert("부서명은 필수 값입니다.");
			return;
		}
		
		if($("#userName").val().trim() === "") {
			alert("사용자명은 필수 값입니다.");
			return;
		}
		
		if($("#userId").val().trim() === "") {
			alert("사용자ID는 필수 값입니다.");
			return;
		}
		
		if($("#userId").val().trim() === "") {
			alert("사용자ID는 필수 값입니다.");
			return;
		}
		
		if($("#userFlag").val().trim() === "N") {
			alert("등록되지 않은 사용자입니다.\n'확인' 버튼을 눌러 등록된 사용자인지 확인하세요.");
			return;
		}
		
		if($("#equipmentBody tr").length == 1 && $("#equipmentBody tr").attr("id") === "emptyRow") {
			alert("행 추가를 눌러서 장비를 입력해주세요.");
			return;
		}
		// Validation 체크 종료
		
		if (!confirm("저장하시겠습니까?")) {
	        return; // 취소 누르면 종료
	    }
		
		// 취득가액 ',' 특수문자 일괄 제거
		$(".acquisitionCost").each(function() {
		    this.value = this.value.replace(/,/g, '');
		});
		
	    const list = [];
	    $("#equipmentBody tr").each(function() {
	        list.push({
	            equipmentId: $(this).find(".equipmentId").val(),
	            userId: $("#userId").val(),
	            networkType: $(this).find(".networkType").val(),
	            equipmentType: $(this).find(".equipmentType").val(),
	            serialNumber: $(this).find(".serialNumber").val(),
	            modelName: $(this).find(".modelName").val(),
	            acquisitionCost: $(this).find(".acquisitionCost").val(),
	            acquisitionDate: $(this).find(".acquisitionDate").val(),
	            status: $(this).find(".status").val(),
	            remark: $(this).find(".remark").val(),
	            rowStatus: $(this).find(".rowStatus").val()
	        });
	    });

	    $.ajax({
	        url: "/equipment/save",
	        type: "POST",
	        contentType: "application/json",
	        data: JSON.stringify(list),
	        success: function(res) {
	        	if(res.success){
	        		alert("저장되었습니다.");
		            loadEquipment($("#userId").val());
	        	} else {
	        		alert(res.message);
	        		console.log(res.error);
	        	}
	        },
	        error: function(xhr) {
	            if (xhr.responseJSON && xhr.responseJSON.message) {
	                alert(xhr.responseJSON.message);
	            } else {
	                alert("서버 오류가 발생했습니다.");
	            }
	        }
	    });
	}
	
	// 삭제 버튼 클릭 처리
	function deleteRow(btn) {
	    const $tr = $(btn).closest("tr");
	    const equipmentId = $tr.find(".equipmentId").val();
	    
	    if (!confirm("삭제하시겠습니까?")) return;

	    if (!equipmentId) {
	        $tr.remove();
	        return;
	    } else {
	    	$tr.addClass("deleted-row");
	    	$tr.find(".rowStatus").val("D");
	    }

	}
	
	// 엑셀 다운로드
	function downloadExcel() {
		location.href = "/equipment/excel";
	}
	
	// 수정 상태 반영
	$(document).on("change", 
		".networkType, .equipmentType, .serialNumber, .modelName, .acquisitionCost, .acquisitionDate, .status, .remark",
		function () {

		    const $tr = $(this).closest("tr");
		    const $status = $tr.find(".rowStatus");
			
		    if($tr.find(".equipmentId").val() != "" && $status.val() != "D") {
		    	$status.val("U");
		    }
	});

	// 포커스 인 → 콤마 제거
	$(document).on("focus", ".acquisitionCost", function() {
	    this.value = this.value.replace(/,/g, '');
	});

	// 포커스 아웃 → 콤마 적용
	$(document).on("blur", ".acquisitionCost", function() {
	    let value = this.value.replace(/[^0-9]/g, '');
	    this.value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	});

</script>

<!--  -->
<title>장비현황관리</title>
<div class="container">
	<div class="row">
		<div class="form-row">
			<input type="hidden" id="userFlag" value = "N">
			<label>부서</label> 
			<input class="form-input" type="text" id="orgName"> 
				
			<label>사용자</label> 
			<input class="form-input" type="text" id="userName">
			
			<label>사번</label>
			<input class="form-input" type="text" id="userId">
			
			<button class="btn primary" onclick="findUserInfo()">확인</button>
		</div>
	
		<table class="table">
			<thead>
				<tr>
					<th>망구분</th>
					<th>장비타입</th>
					<th>시리얼번호</th>
					<th>모델명</th>
					<th>취득가액</th>
					<th>취득시기</th>
					<th>상태</th>
					<th>비고</th>
					<th>관리</th>
				</tr>
			</thead>
			<tbody id="equipmentBody">
				<!-- 초기 안내 메시지 -->
				<tr id="emptyRow">
					<td colspan="9" class="empty-message">사용자 확인을 먼저 하세요.<br>
						<small>(상단에서 부서/사용자를 입력 후 확인)</small>
					</td>
				</tr>
			</tbody>
		</table>

		<div class="table-btn-area">
			<button class="btn" onclick="addRow()">행 추가</button>
			<button class="btn primary" onclick="saveEquipment()">저장</button>
			<button class="excel-btn" onclick="downloadExcel()">엑셀 다운로드(전체)</button>
		</div>
	</div>
</div>

<!-- // -->

<template id="equipment-row-template">
	<tr>
	    <td>
	        <input type="hidden" class="equipmentId">
	        <input type="hidden" class="rowStatus">
	        <select class="networkType">
	            <option value="INT">내부망</option>
	            <option value="EXT">외부망</option>
	        </select>
	    </td>
	    <td>
	        <select class="equipmentType">
	            <option value="DESKTOP">데스크탑</option>
	            <option value="LAPTOP">노트북</option>
	            <option value="KEYBOARD">키보드</option>
	            <option value="MONITOR">모니터</option>
	            <option value="ETC">기타장비</option>
	        </select>
	    </td>
	    <td><input class="serialNumber"></td>
	    <td><input class="modelName"></td>
	    <td><input class="acquisitionCost"></td>
	    <td><input type="date" class="acquisitionDate"></td>
	    <td>
	        <select class="status">
	            <option value="USE">사용중</option>
	            <option value="RET">반납</option>
	            <option value="BRK">고장</option>
	        </select>
	    </td>
	    <td><input class="remark"></td>
	    <td><button class="btn btn-delete" onclick="deleteRow(this)">삭제</button></td>
	</tr>
</template>
