<%--
  Created by IntelliJ IDEA.
  User: dgp
  Date: 2017/8/25
  Time: 11:56
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
    <form action="/version/save" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${version.id}" name="id">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>版本号：</label>
            <div class="formControls col-xs-8 col-sm-5">
                <input type="text" class="input-text" placeholder="请输入版本号" value="${version.version}" id="version" name="version">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>下载地址：</label>
            <div class="formControls col-xs-8 col-sm-5">
                <input type="text" class="input-text" value="${version.url}" placeholder="请输入下载地址" id="url" name="url">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>发布时间：</label>
            <div class="formControls col-xs-8 col-sm-5">

                <input id="createTime" name="createTime" value="<fmt:formatDate value="${version.createTime}" type="date"></fmt:formatDate>" type="text" placeholder="请选择发布时间" class="input-text size-M" />
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>设备类型：</label>
            <div class="formControls col-xs-8 col-sm-5 skin-minimal">
                <div class="radio-box">
                    <input <c:if test="${version.device == 1}">checked</c:if> value="1" name="device" type="radio" id="device-1">
                    <label for="device-1">android</label>
                </div>
                <div class="radio-box">
                    <input <c:if test="${version.device == 2}">checked</c:if> value="2" type="radio" id="device-2" name="device">
                    <label for="device-2">IOS</label>
                </div>
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
    laydate.render({
        elem: '#createTime'
    });
    $(function(){
        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });

        $("#form-member-add").validate({
            rules:{
                version:{
                    required:true,
                },
                url:{
                    required:true,
                },
                device:{
                    required:true,
                },
                createTime:{
                    required:true,
                }
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                $.ajax({
                    type: 'post',
                    url: '/version/save',
                    data: $(form).serializeArray(),
                    success:function (data) {
                        succMsg(data.msg);
                        setTimeout(function () {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.$('#btn-refresh').click();
                            parent.layer.close(index);
                        },1000)
                    },
                    error:function () {
                        errMsg("网络异常");
                    }
                });
            }
        });
    });
</script>

</html>
