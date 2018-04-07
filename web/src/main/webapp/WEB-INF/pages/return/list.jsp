<%--
  Created by IntelliJ IDEA.
  User: dgb
  Date: 2017/9/4
  Time: 11:53
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
    <title>退货列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 退货管理 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c">
        <form action="/return/merchant" method="post">
            <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
            <input type="hidden" id="pn" name="pn" value="${page.currentPage}">
            <input type="text" class="input-text" style="width:250px" placeholder="输入商品名称" id="goodsName" name="goodsName" value="${params.roleName}">
            <button type="button" onclick="query()" class="btn btn-success radius size-M"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
        </form>
    </div>
    <c:if test="${user.type == 2}">
        <div class="cl pd-5 bg-1 bk-gray mt-20">
            <span class="l">
                    <a href="javascript:;" onclick="add('发起退货','/return/edit','','510')" class="btn btn-primary radius size-M"><i class="Hui-iconfont">&#xe600;</i> 发起退货</a>
                    <a href="javascript:;" onclick="iseditable('编辑','/return/edit','','510')" class="btn btn-primary radius size-M">编辑</a>
            </span>
        </div>
    </c:if>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="25"><input type="checkbox" name="ids" value=""></th>
                <th width="70">ID</th>
                <th width="130">商品条形码</th>
                <c:if test="${user.type == 1}">
                    <th width="70">商户名称</th>
                </c:if>
                <th width="100">商品名称</th>
                <th width="100">商品分类</th>
                <th width="100">退货数量</th>
                <th width="100">商品规格</th>
                <th width="100">退货价格</th>
                <th width="100">退货原因</th>
                <th width="100">退货时间</th>
                <th width="100">退货状态</th>
                <th width="100">供应商名称</th>
                <th width="100">备注</th>
                <c:if test="${user.type == 1}">
                    <th width="50">操作</th>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="r">
                <tr class="text-c"  onclick="checked(this)">
                    <td><input type="checkbox" value="${r.id}" name="ids"></td>
                    <td>${r.id}</td>
                    <td>${r.barCode}</td>
                    <c:if test="${user.type == 1}">
                        <td>${r.mrhtName}</td>
                    </c:if>
                    <td>${r.goodsName}</td>
                    <td>${r.typeName}</td>
                    <td>${r.retNum}</td>
                    <td>${r.goodsSpec}</td>
                    <td>${r.retPrice}</td>
                    <td>${r.reasonName}</td>
                    <td><fmt:formatDate value="${r.retTime}" type="date"/></td>
                    <td>
                        <c:if test="${r.retStatus == 20}"><span style="color: #fffdef;background-color: #770088;padding:3px;border-radius: 3px;">待同意</span></c:if>
                        <c:if test="${r.retStatus == 21}"><span style="color: #fffdef;background-color: red;padding:3px;border-radius: 3px;">拒绝退货</span></c:if>
                        <c:if test="${r.retStatus == 22}"><span style="color: #fffdef;background-color: green;padding:3px;border-radius: 3px;">退货完成</span></c:if>
                    </td>
                    <td>${r.supplierName}</td>
                    <td>${r.remark}</td>
                    <c:if test="${user.type == 1}">
                        <td width="50">
                            <c:if test="${r.retStatus == 20}">
                                <a href="javascript:;" onclick="retGoods('22','${r.id}')" style="margin-bottom: 5px;" class="btn btn-primary radius">同意退货</a>
                                <a href="javascript:;" onclick="retGoods('21','${r.id}')" class="btn btn-danger radius">拒绝退货</a>
                            </c:if>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div><%@include file="../common/page.jsp"%></div>
    </div>
</div>
</body>
<script type="text/javascript">

    function iseditable(title,url,a,b) {
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
            errMsg("请选择一条数据编辑！");
            return;
        }
        $.ajax({
            type: 'post',
            url: '/return/iseditable',
            data: {id:id},
            success:function (data) {
                if(data.code == 2) {
                    errMsg(data.msg);
                    return;
                }
                layer_show(title, url+'?id='+id, a, b);
            }

        })
    }

    function retGoods(num,id) {
        if (num == 21){
            layer_show("拒绝原因", '/return/remark?id=' +id+'&num='+num, '420', '340');
        }else{
            retGs(num,id);
        }

    }
    function retGs(num,id){
        $.ajax({
            type: 'post',
            url: '/return/retGoods',
            data: {num:num,id:id},
            success:function (data) {
                if(data.code == 2) {
                    errMsg(data.msg);
                    return;
                }
                succMsg(data.msg);
                setTimeout(function () {
                    refresh();
                },1000)
            }
        })
    }
</script>
</html>
