<%--
  Created by IntelliJ IDEA.
  User: dgp
  Date: 2017/9/1
  Time: 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>添加产品分类</title>
</head>
<body>
<div class="page-container">
    <form action="/clazz/save" method="post" class="form form-horizontal" id="form-member-add">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                父类别编码：</label>
            <div class="formControls col-xs-6 col-sm-6">
                ${mrhtClass.parCode}
                <input type="hidden" name="parCode" value="${mrhtClass.parCode}">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                类别编码：</label>
            <div class="formControls col-xs-6 col-sm-6">
                ${mrhtClass.code}
                <input type="hidden" value="${mrhtClass.code}" name="code">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                等级级别：</label>
            <div class="formControls col-xs-6 col-sm-6">
                ${mrhtClass.grade}
                <input type="hidden" name="grade" value="${mrhtClass.grade}">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>
                分类名称：</label>
            <div class="formControls col-xs-6 col-sm-6">
                <input type="text" class="input-text" value="" placeholder="请输入分类名称" id="user-name" name="name">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">备注：</label>
            <div class="formControls col-xs-6 col-sm-6">
                <textarea name="remark" cols="" rows="" class="textarea"  placeholder="比如（普通|婴幼儿|功能性|护发素）" onKeyUp="$.Huitextarealength(this,100)"></textarea>
                <p class="textarea-numberbar"><em class="textarea-length">0</em>/100</p>
            </div>
        </div>
        <div class="row cl">
            <div class="col-9 col-offset-2">
                <input class="btn btn-primary radius size-M" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
            </div>
        </div>
    </form>
</div>
</body>
<script type="text/javascript">
    $(function(){
        $("#form-member-add").validate({
            rules:{
                name:{
                    required:true,
                }
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                $.ajax({
                    type: 'post',
                    url: '/clazz/save',
                    data: $(form).serializeArray(),
                    success:function (data) {
                        succMsg(data.msg);
                        setTimeout(function () {
                           window.parent.refresh();
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