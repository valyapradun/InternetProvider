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
<fmt:setLocale value = "${lang}" />
<fmt:setBundle basename = "com.epam.training.provider.util.data" var = "data"/>
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
				
				<c:if test="${info!=null}"> 
							<div class="alert alert-info fade in">
    							<a href="#" class="close" data-dismiss="alert">&times;</a>
    							<strong>Note! </strong> <c:out value="${info}" />
							</div>
				</c:if>
				
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
													<a href="Controller?action=search_one_tariff&id=${i.getId()}" class="btn btn-info" title="Просмотреть тариф"><i class="fa fa-eye"></i></a> 
													<a href="Controller?action=search_one_tariff&id=${i.getId()}" class="btn btn-success" title="Редактировать"><i class="fa fa-pencil"></i></a> 
													<a href="Controller?action=delete_tariff&id=${i.getId()}" class="btn btn-danger" title="Удалить"><i class="fa fa-trash"></i></a>
												</div>
											</div>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<a href="admin_add_tariff.jsp" class="btn btn-primary" title="Добавить">Добавить тариф</a>
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
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/jasny-bootstrap.min.js"></script>
</body>
</html>