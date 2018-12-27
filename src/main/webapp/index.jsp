<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <title>HELLO</title>
    <style>
		body {
			background-image:url('lizixuan.jpg');
			
			background-repeat:repeat;
			background-position:left 780px top 15px;

			
			}
	</style>
</head>
<body style="padding-top: 80px">

	<div class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container-fluid">
                <ul class="nav navbar-nav">
                    <li><a>&nbsp;&nbsp;</a></li>
                    <li class="active"><a>Hello</a></li>
                    <li><a>Yufeng Yang</a></li>
                    <li><a>305268266</a></li>
                    <li><a>CS 144</a></li>
                    <li><a>Web Application</a></li>
                </ul>
            </div>
        </div>

        <br>
        <br>
	
	<div style="width: 60%;margin:auto">
		<h3 face="arial" color="blue" style="text-align: center;">
			Please type your username and enter the List Page!
		</h3>
		<br>
		<br>
		<form action="post" role="form" method="GET">
			<input type="hidden" name="action" value="list">
  			<div class="form-group" style="width: 70%;margin:auto">
    			<label for="username" style="font-size: 17px" >username</label>
    			<input name="username" class="form-control" id="username" placeholder="Type your username">
  			</div>
  			
  			<div class="checkbox" style="width: 70%;margin:auto">
    			<label>
      				<input type="checkbox"> Remember username
    			</label>
  			</div>
  			<br>
  			<div  style="text-align:center">
  				<button type="submit" class="btn btn-success btn-lg">Enter List Page</button>
  			</div>
		</form>	
		
	</div>

</body>
</html>