<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
</head>
<body>
 <h2>文件上传</h2>
   
       <form action="${pageContext.request.contextPath }/picupload" enctype="multipart/form-data" method="post">
       	<table>
       		<tr>
       			<td>请选择文件1</td>
       			<td><input type="file" name="file"></td>
       		</tr>
       		<tr>
       			<td>请选择文件2</td>
       			<td><input type="file" name="file"></td>
       		</tr>
       		<tr>
       			<td><input type="submit" value="上传"></td>
       		</tr>

       	</table>
       </form>
</body>
</html>