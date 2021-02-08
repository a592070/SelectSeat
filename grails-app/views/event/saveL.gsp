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
        .available{
            background-color: white;
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
<g:each in="${map2List}" var="a" status="aIndex">
    <tr>
    <g:each in="${a}" var="b" status="bIndex">
        <g:if test="${b=='0'}">
            <td id='[${bIndex},${aIndex}]' onclick="choose(this)" rowIdx='${bIndex}' colIdx='${aIndex}'
            class="available"> ${bIndex}-${aIndex} </td>

        </g:if>
        <g:elseif test="${b=='1'}">
            <td id='[${bIndex},${aIndex}]' onclick="choose(this)" rowIdx='${bIndex}' colIdx='${aIndex}'
             class="reserved"> ${bIndex}-${aIndex} </td>

        </g:elseif>
        <g:else>
            <td id='[${bIndex},${aIndex}]' onclick="choose(this)" rowIdx='${bIndex}' colIdx='${aIndex}'></td>
        </g:else>
    </g:each>
    </tr>
</g:each>
</table>
</body>
</html>