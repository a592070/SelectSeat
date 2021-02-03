<%--
  Created by IntelliJ IDEA.
  User: zhengxuanmin
  Date: 2021/1/27
  Time: 3:39 下午
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>

<body>
<g:form action="countUserTicketByEve">
    <tr>
        <td>
            輸入會員 email：<g:textField name="user"/><br>
            event: ${eventId}
            <g:hiddenField name="eventId" value="${eventId}"/>
        </td>
    </tr>
    <tr>
        <td><g:submitButton name="登入以完成訂票"/></td>
    </tr>

</g:form>
<h3>${result}</h3>

</body>
</html>