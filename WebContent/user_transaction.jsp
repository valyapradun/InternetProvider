<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<html lang="ru">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<fmt:setLocale value = "${lang}" />
<fmt:setBundle basename = "com.epam.training.provider.util.data" var = "data"/>
<title>Домашний Интернет - Платежи</title>

<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/font-awesome.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="wrapper">
		<%@ include file="menu.jsp"%>

		<div class="main">
			<div class="container">
				<div class="row">
					<div class="container">
						<ul class="nav nav-tabs">
							<li <c:if test="${action=='show_user_page'}"> class="active"</c:if>><a href="Controller?action=show_user_page">Состояние счета</a></li>
							<li <c:if test="${action=='show_user_tariffs'}"> class="active"</c:if>><a href="Controller?action=show_user_tariffs">Тарифы и услуги</a></li>
							<li <c:if test="${action=='search_user_payments'}"> class="active"</c:if>><a href="Controller?action=search_user_payments">Платежи</a></li>
					<!-- 		<li <c:if test="${action=='users'}"> class="active"</c:if>><a href="Controller?action=users">Настройки профиля</a></li>  -->
						</ul>
					</div>
				</div>
			</div>
		
		
		
		
			<div class="container">
				<div class="row">
					
					<div class="col-md-2 col-lg-2 col-sm-1"></div>
					<div class="col-md-8 col-lg-8 col-sm-10">
					<h2>Платежи </h2>
					<table class="table table-hover">
						<thead class="thead-inverse">
							<tr>
								<th>#</th>
								<th>Тип</th>
								<th>Сумма (BYN)</th>
								<th>Дата</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${transactions}" var="i">
							<tr>
							<th scope="row"><c:out value="${i.getId()}" /></th>
							<td><c:out value="${i.getType()}" /></td>
							<td><c:out value="${i.getAmount()}" /></td>
							<td><c:out value="${i.getDate()}" /></td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
					<br>
					<hr>
					<form role="form" action="Controller" method="POST">
						<h4>Пополнение счета </h4>
						<input type="hidden" name="action" value="add_payment" />
						<input type="text" class="user-pay" id="inputAmount" name="amount" placeholder="Введите сумму" required pattern="^[ 0-9]+$" title="Ввести можно только целое положительное число"/>						
						<button type="submit" class="btn btn-labeled btn-primary"><span class="btn-label"><i class="glyphicon glyphicon-thumbs-up"></i></span>Зачислить</button>
						<div class = "validate-info">
						<c:if test="${info!=null}"> 
							<c:out value="${info}" />
						</c:if>
						</div>
					</form>
					</div>
					<div class="col-md-2 col-lg-2 col-sm-1"></div>
				</div>
			</div>
		</div>
		
		<footer>
			<div class="container footer">
				<div class="row">
					<div class="col-md-6 col-lg-6 col-sm-6">
						<ul class="list-unstyled social-link">
							<li><a href="http://vk.com/" target="_blank"><img
									src="images/vk.png" title="ВКонтакте" alt="ВКонтакте"></a></li>
							<li><a href="http://twitter.com/" target="_blank"><img
									src="images/twitter.png" title="Twitter" alt="Twitter"></a></li>
							<li><a href="http://www.facebook.com/" target="_blank"><img
									src="images/facebook.png" title="Facebook" alt="Facebook"></a></li>
							<li><a href="http://odnoklassniki.ru/" target="_blank"><img
									src="images/odnokl.png" title="Одноклассники"
									alt="Одноклассники"></a></li>
							<li><a href="http://instagram.com/" target="_blank"><img
									src="images/instagram.png" title="Instagram" alt="Instagram"></a></li>
						</ul>
					</div>
					<div class="col-md-6 col-lg-6 col-sm-6">
						<ul class="list-unstyled info">
							<li><a href="#"><fmt:message key = "footer.contacts" bundle = "${data}"/></a></li>
							<li><a href="#"><fmt:message key = "footer.map" bundle = "${data}"/></a></li>
							<li><a href="#"><fmt:message key = "footer.info" bundle = "${data}"/></a></li>
						</ul>
						<br>
						<p class="footer-p">&copy; <fmt:message key = "footer.title" bundle = "${data}"/></p>
					</div>
				</div>
			</div>
		</footer>



	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.js"></script>
</body>
</html>