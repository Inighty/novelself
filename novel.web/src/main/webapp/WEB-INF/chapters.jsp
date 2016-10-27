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

<title>章节列表-小说搜搜-免费且无广告的小说阅读网</title>

<!-- Bootstrap core CSS -->
<link
	href="css/bootstrap.min.css"
	rel="stylesheet">
<style>
.jumbotron {
	padding-top: 10px;
	padding-bottom: 10px;
}
</style>
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<a href="./">回到搜索页</a>
				</div>
			</div>
		</div>
	</div>
	<div class="container no-table-responsive">
		<table
			class="table table-striped table-bordered table-condensed table-hover">
			<thead>
				<tr>
					<th colspan="4" style="text-align: center;">章节列表</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<c:forEach items="${chapters}" var="chapter" varStatus="status">
						<td><a
							href="./content.do?url=${chapter.url}&baseUrl=${baseUrl}" target="_blank">${chapter.title}</a>
						</td>
						<c:if test="${status.count % 4 == 0}">
						</tr>
						<tr>
						</c:if>
					</c:forEach>
				</tr>
			</tbody>
		</table>
	</div>
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="js/1.11.3jquery.min.js"></script>
	<script
		src="js/bootstrap.min.js"></script>
	<script src="js/base64.js"></script>
	<script>
		
	</script>
</body>
</html>