<!DOCTYPE html>
<html lang="en">

<head>

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
		      		<a class="navbar-brand" href="">My Events</a>
		    	</div>
		    	<ul class="nav navbar-nav">
		      		<!--<li class="active"><a href="index.html">Home</a></li>-->
		    	</ul>
		    	<!-- <ul class="nav navbar-nav navbar-right">
		      		<li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
		      		<li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
		    	</ul>
		    -->
		  	</div>
		</nav> 
		<div class="container">
			<div class="row">
				<div class="col-xs-4 col-xs-offset-4">
					<div class="row">
						<ul class="list-group">
							<li class="list-group-item list-group-item-danger">Title</li>
							<li class="list-group-item list-group-item-info">Sub-title </li>
							<li class="list-group-item">item : not done!</li>
							<li class="list-group-item list-group-item-success">item: done!</li>
						</ul>

					</div>					
				</div>

				<div class="col-xs-6">
					<div class="row">
						 <ul class="list-group">
	  						<li class="list-group-item list-group-item-danger"><h2>Members</h2></li>
	  						<li class="list-group-item list-group-item-info"><h4>Member Pages : Pages that are in usage of the members.</h4></li>
	  						<li class="list-group-item">welcome_member.php : This page comes after member login.</li>
	  						<li class="list-group-item">edit_member_profile.php : This page is for editing member profile.</li>
	  						<li class="list-group-item">logout.php : This page comes after member logs out.</li>
	  						<li class="list-group-item">search_events.php : This page is for searching events. </li>
	  						<li class="list-group-item">related_event.php : This page changes itself depending on the event type. One page for every type.</li>
						</ul> 
					</div>
				</div>
				<div class="col-xs-6">
					<div class="row">
						<ul class="list-group">
							<li class="list-group-item list-group-item-danger"><h2>Admins</h2></li>
							<li class="list-group-item list-group-item-info"><h4>Admin Pages</h4></li>
							<li class="list-group-item">welcome_admin.php : This page come after admin login.</li>
							<li class="list-group-item">handling_events.php : This page is for adding, editing, deleting events.</li>
							<li class="list-group-item">add_event.php : This page is for adding new event.</li>
							<li class="list-group-item">edit_event.php : This page is for editing specific event.</li>
							<li class="list-group-item">delete_event.php : This page is for deleting event.</li>
							<li class="list-group-item">member_records.php : This page is for keeping track of members usage history.</li>
							<li class="list-group-item">gold_member_</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6 col-xs-offset-3">
					<ul class="list-group"> 
						<li class="list-group-item list-group-item-info"><h2>Common</h2></li>
						<li class="list-group-item list-group-item-danger"><h4>Common Pages</h4></li>
						<li class="list-group-item list-group-item-success">index.php : First page of the system. Login and sign up can be done in this page!</li>
						<li class="list-group-item">phpinfo.php : Show the used php's information.</li>
					</ul>
				</div>
			</div>
		</div>


	 	<footer style="margin-top: 20%">
	 	</footer>
	</body>
</html>