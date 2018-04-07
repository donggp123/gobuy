<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/common.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>产品分类</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 商品管理 <span class="c-gray en">&gt;</span> 产品分类 <a class="btn btn-success radius r size-M" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<table class="table">
    <tr>
        <td width="200" class="va-t"><ul id="treeDemo" class="ztree"></ul></td>
        <td class="va-t"><iframe ID="testIframe" Name="testIframe" FRAMEBORDER=0 SCROLLING=AUTO width=100%  height=390px src="/clazz/add"></iframe></td>
    </tr>
</table>
<script type="text/javascript">
    var setting = {
        view: {
            dblClickExpand: false,
            showLine: false,
            selectedMulti: false
        },
        edit: {
            enable: true, //单独设置为true时，可加载修改、删除图标
            drag:{
                isMove:true,
                prev:true,
                autoOpenTime: 0
            },
            showRemoveBtn:false,
            renameTitle: "编辑商品分类名称",
        },

        data: {
            simpleData: {
                enable:true,
                idKey: "code",
                pIdKey: "parCode",
                rootPId: ""
            }
        },
        callback: {
            onRename: onRename ,//修改事件
            beforeRename:beforeRename,
            beforeClick: function(treeId, treeNode) {
                console.log(treeNode)
               /* var zTree = $.fn.zTree.getZTreeObj("tree");*/
                demoIframe.attr("src","/clazz/add?parCode="+treeNode.code+"&grade="+treeNode.grade);
                return true;
            }
        }
    };

    var code;

    function showCode(str) {
        if (!code) code = $("#code");
        code.empty();
        code.append("<li>"+str+"</li>");
    }

    $(document).ready(function(){
        $.ajax({
            type: 'post',
            url: '/clazz/data',
            success:function (data) {
                var t = $("#treeDemo");
                t = $.fn.zTree.init(t, setting, data.data);
                demoIframe = $("#testIframe");
                var zTree = $.fn.zTree.getZTreeObj("tree");
            }
        });
    });

    function onRename(e, treeId, treeNode, isCancel) {
        $.ajax({
            url: "/clazz/edit",
            type: "post",
            data: {
                id: treeNode.id,
                name: treeNode.name
            },
            success: function (data) {
                if(data.code == 1){
                    succMsg(data.msg);
                    setTimeout(function () {
                        refresh();
                    },1000)
                }
            },
            error: function () {
                errMsg("网路异常");
            }
        });

    }
    function beforeRename(treeId, treeNode, newName, isCancel) {
        if (newName.length == 0) {
            errMsg("节点名称不能为空.");
            return false;
        }
        return true;
    }

</script>
</body>
</html>