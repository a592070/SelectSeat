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
    <p>${zon.name}，剩餘座位數</p>
</g:each>

%{--<g:javascript>--}%
%{--  function callMyAjax(){--}%
%{--    $.ajax({--}%
%{--      url:'${g.createLink( controller:'FrontPage', action:'getSeatAjax', params: 'zonId')}',--}%
%{--      data:{--}%
%{--           param1:param1,--}%
%{--           param2:param2--}%
%{--      }--}%
%{--    });--}%
%{--  }--}%
%{--</g:javascript>--}%

</body>

</html>