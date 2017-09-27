<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新規投稿</title>
	<link href="css/newMessage.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="main-contents">

<div class="header">
	<a href="./">＜ ホームへ戻る</a>
</div>

<h1>新規投稿</h1>

<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>

<div class="form-area">
	<form action="newMessage" method="post">
		<div class="form-item">
			<label for="title">件名　(30文字以下)</label>
			<input name="title" id="title" value="${title}" /><br />
		</div>
		<div class="form-item">
			<label for="category">カテゴリー　(10文字以下)</label>
			<input name="category" id="category" value="${category}" list="keywords" /><br />
			<datalist id="keywords">
			<c:forEach items="${categorys}" var="category">
				<option value="${category}">
     		</c:forEach>
     	</datalist>
		</div>
		<div class="form-item">本文　(1000文字以下)
			<textarea name="text" cols="100" rows="10" class="message-box">${text}</textarea><br />
		</div>
		<div class="button-panel">
			<input type="submit" class="button" value="投稿">
		</div>
	</form>
</div>
</div>

</div>
</body>
</html>