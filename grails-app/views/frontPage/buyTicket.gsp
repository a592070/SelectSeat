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
${exiting}<br>
您已購買 ${ticketNum} 張 ${eventResult.name} 節目之票卷 <br>

<g:each var="zon" in="${zoneList}">
    <p>${zon.name}，<g:link action="countEmptySeat" controller="FrontPage" params="[zoneId : zon.id ]">
        查詢座位 / 劃位
    </g:link>${emptySeat}</p>
</g:each>


</body>

</html>