<%--
  Created by IntelliJ IDEA.
  User: zhengxuanmin
  Date: 2021/1/27
  Time: 10:49 上午
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Front page</title>

</head>

<body onload="">

<g:link url="[action: 'init', controller: 'frontPage']"> insert</g:link>
<br>

<g:form action="eventSearch">
    <tr>
        <td>
            輸入關鍵字：<g:textField name="query"/>
        </td>
    </tr>
    <tr>
        <td><g:submitButton name="查詢"/></td>
    </tr>

</g:form><br>


</body>
</html>