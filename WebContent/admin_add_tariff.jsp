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
				<form role="form" action="Controller" method="POST">
					<input type="hidden" name="action" value="add_tariff" />
					<h2>Заполните поля</h2>
					<table class="table table-tariff">
							<tr> 
								<th scope="row">Название тарифа</th>
								<td><input type="text" id="inputName" name="name" size="30" value="" required /> </td>
							</tr>
							<tr>
								<th scope="row">Цена (BYN)</th>
								<td><input type="text" id="inputPrice" name="price" size="30" value="" required pattern="\d+(\.\d{1})?" /></td>
							</tr>
							<tr>
								<th scope="row">Размер (Гб)</th>
								<td><input type="text" id="inputSize" name="size" size="30" value="" pattern="\d+(\.\d{1})?" /></td>
							</tr>
							<tr>
								<th scope="row">Скорость (Мбит/с)</th>
								<td><input type="text" id="inputSpeed" name="speed" size="30" value="" pattern="^[ 0-9]+$" /></td>
							</tr>	
							<tr>
								<th scope="row">Тип</th>
								<td><select name="type" id="inputType">
    								<option selected value="TRAFFIC">TRAFFIC</option>
    								<option value="UNLIM">UNLIM</option></select>
								</td>
							</tr>
							<tr>
								<th scope="row">Изображение</th>
								<td><input type="text" id="inputPicture" name="picture" size="30" value="" /></td>
							</tr>
					</table>
					
					<div class="button-tariff">
						<button type="submit" class="btn btn-labeled btn-success"><span class="btn-label"><i class="glyphicon glyphicon-ok"></i></span>Добавить тариф</button>
						<a href="Controller?action=display_tariffs" class="btn btn-labeled btn-danger" title="Вернуться обратно"><span class="btn-label"><i class="glyphicon glyphicon-remove"></i></span>Вернуться обратно</a>					
				 
				    </div>
					</form>
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