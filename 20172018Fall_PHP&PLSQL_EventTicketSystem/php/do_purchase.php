<!DOCTYPE html>
<html lang="en">

<head>

	<script>
		function updatePrice(ticketPrice, discountPercentage = 0){
			myLabel = document.getElementById("totalPrice");
			myInput = document.getElementById("ticketNumber");
			ticketNumber = parseInt(myInput.value);
			myLabel.innerHTML = "Total : " + (ticketNumber*ticketPrice*((100-discountPercentage)/100));
		}
	</script>

	<?php 


		// Loggin Control
		include 'C:/xampp/htdocs/src/myEvents.php';
		include 'C:/xampp/htdocs/src/memberLoginAuthentication.php';

		$eventID ="";
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


		$avaliableTicketNumber = 0;
		$ticketPrice = 1;
		$isGoldMember = False;
		$discountPercentage = 0;

		if($_SERVER['REQUEST_METHOD'] == "GET"){
			if(! isset($_GET['eventID'])){
				redirect("404.php");
			}else{
				$eventID = $_GET['eventID'];
				$_SESSION['eventID']=$eventID;
				$eventData = getEventData($eventID);
				if(count($eventData['EVENTID'])==0){
					redirect("404.php");
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
				 
				$avaliableTicketNumber = getAvailableTicketNumber($eventID);
				$ticketPrice = getTicketPrice($eventID);
				$isGoldMember = isGoldMember($_SESSION["USERID"]);
				$_SESSION['ISGOLDMEMBER'] = $isGoldMember;
				if($isGoldMember){
					$discountPercentage = getDiscountPercentage($_SESSION["USERID"]);
				}
			}		
		}else if($_SERVER['REQUEST_METHOD'] == "POST"){
			if($_POST["submit"] = "doPurchase"){
				$ticketNumber = $_POST['ticketNumber'];
				$eventID  = $_SESSION['eventID'];
				if(purchaseTickets($ticketNumber, $eventID, $_SESSION['USERID'], $_SESSION['ISGOLDMEMBER'])){
					redirect("myTickets.php?submit=doPurchase");
				}else{
					echo "somethings wrong!";
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
			<div class="row" style="text-align: center;">
				<h2 class="text-danger" align="center"><?php echo $eventName; ?></h2>
				<p><strong>Event</strong></p>
				<p><strong>Name: </strong><?php echo $eventName; ?></p>
				<p><strong>Information: </strong><?php echo $eventInfo; ?></p>
				<p><strong>Date: </strong><?php echo $eventDate; ?></p>
				<p><strong>Type: </strong><?php echo $eventType; ?></p>
				</br>
			</div>
			<div class="row">
				<div class="col-xs-6 col-xs-offset-3">
					<h4 class="text-danger" align="center">Purhcase</h4>
					
					<form action="do_purchase.php" method="POST" class="form-horizontal">

    <fieldset>
      <legend>Payment<small><?php if($isGoldMember){
      	echo " (You're a gold member, discountPercentage : ".$discountPercentage.")";}?></small></legend>

    					<div class="form-group">
							<label class="col-sm-3 control-label text-info" for="avaliableTicketNumber">#Avaliable Tickets : <?php echo $avaliableTicketNumber ?> </label>

							<label class="col-sm-3 control-label" for="ticketNumber">Ticket number: </label>
							        <div class="col-sm-9">
    						<input type="number" id="ticketNumber" onchange="updatePrice(<?php echo $ticketPrice;
    						if($isGoldMember){
    							echo ", ".$discountPercentage;
    						} ?>)" name="ticketNumber" min="1" max="<?php echo $avaliableTicketNumber?>" class="form-control" placeholder="1">
							<label for="totalPrice" id="totalPrice">Total : </label>
	    </div>
						</div>
      
      <div class="form-group">
        <label class="col-sm-3 control-label" for="card-holder-name">Name on Card</label>
        <div class="col-sm-9">
          <input type="text" class="form-control" name="card-holder-name" id="card-holder-name" placeholder="Card Holder's Name">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label" for="card-number">Card Number</label>
        <div class="col-sm-9">
          <input type="text" class="form-control" maxlength="16" name="card-number" id="card-number" placeholder="Debit/Credit Card Number">
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label" for="expiry-month">Expiration Date</label>
        <div class="col-sm-9">
          <div class="row">
            <div class="col-xs-3">
              <select class="form-control col-sm-2" name="expiry-month" id="expiry-month">
                <option>Month</option>
                <option value="01">Jan (01)</option>
                <option value="02">Feb (02)</option>
                <option value="03">Mar (03)</option>
                <option value="04">Apr (04)</option>
                <option value="05">May (05)</option>
                <option value="06">June (06)</option>
                <option value="07">July (07)</option>
                <option value="08">Aug (08)</option>
                <option value="09">Sep (09)</option>
                <option value="10">Oct (10)</option>
                <option value="11">Nov (11)</option>
                <option value="12">Dec (12)</option>
              </select>
            </div>
            <div class="col-xs-3">
              <select class="form-control" name="expiry-year">
                <option value="13">2013</option>
                <option value="14">2014</option>
                <option value="15">2015</option>
                <option value="16">2016</option>
                <option value="17">2017</option>
                <option value="18">2018</option>
                <option value="19">2019</option>
                <option value="20">2020</option>
                <option value="21">2021</option>
                <option value="22">2022</option>
                <option value="23">2023</option>
              </select>
            </div>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label class="col-sm-3 control-label" for="cvv">Card CVV</label>
        <div class="col-sm-3">
          <input type="text" class="form-control" maxlength="3" name="cvv" id="cvv" placeholder="Security Code">
        </div>
      </div>
      <div class="form-group">
        <div class="col-sm-offset-3 col-sm-9">
          <button type="submit" name="submit" value="doPurchase" class="btn btn-success">Pay Now</button>
        </div>
      </div>
    </fieldset>
  </form>
				</div>
			</div>
		</div>


	 	<footer style="margin-top: 20%">
	 	</footer>
	</body>
</html>