<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="ru">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Домашний Интернет - Транзакции</title>

<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/font-awesome.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

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
					<!--  		<li <c:if test="${action=='users'}"> class="active"</c:if>><a href="Controller?action=users">Настройки профиля</a></li> -->
						</ul>
					</div>
				</div>
			</div>
		
		
		
		
			<div class="container">
				<div class="row">
					
					<div class="col-md-2 col-lg-2 col-sm-1"></div>
					<div class="col-md-8 col-lg-8 col-sm-10">
					<h2>Тарифы и услуги </h2>
					<br>
					<table class="table table-hover">
						<thead class="thead-inverse">
							<tr>
								<th></th>
								<th>Название</th>
								<th>Цена (BYN)</th>
								<th>Размер (Гб)</th>
								<th>Скорость (Мбит/с)</th>
								<th>Тип</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${tariffs}" var="i">
								<tr>
									<td><img class="picture-user-tariff" src=<c:out value="${i.getPicture()}" /> alt=""></td>
									<td><c:out value="${i.getName()}" /></td>
									<td><c:out value="${i.getPrice()}" /></td>
									<td><c:out value="${i.getSize()}" /></td>
									<td><c:out value="${i.getSpeed()}" /></td>
									<td><c:out value="${i.getType()}" /></td>
									
									<td>
										<div class="row">
												<a href="Controller?action=buy_tariff&id=${i.getId()}" class="btn btn-primary btn-labeled buy-btn" title="Купить тариф"><span class="btn-label"><i class="fa fa-shopping-cart"></i></span>Купить</a> 
												 
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

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
						<li><a href="http://vk.com/" target="_blank"><img src="images/vk.png" title="ВКонтакте" alt="ВКонтакте"></a></li>
						<li><a href="http://twitter.com/" target="_blank"><img src="images/twitter.png" title="Twitter" alt="Twitter"></a></li>
						<li><a href="http://www.facebook.com/" target="_blank"><img src="images/facebook.png" title="Facebook" alt="Facebook"></a></li>
						<li><a href="http://odnoklassniki.ru/" target="_blank"><img src="images/odnokl.png" title="Одноклассники" alt="Одноклассники"></a></li>
						<li><a href="http://instagram.com/" target="_blank"><img src="images/instagram.png" title="Instagram" alt="Instagram"></a></li>
					</ul>
				</div>
				<div class="col-md-6 col-lg-6 col-sm-6">
					<ul class="list-unstyled info">
						<li><a href="#">Контакты</a></li>
						<li><a href="#">Карта сайта</a></li>
						<li><a href="#">Полезная информация</a></li>
					</ul>
					<br>
					<p class="footer-p">&copy; 2017 «Домашний Интернет»</p>
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