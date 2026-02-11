<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="sysYear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set> 
<c:set var="sysMonth"><fmt:formatDate value="${now}" pattern="MM" /></c:set>
<c:set var="sysdate"><fmt:formatDate value="${now}" pattern="yyyyMMdd" /></c:set>
<c:set var="sysdateTime"><fmt:formatDate value="${now}" pattern="yyyyMMddHHmm" /></c:set>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>

<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script>
	function findUser() {
		
		// Validation 체크
		if($("#orgName").val().trim() === ""){
			alert("부서명은 필수 값입니다.");
			return;
		}
		
		if($("#userName").val().trim() === ""){
			alert("사용자명은 필수 값입니다.");
			return;
		}
		
	    $.get("/user/find", {
	        orgName: $("#orgName").val(),
	        userName: $("#userName").val()
	    }, function(res) {
	    	console.log(res);
	    	console.log(res.userId);
	        if (!res.userId) {
	            alert("사용자를 찾을 수 없습니다.");
	            return;
	        } else {
	        	$("#userId").val(res.userId);
	        	alert("등록된 사용자입니다.");
	        }
	        
	        loadEquipment(res.userId);
	    });
	}
	
	function loadEquipment(userId) {
	    $.get("/equipment/list", { userId }, function(list) {
	    	
	    	const tbody = document.getElementById("equipmentBody");
	    	tbody.replaceChildren();
	    	
	        list.forEach((e) => {
	        	tbody.appendChild(makeRow(e));
	        });
	    });
	}

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
	    
	    /* return `
	    <tr>
	        <td>
	        	<input type="hidden" class="equipmentId" value="${e.equipmentId!=null?e.equipmentId:''}">
	            <select class="networkType">
	                <option value="INT" ${e.networkType == 'INT'?'selected':''}>내부망</option>
	                <option value="EXT" ${e.networkType == 'EXT'?'selected':''}>외부망</option>
	            </select>
	        </td>
	        <td>
	            <select class="equipmentType">
	                <option value="DESKTOP" ${e.equipmentType == 'DESKTOP'?'selected':''}>데스크탑</option>
	                <option value="LAPTOP" ${e.equipmentType == 'LAPTOP'?'selected':''}>노트북</option>
	                <option value="KEYBOARD" ${e.equipmentType == 'KEYBOARD'?'selected':''}>키보드</option>
	                <option value="MONITOR" ${e.equipmentType == 'MONITOR'?'selected':''}>모니터</option>
	                <option value="ETC" ${e.equipmentType == 'ETC'?'selected':''}>기타장비</option>
	            </select>
	        </td>
	        <td><input class="serialNumber" value="${e.serialNumber!=null?e.serialNumber:''}"></td>
	        <td><input class="modelName" value="${e.modelName!=null?e.modelName:''}"></td>
	        <td><input class="acquisitionCost" value="${e.acquisitionCost!=null?e.acquisitionCost:''}"></td>
	        <td><input type="date" class="acquisitionDate" value="${e.acquisitionDate!=null?e.acquisitionDate:''}"></td>
	        <td>
	            <select class="status">
	                <option value="USE" ${e.status == 'USE'?'selected':''}>사용중</option>
	                <option value="RET" ${e.status == 'RET'?'selected':''}>반납</option>
	                <option value="BRK" ${e.status == 'BRK'?'selected':''}>고장</option>
	            </select>
	        </td>
	        <td><input class="remark" value="${e.remark!=null?e.remark:''}"></td>
	        <td><button class="btn" onclick="deleteRow(this)">삭제</button></td>
	    </tr>
	    `; */
	}

	function addRow() {
		
		$("#emptyRow").remove();
		
	    $("#equipmentBody").append(makeRow({}));
	}

	function saveEquipment() {
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
	            remark: $(this).find(".remark").val()
	        });
	    });

	    $.ajax({
	        url: "/equipment/save",
	        type: "POST",
	        contentType: "application/json",
	        data: JSON.stringify(list),
	        success: function() {
	            alert("저장되었습니다.");
	            loadEquipment($("#userId").val());
	        }
	    });
	}
	
	function deleteRow(btn) {
	    const tr = $(btn).closest("tr");
	    const equipmentId = tr.find(".equipmentId").val();

	    if (!equipmentId) {
	        tr.remove();
	        return;
	    }

	    $.post("/equipment/delete", { equipmentId }, function() {
	        tr.remove();
	    });
	}
	
	// 파일(csv)로 저장
	function saveToFile() {
		
		// Validation 체크
		if($("#orgName").val().trim() === ""){
			alert("부서명은 필수 값입니다.");
			return;
		}
		
		if($("#userName").val().trim() === ""){
			alert("사용자명은 필수 값입니다.");
			return;
		}
		
		if($("#userId").val().trim() === ""){
			alert("사용자ID는 필수 값입니다.");
			return;
		}
		
		if($("#equipmentBody tr").length == 1 && $("#equipmentBody tr").attr("id") === "emptyRow") {
			alert("행 추가를 눌러서 장비를 입력해주세요.");
			return;
		}
		
		// 저장 시작
	    const equipmentList = [];
	    $("#equipmentBody tr").each(function () {
	        equipmentList.push({
	            networkType: $(this).find(".networkType").val(),
	            equipmentType: $(this).find(".equipmentType").val(),
	            serialNumber: $(this).find(".serialNumber").val(),
	            modelName: $(this).find(".modelName").val(),
	            acquisitionCost: $(this).find(".acquisitionCost").val(),
	            acquisitionDate: $(this).find(".acquisitionDate").val(),
	            status: $(this).find(".status").val(),
	            remark: $(this).find(".remark").val()
	        });
	    });

	    const data = {
	    		orgName: $("#orgName").val(),
	        userName: $("#userName").val(),
	        userId: $("#userId").val(),
	        equipmentList: equipmentList
	    };

	    $.ajax({
	        url: "/comm/saveToFile",
	        type: "POST",
	        contentType: "application/json",
	        data: JSON.stringify(data),
	        success: function (r) {
	        	if(r.result === "OK") {
	        		alert("저장되었습니다.");
	        		location.href = "/main";
	        	} else {
	        		alert("오류가 발생했습니다.\n" + r.message);
	        	}
	        }
	    });
	}
	
	// 전체 현황 엑셀 다운로드
	function downloadExcel() {
		location.href = "/comm/file/excel/download";
	}
	
	// 조회
	function getUser() {
		if($("#orgName").val().trim() === ""){
			alert("부서명은 필수 값입니다.");
			return;
		}
		
		if($("#userName").val().trim() === ""){
			alert("사용자명은 필수 값입니다.");
			return;
		}
		
		if($("#userId").val().trim() === ""){
			alert("사용자ID는 필수 값입니다.");
			return;
		}
		
		const data = {
	    		orgName: $("#orgName").val(),
	        userName: $("#userName").val(),
	        userId: $("#userId").val()
	    };
		
		$.ajax({
	        url: "/comm/getUser",
	        type: "POST",
	        contentType: "application/json",
	        data: JSON.stringify(data),
	        success: function (list) {
	        	console.log(list);
	            const tbody = $("#equipmentBody tbody");
	            tbody.empty();
	            
	            let html = "";
	            list.forEach(function(e) {
	            	html += makeRow(e);
	            });
	            $("#equipmentBody").html(html);
		        
	            alert("조회하였습니다.");
	    	}
		});
	}

