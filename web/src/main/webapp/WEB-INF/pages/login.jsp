<%--
  Created by IntelliJ IDEA.
  User: dgp
  Date: 2017/8/18
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="Shortcut Icon" href="${ctx}/static/h-ui.admin/images/favicon.ico" />
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${ctx}/lib/html5shiv.js"></script>
    <script type="text/javascript" src="${ctx}/lib/respond.min.js"></script>
    <![endif]-->
    <link href="${ctx}/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="${ctx}/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>后台登录</title>

    <script language="JavaScript">
        if (window != top)
            top.location.href = location.href;
    </script>
</head>
<body>
<div class="header">
    <img style="float: left;margin-top:10px;margin-right: 12px;margin-left:20px;width: 40px;height: 40px;border-radius: 20px;" src="${ctx}/static/h-ui.admin/images/favicon.ico" alt="">
    内蒙古蒂诺科技有限公司</div>
<div class="loginWraper">
    <div id="loginform" class="loginBox" style="float:right;">
        <div class="form form-horizontal">
            <span style="margin-left:160px;" id="msg"></span>
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
                <div class="formControls col-xs-8">
                    <input id="name" name="username" type="text" placeholder="账户" class="input-text size-L" onblur="isName();">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
                <div class="formControls col-xs-8">
                    <input id="password" name="password" type="password" placeholder="密码" class="input-text size-L" onblur="isPwd()">
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input id="code" name="code" class="input-text size-L" type="text" placeholder="验证码" onblur="isCode();" style="width:150px;">
                    <img id="img" src="/validateCode" style="cursor:pointer;" onclick="this.src='/validateCode'+'?'+Math.random();">
                    <a id="kanbuq" href="javascript:;" onclick="refresh();">看不清，换一张</a>
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <button onclick="submuit();" class="btn btn-success radius size-L">&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;</button>
                    <a  href="/register" class="btn btn-secondary radius size-L" >商家注册</a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="footer">内蒙古蒂诺科技有限公司</div>
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
    function refresh(){
        $("#img").attr("src","/validateCode?id="+Math.random());
    }
    function isName(){
        if($("#name").val() == ""){
            $("#msg").html("登录名不能为空");
            $("#msg").css("color","red");
            return false;
        }
        $("#msg").html("");
        return true;
    }
    function isPwd(){
        if($("#password").val() == ""){
            $("#msg").html("密码不能为空");
            $("#msg").css("color","red");
            return false;
        }
        $("#msg").html("");
        return true;
    }
    function isCode(){
        if($("#code").val() == ""){
            $("#msg").html("验证码不能为空");
            $("#msg").css("color","red");
            return false;
        }
        $("#msg").html("");
        return true;
    }
    function submuit(){
        if(isName() && isPwd() && isCode()){
            $.ajax({
                url:'/login',
                data:{
                    username:$("#name").val(),
                    password:$("#password").val(),
                    code:$("#code").val(),
                },
                type:"post",
                success:function(data){
                    if(data.code == 0){
                        errMsg(data.msg)
                    }else if(data.code == 1){
                        succMsg(data.msg);
                        setTimeout(function () {
                            location.href="/index";
                        },1000)

                    }else{
                        errMsg(data.msg)
                    }
                },
                error:function (){
                    errMsg("网络异常");
                }
            })
        }
        return false;
    }
    //回车登录
    $("body").keydown(function(event) {
        if (event.keyCode == "13") {//keyCode=13是回车键
            submuit();
        }
    });
</script>
</body>
</html>
