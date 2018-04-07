<%--
  Created by IntelliJ IDEA.
  User: dgp
  Date: 2017/8/25
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>错误提示</title>
    <link rel="Shortcut Icon" href="${ctx}/static/h-ui.admin/images/favicon.ico" />
</head>

<body>
<section class="container-fluid page-404 minWP text-c">
    <p class="error-title"><i class="Hui-iconfont va-m" style="font-size:80px">&#xe688;</i>
    </p>
    <p class="error-description">${message }</p>
    <p class="error-info">您可以：
        <a href="javascript:;" onclick="history.go(-1)" class="c-primary">&lt; 返回上一页</a>
        <span class="ml-20">|</span>
        <a href="/" class="c-primary ml-20">去首页 &gt;</a>
    </p>
</section>
<h1><font color="red"></font></h1><br>
</body>
</html>