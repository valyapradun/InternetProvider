
<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<title><fmt:message key = "title.error.page" bundle = "${data}"/></title>

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
				<h1><fmt:message key = "data.error" bundle = "${data}"/></h1>

				<h3><c:out value="${error.toString()}" /></h3>


				Request from ${pageContext.errorData.requestURI} is failed <br />
				Servlet name or type: ${pageContext.errorData.servletName} <br />
				Status code: ${pageContext.errorData.statusCode} <br /> Exception:
				${pageContext.errorData.throwable}

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
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.js"></script>
</body>
</html>
