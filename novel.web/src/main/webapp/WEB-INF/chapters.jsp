<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="#">

<title>${book}-章节列表-小说搜搜-免费且无广告的小说阅读网</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<style>
.jumbotron {
	padding-top: 10px;
	padding-bottom: 10px;
}

#back-to-top {
	float: right;
	margin-right: 10px;
}

.container {
	padding-bottom: 10px;
}
</style>

<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?13ab0b1b60fab3033c9eb9cb7ddbf67e";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>


</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<a href="./">回到搜索页</a> <a id="go-to-bottom" href=""><span></span>去到底部</a>
					<a id="download-txt" href="javascript:;" qhref="${bookUrl}"><span></span>下载txt</a>
				</div>
			</div>
		</div>
	</div>
	<div class="container no-table-responsive">
	<c:if test="${isSuccess}">
		<table
			class="table table-striped table-bordered table-condensed table-hover">
			<thead>
				<tr>
					<th colspan="4" style="text-align: center;">${book}-章节列表</th>
				</tr>
			</thead>
			<tbody>
					<tr>
						<c:forEach items="${chapters}" var="chapter" varStatus="status">
							<td><a
								href="./content.do?url=${chapter.url}&baseUrl=${baseUrl}&book=${book}"
								target="_blank">${chapter.title}</a></td>
							<c:set var="flag" scope="session" value="${isMobile}" />
							<c:if test="${!flag}">
								<c:if test="${status.count % 4 == 0}">
									</tr>
									<tr>
								</c:if>
							</c:if>

							<c:if test="${flag}">
								</tr>
								<tr>
							</c:if>
						</c:forEach>
					</tr>
			</tbody>
		</table>
		<a id="back-to-top" href="#top"><span></span>回到顶部</a>
	</c:if>
	<c:if test="${!isSuccess}">
		很抱歉，读取章节列表失败了，请再试一次！
	</c:if>
	</div>
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/1.11.3jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/base64.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {

			$(function() {
				$("#download-txt").click(function() {
					var bookUrl = $("#download-txt").attr("qhref");
					$.ajax({
						url : "./download.do",
						async : false,
						type : "post",
						dataType : "json",
						data : {
							"bookUrl" : bookUrl,
						},
						error : function(data) {
							//alert(data.status);
							console.log("error" + data.status);
						},
						success : function(result) {
							//alert("url = " + result.data);
							console.log(result.data);
							location.href = result.data;

						}
					});
				});

				$(function() {

					$("#back-to-top").click(function() {
						$('body,html').animate({
							scrollTop : 0
						}, 1000);
						return false;
					});
				});

				$(function() {

					$("#go-to-bottom").click(function() {
						$('body,html').animate({
							scrollTop : $('#back-to-top').offset().top
						}, 1000);
						return false;
					});
				});
			})
		})
	</script>
</body>
</html>