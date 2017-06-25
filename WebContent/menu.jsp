<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div class="navbar navbar-inverse navbar-static-top">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#responsive-menu">
						<span class="sr-only">Открыть навигацию</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="index.jsp" rel="home"> <img
						src="images/logo.png" alt="Домашний интернет">
					</a>
				</div>
				<div class="collapse navbar-collapse" id="responsive-menu">
					<ul class="nav navbar-nav">
						<li><a href="#">НОВОСТИ</a></li>
						<li><a href="#">АКЦИИ</a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">ТАРИФЫ <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="Controller?action=all_tariffs&type=1">Безлимитные</a></li>
								<li><a href="Controller?action=all_tariffs&type=2">По трафику</a></li>
								<li><a href="Controller?action=all_tariffs">Все тарифы</a></li>
							</ul>
						</li>
						<li><a href="#">КОНТАКТЫ</a></li>
					</ul>
					<c:choose>
						<c:when test="${user==null}">
							<form action="" class="navbar-form navbar-right">
								<button data-target="#signin" type="button" class="btn btn-secondary" data-toggle="modal">
									<span class="glyphicon glyphicon-log-in"></span> ВХОД
								</button>
							</form>
						</c:when>
						<c:otherwise>
							<form action="Controller" method="POST" class="navbar-form navbar-right">
							    <h5>Добро пожаловать,
							    	<a href="	
										<c:choose>
											<c:when test="${user.getRole()=='admin'}">admin_index.jsp</c:when>
											<c:otherwise>user_main.jsp</c:otherwise>
										</c:choose>
							    	"> 
							    		<c:out value="${user.getName()}" />
							    	</a>
							    !</h5>
								<input type="hidden" name="action" value="sign_out" />
								<button type="submit" class="btn btn-secondary">
									<span class="glyphicon glyphicon-log-out"></span> ВЫХОД
								</button>
							</form>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>