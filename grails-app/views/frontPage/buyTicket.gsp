<%--
  Created by IntelliJ IDEA.
  User: zhengxuanmin
  Date: 2021/2/2
  Time: 1:48 下午
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>

活動名稱：${eventResult.name}

<g:each var="zon" in="${zoneList}">
    <p>${zon.name}，<g:link action="countEmptySeat" controller="FrontPage" params="[zoneId : zon.id ]">
        剩餘座位數
    </g:link>${emptySeat}</p>
</g:each>


</body>

</html>