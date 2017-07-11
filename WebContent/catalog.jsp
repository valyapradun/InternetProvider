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
<title>Домашний Интернет - Тарифы</title>

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
         <li <c:if test="${type==null}">class="active"</c:if>>
           <a href="Controller?action=search_tariffs">Все тарифы 
          <c:if test="${type==null}">
           <span class="badge">
           
           <c:out value="${tariffs.size()}" />
    
           </span></c:if></a>
         </li>
         <li <c:if test="${type=='1'}">class="active"</c:if>>
           <a href="Controller?action=search_tariffs&type=1">Безлимитные 
           <c:if test="${type=='1'}">
           <span class="badge">
           
           <c:out value="${tariffs.size()}" />
           
           </span>
           </c:if></a>
         </li>
         <li <c:if test="${type=='2'}">class="active"</c:if>>
           <a href="Controller?action=search_tariffs&type=2">По трафику 
            <c:if test="${type=='2'}">
           <span class="badge">
          
           <c:out value="${tariffs.size()}" />
           </span></c:if></a>
         </li>
       </ul>
       </div>
     </div>
     <div class="row">
       <c:forEach items="${tariffs}" var="i">
         <div class="col-xs-6 col-sm-4">
           <div class="thumbnail">
             <img src=<c:out value="${i.getPicture()}" /> alt="">
             <div class="caption">
                <h3><c:out value="${i.getName()}" /></h3>
                <p>Тип тарифа: <c:out value="${i.getType()}" /><br>
                   Цена: <c:out value="${i.getPrice()}" /> BYN<br>
                   <c:if test="${i.getSize() != 0}">
                     Размер: <c:out value="${i.getSize()}" /> Гб<br>
                   </c:if>
                   <c:if test="${i.getSpeed() != 0}">
                     Скорость: <c:out value="${i.getSpeed()}" /> Мбит/с<br>
                   </c:if>
                </p>
                <a href="Controller?action=current_tariff" class="btn btn-success">Подробнее <i class="fa fa-arrow-right"></i></a>
              </div>
           </div>
         </div>
       </c:forEach>
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
										name="login" class="form-control"
										placeholder="Ваш логин" required autofocus />
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