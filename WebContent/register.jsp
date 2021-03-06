﻿<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<html lang="ru">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<fmt:setLocale value = "${lang}" />
<fmt:setBundle basename = "com.epam.training.provider.util.data" var = "data"/>
<title>Домашний Интернет - Регистрация</title>
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/font-awesome.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<!--  <link rel="stylesheet"	href="css/bootstrap.min.css" /> -->

</head>
<body>
	<div class="wrapper">
		<%@ include file="menu.jsp"%>

		<div class="main">
			<div class="container">
				<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<form data-toggle="validator" role="form" action="Controller?action=registration" method="POST">
							<h3>Регистрация</h3>
							<hr>
							<div class="form-group">
								<label for="inputLogin" class="control-label">Введите логин</label>
								<div class="input-group">
									<span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span> 
									<input type="text" class="form-control" id="inputLogin" name="login" placeholder="Ваш логин" required title="Латинские символы, цифры, дефисы и подчеркивания" pattern="^[A-Za-z0-9_-]{3,15}"
									value="<c:if test="${user!=null}"><c:out value="${user.getLogin()}" /></c:if>"/>          
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword" class="control-label">Введите пароль <br>(минимум 6 символов)</label>
								<div class="form-inline row">
									<div class="form-group col-sm-6">
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span> 
											<input type="password" data-toggle="validator" data-minlength="6" class="form-control" id="inputPassword" name="password" placeholder="Ваш пароль" required>
										</div>
									</div>
									<div class="form-group col-sm-6">
										<div class="input-group">
											<span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span> 
											<input type="password" class="form-control" id="inputPasswordConfirm" data-match="#inputPassword" data-match-error="Пароли не совпадают!" placeholder="Повторите пароль" required>
										</div>
										<div class="help-block with-errors"></div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="inputName" class="control-label">Введите имя</label>
								<div class="input-group">
									<span class="input-group-addon"><span class="glyphicon glyphicon-sunglasses"></span></span> 
									<input type="text" class="form-control" id="inputName" name="name" placeholder="Ваше имя" required
									value="<c:if test="${user!=null}"><c:out value="${user.getName()}" /></c:if>"/>
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail" class="control-label">Введите e-mail</label>
								<div class="input-group">
									<span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span> 
									<input type="email" class="form-control" id="inputEmail" name="email" placeholder="Email" data-error="Некорректный e-mail!" required
									value="<c:if test="${user!=null}"><c:out value="${user.getEmail()}" /></c:if>"/>
								</div>
								<div class="help-block with-errors"></div>
							</div>
							<hr>
							
							<h6 class="reg-error">
								<c:forEach items="${error}" var="i">
									<c:out value="${i}" />
									<br>
								</c:forEach>
							</h6>
							
							<div class="form-group">
								<button type="submit" class="btn btn-primary new-user">Зарегистрировать</button>
							</div>
						</form>
					</div>
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

		<div class="modal fade" id="signin" data-backdrop="static">
			<div class="modal-dialog ">
				<div class="modal-content">
					<div class="modal-header">
						<button class="close" type="button" data-dismiss="modal">&times;</button>
						<h4 class="modal-title"><fmt:message key = "title.signin.page" bundle = "${data}"/></h4>
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
											name="login" class="form-control" placeholder=<fmt:message key = "placeholder.login" bundle = "${data}"/>
											required autofocus />
									</div>
									<div class="input-group">
										<span class="input-group-addon"><span
											class="glyphicon glyphicon-lock"></span></span> <input
											type="password" name="password" class="form-control"
											placeholder=<fmt:message key = "placeholder.password" bundle = "${data}"/> required />
									</div>
									<fmt:message key = "placeholder.question" bundle = "${data}"/> <a href="Controller?action=registration"><fmt:message key = "title.registration.page" bundle = "${data}"/></a>
								</div>
								<div class="col-xs-2 col-sm-2 col-md-2 col-lg-3"></div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-labeled btn-success">
								<span class="btn-label"><i class="glyphicon glyphicon-ok"></i></span><fmt:message key = "button.signin.small" bundle = "${data}"/>
							</button>
							<button type="button" class="btn btn-labeled btn-danger"
								data-dismiss="modal">
								<span class="btn-label"><i
									class="glyphicon glyphicon-remove"></i></span><fmt:message key = "button.cancel" bundle = "${data}"/>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/jquery-1.11.3.min.js"></script>
<!-- 	<script src="js/bootstrap.min.js"></script>   -->
	<script src="js/validator.min.js"></script>
</body>
</html>