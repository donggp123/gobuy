<%--
  Created by IntelliJ IDEA.
  User: dgp
  Date: 2017/9/14
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common/common.jsp"%>
<html>
<head>
    <title>回复</title>
</head>
<body>
<article class="page-container">
    <form action="/rider/save" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${mberId}" name="mberId">
        <input type="hidden" value="${id}" name="id">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>拒绝理由：</label>
            <div class="formControls col-xs-8 col-sm-6">
                <textarea cols="32" rows="8" type="text" style="padding: 5px;" placeholder="请输入拒绝内容"  name="remark"></textarea>
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
<script>
    $(function(){
        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });

        $("#form-member-add").validate({
            rules:{
                remark:{
                    required:true,
                }
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                $.ajax({
                    type: 'post',
                    url: '/rider/save',
                    data: $(form).serializeArray(),
                    success:function (data) {
                        succMsg(data.msg);
                        setTimeout(function () {
                            var index = parent.parent.layer.getFrameIndex(window.name);
                            parent.parent.$('#btn-refresh').click();
                            parent.parent.layer.close(index);
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
