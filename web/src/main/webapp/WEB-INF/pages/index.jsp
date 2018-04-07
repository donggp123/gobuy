<%--
  Created by IntelliJ IDEA.
  User: dgp
  Date: 2017/8/19
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/common.jsp"%>

<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="Shortcut Icon" href="${ctx}/static/h-ui.admin/images/favicon.ico" />
    <title>后台管理系统</title>
</head>
<body>
<header class="navbar-wrapper">
    <div class="navbar navbar-fixed-top">
        <div class="container-fluid cl">

            <a class="logo navbar-logo f-l mr-10 hidden-xs" href="/index">
                <img style="float: left;margin-top:7px;margin-right: 12px;width: 25px;height: 30px;" src="${ctx}/static/h-ui.admin/images/favicon.ico" alt="">
                后台管理</a>
            <nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                <ul class="cl">
                    <li id="webjx" style="margin-right:30px;"></li>
                    <li>${user.realName}</li>
                    <li class="dropDown dropDown_hover">
                        <a href="#" class="dropDown_A"><shiro:principal/> <i class="Hui-iconfont">&#xe6d5;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="###" onclick="change_password('修改密码','/user/tochange?id='+${user.id},'500','270')">修改密码</a></li>
                            <li><a href="###" onclick="logOut()">退出</a></li>
                        </ul>
                    </li>
                    <c:if test="${user.status == 2 && (user.type == 1 || user.type == 2)}">
                        <li id="Hui-msg"> <a onclick="open_msg(this)" data-href="message/list?flag=to&status=0" data-title="我的消息" href="javascript:void(0)"><span class="badge badge-danger" id="badge-danger"></span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
                    </c:if>
                    <li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
                            <li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
                            <li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
                            <li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
                            <li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
                            <li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</header>
<aside class="Hui-aside">
    <div class="menu_dropdown bk_2">
        <c:forEach items="${menus}" var="v">
            <dl id="menu-article">
                <dt> ${v.menuName}<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <c:forEach items="${v.childs}" var="vv">
                        <li><a data-href="${vv.menuUrl}" data-title="${vv.menuName}" href="javascript:void(0)">${vv.menuName}</a></li>
                        </c:forEach>
                    </ul>
                </dd>
            </dl>
        </c:forEach>
    </div>
</aside>
<div class="dislpayArrow hidden-xs" style="position: absolute;z-index: 999;"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box" style="position: absolute;z-index: 100;">
    <div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
        <div class="Hui-tabNav-wp">
            <ul id="min_title_list" class="acrossTab cl">
                <li class="active">
                    <span title="我的桌面" data-href="welcome.html">我的桌面</span>
                    <em></em></li>
            </ul>
        </div>
        <div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
    </div>
    <div id="iframe_box" class="Hui-article">
        <div class="show_iframe">
            <div style="display:none" class="loading"></div>
            <c:if test="${user.status == 2}">
                <iframe id="iframe" scrolling="yes" frameborder="0" src="${user.homepage}"></iframe>
            </c:if>
            <c:if test="${user.status == 1 || user.status == 3}">
                <div style="font-size: 50px;color: red;position: absolute;left: 20%;top: 40%;">您还没有通过审核，暂时没有权限操作!!!</div>
            </c:if>
        </div>
    </div>
</section>

<div class="contextMenu" id="Huiadminmenu">
    <ul>
        <li id="closethis">关闭当前 </li>
        <li id="closeall">关闭全部 </li>
    </ul>
</div>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${ctx}/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
<script type="text/javascript">
    function show(){
        var d = new Date()
        var vYear = d.getFullYear();
        var vMon = d.getMonth() + 1;
        var vDay = d.getDate();
        var h = d.getHours();
        var m = d.getMinutes();
        var se = d.getSeconds();
        s=vYear+"年"+(vMon<10 ? "0" + vMon : vMon)+"月"+(vDay<10 ? "0"+ vDay : vDay)+"日"+(h<10 ? "0"+ h : h)+"点"+(m<10 ? "0" + m : m)+"分"+(se<10 ? "0" +se : se);
        $("#webjx").html(s);
    }
    setInterval("show()",1000);
 function logOut() {
     layer.confirm('确认要退出吗？',function(index){
         location.href = "/logout";
     });
 }

 function change_password(title,url,w,h) {
     layer_show(title,url,w,h);
 }

 function open_msg(obj) {
     Hui_admin_tab(obj);
     $('#badge-danger').html('');
 }
 function showAuto() {
     $.ajax({
         type: 'post',
         url: '/message/count',
         data:{status:0},
         success:function (data) {
             if(data.data > 0){
                 $('#badge-danger').html(data.data);
                 return;
             }
             $('#badge-danger').html('');
         }
     })
 }
</script>
<%@include file="common/msg.socket.jsp"%>
</body>
</html>