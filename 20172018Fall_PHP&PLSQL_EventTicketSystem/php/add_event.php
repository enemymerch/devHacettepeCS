<!DOCTYPE html>
<html lang="en">

<head>


	<?php 
		// Loggin Control
		include 'C:/xampp/htdocs/src/myEvents.php';
		include 'C:/xampp/htdocs/src/adminLoginAuthentication.php';

		$event_name = "";
		$event_info = "";
		$event_date = "";
		$event_ticket = "";
		$event_price = "";
		$event_result = "";


		if($_SERVER['REQUEST_METHOD'] == "POST"){
			$e_name = $_POST['event_name'];
			$e_info = $_POST['event_info'];
			$e_date = $_POST['event_date'];
			$e_ticketNumber = $_POST['event_ticket'];
			$e_ticketPrice = $_POST['event_price'];
			$e_location = $_POST['event_location'];
			$e_type = $_POST['event_type'];


			if( isEmpty($e_name)){
				$event_name = "Event Name is requered!";
			}else if( isEmpty($e_info)){
				$event_info = "Event info is requered!";
			}else if ( isEmpty($e_date)){
				$event_date = "Event date is requered!";
			}else if( isEmpty($e_ticketNumber)){
				$event_ticket = "Ticket Number is requered!";
			}else if( !is_numeric($e_ticketNumber)){
				$event_ticket = "Must be numeric!";
			}else if( isEmpty($e_ticketPrice)){
				$event_price = "Ticket Price is requered!";
			}else if( !is_numeric($e_ticketPrice)){
				$event_price = "Must be numeric!";
			}else{
				$result = addEvent($e_name, $e_info, $e_date, $e_type, $e_location, $e_ticketNumber, $e_ticketPrice);
				if($result){
					$event_result = "New Event Added to the System!";
				}else{
					$event_result = "Somethings went wrong!";
				}

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
		      		<a class="navbar-brand" href="welcome_admin.php">My Events</a>
		    	</div>
		    	<ul class="nav navbar-nav">
		      		<li><a href="welcome_admin.php">Home</a></li>
		      		<li class="dropdown">
        				<a class="dropdown-toggle" data-toggle="dropdown" href="#">
        					Events</a>
        				<ul class="dropdown-menu">
          					<li><a href="add_event.php">Add Event</a></li>
          					<li><a href="edit_event.php">Edit Event</a></li>
          					<li><a href="delete_event.php">Delete Event</a></li>
        				</ul>
      				</li>
      				<li><a href="members_history.php">Member History</a></li>
      				<li><a href="handling_gold_members.php">Handling Gold Members</a></li>
		    		<li><a href="handling_discounts.php">Handling Discounts</a></li>
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
          					<li><a href="logout.php">Log Out</a></li>
        				</ul>
      				</li>
		    	</ul>
		  	</div>
		</nav> 
		<div class="container" style="margin-top: 10%; ">
			<div class="row">
				<h1 class="text-succes" style="text-align: center;">ADD NEW EVENTS</h1>
				<h5 class="text-danger" style="text-align: center;"><?php echo $event_result ?></h5>
				</br>
				</br>

				<form method="POST" action="add_event.php">
					<div class="col-xs-3 col-xs-offset-2">
						<div class="form-group">
							<label for="eventName">Event Name: <?php echo "<p>". $event_name."</p>"?></label>
		 					<input type="text" class="form-control" name="event_name">
		 					</br>
		 					<label>Event Information: <?php echo "<p>". $event_info."</p>"?></label>
		 					<input type="text" class="form-control" name="event_info">
		 					</br>
		 					<label>Event Date: <?php echo "<p>". $event_date."</p>"?></label>
		 					</br>
		 					<input type="datetime-local" name="event_date">						
		 					</br>
		 					</br>
		 					</br>
							<label for="eventType">Event type: </label>
							<?php echo getEventtypeTable();?>	

						</div>
					</div>
					<div class="col-xs-3 col-xs-offset-1">
						<label for="eventTicket">Total Ticket Number: <?php echo "<p>". $event_ticket."</p>"?></label>
		 				<input type="text" class="form-control" name="event_ticket">
		 				</br>
						<label for="eventPrice">Ticket Price: <?php echo "<p>". $event_price."</p>"?></label>
		 				<input type="text" class="form-control" name="event_price">
						</br>
						</br>
						<label for="eventLocation">Location:</label>
							<?php echo getLocationTable();?>
						</br>
						</br>
						</br>
	 					<button name="submit" value="addEvent" type="submit" class="btn btn-right">Add Event</button>
					</div>

				</form>

			</div>
		</div>


	 	<footer style="margin-top: 20%">
	 	</footer>
	</body>
</html>