<!DOCTYPE html>
<html lang="en">

<head>

	<?php 
	function logOut(){
		if(isset($_SESSION)){
			$dbClient = new DatabaseClient();
			$dbClient->openConnection();
			$dbClient->addNewMemberLog($_SESSION["USERID"], "Logged  out!");
			$dbClient->closeConnection();
    		session_destroy();
		}else{
			session_start();
			if(isset($_SESSION["USERID"])){
				include "src/db.php";
				$dbClient = new DatabaseClient();
				$dbClient->openConnection();
				$dbClient->addNewMemberLog($_SESSION["USERID"], "Logged  out!");
				$dbClient->closeConnection();	
			}
	    	session_destroy();
		}
  	}
  	logOut();
	?>
	<title>myticket</title>
		 <!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

	<!-- Latest compiled JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 

	</head>

	<body>


		<!-- NAVBAR  -->
		 <nav class="navbar navbar-inverse">
		 	<div class="container-fluid">
		    	<div class="navbar-header">
		      		<a class="navbar-brand" href="index.php">My Events</a>
		    	</div>
		    	<ul class="nav navbar-nav">
		      		<li class="active"><a href="index.php">Home</a></li>
		      		//<li><a href="search_events.php">Events</a></li>

		    	</ul>
		  	</div>
		</nav> 

		<div class="container">
			<div class="row">
				<h2 class="text-danger" style="text-align: center;">Just Logged Out</h2>
				<h6 style="text-align: center;"><a  href="index.php">Go to index page</a></h6>
			</div>
		</div>
	 	<footer style="margin-top: 20%">
	 	</footer>
	</body>
</html>