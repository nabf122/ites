<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>장비관리현황 로그인</title>
  <style>
    body { font-family: Arial, sans-serif; background:#f5f6f7; }
    .wrap { max-width: 420px; margin: 80px auto; background:#fff; border:1px solid #ddd; border-radius:10px; padding: 24px; }
    h2 { margin: 0 0 16px 0; }
    .row { margin: 10px 0; }
    label { display:block; margin-bottom:6px; font-size: 13px; color:#333; }
    input { width:95%; height:40px; padding:0 10px; border:1px solid #ccc; border-radius:8px; }
    button { width:100%; height:42px; border:0; border-radius:8px; background:#1e6bd6; color:#fff; font-weight:700; cursor:pointer; }
    .hint { margin-top: 12px; font-size: 12px; color:#666; line-height: 1.4; }
    .err { margin-top: 12px; color:#d33; font-size: 13px; }
  </style>
</head>
<body>
	<div class="wrap">
		<h2>장비관리현황 로그인</h2>
		
		<c:if test="${not empty msg}">
			<script type="text/javascript">
				alert("${fn:escapeXml(msg)}");
			</script>
		</c:if>

		<form method="post" action="<c:url value='/auth/login'/>">
			<div class="row">
	        	<label for="id">아이디</label>
	        	<input id="userId" name="userId" type="text" autocomplete="username" required />
	      	</div>
	      	<div class="row">
	        	<label for="pw">비밀번호</label>
	        	<input id="userPw" name="userPw" type="password" autocomplete="current-password" required />
	      	</div> 
	
	      	<button type="submit">로그인</button>
		</form>
	</div>
</body>
</html>