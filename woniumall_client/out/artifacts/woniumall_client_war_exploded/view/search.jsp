<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" src="${base}/js/jquery-3.5.1.js"></script>
<script>
	$(function() {
		$('.allsort').hover(function() {
			$('#div_allsort').show();
		}, function() {
			$('#div_allsort').hide();
		});
	});
</script>
<div class="searchbar">
	<div class="allsort">
		<a href="javascript:void(0);">全部商品分类</a>

		<!--总的商品分类-开始-->
		<ul class="sortlist" id='div_allsort' style='display:none'>
			<c:forEach items="${listCategory}" var="Category">
			<li>
				<h2>
					<a href="good?opr=categoryList&categoryid=${Category.id }">${Category.name}</a>
				</h2>
			</li>
			</c:forEach>
		</ul>
	</div>

	<div class="searchbox">
		<form method='post' action='good?opr=query'>
			<input type='hidden' name='controller' value='site' /> <input
				type='hidden' name='action' value='search_list' /> <input
				class="text" type="text" name='word' value="${word}" autocomplete="off"
				placeholder="请输入关键字..."/> <input class="btn" type="submit" value="商品搜索" />
		</form>
	</div>
	<div class="hotwords">热门搜索： IT</div>
</div>