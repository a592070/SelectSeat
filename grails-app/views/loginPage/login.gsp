<%--
  Created by IntelliJ IDEA.
  User: zhengxuanmin
  Date: 2021/1/27
  Time: 3:56 下午
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<h2>${result}</h2>
<table>
    <g:each var="eve" in= "${list}">

        <ul>
            <tr>
                <td><li>${eve.orderCode}, ${eve.eventName}</li></td>
            </tr>
        </ul>
    </g:each>
    <g:if test="${total < 4}">
        <g:link action="test">
            <input type="button" value="有連結功能的按鈕" class="button"/>
        </g:link>
        <br>
        ${test}
        <g:each var="eve" in="${event}">
            <p>節目名稱：${eve.name}</p>

        </g:each>
   </g:if>
    <g:else>
        <div>已達上限</div>
    </g:else>
</table>
<br>
</body>
</html>