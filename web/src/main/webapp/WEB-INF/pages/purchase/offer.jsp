<%--
  Created by IntelliJ IDEA.
  User: sunwei
  Date: 2017/9/1
  Time: 19:49
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
    <title>报价详情</title>
</head>
<body>
<article class="page-container">
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg mt-20">
            <thead>
            <tr>
                <th colspan="2" scope="col" style="font-size: larger;text-align: center;">报价</th>
            </tr>
            </thead>
            <tr>
                <td>商户名称</td>
                <td>${purchase.mrhtName}</td>
            </tr>
            <tr>
                <td>商品名称</td>
                <td>${purchase.goodsName}</td>
            </tr>
            <tr>
                <td>商品规格</td>
                <td>${purchase.goodsSpec}</td>
            </tr>
            <tr>
                <td>商品类别</td>
                <td>${purchase.typeName}</td>
            </tr>
            <tr>
                <td>采购数量</td>
                <td><span style="color: red;font-size: larger;">${purchase.num}</span></td>
            </tr>

        </table>
        <form action="" method="post" class="form form-horizontal" id="form-member-add">
            <input type="hidden" name="purId" value="${purchase.id}">
            <div class="row cl">
                <div class="formControls col-xs-8 col-sm-12">
                    <dl class="permission-list">
                        <dt>
                            <label>是否可退换</label>
                        </dt>
                        <dd>
                            <dl class="cl permission-list2">
                                <c:forEach items="${dicMap}" var="dic">
                                    <dt>
                                        <label class="">
                                            <input type="checkbox" value="${dic.value}" name="isReturn"> ${dic.name}
                                        </label>
                                    </dt>
                                </c:forEach>
                            </dl>
                        </dd>
                    </dl>
                    <dl class="permission-list">
                        <dt>
                            <label><span style="color: red">*</span>支付方式</label>
                        </dt>
                        <dd>
                            <dl class="cl permission-list2">
                                <dt>
                                    <label class="">
                                        <input type="radio" checked value="1" name="payType" id="3">现金支付
                                    </label>
                                </dt>
                            </dl>
                        </dd>
                    </dl>
                    <dl class="permission-list">
                        <dt>
                            <label><span style="color: red">*</span> 商品报价</label>
                        </dt>
                        <dd>
                            <dl class="cl permission-list2">
                                <c:if test="${goods.sellPrice != null && goods.sellPrice >0}">
                                    <input type="hidden" value="${goods.sellPrice}" name="quote">
                                    ${goods.sellPrice}
                                </c:if>
                                <c:if test="${goods.sellPrice == null || goods.sellPrice == 0}">
                                    <input type="test" class="input-text" value="" placeholder="请输入商品预报价格" name="quote">
                                </c:if>
                            </dl>
                        </dd>
                    </dl>
                    <dl class="permission-list">
                        <dt>
                            <label>备注</label>
                        </dt>
                        <dd>
                            <dl class="cl permission-list2">
                                <textarea name="remark"  class="input-text" placeholder="对商家说点什么..."></textarea>
                            </dl>
                        </dd>
                    </dl>
                </div>
            </div>
            <div class="row cl">
                <div class="col-xs-8 col-sm-9">
                    <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
                </div>
            </div>
        </form>
    </div>
</article>
<script type="text/javascript">
    $(function () {
        $("#form-member-add").validate({
            rules:{
                payType:{
                    required:true,
                },
                quote:{
                    required:true,
                    number:true
                },
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                $.ajax({
                    type: 'post',
                    url: '/flow/save',
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
    })
</script>
</body>
</html>
