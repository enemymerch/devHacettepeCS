<!DOCTYPE html>
<html lang="en">

<head>


	<?php 
		// Loggin Control
		include 'C:/xampp/htdocs/src/myEvents.php';
		include 'C:/xampp/htdocs/src/adminLoginAuthentication.php';


    $makeGoldInfo = "";

    if($_SERVER['REQUEST_METHOD'] == "POST"){
        if($_POST['submit'] == "makeGold"){
        	$discountData = $_POST['discountData'];
        	$memberData = $_POST['memberData'];

        	$dicountTokens = explode("-", $discountData);
        	$memberTokens = explode("-", $memberData);

        	if(( count($dicountTokens)==2) and( count($memberTokens)==2)){
        		$discountID = $dicountTokens[1];
        		$memberID = $memberTokens[0];

        		$result = makeMemberGold($memberID, $discountID);

        		if($result){
        			$makeGoldInfo = "Member succesfully made goldMember!";
        		}else{
        			$makeGoldInfo = "OMG, Cannot Make Member gold!";
        		}
        	}else{
        		$makeGoldInfo = "Somethings went wrong!";
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
				<h2 class="text-danger" style="text-align: center;">Handing Gold Members</h2>
				<div class="col-xs-4 col-xs-offset-1">
					<h4 class="text-info" style="text-align: center;">Currnet Gold Members</h4>
					<?php getGoldMemberTable();?>
				</div>
				<div class="col-xs-4 col-xs-offset-2">
					<h4 class="text-info"  style="text-align: center;">Make Gold Members</h4>
					<form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" method="POST">
						<div class="form-group">
							<?php getNormalMemberSelection(); ?>
							</br>
							<?php  getDiscountSelection(); ?>
							</br>
							<button type="submit" name="submit" value="makeGold" class="btn btn-default">Make Gold</button>
							<h4 class="text-succes" style="text-align: center;"><?php echo $makeGoldInfo ;?></h4>
						</div>
					</form>	
				</div>

			</div>
		</div>


	 	<footer style="margin-top: 20%">
	 	</footer>
	</body>
</html>