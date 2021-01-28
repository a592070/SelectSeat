<%--
  Created by IntelliJ IDEA.
  User: zhengxuanmin
  Date: 2021/1/28
  Time: 4:34 下午
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<g:each var="var" in= "${ticketList}">

    <ul>
        <tr>
            <td><li>${var.id},${var.price}, ${var.type}</li><g:link action="show" controller="Ticket" id="${var.id}">點我看看</g:link>

    </td>
        </tr>
    </ul>
</g:each>
</body>
</html>