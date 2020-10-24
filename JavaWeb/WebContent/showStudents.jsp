<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="code01.DB" %>
<%@ page import="code01.Student" %>
<%@ page import="code01.DBServlet" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>学生信息表</title>
</head>
<body>
	<a href="addStudent.jsp">添加学生</a>
	<table width="600" cellpadding="1" cllspacing="1">
		<tr height="30">
			<td algin="center" class="header">
				ID
			</td>
			<td algin="center" class="header">
				Name
			</td>
			<td algin="center" class="header">
				Password
			</td>
			</td>
			<td algin="center" class="header">
				操作
			</td>
		</tr>
	<%
	ArrayList<Student> students=DB.getAllStudents();
	for(Student student:students){
		%>
		<tr height="30"></tr>
		<td align="center" class="data">
		<%=student.getId() %>
		</td>
		<td align="center" class="data">
		<%=student.getName() %>
		</td>
		<td align="center" class="data">
		<%=student.getPassword() %>
		</td>
		<td align="center" class="data">
		<a href="deleteStudent?id=<%=student.getId() %>" >删除</a>
		</td>
	<% 
		}
	%>
	</table>
</body>
</html>