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
				<form role="form" action="Controller" method="POST" onsubmit="enabledAllField()">
					<input type="hidden" name="action" value="edit_tariff" />
					<h2>Тариф "<c:out value="${tariff.getName()}" />"</h2>
					<table class="table table-tariff">
							<tr> 
								<th scope="row">ID</th>
								<td><input type="text" disabled id="inputId" name="id" size="30" value=<c:out value="${tariff.getId()}" />  /></td>
								<td></td>
								<td rowspan="7"> <img src=<c:out value="${tariff.getPicture()}" /> alt=""></td>
							</tr>
							<tr> 
								<th scope="row">Название</th>
								<td><input type="text" disabled id="inputName" name="name" size="30" value=<c:out value="${tariff.getName()}" /> required /></td>
								<td><a href="#" onclick="enabledField('inputName');" class="btn btn-link" title="Редактировать"><i class="fa fa-pencil"></i></a></td>
							
							</tr>
							<tr>
								<th scope="row">Цена (BYN)</th>
								<td><input type="text" disabled id="inputPrice" name="price" size="30" value=<c:out value="${tariff.getPrice()}" />  required pattern="\d+(\.\d{1})?" /></td>
								<td><a href="#" onclick="enabledField('inputPrice');" class="btn btn-link" title="Редактировать"><i class="fa fa-pencil"></i></a></td>
							</tr>
							<tr>
								<th scope="row">Размер (Гб)</th>
								<td><input type="text" disabled id="inputSize" name="size" size="30" value=<c:out value="${tariff.getSize()}" /> pattern="\d+(\.\d{1})?" /></td>
								<td><a href="#" onclick="enabledField('inputSize');" class="btn btn-link" title="Редактировать"><i class="fa fa-pencil"></i></a></td>
							</tr>
							<tr>
								<th scope="row">Скорость (Мбит/с)</th>
								<td><input type="text" disabled id="inputSpeed" name="speed" size="30" value=<c:out value="${tariff.getSpeed()}" /> pattern="^[ 0-9]+$" /></td>
								<td><a href="#" onclick="enabledField('inputSpeed');" class="btn btn-link" title="Редактировать"><i class="fa fa-pencil"></i></a></td>
							</tr>	
							<tr>
								<th scope="row">Тип</th>
								<td><select disabled name="type" id="inputType">
    								<option <c:if test="${tariff.getType()=='TRAFFIC'}"> selected</c:if> value="TRAFFIC">TRAFFIC</option>
    								<option <c:if test="${tariff.getType()=='UNLIM'}"> selected</c:if> value="UNLIM">UNLIM</option></select>
								</td>
								<td><a href="#" onclick="enabledField('inputType');" class="btn btn-link" title="Редактировать"><i class="fa fa-pencil"></i></a></td>
							</tr>
							<tr>
								<th scope="row">Изображение</th>
								<td><input type="text" disabled id="inputPicture" name="picture" size="30" value=<c:out value="${tariff.getPicture()}" />  /></td>
								<td><a href="#" onclick="enabledField('inputPicture');" class="btn btn-link" title="Редактировать"><i class="fa fa-pencil"></i></a></td>
							</tr>
					</table>
					
					<div class="button-tariff">
						<button type="submit" class="btn btn-labeled btn-success" onclick="enabledAllField()"><span class="btn-label"><i class="glyphicon glyphicon-ok"></i></span>Сохранить изменения</button>
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