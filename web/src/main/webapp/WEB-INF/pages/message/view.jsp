<%--
  Created by IntelliJ IDEA.
  User: sunwei
  Date: 2017/9/2
  Time: 9:57
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
    <title>消息详情</title>
</head>
<body>
<article class="page-container">
   <div style="width:80%;height:auto;margin:0 auto;">
       <div style="text-align:center;font-size: 20px;">
            我的消息${message.title}
       </div>
       <div>
           <span>
               接收者:${message.toName}
           </span>
           <div style="margin:15px 0px 15px 30px;">
               ${message.text}
           </div>
           <div style="text-align: right;">
               <span>
                   发送者:${message.fromName}
               </span><br/>
               <span>
                   发送时间:<fmt:formatDate value="${message.sendTime}" type="date"/>
               </span>
           </div>
       </div>
   </div>

</article>
</body>
</html>
