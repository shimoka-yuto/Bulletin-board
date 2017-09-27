<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー編集</title>
	<link href="css/settings.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">

<div class="header">
	<a href="users">＜ ユーザー管理画面へ戻る</a>
</div>

<h1>ユーザー編集</h1>
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

<form action="settings" method="post" enctype="multipart/form-data"><br />

	<div class="form-item">
		<label for="account">ログインID　(半角英数字6文字以上20文字以下)</label>
		<input name="account" value="${editUser.account}" id="account"/><br />
	</div>
	<div class="form-item">
		<label for="name">名前　(10文字以下)</label>
		<input name="name" value="${editUser.name}" id="name"/><br />
	</div>
	<div class="form-item">
		<c:if test="${editUser.id == loginUser.id}">
			<label for="branch_name">支店<br /></label>
			<select name="branch_name" >
				<option value="${editUser.branchName}" selected>${editUser.branchName}</option>
			</select><br />
		</c:if>
		<c:if test="${editUser.id != loginUser.id}">
			<label for="branch_name">支店<br /></label>
			<select name="branch_name" >
				<c:forEach var="s" items="${branch_name}">
					<c:if test="${s.equals(editUser.branchName)}">
						<option value="${s}" selected>${s}</option>
					</c:if>
					<c:if test="${!s.equals(editUser.branchName)}">
						<option value="${s}">${s}</option>
					</c:if>
				</c:forEach>
			</select><br />
		</c:if>
	</div>
	<div class="form-item">
		<c:if test="${editUser.id == loginUser.id}">
			<label for="branch_name">部署・役職<br /></label>
			<select name="department_name">
				<option value="${editUser.departmentName}" selected>${editUser.departmentName}</option>
			</select><br />
		</c:if>
		<c:if test="${editUser.id != loginUser.id}">
			<label for="department_name">部署・役職<br /></label>
			<select name="department_name">
	  			<c:forEach var="s" items="${department_name}">
					<c:if test="${s.equals(editUser.departmentName)}">
						<option value="${s}" selected>${s}</option>
					</c:if>
					<c:if test="${!s.equals(editUser.departmentName)}">
						<option value="${s}">${s}</option>
					</c:if>
				</c:forEach>
			</select><br />
		</c:if>
	</div>
	<div class="form-item">*空白の場合はパスワードを変更しません <br />
		<label for="password">パスワード　(記号を含む半角文字6文字以上20文字以下)</label>
		<input name="password" type="password" id="password" /> <br />
	</div>
	<div class="form-item">
		<label for="password_conf">パスワード(確認用)</label>
		<input name="password_conf" type="password" id="password_conf"/> <br />
	</div>
	<div class="button-panel">
		<input type="submit" class="button" value="登録" /> <br />
	</div>

</form>

</div>
</body>
</html>
