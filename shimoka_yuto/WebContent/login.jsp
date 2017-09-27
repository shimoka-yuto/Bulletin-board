<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ログイン</title>
	<link href="./css/login.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="main-contents">
<h1>ログイン</h1>

<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}" /></li><br />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>

<form action="login" method="post"><br />
	<div class="form-item">
		<label for="accountOrEmail">ログインID</label>
		<input name="accountOrEmail" id="accountOrEmail"  value="${id}"  /> <br />
	</div>
	<div class="form-item">
		<label for="password">パスワード</label>
		<input name="password" type="password" id="password" /> <br />
	</div>
	<div class="button-panel">
		<input type="submit" class="button" value="ログイン" /> <br />
	</div>
</form>

</div>
</body>
</html>
