<!DOCTYPE html>
<html lang="en">

<head>


	<?php 


		// Loggin Control
		include 'C:/xampp/htdocs/src/myEvents.php';
		include 'C:/xampp/htdocs/src/memberLoginAuthentication.php';

		$eventID  = "";
		$eventName = "";
		$eventInfo = "";
		$eventDate = "";
		$eventType = "";
		$locationName = "";
		$locationInformation = "";
		$locationType = "";
		$locationAddressStreetAddress = "";
		$locationAddressCityName = "";
		$locationAddressPostCode = "";
		$locationAddressCountry = "";

		if($_SERVER['REQUEST_METHOD'] == "GET"){
			if(! isset($_GET['eventID'])){
				redirect("search_events.php");
			}else{
				$eventID = $_GET['submit'];
				$eventData = getEventData($eventID);
				if(count($eventData['EVENTID'])==0){
					redirect("search_events.php");
				}
				$eventName = $eventData['EVENTNAME'][0];
				$eventInfo = $eventData['EVENTINFORMATION'][0];
				$eventDate = $eventData['EVENTDATE'][0];
				$eventType = $eventData['EVENTTYPE'][0];
				$locationName = $eventData['LOCATIONNAME'][0];
				$locationInformation = $eventData['LOCATIONINFORMATION'][0];
				$locationType = $eventData['LOCATIONTYPE'][0];
				$locationAddressStreetAddress = $eventData['STREETADDRESS'][0];
				$locationAddressCityName = $eventData['CITYNAME'][0];
				$locationAddressPostCode = $eventData['POSTCODE'][0];
				$locationAddressCountry = $eventData['COUNTRYNAME'][0];
			}		
		}else if($_SERVER['REQUEST_METHOD'] == "POST"){
			// TODO
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

		<div class="container" style="margin-top: 8%;" >
			<h2 class="text-danger" align="center"><?php echo $eventName; ?></h2>
				</br>
				</br>
				</br>
				<div class="row">
					<div class="col-xs-4" style="text-align: center;">
						<p><strong>Event</strong></p>
						<p><strong>Name: </strong><?php echo $eventName; ?></p>
						<p><strong>Information: </strong><?php echo $eventInfo; ?></p>
						<p><strong>Date: </strong><?php echo $eventDate; ?></p>
						<p><strong>Type: </strong><?php echo $eventType; ?></p>
					</div>
					<div class="col-xs-4" style="text-align: center;">
						<p><strong>Location</strong></p>
						<p><strong>Name: </strong><?php echo $locationName; ?></p>
						<p><strong>Information: </strong><?php echo $locationInformation; ?></p>
						<p><strong>Type: </strong><?php echo $locationType; ?></p>
					</div>
					<div class="col-xs-4" style="text-align: center;">
						<p><strong>Address</strong></p>
						<p><strong>Street: </strong><?php echo $locationAddressStreetAddress; ?></p>
						<p><strong>City: </strong><?php echo $locationAddressCityName; ?></p>
						<p><strong>PostCode: </strong><?php echo $locationAddressPostCode; ?></p>
						<p><strong>Country: </strong><?php echo $locationAddressCountry; ?></p>

					</div>
				</div>
				<br/>
				<div class="row">
					<div class="form-group" align="center">
						<form action="do_purchase.php" method="GET">
							<input style='display: none;' type='text' name='eventID' value='<?php echo $eventID?>'>			
							<button type='submit' name='submit' value='doPurchase' class='btn btn-info'>Purhcase Tickets</button>
						</form>
					</div>
				</div>
		</div>
	 	<footer style="margin-top: 20%">
	 	</footer>
	</body>
</html>