</script>

<!-- main -->
<title>정보화 장비 현황</title>
<main id="content">
	<!-- section01 main -->
	<section class="ctn01">
		<div class="form-row">
		    <label>부서명 :</label>
		    <input class="form-input" type="text" id="orgName">
		    <label>사용자명 :</label>
		    <input class="form-input" type="text" id="userName">
		    <label>사용자ID :</label>
		    <input class="form-input" type="text" id="userId">
		    <button class="btn" onclick="findUser()">확인</button>
		    <!-- <button class="btn" onclick="getUser()">조회</button> -->
		</div>
		
		<!-- <input type="hidden" id="userId"> -->
	</section>
	<!-- //section01 main -->

	<section>
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
		            <td colspan="9" class="empty-message">
		                사용자 확인을 먼저 하세요.<br>
   						<small>(상단에서 부서명 / 사용자명을 입력 후 확인)</small>
		            </td>
		        </tr>
		    </tbody>
		</table>
		
		<div class="table-btn-area">
			<button class="btn" onclick="addRow()">행 추가</button>
			<button class="btn" onclick="saveEquipment()">저장</button>
			<!-- <button class="btn" onclick="saveToFile()">저장</button> -->
			<button class="btn" onclick="downloadExcel()">엑셀 다운로드</button>
		</div>
	</section>
</main>
<!-- //main -->

<template id="equipment-row-template">
<tr>
    <td>
        <input type="hidden" class="equipmentId">
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

