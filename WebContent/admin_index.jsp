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
         <li <c:if test="${action=='tariffs'}"> class="active"</c:if>>
           <a href="Controller?action=tariffs">Тарифы  <c:out value="${action}" /> 
           <c:if test="${action==''}"> empty</c:if>
           </a>
         </li>
         <li>
           <a href="#">Пользователи</a>
         </li>
       </ul>
       </div>
				
				
					
					
					
					
					
					
					
					
					
					
					</div>
					
					
					
					
					
					
					<div class="row">
				
						<h2>Администрирование тарифов</h2>
						
						<table class="table table-hover">
							<thead class="thead-inverse">
								<tr>
									<th>#</th>
									<th>Название</th>
									<th>Цена (BYN)</th>
									<th>Размер (Гб)</th>
									<th>Скорость (Мбит/с)</th>
									<th>Тип</th>
									<th>Изображение</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${tariffs}" var="i">
								<tr>
									<th scope="row"><c:out value="${i.getId()}" /></th>
									<td><c:out value="${i.getName()}" /></td>
									<td><c:out value="${i.getPrice()}" /></td>
									<td><c:out value="${i.getSize()}" /></td>
									<td><c:out value="${i.getSpeed()}" /></td>
									<td><c:out value="${i.getType()}" /></td>
									<td><c:out value="${i.getPicture()}" /></td>
									<td>
										<div class="row">
											<div class="btn-toolbar">
												<div class="btn-group">
													<a href="#" class="btn btn-info" title="Просмотреть тариф"><i class="fa fa-eye"></i></a> 
													<a href="#" class="btn btn-success" title="Редактировать"><i class="fa fa-pencil"></i></a> 
													<a href="#"	class="btn btn-danger" title="Удалить"><i class="fa fa-trash"></i></a>
												</div>
											</div>
										</div>
									</td>
								</tr>
									 </c:forEach>
							</tbody>
						</table>
					
						<a href="#" class="btn btn-primary" title="Добавить">Добавить тариф</a></div>
					
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

	<div class="modal fade" id="signin" data-backdrop="static">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Авторизация</h4>
				</div>
				<form role="form" action="Controller" method="POST">
					<div class="modal-body">
						<div class="row">
							<div class="col-xs-2 col-sm-2 col-md-2 col-lg-3"></div>
							<div class="col-xs-8 col-sm-8 col-md-8 col-lg-6 login-box">
								<input type="hidden" name="action" value="sign_in" />
								<div class="input-group">
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-user"></span></span> <input type="text"
										name="login" class="form-control" placeholder="Ваш логин"
										required autofocus />
								</div>
								<div class="input-group">
									<span class="input-group-addon"><span
										class="glyphicon glyphicon-lock"></span></span> <input
										type="password" name="password" class="form-control"
										placeholder="Ваш пароль" required />
								</div>
								Нет аккаунта? <a href="register.jsp">Регистрация</a>
							</div>
							<div class="col-xs-2 col-sm-2 col-md-2 col-lg-3"></div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-labeled btn-success">
							<span class="btn-label"><i class="glyphicon glyphicon-ok"></i></span>Вход
						</button>
						<button type="button" class="btn btn-labeled btn-danger"
							data-dismiss="modal">
							<span class="btn-label"><i
								class="glyphicon glyphicon-remove"></i></span>Отмена
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.js"></script>
	<!-- Подключаем JavaScript Jasny Bootstrap -->
	<script src="js/jasny-bootstrap.min.js"></script>
</body>
</html>