<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー管理</title>
	<link href="css/users.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="main-contents">

<div class="header">
	<a href="signup"> 新規ユーザー登録</a>
	<a href="./">ホーム | </a>
</div>

<h1>ユーザー管理</h1>

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

<div class="users">
	<div class="item name">名前<hr noshade></div>
	<div class="item id">ログインID<hr noshade></div>
	<div class="item branch">支店<hr noshade></div>
	<div class="item department">部署・役職<hr noshade></div>
	<div class="item isActived">アカウント停止<hr noshade></div>
	<div class="item setting">ユーザー編集<hr noshade><br /></div>

	<c:forEach items="${users}" var="user">
		<div class="item name"><c:out value="${user.name}" /></div>
		<div class="item id"><c:out value="${user.account}" /></div>
		<div class="item branch"><c:out value="${user.branchName}" /></div>
		<div class="item department"><c:out value="${user.departmentName}" /></div>
		<div class="item isActived">
			<c:if test="${user.id == loginUser.id}">
				ログイン中
			</c:if>
			<c:if test="${user.id != loginUser.id}">
				<c:if test="${user.isActived == false}">
					<form action="users" method="post" onSubmit="return check()">
						<input type="hidden" name="user_id" value="${user.id}">
						<input type="hidden" name="is_actived" value="true">
						<div class="button-panel">
							<input type="submit" class="stop_button" value="停止" >
						</div>
					</form>
				</c:if>
				<c:if test="${user.isActived == true}">
					<form action="users" method="post" onSubmit="return check2()">
						<input type="hidden" name="user_id" value="${user.id}">
						<input type="hidden" name="is_actived" value="false">
						<div class="button-panel">
							<input type="submit" class="resume_button" value="復活" >
						</div>
					</form>
				</c:if>
			</c:if>
		</div>
		<div class="item setting">
			<form action="settings" method="get">
				<input type="hidden" name="user_id" value="${user.id}">
				<div class="button-panel">
					<a href="settings"><input type="submit" class="setting_button" value="編集"></a><br /><br />
				</div>

			</form>
		</div>
	</c:forEach>
</div>

</div>

<script type="text/javascript">
<!--
//確認ダイアログの表示（停止）
function check(){
	if(window.confirm('このユーザーを停止しますか？')){
		window.alert('ユーザーを停止しました');
		return true;
	}
	else{
		window.alert('キャンセルしました');
		return false;
	}
}

//確認ダイアログの表示（復活）
function check2(){
	if(window.confirm('このユーザーを復活しますか？')){
		window.alert('ユーザーを復活しました');
		return true;
	}
	else{
		window.alert('キャンセルしました');
		return false;
	}
}
-->
</script>


</body>
</html>
