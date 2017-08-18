<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="ru">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Домашний Интернет</title>

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
						<!--  <li <c:if test="${action=='users'}"> class="active"</c:if>><a href="Controller?action=users">Настройки профиля</a></li> -->
						</ul>
					</div>
				</div>
			</div>
		
		
		
		
			<div class="container">
				<div class="row">
					
					<div class="col-md-2 col-lg-2 col-sm-1"></div>
					<div class="col-md-8 col-lg-8 col-sm-10">
					<h2>Состояние счета </h2>
					<table class="table table-hover">
						<tbody>
							<tr>
								<th>Абонент</th>
								<td><c:out value="${user.getName()}" /></td>
								<td></td>
							</tr>
							<tr>
								<th>Логин</th>
								<td><c:out value="${user.getLogin()}" /></td>
								<td></td>
							<tr>
								<th>Email</th>
								<td><c:out value="${user.getEmail()}" /></td>
								<td></td>
							</tr>
							<tr>
								<th>Статус блокировки</th>
								<td><c:out value="${user.isActiveBan()}" /></td>
								<td>
									<c:if test="${user.isActiveBan() == true}">	
										<a href="mailto:admin@homeinternet.by?subject=Удалите бан, мой id = <c:out value="${user.getId()}" />"  title="Удалить бан">Хочу удалить бан <i class="fa fa-envelope-o" aria-hidden="true"></i></a>
									</c:if>
								</td>
							</tr>
							<tr>
								<th>Актуальный баланс</th>
								<td><c:out value="${user.getBalance()}" /></td>
								<td></td>
							</tr>
							<tr>
								<th>Тарифный план</th>
								<td><c:out value="${user.getTariff().getName()}" /></td>
								<td>
									<c:if test="${user.getTariff().getName() != ''}"> 
										<a href="mailto:admin@homeinternet.by?subject=Измените тариф, мой id = <c:out value="${user.getId()}" />"  title="Изменить тариф">Хочу изменить тариф <i class="fa fa-envelope-o" aria-hidden="true"></i></a>
									</c:if>
								</td>
							</tr>
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