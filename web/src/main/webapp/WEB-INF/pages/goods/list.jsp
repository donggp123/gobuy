<%--
  Created by IntelliJ IDEA.
  User: dgb
  Date: 2017/8/21
  Time: 13:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/common.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title></title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 商品管理 <a id="btn-refresh" onclick="refresh()" class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="###" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="text-c">
        <form action="/goods/list" method="post">
            <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}">
            <input type="hidden" id="pn" name="pn" value="${page.currentPage}">
            <input type="text" class="input-text" style="width:250px" placeholder="请输入商品名称" id="username" name="goodsName" value="${params.goodsName}">
            <input type="text" class="input-text" style="width:250px" placeholder="请输入条形码"  name="goodsName" value="${params.barCode}">
            <button type="button" onclick="query()" class="btn btn-success radius size-M"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:;" onclick="datadel('/goods/del')" class="btn btn-danger radius size-M"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>
            <a href="javascript:;" onclick="add('添加商品','/goods/edit','','600')" class="btn btn-primary radius size-M"><i class="Hui-iconfont">&#xe600;</i> 添加</a>
            <a href="javascript:;" onclick="isEditable('修改商品','/goods/edit','','600')" class="btn btn-primary radius size-M" title=""><i class="Hui-iconfont">&#xe6df;</i> 编辑</a>

            <a style="text-decoration:none" onClick="putoutAway(1,'确定要上架吗？','/goods/release')"  class="btn btn-primary radius size-M" href="javascript:;" title="上架"><i class="Hui-iconfont">&#xe6de;</i>上架</a>
            <a style="text-decoration:none" onClick="putoutAway(0,'确定要下架吗？','/goods/release')"  class="btn btn-primary radius size-M" href="javascript:;" title="下架"><i class="Hui-iconfont">&#xe603;</i>下架</a>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="25"><input type="checkbox" name="ids" value=""></th>
                <th width="50">ID</th>
                <th width="150">商品条形码</th>
                <th width="150">商品名称</th>
                <th width="100">商品图片</th>
                <th width="70">商品规格</th>
                <th width="150">商品分类</th>
                <c:if test="${user.type == 2}">
                <th width="80">库存量</th>
                <th width="130">供应商名称</th>
                </c:if>
                <th width="80">进价</th>
                <th width="80">售价</th>
                <th width="80">上下架</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.results}" var="u">
                <tr class="text-c" onclick="checked(this)">
                    <td><input type="checkbox" value="${u.id}" name="ids"></td>
                    <td>${u.id}</td>
                    <td>${u.barCode}</td>
                    <td>${u.goodsName}</td>
                    <td align="center">
                        <div id="goodsImageShow${u.id}">
                            <c:if test="${not empty u.goodsImage}">
                            <script>
                                loadImg('${u.goodsImage}', '${imgServer}', 'goodsImageShow${u.id}', 1);
                            </script>
                            </c:if>
                        </div>
                    </td>
                    <td>${u.goodsSpec}</td>
                    <td>${u.typeName}</td>
                    <c:if test="${user.type == 2}">
                    <td>${u.stockNum}</td>
                    <td>${u.supplierName}</td>
                    </c:if>
                    <td>${u.originalPrice == null ? 0 : u.originalPrice}</td>
                    <td>${u.sellPrice}</td>
                    <td>
                        <span style="color:red;"><c:if test="${u.status eq 0}">未上架</c:if></span>
                        <span style="color:green;"><c:if test="${u.status eq 1}">已上架</c:if></span>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div><%@include file="../common/page.jsp"%></div>
    </div>
</div>
<script type="text/javascript">
    function isEditable(title,url,a,b) {
        var id = '';
        var count = 0;
        $.each($('input:checkbox:checked'), function () {
            if ($(this).val() != '') {
                id += $(this).val() + ',';
                count++;
            }
        });
        id = id.substring(0, id.length - 1);
        if (id == '' || id == null || count > 1) {
            errMsg("请选择一条数据编辑！");
            return;
        }
        $.ajax({
            type: 'post',
            url: '/goods/isShelves',
            data: {id: id},
            success: function (data) {
                if (data.code == 2) {
                    errMsg(data.msg);
                    return;
                }
                layer_show(title, url + '?id=' + id, a, b);
            }

        })
    }

    /**
     *
     * @param flag
     * @param msg
     * @param url
     */
    function putoutAway(flag,msg,url) {
        var id = '';
        var count = 0;
        $.each($('input:checkbox:checked'),function(){
            if($(this).val() != '') {
                id += $(this).val() + ',';
                count ++;
            }
        });
        id = id.substring(0,id.length-1);
        if(id == '' || id == null) {
            errMsg("请选择一条数据编辑！");
            return;
        }
        layer.confirm(msg, function () {
            $.ajax({
                url:url,
                data:{id:id,num:flag},
                type:"post",
                success:function (data) {
                   if(data.code == 2) {
                       errMsg(data.msg);
                       return;
                   }
                    succMsg(data.msg);
                    setTimeout(function () {
                        refresh();
                    },1000)
                },
                error:function () {
                    errMsg("网路异常");
                }
            });

        });
    }
</script>
</body>
</html>
