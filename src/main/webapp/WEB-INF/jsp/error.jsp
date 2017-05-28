<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>errorPage</title>
</head>
<body>
<h1>数据库连接正常</h1>

 导航：${navigationList[0].id}，${navigationList[0].position}
 思政：${dynamicList[0].author},${dynamicList[1].author}
 最新：${newestPassageList[0].author},${newestPassageList[1].author}
 马院：${headlineList[0].author},${headlineList[1].author}
 作家：${teacherPassageList[0].author},${teacherPassageList[1].author}

</body>
</html>