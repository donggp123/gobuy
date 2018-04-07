<%--
  Created by IntelliJ IDEA.
  User: dgp
  Date: 2017/8/30
  Time: 9:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common/common.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/merchant/saveAccount" method="post" class="form form-horizontal" id="form-member-add">
<div style="padding-bottom: 20px;margin-top:20px;">
    <div class="row cl">
        <label class="form-label col-xs-2"><i class="Hui-iconfont" style="font-size: larger;">收款账号</i></label>
    </div>
    <div class="row cl">
        <label class="form-label col-xs-2"><span class="c-red">*</span>财务联系人 :</label>
        <div class="formControls col-xs-5">
            <input value="${mrht.financialContact}" id="financialContact" name="financialContact" type="text" placeholder="请填写财务联系人" class="input-text size-M" />
        </div>
    </div>
    <div class="row cl">
        <label class="form-label col-xs-2"><span class="c-red">*</span>联系电话 :</label>
        <div class="formControls col-xs-5">
            <input value="${mrht.contactNum}" id="contactNum" name="contactNum" type="text" placeholder="请填写联系电话" class="input-text size-M" />
        </div>
    </div>
    <div class="row cl">
        <label class="form-label col-xs-2"><span class="c-red">*</span>开户行 :</label>
        <div class="formControls col-xs-5">
            <input value="${mrht.openBank}" id="openBank" name="openBank" type="text" placeholder="请填写开户行" class="input-text size-M" />
        </div>
    </div>
    <div class="row cl">
        <label class="form-label col-xs-2"><span class="c-red">*</span>开户名 :</label>
        <div class="formControls col-xs-5">
            <input value="${mrht.accountName}" id="accountName" name="accountName" type="text" placeholder="请填写开户名" class="input-text size-M" />
        </div>
    </div>
    <div class="row cl">
        <label class="form-label col-xs-2"><span class="c-red">*</span>账号 :</label>
        <div class="formControls col-xs-5">
            <input value="${mrht.accountNum}" id="accountNum" name="accountNum" type="text" placeholder="请填写账号" class="input-text size-M" />
        </div>
    </div>
</div>
<div class="row cl">
    <div class="formControls col-xs-6 col-xs-offset-2">
        <input type="hidden" value="${mrht.id}" name="id">
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
                financialContact:{
                    required:true,
                },
                contactNum:{
                    required:true,
                },
                openBank:{
                    required:true,
                },
                accountName:{
                    required:true,
                },
                accountNum:{
                    required:true,
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
                    url: '/merchant/saveAccount',
                    data: $(form).serializeArray(),
                    success:function (data) {
                        if(data.code == 1){
                            succMsg(data.msg);
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
