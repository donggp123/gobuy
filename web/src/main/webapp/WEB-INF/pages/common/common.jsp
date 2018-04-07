
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String base = request.getServerName() + ":" + request.getServerPort() + path + "/";
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<c:set var="ctx" value="<%=basePath %>"/>

<script>
    var CTX = "<%=basePath %>";
    var path = "<%=base %>"
</script>

<!--[if lt IE 9]>
<script type="text/javascript" src="${ctx}/lib/html5shiv.js"></script>
<script type="text/javascript" src="${ctx}/lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/lib/webuploader/0.1.5/webuploader.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css"/>
<link rel="stylesheet" href="${ctx}/lib/layer/theme/default/laydate.css">
<link rel="stylesheet" href="${ctx}/lib/lightbox2/lightbox/css/bootstrap-grid.min.css">
<link rel="stylesheet" href="${ctx}/lib/lightbox2/lightbox/dist/zoomify.min.css">
<link rel="stylesheet" href="${ctx}/lib/lightbox2/lightbox/css/style.css">
<!--[if IE 6]>
<script type="text/javascript" src="${ctx}/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${ctx}/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript" src="${ctx}/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="${ctx}/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${ctx}/lib/layer/laydate.js"></script>
<script type="text/javascript" src="${ctx}/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<script type="text/javascript" src="${ctx}/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="${ctx}/lib/webuploader/0.1.5/webuploader.min.js"></script>
<script type="text/javascript" src="${ctx}/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
<script src="${ctx}/lib/lightbox2/lightbox/dist/zoomify.min.js"></script>
<script type="text/javascript" src="${ctx}/lib/common/common.js?v=1.2"></script>