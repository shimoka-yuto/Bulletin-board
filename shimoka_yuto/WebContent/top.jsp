<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>掲示板</title>
	<link href="./css/top.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
 	<script src="http://code.jquery.com/jquery-1.8.3.js"></script>
	<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
 	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/i18n/jquery.ui.datepicker-ja.min.js"></script>

  	<script>
		$(function() {
			$.datepicker.setDefaults( $.datepicker.regional[ "ja" ] );
			$.datepicker.setDefaults({

			});
			$( ".datepicker" ).datepicker();
		});
	</script>

</head>

<body>

<div class="main-contents">

<div class="header">
	<a href="newMessage">新規投稿 |</a>
	<c:if test="${loginUser.departmentId == 1}">
		<a href="users"> ユーザー管理 |</a>
	</c:if>
	<a href="logout"> ログアウト</a>
</div>

<div class="profile">
	<div class="name"><h2><c:out value="${loginUser.name}" /></h2></div>
	<div class="account">
		 <c:out value="${loginUser.branchName}" />　/　
		<c:out value="${loginUser.departmentName}" /><br />
	</div>
</div>


<div class="refine">
	<h2>絞込み検索</h2><br />
	<form action="./" method="get">
		カテゴリー<input name="category" id="category" value="${category}" list="keywords" ><br />
		<datalist id="keywords">
			<c:forEach items="${categorys}" var="category">
				<option value="${category}">
     		</c:forEach>
     	</datalist>
		投稿日<input type="text" name="start_date" class="datepicker" value="${start_date}" readonly="readonly" > ～
		<input type="text" name="end_date" class="datepicker" value="${end_date}" readonly="readonly"><br />
		<div class="button-panel">
			<input type="submit" class="button" value="検索">
		</div>
	</form>
	<form action="./" method="get">
		<input type="hidden" name="category"  >
		<input type="hidden" name="start_date" >
		<input type="hidden" name="end_date" >
		<div class="button-panel">
			<input type="submit" class="reset_button" value="検索リセット">
		</div>
	</form>

</div>


<div class="messages">
	<c:forEach items="${messages}" var="message">

		<div class="message">
			<span class="title">件名: <c:out value="${message.title}" /></span>
			<span class="name">　投稿者: <c:out value="${message.name}" /></span>
			<div class="category">カテゴリー: <c:out value="${message.category}" /></div>
			<div class="text"><br /><h3>
				<c:forEach items="${message.splitedText}" var="splited_message">
					<c:out value="${splited_message}" /><br />
				</c:forEach></h3>
			</div>
			<div class="date">投稿日時: <fmt:formatDate value="${message.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>

			<c:if test="${loginUser.branchId == message.branchId && loginUser.departmentId == 3}">
				<form action="deleteMessage" method="post" onSubmit="return check()">
					<div class="button-panel">
						<input type="hidden" name="message_id" value="${message.messageId}">
						<input type="submit" class="delete_button" value="削除">
					</div>
				</form>
			</c:if>
			<c:if test="${loginUser.id == message.userId && loginUser.departmentId != 2 && loginUser.departmentId != 3}">
				<form action="deleteMessage" method="post" onSubmit="return check()">
					<div class="button-panel">
						<input type="hidden" name="message_id" value="${message.messageId}">
						<input type="submit" class="delete_button" value="削除">
					</div>
				</form>
			</c:if>
			<c:if test="${loginUser.departmentId == 2}">
				<form action="deleteMessage" method="post" onSubmit="return check()">
					<div class="button-panel">
						<input type="hidden" name="message_id" value="${message.messageId}">
						<input type="submit" class="delete_button" value="削除">
					</div>
				</form>
			</c:if>

		</div>

		<c:forEach items="${comments}" var="comment">
			<c:if test="${comment.messageId == message.messageId}">
				<div class="comments">
					<div class="account-name">
					</div>
					<div class="text"><h3>
						<c:forEach items="${comment.splitedText}" var="splited_comment">
							<c:out value="${splited_comment}" /><br />
						</c:forEach></h3>
					</div>
					<div class="account-name">
					</div>
					<span class="name_date">
						投稿者: <c:out value="${comment.name}" />
						　コメント日時: <fmt:formatDate value="${comment.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" />
					</span>

					<c:if test="${loginUser.branchId == comment.branchId && loginUser.departmentId == 3}">
						<form action="deleteComment" method="post" onSubmit="return check()" >
							<div class="button-panel">
								<input type="hidden" name="comment_id" value="${comment.commentId}">
								<input type="submit" class="delete_button" value="削除">
							</div>
						</form>
					</c:if>
					<c:if test="${loginUser.id == comment.userId && loginUser.departmentId != 2 && loginUser.departmentId != 3}">
						<form action="deleteComment" method="post" onSubmit="return check()" >
							<div class="button-panel">
								<input type="hidden" name="comment_id" value="${comment.commentId}">
								<input type="submit" class="delete_button" value="削除">
							</div>
						</form>
					</c:if>
					<c:if test="${loginUser.departmentId == 2}">
						<form action="deleteComment" method="post" onSubmit="return check()" >
							<div class="button-panel">
								<input type="hidden" name="comment_id" value="${comment.commentId}">
								<input type="submit" class="delete_button" value="削除">
							</div>
						</form>
					</c:if>
				</div>
			</c:if>
		</c:forEach>

		<div class="comment_form">
			<c:if test="${ not empty errorMessages && errorComment.messageId == message.messageId}">
				<div class="errorMessages">
					<ul>
						<c:forEach items="${errorMessages}" var="errorMessage">
							<li><c:out value="${errorMessage}" /></li><br />
						</c:forEach>
					</ul>
				</div>
				<c:remove var="errorMessages" scope="session"/>
			</c:if>

			<form action="newComment" method="post">
				<input type="hidden" name="message_id" value="${message.messageId}">
				コメント　(500文字以下)<br />
				<c:if test="${errorComment.messageId == message.messageId}">
				<textarea name="comment" cols="100" rows="1" class="comment-box" >${comment_text}</textarea><br />
				</c:if>
				<c:remove var="comment_text" scope="session"/>
				<c:if test="${errorComment.messageId != message.messageId}">
				<textarea name="comment" cols="100" rows="1" class="comment-box" ></textarea><br />
				</c:if>
				<div class="button-panel">
					<input type="submit" class="comment_button" value="コメントする">
				</div>
			</form>
		</div>

	</c:forEach>
</div>

</div>

<script type="text/javascript">
<!--
//確認ダイアログの表示
function check(){
	if(window.confirm('本当に削除しますか？')){
		window.alert('削除しました');
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
