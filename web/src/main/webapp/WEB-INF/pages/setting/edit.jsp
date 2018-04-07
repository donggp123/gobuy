<%--
  Created by IntelliJ IDEA.
  User: dgb
  Date: 2017/8/22
  Time: 17:49
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
    <title></title>
</head>
<body>
<article class="page-container">
    <form action="/setting/save" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${setting.id}" name="id">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>key：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${setting.key}" placeholder="" id="key" name="key">
            </div>
        </div><div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>name：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${setting.name}" placeholder="" id="name" name="name">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>value：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${setting.value}" placeholder="" id="value" name="value">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">text：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${setting.text}" placeholder="" id="text" name="text">
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
            </div>
        </div>
    </form>
</article>
</body>
<script type="text/javascript">
    $(function(){
        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });

        $("#form-member-add").validate({
            rules:{
                key:{
                    required:true
                },
                value:{
                    required:true
                }
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                $.ajax({
                    type: 'post',
                    url: '/setting/save',
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
