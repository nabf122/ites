<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>장비현황(관리자)</title>
  <style>
    body { font-family: Arial, sans-serif; background:#f5f6f7; margin:0; }
    .topbar { background:#0f4aa3; color:#fff; padding: 14px 18px; display:flex; justify-content:space-between; align-items:center; }
    .topbar a { color:#fff; text-decoration:none; font-weight:700; }
    .wrap { padding: 18px; }
    .card { background:#fff; border:1px solid #ddd; border-radius:10px; padding: 16px; margin-bottom: 12px; }
    h2 { margin:0 0 12px 0; font-size: 18px; }
    .grid { display:grid; grid-template-columns: repeat(6, 1fr); gap: 10px; }
    .field label { display:block; font-size: 12px; color:#444; margin-bottom: 6px; }
    .field input, .field select { width:90%; height:36px; border:1px solid #ccc; border-radius:8px; padding: 0 10px; }
    .actions { display:flex; gap:10px; justify-content:flex-end; margin-top: 10px; }
    .btn { height:38px; padding:0 14px; border-radius:8px; border:1px solid #ccc; background:#fff; cursor:pointer; font-weight:700; }
    .btn.primary { border-color:#1e6bd6; background:#1e6bd6; color:#fff; }
    .btn.success { border-color:#0a8a3a; background:#0a8a3a; color:#fff; }
    .meta { display:flex; justify-content:space-between; align-items:center; gap:10px; margin-top: 8px; color:#333; font-size: 13px; }
    table { width:100%; border-collapse:collapse; }
    th, td { border-bottom:1px solid #eee; padding:10px 8px; font-size: 13px; text-align:left; }
    th { background:#fafafa; font-weight:700; }
    .empty { text-align:center; color:#666; padding: 24px 0; }
    .pager { display:flex; justify-content:center; align-items:center; gap: 6px; margin-top: 12px; }
    .pager a, .pager span { display:inline-block; padding: 6px 10px; border:1px solid #ddd; border-radius:8px; background:#fff; text-decoration:none; color:#333; font-size: 13px; }
    .pager .on { background:#1e6bd6; border-color:#1e6bd6; color:#fff; font-weight:700; }
    .right { display:flex; gap:10px; align-items:center; }
    .note { color:#666; font-size: 12px; margin-top: 6px; }
    @media (max-width: 1100px) { .grid { grid-template-columns: repeat(3, 1fr);} }
    @media (max-width: 700px) { .grid { grid-template-columns: repeat(2, 1fr);} }
  </style>
</head>

<body>
  <div class="topbar">
    <div><a href="<c:url value='/admin/list'/>">관리자 · 장비현황</a></div>
    <div class="right">
      <a href="<c:url value='/admin/logout'/>">로그아웃</a>
    </div>
  </div>

  <div class="wrap">
    <div class="card">
      <h2>장비 현황 조회</h2>

      <!-- 검색 폼: GET 방식 / page는 검색 시 1로 초기화 -->
      <form method="post" action="<c:url value='/admin/list'/>" id="searchForm">
        <input type="hidden" name="page" value="1"/>

        <div class="grid">
          <div class="field">
            <label>사용자ID</label>
            <input type="text" name="userId" value="${cond.userId}" placeholder="user_id"/>
          </div>

          <div class="field">
            <label>망구분</label>
            <select name="networkType">
              <option value="">전체</option>
              <option value="업무망" ${cond.networkType=='업무망' ? 'selected' : ''}>업무망</option>
              <option value="인터넷망" ${cond.networkType=='인터넷망' ? 'selected' : ''}>인터넷망</option>
            </select>
          </div>

		<div class="field">
			<label>장비타입</label>
            <select name="equipmentType">
				<option value="">전체</option>
				<option value="DESKTOP" ${cond.equipmentType=='DESKTOP' ? 'selected' : ''}>데스크탑</option>
            	<option value="LAPTOP" ${cond.equipmentType=='LAPTOP' ? 'selected' : ''}>노트북</option>
            	<option value="KEYBOARD" ${cond.equipmentType=='KEYBOARD' ? 'selected' : ''}>키보드</option>
            	<option value="MONITOR" ${cond.equipmentType=='MONITOR' ? 'selected' : ''}>모니터</option>
            	<option value="ETC" ${cond.equipmentType=='ETC' ? 'selected' : ''}>기타장비</option>
            </select>
		</div>

          <div class="field">
            <label>모델명</label>
            <input type="text" name="modelName" value="${cond.modelName}" placeholder="model_name"/>
          </div>

          <div class="field">
            <label>시리얼</label>
            <input type="text" name="serialNumber" value="${cond.serialNumber}" placeholder="serial_number"/>
          </div>

		<div class="field">
			<label>상태</label>
            <select name="status">
				<option value="">전체</option>
				<option value="USE" ${cond.status=='USE' ? 'selected' : ''}>사용중</option>
            	<option value="RET" ${cond.status=='USE' ? 'selected' : ''}>반납</option>
            	<option value="BRK" ${cond.status=='USE' ? 'selected' : ''}>고장</option>
            </select>
        </div>

          <div class="field">
            <label>취득일자</label>
            <input type="text" name="acqDateFrom" value="${cond.acqDateFrom}" placeholder="YYYY-MM-DD"/>
            ~
            <input type="text" name="acqDateTo" value="${cond.acqDateTo}" placeholder="YYYY-MM-DD"/>
          </div>
        </div>

        <div class="actions">
          <button class="btn" type="reset" onclick="location.href='<c:url value='/admin/list'/>';">초기화</button>
          <button class="btn primary" type="submit">검색</button>

          <!-- 엑셀 다운로드: 검색조건 그대로 전달 -->
          <c:url var="excelUrl" value="/admin/equipment/excel">
            <c:param name="userId" value="${cond.userId}"/>
            <c:param name="networkType" value="${cond.networkType}"/>
            <c:param name="equipmentType" value="${cond.equipmentType}"/>
            <c:param name="modelName" value="${cond.modelName}"/>
            <c:param name="serialNumber" value="${cond.serialNumber}"/>
            <c:param name="status" value="${cond.status}"/>
            <c:param name="acqDateFrom" value="${cond.acqDateFrom}"/>
            <c:param name="acqDateTo" value="${cond.acqDateTo}"/>
          </c:url>

          <a class="btn" href="${excelUrl}">엑셀 다운로드</a>
        </div>
      </form>
    </div>

    <div class="card">
      <div class="meta">
        <div>
          총 <b><c:out value="${result.totalCount}"/></b>건
        </div>
        <div class="field">
            <select name="size" onchange="document.getElementById('searchForm').submit();">
				<option value="10"  ${cond.size==10  ? 'selected' : ''}>10</option>
				<option value="20"  ${cond.size==20  ? 'selected' : ''}>20</option>
				<option value="50"  ${cond.size==50  ? 'selected' : ''}>50</option>
				<option value="100" ${cond.size==100 ? 'selected' : ''}>100</option>
            </select>
		</div>
      </div>

      <table>
        <thead>
          <tr>
            <th>순번</th>
            <th>사용자</th>
            <th>부서</th>
            <th>망구분</th>
            <th>장비타입</th>
            <th>모델명</th>
            <th>시리얼번호</th>
            <th>취득가액</th>
            <th>취득시기</th>
            <th>상태</th>
            <th>비고</th>
          </tr>
        </thead>
        <tbody>
          <c:choose>
            <c:when test="${empty result.items}">
              <tr><td class="empty" colspan="11">조회 결과가 없습니다.</td></tr>
            </c:when>
            <c:otherwise>
              <c:forEach var="e" items="${result.items}">
                <tr>
                  <td><c:out value="${e.equipmentNo}"/></td>
                  <td><c:out value="${e.userName}"/>(<c:out value="${e.userId}"/>)</td>
                  <td><c:out value="${e.orgName}"/></td>
                  <td><c:out value="${e.networkType}"/></td>
                  <td><c:out value="${e.equipmentType}"/></td>
                  <td><c:out value="${e.modelName}"/></td>
                  <td><c:out value="${e.serialNumber}"/></td>
                  <td><c:out value="${e.acquisitionCost}"/></td>
                  <td><c:out value="${e.acquisitionDate}"/></td>
                  <td><c:out value="${e.status}"/></td>
                  <td><c:out value="${e.remark}"/></td>
                </tr>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </tbody>
      </table>

      <!-- 페이징: 1~10 링크 -->
      <c:set var="page" value="${result.page}" />
      <c:set var="totalPages" value="${result.totalPages}" />
      <c:set var="blockSize" value="10" />
      <c:set var="blockStart" value="${((page-1)/blockSize)*blockSize + 1}" />
      <c:set var="blockEnd" value="${blockStart + blockSize - 1}" />
      <c:if test="${blockEnd > totalPages}">
        <c:set var="blockEnd" value="${totalPages}" />
      </c:if>

      <c:url var="baseUrl" value="/admin/list">
        <c:param name="size" value="${cond.size}"/>
        <c:param name="userId" value="${cond.userId}"/>
        <c:param name="networkType" value="${cond.networkType}"/>
        <c:param name="equipmentType" value="${cond.equipmentType}"/>
        <c:param name="modelName" value="${cond.modelName}"/>
        <c:param name="serialNumber" value="${cond.serialNumber}"/>
        <c:param name="status" value="${cond.status}"/>
        <c:param name="acqDateFrom" value="${cond.acqDateFrom}"/>
        <c:param name="acqDateTo" value="${cond.acqDateTo}"/>
      </c:url>

      <div class="pager">
        <!-- 첫/이전 -->
        <c:if test="${page > 1}">
          <a href="${baseUrl}&page=1">처음</a>
          <a href="${baseUrl}&page=${page-1}">이전</a>
        </c:if>

        <!-- 숫자 -->
        <c:forEach var="p" begin="${blockStart}" end="${blockEnd}">
          <c:choose>
            <c:when test="${p == page}">
              <span class="on">${p}</span>
            </c:when>
            <c:otherwise>
              <a href="${baseUrl}&page=${p}">${p}</a>
            </c:otherwise>
          </c:choose>
        </c:forEach>

        <!-- 다음/끝 -->
        <c:if test="${page < totalPages}">
          <a href="${baseUrl}&page=${page+1}">다음</a>
          <a href="${baseUrl}&page=${totalPages}">끝</a>
        </c:if>
      </div>
    </div>
  </div>
</body>
</html>