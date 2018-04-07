<%--
  Created by IntelliJ IDEA.
  User: sunwei
  Date: 2017/9/1
  Time: 13:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common/common.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>消息列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 用户中心 <span class="c-gray en">&gt;</span> 我的消息 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">

    <div style="float:left;width:20%;height:100%;">
        <div class="cl pd-5 mt-20">
            <a href="javascript:;" onclick="message('to')" class="btn btn-danger radius size-M"><i class="Hui-iconfont"></i> 接收到的消息</a>
        </div>
        <div class="cl pd-5 mt-20" style="margin-top: -6px;">
            <a href="javascript:;" onclick="message('from')" class="btn btn-primary radius size-M"><i class="Hui-iconfont"></i> 已发送的消息</a>
        </div>
    </div>
    <div style="float:left;width:80%;height:100%;">
        <div class="text-c">
            <form action="/message/list" method="post">
                <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
                <input type="hidden" id="pn" name="pn" value="${page.currentPage}">
                <input type="hidden" id="flag" name="flag" value="${params.flag}">
            </form>
        </div>
        <div class="cl pd-5 mt-20">
            <a href="javascript:;" onclick="view('查看','/message/view')" class="btn btn-danger radius size-M"><i class="Hui-iconfont"></i> 查看</a>
        </div>
        <div class="mt-20">
            <table class="table table-border table-bordered table-hover table-bg table-sort">
                <thead>
                <tr class="text-c">
                    <th width="25"><input type="checkbox" name="ids" value=""></th>
                    <th width="80">ID</th>
                    <th width="100">消息标题</th>
                    <th width="130">消息内容</th>
                    <th width="100">发送者</th>
                    <th width="100">接收者</th>
                    <th width="100">发送时间</th>
                    <th width="100">消息状态</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.results}" var="p">
                    <tr class="text-c" onclick="checked(this)">
                        <td><input type="checkbox" value="${p.id}" name="ids"></td>
                        <td>${p.id}</td>
                        <td>${p.title}</td>
                        <td>${p.text}</td>
                        <td>${p.fromName}</td>
                        <td>${p.toName}</td>
                        <td><fmt:formatDate value="${p.sendTime}" type="date"/></td>
                        <td>
                            <c:if test="${p.status == 0}"><span style="color: red">未读消息</span></c:if>
                            <c:if test="${p.status == 1}"><span style="color: green">已读消息</span></c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div><%@include file="../common/page.jsp"%></div>
        </div>
    </div>

</div>
</body>
<script type="text/javascript">
    function message(flag) {
        location.href = '/message/list?flag='+flag;
    }

    function view(title,url){
        var id = '';
        var count = 0;
        $.each($('input:checkbox:checked'),function(){
            if($(this).val() != '') {
                id += $(this).val() + ',';
                count ++;
            }
        });
        id = id.substring(0,id.length-1);
        if(id == '' || id == null || count > 1) {
            errMsg("请选择一条数据查看！");
            return;
        }
        //iframe层
        layer.open({
            type: 2,
            title: title,
            shadeClose: false, //点击遮罩关闭
            shade: 0.3,
            area: ['60%', '70%'],
            maxmin: true,
            closeBtn: 1,
            content: [url+'?id=' + id, 'yes'], //iframe的url，yes是否有滚动条
            end: function () {
                location.reload();
            }

        })
       // layer_show(title,url+"?id="+id);
    }

</script>
</html>
