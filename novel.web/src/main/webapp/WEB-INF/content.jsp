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
    <link rel="icon" href="ico/favicon.ico">

<!-- 章节标题 -->
<title>${content.title}-章节内容-小说搜搜-免费且无广告的小说阅读网</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<style>
body {
	background-color: #E5E4DB;
}

.content {
	font-size: 16px;
	background-color: #F6F4EC;
	color: #333;
	padding: 20px;
	border-radius: 5px;
	-webkit-border-radiu: 5px;
}

.jumbotron {
	padding-top: 10px;
	padding-bottom: 10px;
	background-color: #F5F5F5;
}
</style>
</head>
<body>


	<div class="jumbotron">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<a href="./">回到搜索页</a> <a
						href="./content.do?url=${content.pre}&baseUrl=${baseUrl}&book=${book}"
						id="apre"><span id="pre">上一章</span></a> <a
						href="./chapters.do?url=${baseUrl}&book=${book}">章节列表</a> <a
						href="./content.do?url=${content.next }&baseUrl=${baseUrl}&book=${book}"
						id="anext"><span id="next">下一章</span></a> <a id="go-to-bottom"
						href=""><span></span>去到底部</a>
				</div>
			</div>
		</div>
	</div>

	<div class="container">
		<h3 style="text-align: center;">${content.title }</h3>
		<!-- 章节内容区域 -->
		<div class="content">
			<c:if test="${isSuccess }">
				${content.content }
				<!-- 前一章 章节列表 后一章 链接区域 -->
				<br />
				<br />
				<div style="text-align: center;">
					<a
						href="./content.do?url=${content.pre}&baseUrl=${baseUrl}&book=${book}"
						id="apre"><span id="pre">上一章</span></a> <a
						href="./chapters.do?url=${baseUrl}&book=${book}">章节列表</a> <a
						href="./content.do?url=${content.next }&baseUrl=${baseUrl}&book=${book}"
						id="anext"><span id="next">下一章</span></a> <a id="back-to-top"
						href="#top"><span></span>回到顶部</a>
				</div>
			</c:if>
			<c:if test="${!isSuccess }">
				很抱歉，读取章节内容失败了，请再试一次！
			</c:if>
		</div>
	</div>
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/1.11.3jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/base64.js"></script>
	<script type="text/javascript">
        var doc = document;
        doc.addEventListener("touchstart", startTouchScroll, false);
        doc.addEventListener("touchmove", moveTouchScroll, false);
        doc.addEventListener("touchend", endTouchScroll, false);
        var startY, endY, startX, endX;

        function startTouchScroll(event) {
            var touch = event.touches[0];
            startX = touch.pageX;
            startY = touch.pageY;
        }

        function moveTouchScroll(event) {
            var touch = event.touches[0];
            endX = touch.pageX;
            endY = touch.pageY;
        }

        function endTouchScroll(event) {
            //判断移动的点,1为手指向下滑动,-1为手指向上滑动
            var scrollDirection = (endY - startY) > 0 ? 1 : -1;
            console.log("scrollDirection:" + scrollDirection);
            //判断移动的点,1为手指向右滑动,-1为手指向左滑动
            var scrollTranslation = (endX - startX) > 0 ? 1 : -1;
            console.log("scrollTranslation:" + scrollTranslation);
            //计算滑动距离
            var scrollDistanceY = Math.abs(endY - startY);
            console.log("scrollDistanceY:" + scrollDistanceY);
            var scrollDistanceX = Math.abs(endX - startX);
            console.log("scrollDistanceX:" + scrollDistanceX);
            ////右滑 上一页
            if (scrollDistanceX / scrollDistanceY > 10
                && scrollTranslation === 1) {
                document.getElementById("apre").click();
            } else if (scrollDistanceX / scrollDistanceY > 10
                && scrollTranslation === -1) {
                document.getElementById("anext").click();
            }
        }
		$(function() {
			$(document).keydown(function(e) {
                if (e.keyCode === 37) {
					document.getElementById("apre").click();
                } else if (e.keyCode === 39) {
					document.getElementById("anext").click();
				}
			})
		})

		$(document).ready(function() {
			//首先将#back-to-top隐藏
			//$("#back-to-top").hide();
			//当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失
			$(function() {
				//$(window).scroll(function() {
				//	if ($(window).scrollTop() > 100) {
				//		$("#back-to-top").fadeIn(1500);
				//	} else {
				//		$("#back-to-top").fadeOut(1500);
				//	}
				//});
				//当点击跳转链接后，回到页面顶部位置
				$("#back-to-top").click(function() {
					$('body,html').animate({
						scrollTop : 0
					}, 1000);
					return false;
				});
			});

			$(function() {
				//$(window).scroll(function() {
				//	if ($(window).scrollTop() > 100) {
				//		$("#back-to-top").fadeIn(1500);
				//	} else {
				//		$("#back-to-top").fadeOut(1500);
				//	}
				//});
				//当点击跳转链接后，回到页面顶部位置
				$("#go-to-bottom").click(function() {
					$('body,html').animate({
						scrollTop : $('#back-to-top').offset().top
					}, 1000);
					return false;
				});
			});

		});
	</script>
</body>
</html>