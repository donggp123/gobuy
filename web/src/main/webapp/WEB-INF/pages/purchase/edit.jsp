<%--
  Created by IntelliJ IDEA.
  User: sunwei
  Date: 2017/8/31
  Time: 10:46
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
</head>
<body>
<article class="page-container">
    <form action="/purchase/save" method="post" class="form form-horizontal" id="form-member-add">
        <c:if test="${goods == null}">
        <input type="hidden" value="${purchase.id}" name="id">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>商品条形码：</label>
            <div class="formControls col-xs-8 col-sm-6">
                <input type="text" class="input-text" value="${purchase.barCode}" placeholder="" id="barCode" name="barCode">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">商品名称：</label>
            <div class="formControls col-xs-8 col-sm-6">
                <input type="text" class="input-text" value="${purchase.goodsName}" placeholder="" id="goodsName" name="goodsName">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>采购数量：</label>
            <div class="formControls col-xs-8 col-sm-6">
                <input type="text" class="input-text" value="${purchase.num}" placeholder="" id="num" name="num">
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input class="btn btn-primary radius size-M" type="submit" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
            </div>
        </div>
        </c:if>
        <c:if test="${goods != null}">
            <input type="hidden" value="${purchase.id}" name="id">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>商品条形码：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" class="input-text" value="${goods.barCode}" placeholder="" name="barCode">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3">商品名称：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" class="input-text" value="${goods.goodsName}" placeholder=""  name="goodsName">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>采购数量：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" class="input-text" value="" placeholder=""  name="num">
                </div>
            </div>
            <div class="row cl">
                <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                    <input class="btn btn-primary radius size-M" type="submit" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
                </div>
            </div>
        </c:if>
    </form>
</article>
</body>
<script type="text/javascript">
    $(function(){
        $("#form-member-add").validate({
            rules:{
                goodsSpec:{
                    required:true
                },
                barCode:{
                    required:true
                },
                goodsType:{
                    required:true
                },
                num:{
                    required:true,
                    number:true
                }
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                $.ajax({
                    type: 'post',
                    url: '/purchase/save',
                    data: $(form).serializeArray(),
                    success:function (data) {
                        succMsg(data.msg);
                        setTimeout(function () {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.$('#btn-refresh').click();
                            parent.layer.close(index);
                        },1000)
                    },
                    error:function (data) {
                        errMsg("网络异常");
                    }
                });

            }
        });
    });
</script>
</html>
