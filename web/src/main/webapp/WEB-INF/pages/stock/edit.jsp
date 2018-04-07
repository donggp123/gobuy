<%--
  Created by IntelliJ IDEA.
  User: sunwei
  Date: 2017/8/30
  Time: 15:08
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
    <title>库存量设置</title>
</head>
<body>
    <form action="/stock/edit" method="post" class="form form-horizontal" id="form-member-add">
        <div style="padding-bottom: 20px;margin-top:20px;">
            <div class="row cl">
                <label class="form-label col-xs-2"><i class="Hui-iconfont" style="font-size: larger;">库存量设置</i></label>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-2"><span class="c-red">*</span>库存量设置 :</label>
                <div class="formControls col-xs-5">
                    <input value="${mrhtStock.stockLimit}" id="stockLimit" name="stockLimit" type="text" placeholder="" class="input-text size-M" />
                </div>
                <div style="color:#666666;" class="formControls col-xs-5"><span class="c-red">*</span>用于提示库存量不足</div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-2"><span class="c-red">*</span>采购数量 :</label>
                <div class="formControls col-xs-5">
                    <input value="${mrhtStock.purNum}" id="purNum" name="purNum" type="text" placeholder="" class="input-text size-M" />
                </div>
                <div style="color:#666666;" class="formControls col-xs-5"><span class="c-red">*</span>库存不足时，设置的默认采购数量</div>
            </div>
        </div>
        <div class="row cl">
            <div class="formControls col-xs-6 col-xs-offset-2">
                <input type="hidden" value="${mrhtStock.id}" name="id">
                <input  type="submit" id="submit" class="btn btn-secondary radius size-L" value="&nbsp;保&nbsp;&nbsp;&nbsp;&nbsp;存&nbsp;">
            </div>
        </div>
    </form>
</body>
<script type="text/javascript">
    $(function(){
        /**
         * 表单验证
         */
        $("#form-member-add").validate({
            rules:{
                stockLimit:{
                    required:true,
                    number:true,
                },
                purNum:{
                    required:true,
                    number:true,
                }
            },
            /**
             * 提交form表单
             */
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                $("#submit").attr({"disabled":"disabled"});
                $.ajax({
                    type: 'post',
                    url: '/stock/save',
                    data: $(form).serializeArray(),
                    success:function (data) {
                        if(data.code == 1){
                            succMsg(data.msg);
                            setTimeout(function () {
                                refresh();
                            },1000)
                            return;
                        }
                        errMsg(data.msg);
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
