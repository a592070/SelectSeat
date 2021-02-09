<%--
  Created by IntelliJ IDEA.
  User: zhengxuanmin
  Date: 2021/2/4
  Time: 11:33 下午
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>save</title>
    <style>
        td{
            border: solid #1d2124 2px
        }
        .available:hover{
            background-color: #e0a800;
        }
        .reserved{
            background-color: #006dba;
        }

    </style>
</head>

<body>
${map2List}<br>
<table id="seatMap">
<g:each in="${map2List}" var="col" status="colIdx">
    <tr>
    <g:each in="${col}" var="row" status="rowIdx">
        <g:if test="${row =='0'}">
            <td id='[${rowIdx},${colIdx}]' onclick="choose(this)" rowIdx='${rowIdx}' colIdx='${colIdx}'
            class="available"> ${rowIdx}-${colIdx} </td>

        </g:if>
        <g:elseif test="${row =='1'}">
            <td id='[${rowIdx},${colIdx}]' onclick="choose(this)" rowIdx='${rowIdx}' colIdx='${colIdx}'
             class="reserved"> ${rowIdx}-${colIdx} </td>

        </g:elseif>
        <g:else>
            <td id='[${rowIdx},${colIdx}]' onclick="choose(this)" rowIdx='${rowIdx}' colIdx='${colIdx}'></td>
        </g:else>
    </g:each>
    </tr>
</g:each>
</table>
</body>
</html>