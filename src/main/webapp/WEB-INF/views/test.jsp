<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TEST</title>
</head>
<body>
    <p>
	Language: <a href="/test.do?language=en_US">English</a>|<a href="/test.do?language=ko_KR">Korea</a>
	</p>

    <h3>
    hello.test : <spring:message code="hello.test" text="default text" />
    </h3>

    Current Locale : ${pageContext.response.locale}
</body>
</html>