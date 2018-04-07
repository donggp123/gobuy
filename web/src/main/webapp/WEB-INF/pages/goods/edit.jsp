
<%--
  Created by IntelliJ IDEA.
  User: dgb
  Date: 2017/8/21
  Time: 15:47
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
    <form action="/goods/save" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${goods.id}" name="id" id="goodsId">
        <input type="hidden" value="${goods.version}" name="version" id="version">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>条形码：</label>
            <div class="formControls col-xs-8 col-sm-6">
                <input id="barCode" type="text" class="input-text" value="${goods.barCode}" name="barCode" placeholder="请输入条形码">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>商品名称：</label>
            <div class="formControls col-xs-8 col-sm-6">
                <input type="text" class="input-text" value="${goods.goodsName}" name="goodsName" placeholder="请输入商品名称">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>商品规格：</label>
            <div class="formControls col-xs-8 col-sm-6">
                <input type="text" class="input-text" value="${goods.goodsSpec}" name="goodsSpec" placeholder="请输入商品规格">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>商品分类：</label>
            <div class="formControls col-xs-8 col-sm-2">
                ${goodsClass['goods1']}
            </div>
            <div class="formControls col-xs-8 col-sm-2" >
                ${goodsClass['goods2']}
            </div>
            <div class="formControls col-xs-8 col-sm-2">
                ${goodsClass['goods3']}
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>商品图片 :</label>
            <div class="formControls col-xs-8 col-sm-6">
                <div id="goodsImageShow" style="float:left;">
                    <input class="goodsImageSave" type="hidden" value="${goods.goodsImage}" name="goodsImage">
                    <c:if test="${goods.goodsImage != null}">
                        <script>
                            loadImg('${goods.goodsImage}','${imgServer}','goodsImageShow',1);
                        </script>
                    </c:if>
                </div>
                <div onclick="uploads('goodsImage','goodsImageSave','goodsImageShow',1);"  style="width:88px;height: 88px;border:1px solid #ccc;line-height:24px;padding-top:20px;text-align: center;color: #ccc;cursor:pointer;float:left;">
                    <span style="font-size: 24px;">+</span><br/>添加图片
                </div>
                <input id="goodsImage" type="file" name="file" style="display: none;">
            </div>
        </div>
        <c:if test="${user.type == 2}">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>库存量：</label>
            <div class="formControls col-xs-8 col-sm-6">
                <input type="text" class="input-text" value="${goods.stockNum}" name="stockNum" placeholder="请输入库存量">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>进价：</label>
            <div class="formControls col-xs-8 col-sm-6">
                <input type="text" class="input-text" value="${goods.originalPrice}" name="originalPrice" placeholder="请输入原价">
            </div>
        </div>
        </c:if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>售价：</label>
            <div class="formControls col-xs-8 col-sm-6">
                <input type="text" class="input-text" value="${goods.sellPrice}" name="sellPrice" placeholder="请输入售价">
            </div>
        </div>

        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
            </div>
        </div>
    </form>
</article>
</body>
<script type="text/javascript">
    function classes(value,id,addr) {
        $.ajax({
            url:"/goods/classes",
            type:"post",
            data:{
                parCode:value
            },
            success:function (data) {
                $("#"+id).empty();
                var option = '<option value="">'+addr+'</option>';
                $("#"+id).append(option);
                $.each(data.data,function (v,d) {
                    option = '<option value="'+d.code+'">'+d.name+'</option>';
                    $("#"+id).append(option);
                })
            },
            error:function () {
                errMsg("网络异常");
            }
        })
    }
$(function (){

    $("#form-member-add").validate({
            ignore: [],
            rules:{
                goodsName:{
                    required:true,
                },
                goodsSpec:{
                    required:true,
                },
                goodsType:{
                    required:true,
                },
                goodsImage:{
                    required:true,
                },
                stockNum:{
                    required:true,
                    number: true
                },
                originalPrice:{
                    required:true,
                    number: true
                },
                sellPrice:{
                    required:true,
                    number: true
                },
                barCode:{
                    required:true,
                    number: true,
                    remote:{
                        url: "/goods/code",
                        type: "post",
                        async:false,
                        dataType: "json",
                        data: {
                            barCode:function () {
                                return $("#barCode").val();
                            }
                        },
                        dataFilter: function (data) {
                            data = JSON.parse(data)
                            if($('#goodsId').val() > 0){
                                return true;
                            }
                            if(data.code == 2) {
                                return false;
                            }
                            return true;
                        }
                    }
                },
            },
            messages:{
                barCode:{
                    remote:'条形码已经存在'
                }
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                if($('#goodsId').val() == ''){
                    $.ajax({
                        url: "/goods/code",
                        type: "post",
                        data: {
                            barCode:$("#barCode").val()
                        },
                        success:function (data) {
                            if(data.code == 2){
                                errMsg(data.msg)
                                return;
                            }else{
                                $.ajax({
                                    type: 'post',
                                    url: '/goods/save',
                                    data: $(form).serializeArray(),
                                    success:function (data) {
                                        if(data.code == 2){
                                            errMsg(data.msg)
                                            return;
                                        }
                                        layer.confirm(data.msg, {
                                            btn: ['取消', '确定'] //可以无限个按钮
                                        }, function(index, layero){
                                            index = parent.layer.getFrameIndex(window.name);
                                            parent.$('#btn-refresh').click();
                                            parent.layer.close(index);

                                        });

                                    },
                                    error:function (data) {
                                        errMsg("网络异常");
                                    }
                                });

                            }
                        }
                    })
                }else{
                    $.ajax({
                        type: 'post',
                        url: '/goods/save',
                        data: $(form).serializeArray(),
                        success:function (data) {
                            if(data.code == 2){
                                errMsg(data.msg)
                                return;
                            }
                            layer.confirm(data.msg, {
                                btn: ['取消', '确定'] //可以无限个按钮
                            }, function(index, layero){
                                index = parent.layer.getFrameIndex(window.name);
                                parent.$('#btn-refresh').click();
                                parent.layer.close(index);

                            });

                        },
                        error:function (data) {
                            errMsg("网络异常");
                        }
                    });
                }
            }
        });
    });
</script>
</html>
