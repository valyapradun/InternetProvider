<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>MyJSP</title>
</head>
<body>
<form action="Controller" method="POST">
<input type="hidden" name="action" value="auth" />
Enter login:<br/>
<input type="text" name="login" value="user" /><br/>
Enter password:<br/>
<input type="password" name="password" value="user" /><br/>
<input type="submit" value="Sign In" /><br/>
</form>
</body>
</html>