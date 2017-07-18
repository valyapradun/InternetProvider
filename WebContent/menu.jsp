<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<fmt:setLocale value = "${lang}" />
<fmt:setBundle basename = "com.epam.training.provider.util.data" var = "data"/>
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
						src=<fmt:message key = "images.logo" bundle = "${data}"/> alt="Домашний интернет">
					</a>
				</div>
				<div class="collapse navbar-collapse" id="responsive-menu">
					<ul class="nav navbar-nav">
						<li><a href="#"><fmt:message key = "menu.news" bundle = "${data}"/></a></li>
						<li><a href="#"><fmt:message key = "menu.actions" bundle = "${data}"/></a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><fmt:message key = "menu.tariffs" bundle = "${data}"/> <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="Controller?action=search_tariffs&type=1"><fmt:message key = "menu.tariffs.unlim" bundle = "${data}"/></a></li>
								<li><a href="Controller?action=search_tariffs&type=2"><fmt:message key = "menu.tariffs.traffic" bundle = "${data}"/></a></li>
								<li><a href="Controller?action=search_tariffs"><fmt:message key = "menu.tariffs.all" bundle = "${data}"/></a></li>
							</ul>
						</li>
						<li><a href="#"><fmt:message key = "menu.contacts" bundle = "${data}"/></a></li>
					</ul>
				  	<span class="language" >
    					<a href="Controller?action=choose_language&lang=en">en</a> 
   						 | 
   						<a href="Controller?action=choose_language&lang=ru">ru</a>
					</span>  
					<c:choose>
						<c:when test="${user==null}">
							<form action="" class="navbar-form navbar-right">
								<button data-target="#signin" type="button" class="btn btn-secondary" data-toggle="modal">
									<span class="glyphicon glyphicon-log-in"></span> <fmt:message key = "button.signin" bundle = "${data}"/>
								</button>
							</form>
						</c:when>
						<c:otherwise>
							<form action="Controller" method="POST" class="navbar-form navbar-right">
							    <h5><fmt:message key = "data.welcome" bundle = "${data}"/>
							    	<a href="	
										<c:choose>
											<c:when test="${user.getRole()=='admin'}">Controller?action=show_admin_page</c:when>
											<c:otherwise>Controller?action=show_user_page</c:otherwise>
										</c:choose>
							    	" class="a-user"> 
							    		<c:out value="${user.getName()}" />
							    	</a>
							    !</h5>
								<input type="hidden" name="action" value="sign_out" />
								<button type="submit" class="btn btn-secondary">
									<span class="glyphicon glyphicon-log-out"></span> <fmt:message key = "button.signout" bundle = "${data}"/>
								</button>
							</form>
						</c:otherwise>
					</c:choose>
					
				</div>
			</div>
		</div>