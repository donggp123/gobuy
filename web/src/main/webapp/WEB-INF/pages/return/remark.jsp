<%--
  Created by IntelliJ IDEA.
  User: dgp
  Date: 2017/9/7
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/common.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<article class="page-container">
    <form action="/goods/save" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" name="id" value="${id}">
        <input type="hidden" name="num" value="${num}">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3" for="">备注：</label>
            <textarea name="remark" id="" cols="30" rows="6" placeholder="请填写拒绝原因" style="padding:5px;"></textarea>
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
    $(function () {
        $("#form-member-add").validate({
            rules:{
                remark:{
                    required:true
                }
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                $.ajax({
                    type: 'post',
                    url: '/return/retGoods',
                    data: $(form).serializeArray(),
                    success:function (data) {
                        succMsg(data.msg);
                        setTimeout(function () {
                            index = parent.layer.getFrameIndex(window.name);
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
</html>
