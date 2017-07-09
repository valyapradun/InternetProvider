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
		<main>
		<div id="carousel" class="carousel slide" data-ride="carousel">
			<!-- Индикаторы слайдов-->
			<ol class="carousel-indicators">
				<li class="active" data-target="#carousel" data-slide-to="0"></li>
				<li data-target="#carousel" data-slide-to="1"></li>
				<li data-target="#carousel" data-slide-to="2"></li>
				<li data-target="#carousel" data-slide-to="3"></li>
			</ol>
			<!-- Слайды-->
			<div class="carousel-inner">
				<div class="item active">
					<a href="admin_tariff.jsp"><img src="images/cover1.png" alt="Тариф 'Ночной'"></a>
				</div>
				<div class="item">
					<a href="admin_tariff.jsp"><img src="images/cover2.png" alt="Тариф 'Подарок'"></a>
				</div>
				<div class="item">
					<a href="tariff.jsp"><img src="images/cover3.png" alt="Тариф 'Для тебя'"></a>
				</div>
				<div class="item">
					<a href="tariff.jsp"><img src="images/cover4.png" alt="Тариф 'Семейный'"></a>
				</div>
			</div>
			<!-- Стрелки переключения слайдов-->
			<a href="#carousel" class="left carousel-control" data-slide="prev">
				<span class="glyphicon glyphicon-chevron-left"></span>
			</a> <a href="#carousel" class="right carousel-control" data-slide="next">
				<span class="glyphicon glyphicon-chevron-right"></span>
			</a>
		</div>

		<div class="row">
			<div class="col-md-6">
				<div class="news">
					<div class="title-section">
						<a href="#">Новости &#9658;</a>
					</div>
					<div class="news-block">
						<div class="date">
							<h4>26.05.2017</h4>
						</div>
						<a href="#" class="news-title">Оплата услуг через ЕРИП</a>
						<div class="news-text">Уважаемые клиенты! Спешим обрадовать
							Вас приятной новостью - вскоре оплатить наши услуги Вы сможете
							через ЕРИП!</div>
						<a class="more" href="#">Подробнее...</a>
					</div>
					<div class="news-block">
						<div class="date">
							<h4>25.05.2017</h4>
						</div>
						<a href="#" class="news-title">Профилактические работы 27 мая</a>
						<div class="news-text">В субботу 27 мая 2017 года с 09:00 до
							13:00 на оборудовании оператора будут проводиться
							профилактические работы. Во время работ возможны перебои с
							доступом в интернет.</div>
						<a class="more" href="#">Подробнее...</a>
					</div>
				</div>

			</div>
			<div class="col-md-6">
				<div class="action">
					<div class="title-section">
						<a href="#">Акции &#9658;</a>
					</div>
					<div class="action-block">
						<div class="date">
							<h4>25.05.2017</h4>
						</div>
						<a href="#" class="news-title">Удивительно низкие цены!</a>
						<div class="news-text">Подключись до конца июля — и получи
							скидку 50% на 3 месяца на безлимитный Интернет! Только до конца
							июля! Таких цен в Минске еще не было!</div>
						<a class="more" href="#">Подробнее...</a>
					</div>
					<div class="action-block">
						<div class="date">
							<h4>25.04.2017</h4>
						</div>
						<a href="#" class="news-title">Сделай подарок себе и близким!</a>
						<div class="news-text">Акция для новых абонентов.</div>
						<a class="more" href="#">Подробнее...</a>
					</div>
				</div>


			</div>
		</div>
		</main>
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
									Нет аккаунта? <a href="Controller?action=registration">Регистрация</a>
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
	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.js"></script>
</body>
</html>