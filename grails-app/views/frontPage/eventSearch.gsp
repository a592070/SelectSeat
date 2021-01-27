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
到達新天地 <br>
總共有 ${totalEvents} 筆搜尋結果: ${result}
<Table>
    <g:each var="eve" in= "${eventList}">
        <tr>
            <ul>
                <td><li>${eve.eventCode}, ${eve.name}</li></td>
            </ul>
        </tr>
    </g:each>
</Table>
</body>
</html>