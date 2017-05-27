<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
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
   <div class="navbar navbar-inverse navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#responsive-menu">
            <span class="sr-only">Открыть навигацию</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#" rel="home">
            <img src="images/logo.png" alt="Домашний интернет">
          </a> 
        </div>
        <div class="collapse navbar-collapse" id="responsive-menu">
          <ul class="nav navbar-nav">
            <li><a href="#">НОВОСТИ</a></li>
            <li><a href="#">АКЦИИ</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">ТАРИФЫ <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">Безлимитные</a></li>
                <li><a href="#">По трафику</a></li>
                <li><a href="#">Все тарифы</a></li>
                <li class="divider"></li>
                <li><a href="#">Пункт 4</a></li>
              </ul>
            </li>
            
            <li><a href="#">КОНТАКТЫ</a></li>
          </ul>

          <form action="" class="navbar-form navbar-right">

            <button data-target="#signin" type="button" class="btn btn-secondary"  data-toggle="modal" >
            <span class="glyphicon glyphicon-log-in"></span>
              ВЫХОД
            </button>
          </form>

        </div>
      </div>
    </div>
    
    <div class="container">
      <div class="row">
        <div class="col-md-6">
     
        </div>
        <div class="col-md-6">
         
        </div>
      </div>
    </div>
    <br>
    <br>
    <br>
    <br>
<h1>Welcome to admin page!</h1>

  
  
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.js"></script>
  </body>
</html>