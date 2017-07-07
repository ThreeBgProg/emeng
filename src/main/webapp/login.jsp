<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="login" type = "post">
	<table>
			<tr>
				<td>用户名：</td>
				<td><input type="text" id="username" name="username" onblur="uonblur()">&nbsp;&nbsp;<font id="usernameTips" color="red" ></font></td>
			</tr>
			<tr>
				<td>密     码：</td>
				<td><input type="password" id="password" name="password" onblur="ponblur()">&nbsp;&nbsp;<font id="passwordTips" color="red"></font></td>
			</tr>
		</table>
		<input type="submit" value="提交">
</form>
</body>
</html>