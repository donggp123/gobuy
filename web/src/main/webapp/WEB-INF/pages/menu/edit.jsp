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
    <form action="/menu/save" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${menu.id}" name="id">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">父菜单：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <span class="select-box">
                    <select name="parentId" class="select">
                        <option value="-1">--请选择--</option>
                        <c:forEach items="${menus}" var="m">
                            <option value="${m.id}" <c:if test="${m.id == menu.parentId}">selected</c:if>>${m.menuName}</option>
                        </c:forEach>
                    </select>
				</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>菜单名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${menu.menuName}" placeholder="" id="menuName" name="menuName">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">url：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${menu.menuUrl}" placeholder="" id="menuUrl" name="menuUrl">
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
                menuName:{
                    required:true,
                }
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                $.ajax({
                    type: 'post',
                    url: '/menu/save',
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
