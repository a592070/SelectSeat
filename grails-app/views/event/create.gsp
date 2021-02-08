<%--
  Created by IntelliJ IDEA.
  User: zhengxuanmin
  Date: 2021/2/4
  Time: 1:49 下午
--%>

<%@ page import="selectseat.Location" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Creat Event</title>
    <meta name="layout" content="main"/>
    <style>
    td {
        border: solid #1d2124 2px
    }

    td:hover {
        background-color: #f1b0b7;
    }
    .act{
        background-color: #6c757d;
    }
    .act:hover{
        background-color: #7c7c7c;
    }
    </style>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"
            integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
</head>

<body>

<g:emoticon happy="true">Hi</g:emoticon><br>
<g:form action="saveL" controller="event">
    <label>名字</label>
    <g:textField name="query"/>
    <g:submitButton name="送出"></g:submitButton>
</g:form>
<br><hr><br>
<g:form action="createEvent" controller="event">
    <label>輸入活動名稱</label>
    <g:textField name="eventName"></g:textField>
    <br>

    <label>選擇活動開始日期</label>
    <g:datePicker name="beginDate" value="${new Date()}"
                  noSelection="['': '-請選擇-']"/>
    <br>
    <label>選擇活動結束日期</label>
    <g:datePicker name="endDate" value="${new Date()}"
                  noSelection="['': '-請選擇-']"/>
    <br>
    <label>選擇活動舉辦場所</label>
    <br>

    <g:select optionKey="id" optionValue="name"
              name="location" from="${locationList}" />
    <br>
    <label>輸入區域名稱</label>
    <g:textField name="zone"></g:textField>
    <br>
    <label>輸入座位行數</label>
    <g:field name="columnCount" type="number" min="1" required="" value="5"/>
    <br>
    <label>輸入座位列數</label>
    <g:field name="rowCount" type="number" min="1" required="" value="5"/>

    <button type="button" onclick="createTable()">生成座位選擇表</button>
    <br>
%{--    <label>已設定無法販售之座位</label>--}%
%{--    <g:field name="clickRecord" type="hidden" readonly=""/>--}%
    <g:field name="clickRecord" type="text" readonly=""/>
    <br><br>

    <div id="liRegion"></div>
    <br>
    <g:submitButton name="送出"></g:submitButton>
</g:form>

<g:javascript>
    var seats = []
    var des = '點選格子以設定無法販售之座位'
    function createTable() {
        var rowCount = document.getElementById("rowCount").value;
        var columnCount = document.getElementById("columnCount").value;
        if (!columnCount) {
            columnCount = 1
        }
        if (!rowCount) {
            rowCount = 1
        }
        var text = '<table id="foo">'
        for (var i = 0; i <= rowCount-1; i++) {
            text += `<tr>`
            for (var j = 0; j <= columnCount-1; j++) {
                text += `<td id='[` + i + `,` + j + `]' onclick="choose(this)" rowIdx='` + i + `' colIdx='` + j + `'>` + i + `-` + j + `</td>`
            }
            text += '</tr>'
        }
        text += '</table>'
        document.getElementById('liRegion').innerHTML = des + text;

        $("#liRegion td").click(function() {
            $(this).toggleClass("act");
        });
    }

    function choose(obj) {
        // var clickSeat = document.getElementById(index).innerText
        var clickSeat = [parseInt(obj.getAttribute('rowIdx'), 10), parseInt(obj.getAttribute('colIdx'), 10)]
        // var clickSeat = [obj.getAttribute('rowIdx'), obj.getAttribute('colIdx')]
        // console.log(obj.getAttribute('rowIdx'))
        // console.log(obj.getAttribute('colIdx'))
        // const newList = [].concat.apply([],seats)
        // console.log(obj['rowIdx'])
        // var ifExist = seats.includes(clickSeat)
        seats.push(clickSeat)
        // console.log(ifExist)
        // console.log(seats)
        // alert(seats)
        // document.getElementById('clickRecord').innerHTML= seats.toString();
        // document.getElementById('clickRecord').innerHTML= `<input type="text" id="clickRecord" value="`+seats+`">`;
        $('#clickRecord').val(JSON.stringify(seats))
    }



</g:javascript>

</body>
</html>