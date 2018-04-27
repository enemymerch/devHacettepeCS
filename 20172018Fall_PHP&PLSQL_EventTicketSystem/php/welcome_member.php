<!DOCTYPE html>
<html lang="en">

<head>


	<?php 
		// Loggin Control
		include 'C:/xampp/htdocs/src/myEvents.php';
		include 'C:/xampp/htdocs/src/memberLoginAuthentication.php';


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
		      		<a class="navbar-brand" href="welcome_member.php">My Events</a>
		    	</div>
		    	<ul class="nav navbar-nav">
		      		<li class="active"><a href="welcome_member.php">Home</a></li>
		      		<li><a href="search_events.php">Search Events</a></li>
		      		<li><a href="myTickets.php">My Tickets</a></li>
		    	</ul>

		    	<ul class="nav navbar-nav navbar-right">
		    		<li class="dropdown">
        				<a class="dropdown-toggle" data-toggle="dropdown" href="#">
        					<?php       		    
        						if(!isset($_SESSION)){
        							session_start();
        						}
        						echo $_SESSION["USERNAME"];
        					?>
        					<span class="glyphicon glyphicon-user"></a>
        				<ul class="dropdown-menu">
        					<li><a href="myTickets.php">My Tickets</a></li>
          					<li><a href="edit_profile.php">Edit Profile</a></li>
          					<li><a href="logout.php">Log Out</a></li>
        				</ul>
      				</li>
		    	</ul>
		  	</div>
		</nav> 

		<div class="container" style="margin-top: 10%; ">
			<div class="row">
				<div class="col-xs-3 col-xs-offset-2">
					<h2 class="text-info" style="text-align: center">SEARCH FOR EVENTS</h2>
					</br>
						<button  class="btn btn-default center-block" name="submit" value="search" ><a href="search_events.php">SEARCH</a></button>
                    </br>
                    </br>

                    <h2 class="text-info" style="text-align: center">MY TICKETS</h2>
                    </br>
                        <button  class="btn btn-default center-block" name="submit" value="search" ><a href="mytickets.php">MY TICKETS</a></button>
                    </br>
                    </br>
                </div>
				<div class="col-xs-3 col-xs-offset-2">
					<h2 class="text-info" style="text-align: center">YOUR PROFILE</h2>
					</br>
                    <img src=<?php echo getPicPath(); ?> class="img-circle" alt="Profile Picture" width="304" height="236">
					</br>
                    </br>
						<button  class="btn btn-default center-block" name="submit" value="edit"><a href="edit_profile.php">GO TO PROFILE</a></button>
				</div>
			</div>
		</div>
		<div class="container" style="margin-top: 10%;">
			<div class="row">
				<h2 class="text-info" style="text-align: center">DASHBOARD FOR HISTORY</h2>
				<?php 
					createLogTable();
				?>
			</div>
		</div>

	 	<footer style="margin-top: 20%">
	 	</footer>
	</body>
</html>