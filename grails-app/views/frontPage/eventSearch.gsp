<%--
  Created by IntelliJ IDEA.
  User: zhengxuanmin
  Date: 2021/1/27
  Time: 11:27 上午
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>

有${result} / ${totalEvents} 筆搜尋結果符合： ${query}
<Table>
    <g:each var="eve" in= "${eventList}">
        <tr>
            <ul>
                <td><li>${eve.eventCode}, ${eve.name}</li></td>
                <td><li><g:link action="checkLogin" controller="FrontPage" params="[eveId : eve.id ]">
                    購票
                </g:link></li></td>
            </ul>
        </tr>
    </g:each>
</Table>
</body>
</html>