<%@ page language="java" pageEncoding="utf-8" %>
<c:if test="${not empty page}">
<div class="mt-20">
	<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
		<div class="dataTables_info" id="DataTables_Table_0_info" role="status" aria-live="polite">共 <font color="red">${page.totalCount}</font> 条记录&nbsp;</div>
		<div class="dataTables_paginate paging_simple_numbers" id="DataTables_Table_0_paginate">
		<c:choose>
			<c:when test="${page.currentPage <= 1}">
				<span class="paginate_button previous disabled" aria-controls="DataTables_Table_0" data-dt-idx="0" tabindex="0" id="DataTables_Table_0_previous">上一页</span>
			</c:when>
			<c:otherwise>
				<a href="javascript:;" class="paginate_button previous disabled" aria-controls="DataTables_Table_0" data-dt-idx="0" tabindex="0" id="DataTables_Table_0_previous" onclick="goPage(${page.currentPage - 1});">上一页</a>
			</c:otherwise>
		</c:choose>
		<c:set var="pre" value="3"/>
		<c:set var="next" value="5"/>
		<c:if test="${(page.pageCount >= pre + next) && (page.currentPage > pre + 1)}">
		  <a href="javascript:;" class="paginate_button current" aria-controls="DataTables_Table_0" data-dt-idx="1" tabindex="0" onclick="goPage(1);">1</a>
		  <c:if test="${(page.currentPage > pre + 2)}">
		  <span>...</span>
		  </c:if>
		</c:if>
		<c:forEach begin="1" end="${page.pageCount}" var="i">
		  <c:if test="${(page.pageCount < pre + next) || ((i >= page.currentPage - pre) && (i <= page.currentPage + next))}">
			<c:choose>
				<c:when test="${(i == page.currentPage)}">
					<span class="paginate_button current" aria-controls="DataTables_Table_0" data-dt-idx="1" tabindex="0">${i}</span>
				</c:when>
				<c:otherwise>
					<a href="javascript:;" onclick="goPage(${i});">${i}</a>
				</c:otherwise>
			</c:choose>
		  </c:if>
		</c:forEach>
		<c:if test="${(page.pageCount >= pre + next) && (page.currentPage < page.pageCount - next)}">
		  <c:if test="${(page.currentPage < page.pageCount - next - 1)}">
			<span>...</span>
		  </c:if>
		  <a href="javascript:;" class="paginate_button current" aria-controls="DataTables_Table_0" data-dt-idx="1" tabindex="0" onclick="goPage(${page.pageCount});">${page.pageCount}</a>
		</c:if>
		<c:choose>
			<c:when test="${(page.currentPage >= page.pageCount)}">
				<span class="paginate_button next disabled" aria-controls="DataTables_Table_0" data-dt-idx="2" tabindex="0" id="DataTables_Table_0_next">下一页</span>
			</c:when>
			<c:otherwise>
				<a href="javascript:;" class="paginate_button next disabled" aria-controls="DataTables_Table_0" data-dt-idx="2" tabindex="0" id="DataTables_Table_0_next" onclick="goPage(${page.currentPage + 1});">下一页</a>
			</c:otherwise>
		</c:choose>
		</div>
	</div>
</div>
<script>
function goPage(pn) {
	$("#pn").val(pn);
	$('form').submit();
}
</script> 
</c:if>  