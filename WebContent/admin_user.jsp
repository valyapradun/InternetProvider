<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="ru">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Домашний Интернет - Администрирование</title>
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/font-awesome.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link href="css/jasny-bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
	<div class="wrapper">
		<%@ include file="menu.jsp"%>
		<div class="main">

			<div class="container">
				<div class="row">
					<div class="container">
						<ul class="nav nav-tabs">
							<li <c:if test="${action=='display_tariffs'}"> class="active"</c:if>><a href="Controller?action=display_tariffs">Тарифы</a></li>
							<li <c:if test="${action=='display_users'}"> class="active"</c:if>><a href="Controller?action=display_users">Пользователи</a></li>
						</ul>
					</div>
				</div>
			</div>
            <div class="container">
				<div class="row">
					<h2>Администрирование пользователей</h2>
					<hr>
					<a href="Controller?action=prolong_unlim_tariffs" class="btn btn-primary" title="Продлить тарифы UNLIM">Продлить тарифы UNLIM</a>
					<a href="Controller?action=put_ban" class="btn btn-warning" title="Поставить бан неплательщикам">Поставить бан неплательщикам</a>
					<hr>
					<table class="table table-hover">
						<thead class="thead-inverse">
							<tr>
								<th>#</th>
								<th>Login</th>
								<th>Имя</th>
								<th>Email</th>
								<th>Баланс</th>
								<th>Использованный трафик</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${users}" var="i">
								<tr>
									<th scope="row"><c:out value="${i.getId()}" /></th>
									<td><c:out value="${i.getLogin()}" /></td>
									<td><c:out value="${i.getName()}" /></td>
									<td><c:out value="${i.getEmail()}" /></td>
									<td><c:out value="${i.getBalance()}" /></td>
									<td><c:out value="${i.getTraffic()}" /></td>
									<td>
										<div class="row">
											<div class="btn-toolbar">
												<div class="btn-group">
													<a href="#" class="btn btn-info" title="Просмотреть"><i class="fa fa-eye"></i></a> 
													<a href="Controller?action=remove_ban&id=${i.getId()}" class="btn btn-warning" title="Убрать бан"><i class="fa fa-ban"></i></a> 
													<a href="#" class="btn btn-success" title="Окончить тариф"><i class="fa fa-calendar-check-o"></i></a> 
													<a href="Controller?action=delete_user&id=${i.getId()}" class="btn btn-danger" title="Удалить"><i class="fa fa-trash"></i></a>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
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
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/jasny-bootstrap.min.js"></script>
</body>
</html>