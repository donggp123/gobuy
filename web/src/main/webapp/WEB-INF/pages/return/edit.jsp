<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/lib/Hui-iconfont/1.0.8/iconfont.css" />
    <script type="application/javascript" src="${ctx}/lib/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/lib/layer/2.4/layer.js"></script>
    <script type="text/javascript" src="${ctx}/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
    <script type="text/javascript" src="${ctx}/lib/jquery.validation/1.14.0/messages_zh.js"></script>
    <script type="text/javascript" src="${ctx}/lib/jquery.validation/1.14.0/validate-methods.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/lib/select3/css/base.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/lib/select3/css/module.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/lib/select3/css/select3.css"/>
    <script type="text/javascript" src="${ctx}/lib/select3/js/select3.js"></script>
    <script type="text/javascript" src="${ctx}/lib/common/common.js?v=1.2"></script>
    <title></title>
</head>
<body>
<article class="page-container">
    <form action="/return/save" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${ret.id}" name="id">
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>条形码：</label>
            <div class="formControls col-xs-8 col-sm-6">
                <input type="text" class="input-text" value="${ret.barCode}" name="barCode" placeholder="请输入条形码">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3">商品名称：</label>
            <div class="formControls col-xs-8 col-sm-6">
                <input type="text" class="input-text" value="${ret.goodsName}" name="goodsName" placeholder="请输入商品名称">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>退货数量：</label>
            <div class="formControls col-xs-8 col-sm-6">
                <input type="text" class="input-text" value="${ret.retNum}" name="retNum" placeholder="请输入库存量">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>退货原因：</label>
            <div class="formControls col-xs-8 col-sm-6">
                <c:forEach items="${dicts}" var="d">
                    <label><input type="checkbox" class="input-text" <c:if test="${fn:contains(ret.retReason, d.value)}">checked</c:if> value="${d.value}" name="retReason"> ${d.name}</label>
                </c:forEach>
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
    $(function (){

        $.getJSON('/clazz/getSelectData', function(json) {
            var data = '${ret.goodsType}' != '' ? '${ret.goodsType}'.split(',') : [] ;
            $('#goodsType').select3({
                treeData: json.data,
                data: data,
                isDevelopMode: false,
                isSelectParent: true,
                isMultiple: true,
                placeholder: '请选择商品分类'
            });
        })

        $("#form-member-add").validate({
            ignore: [],
            rules:{
                retNum:{
                    required:true,
                    number:true
                },
                barCode:{
                    required:true
                },
                retReason:{
                    required:true
                }
            },
            onkeyup:false,
            focusCleanup:true,
            success:"valid",
            submitHandler:function(form){
                $.ajax({
                    type: 'post',
                    url: '/return/save',
                    data: $(form).serializeArray(),
                    success:function (data) {
                        if(data.code == 2){
                            errMsg(data.msg);
                            return;
                        }
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
