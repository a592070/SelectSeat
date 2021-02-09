<%--
  Created by IntelliJ IDEA.
  User: zhengxuanmin
  Date: 2021/2/8
  Time: 4:46 下午
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>zone 一覽</title>
    <meta name="layout" content="main" />
</head>

<body>
<table>
    <tr>
        <th>#</th>
        <th>區域名稱</th>
        <th>區域代碼</th>
        <th>活動名稱</th>
        <th>連結</th>
    </tr>
<g:each in="${zoneList}" var="z" status="index">
    <tr>
        <td>${index+1}</td>
    <td>${z.name}</td>
    <td>${z.zoneCode}</td>
    <td>${z.event.name}</td>
        <td><g:link action= "zoneDetail" controller=" zone" params ="[zoneId: z.id]" >詳情</g:link></td>
    </tr>

</g:each>
</table>
</body>
</html>