<%@page import="com.douzon.guestbook.dao.GuestBookDao"%>
<%@page import="com.douzon.guestbook.vo.GuestBookVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	List<GuestBookVo> list = (List)request.getAttribute("list");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>방명록</title>
<link href="<%=request.getContextPath() %>/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="out">
	<div class="in">
	<form action="<%=request.getContextPath() %>/gb" method="post">
		<input type="hidden" name="a" value="add">
		<table border=1 width="500">
			<tr>
				<td>이름</td>
				<td><input type="text" name="name"></td>
				<td>비밀번호</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td colspan=4><textarea name="message" cols=60 rows=5></textarea></td>
			</tr>
			<tr>
				<td colspan=4 align=right><input type="submit" VALUE=" 등록 "></td>
			</tr>
		</table>
	</form>
	</div>
	
	<br>
	<%
		int i = 1;
		for (GuestBookVo vo : list) {
	%>
	<div class="in">
	<table width=510 border=1>
		<tr>
			<td><%=i%></td>
			<td><%=vo.getName()%></td>
			<td><%=vo.getDate()%></td>
			<td><a href="<%=request.getContextPath() %>/gb?a=deleteform&no=<%=vo.getNo() %>">삭제</a></td>
		</tr>
		<tr>
			<td colspan=4><%=vo.getMessage()%></td>
		</tr>
	</table>
	<%
		i++;
		}
	%>
	</div>
	</div>
</body>
</html>