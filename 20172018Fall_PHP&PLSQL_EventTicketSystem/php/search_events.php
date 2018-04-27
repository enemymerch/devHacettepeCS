<!DOCTYPE html>
<html lang="en">

<head>


	<?php 

		$isUserSearchedAnything = False;
		$tokenEventType = "";
		$tokenEventName = "";
		$tokenEventInfo = "";

		// Loggin Control
		include 'C:/xampp/htdocs/src/myEvents.php';
		include 'C:/xampp/htdocs/src/memberLoginAuthentication.php';


		$goEventCategoryPageInfo = "";
		$seachEventsInfo = "";
		if($_SERVER['REQUEST_METHOD'] == "POST"){
			if($_POST['submit'] == "goEventCategory"){
	            $event_type = $_POST['event_type'];
	            if(isset($_SESSION)){
	            	$_SESSION['selectedEventType'] = $event_type;	   
					redirect("related_event.php");
	            }else{
	            	$goEventCategoryPageInfo = "No, you cannot see this page! Somethings wrong! SOS";	
	            }
		    }else if($_POST['submit'] == "seachEvents"){
	            $isUserSearchedAnything = True;

	            $tokenEventType = $_POST['event_type'];
	            $tokenEventName = $_POST['searchEventName'];
	            $tokenEventInfo = $_POST['searchEventInfo'];
		    }
		}
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

		<div class="container" style="margin-top: 8%; ">
			<div class="row">
				<h2 class="text-info" style="text-align: center;">Search Events</h2>
				<div class="col-xs-8">
					<div class="form-group row">
						<h4 style="text-align: center;">Search Events</h4>

						<form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" method="POST">
						</br>
						<div class="row">
								<div class="col-xs-6">
									
								<label for="eventName">EventName: </label>
								<input type="text" name="searchEventName" class="form-control">
								</br>
							</div>
							<div class="col-xs-6">
								<label for="eventName">EventInfo: </label>
								<input type="text" name="searchEventInfo" class="form-control">
								</br>
							</div>
						</div>
						<div class="row">
								<div class="col-xs-6">
										
									<label for="eventType">EventType: </label>
									<?php echo getEventtypeTable(); ?>
									</br>	</br>
							</div>
							<div class="col-xs-4 col-xs-offset-2">
								</br>
								<button type="submit" name="submit" value="seachEvents" class="btn btn-info"><span class="glyphicon glyphicon-search"></span> 	Search</button>
							</div>
						</form>	
					</div>
					<div class="row">
						<div class="col-xs-10">
								
							<h4 class="text-default" style="text-align: center;">Search Result</h4>
							<?php 
							if($isUserSearchedAnything){

								displaySearchedEvents($tokenEventName, $tokenEventInfo, $tokenEventType);
								echo $seachEventsInfo;
							}else{
								echo "<h6 class='text-default' style='text-align: center;'>Nothing searched!</h6>";
							}

								?>
						</div>
					</div>
					</div>
					
				</div>
				
				<div class="col-xs-3 col-xs-offset-1">
					<h4 style="text-align: center;">Go to Event Category Page</h4>
					<div class="form-group col-xs-8 col-xs-offset-1">
						<form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" method="POST">
							</br>
							<label for="evenType">Select Event Type</label>
							<?php echo getEventtypeTable(); ?>
							</br>
							<button type="submit"  name="submit" value="goEventCategory" class="btn btn-danger">Go</button>
							</br>
							<h3 class="text-info" style="text-align: center;"><?php echo $goEventCategoryPageInfo; ?></h3>
						</form>
					</div>
				</div>
			</div>
		</div>


	 	<footer style="margin-top: 20%">
	 	</footer>
	</body>
</html>