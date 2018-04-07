<%--
  Created by IntelliJ IDEA.
  User: dgb
  Date: 2017/9/2
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="pop" style="display:none;position: absolute;z-index: 1000;">
    <style type="text/css">
        *{margin:0;padding:0;}
        #pop{background:#fff;width:260px;border:1px solid #e0e0e0;font-size:12px;position: fixed;right:10px;bottom:10px;}
        #popHead{line-height:32px;background:#f6f0f3;border-bottom:1px solid #e0e0e0;position:relative;font-size:12px;padding:0 0 0 10px;}
        #popHead h2{font-size:14px;color:#666;line-height:32px;height:32px;}
        #popHead #popClose{position:absolute;right:10px;top:1px;}
        #popHead a#popClose:hover{color:#f00;cursor:pointer;}
        #popContent{padding:5px 10px;}
        #popTitle a{line-height:24px;font-size:14px;font-family:'微软雅黑';color:#333;font-weight:bold;text-decoration:none;}
        #popTitle a:hover{color:#f60;}
        #popIntro{text-indent:24px;line-height:160%;margin:5px 0;color:#666;}
        #popMore{text-align:right;border-top:1px dotted #ccc;line-height:24px;margin:8px 0 0 0;}
        #popMore a{color:#f60;}
        #popMore a:hover{color:#f00;}
    </style>
    <div id="popHead">
        <a id="popClose" title="关闭">关闭</a>
        <h2>温馨提示</h2>
    </div>
    <div id="popContent">
        <dl>
            <dt id="popTitle"><a href="javascript:void(0);" >这里是参数</a></dt>
            <dd id="popIntro">这里是内容简介</dd>
        </dl>
        <p id="popMore"><a href="javascript:void(0);">查看 »</a></p>
    </div>
</div>
<script type="text/javascript" src="${ctx}/lib/common/msg.pop.js"></script>
<script type="text/javascript" src="${ctx}/lib/websocket/web-socket.js"></script>
