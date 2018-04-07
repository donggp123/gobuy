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
    <form action="/role/save" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${role.id}" name="id">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色名：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${role.roleName}" placeholder="" id="roleName" name="roleName">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">描述：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <textarea name="content" cols="" rows="" class="textarea"  placeholder="说点什么...最少输入10个字符" >${role.content}</textarea>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">角色权限：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <c:forEach items="${menus}" var="m">
                <dl class="permission-list">
                    <dt>
                        <label><input type="checkbox" <c:if test="${m.checked == true}">checked</c:if> value="${m.id}" name="menuIds"> ${m.menuName}</label>
                    </dt>
                    <c:forEach items="${m.childs}" var="c">
                    <dd>
                        <dl class="cl permission-list2">
                            <dt>
                                <label class=""><input type="checkbox" <c:if test="${c.checked == true}">checked</c:if> value="${c.id}" name="menuIds" id="user-Character-0-0"> ${c.menuName}</label>
                            </dt>
                            <dd>
                                <c:forEach items="${c.btns}" var="b">
                                <label class=""><input type="checkbox" value="${b.id}" <c:if test="${b.checked == true}">checked</c:if> name="btnIds" id="user-Character-0-0-0"> ${b.btnName}</label>
                                </c:forEach>
                            </dd>
                        </dl>
                    </dd>
                    </c:forEach>
                </dl>
                </c:forEach>
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

        $(".permission-list dt input:checkbox").click(function(){
            $(this).closest("dl").find("dd input:checkbox").prop("checked",$(this).prop("checked"));
        });
        $(".permission-list2 dd input:checkbox").click(function(){
            var l =$(this).parent().parent().find("input:checked").length;
            var l2=$(this).parents(".permission-list").find(".permission-list2 dd").find("input:checked").length;
            if($(this).prop("checked")){
                $(this).closest("dl").find("dt input:checkbox").prop("checked",true);
                $(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",true);
            }else{
                if(l==0){
                    $(this).closest("dl").find("dt input:checkbox").prop("checked",false);
                }
                if(l2==0){
                    $(this).parents(".permission-list").find("dt").first().find("input:checkbox").prop("checked",false);
                }
            }
        });

        $("#form-member-add").validate({
            rules:{
                roleName:{
                    required:true,
                }
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                $.ajax({
                    type: 'post',
                    url: '/role/save',
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
