<?php 


function getEventDiv($eventID, $eventName, $eventInfo, $eventDate){
	$htmlCode = 	"<div class='row'>
			<h4>".$eventName."</h4>
			<h6>".$eventInfo."</h6>
			<div class='form-group'>
				<form action='event.php' method='GET'>
					<input style='display: none;' type='text' name='eventID' value='".$eventID."'>			
					<button type='submit' name='submit' value='".$eventID."' class='btn btn-info'>Go to Event Page</button>
				</forrm>
			</div>
		</div>";

		return $htmlCode;
	}

function getPicPath()   // new function
{
    $pic = "images/".$_SESSION['USERID']."."."jpg";
    if(file_exists($pic)){
        return $pic;
    }else{
        $pic = "images/default.jpg";
        return $pic;
    }
}

function getDiscountSelection(){
	$dbClient = new DatabaseClient();
	$dbClient->openConnection();
	$result = $dbClient->getDiscounts();

	$dicountIDs = $result["DISCOUNTID"];
	$discountPercentages = $result["DISCOUNTPERCENTAGE"];

    echo "<select class=\"form-control\" id=\"members\" name=\"discountData\"> ";
	for($i = 0 ; count($dicountIDs)>$i ; $i++){
        echo '<option>'.( $discountPercentages[$i] ). "-" . $dicountIDs[$i].'</option>';
    }

    echo "</select>";


	$dbClient->closeConnection();

}

function getDiscountTable(){
	$dbClient = new DatabaseClient();
	$dbClient->openConnection();
	$result = $dbClient->getDiscounts();

	$discountIDs = $result['DISCOUNTID'];
	$discountPercentages = $result['DISCOUNTPERCENTAGE'];
	

	echo  "<table class=\"table table-dark\"> <thead> <tr> 
              <th scope=\"col\"></th>
              <th scope=\"col\">Discount ID</th>
              <th scope=\"col\">Discount Percentage</th>
            </tr>
          </thead>";
    echo  "<tbody>";
	
	for($i = 0; $i<count($discountIDs); $i++){
		echo "     <tr>
			<th scope=\"row\">".($i+1)."</th>
				<td>".$discountIDs[$i]."</td>
				<td>".$discountPercentages[$i]."</td>".
			"</tr>";
    }
    echo " </tbody></table> ";
    echo "</br></br></br>";

	$dbClient->closeConnection();

}
function getNormalMemberSelection(){
    $dbClient = new DatabaseClient();
    $dbClient->openConnection();
    $members = $dbClient->getNormalMembers();
    $memberIDs = $members['USERID'];
    $memberUsernames = $members['USERNAME'];

    echo "<select class=\"form-control\" id=\"members\" name=\"memberData\"> ";
    for($i = 0 ; count($memberIDs)>$i ; $i++){
        echo '<option>'.( $memberIDs[$i] ). "-" . $memberUsernames[$i].'</option>';
    }

    echo "</select>";


    $dbClient->closeConnection();

}

function getGoldMemberSelection(){

	$dbClient = new DatabaseClient();
	$dbClient->openConnection();
	$result = $dbClient->getGoldMembers();

	$userIDs = $result['USERID'];
	$discountIDs = $result['DISCOUNTID'];
	$discountPercentages = $result['DISCOUNTPERCENTAGE'];
	

}

function getGoldMemberTable(){
	$dbClient = new DatabaseClient();
	$dbClient->openConnection();
	$result = $dbClient->getGoldMembers();

	$userIDs = $result['USERID'];
	$discountIDs = $result['DISCOUNTID'];
	$discountPercentages = $result['DISCOUNTPERCENTAGE'];
	

	echo  "<table class=\"table table-dark\"> <thead> <tr> 
              <th scope=\"col\"></th>
              <th scope=\"col\">Member ID</th>
              <th scope=\"col\">Discount ID</th>
              <th scope=\"col\">Discount Percentage</th>
            </tr>
          </thead>";
    echo  "<tbody>";
	
	for($i = 0; $i<count($userIDs); $i++){
		echo "     <tr>
			<th scope=\"row\">".($i+1)."</th>
				<td>".$userIDs[$i]."</td>
				<td>".$discountIDs[$i]."</td>
				<td>".$discountPercentages[$i]."</td>".
			"</tr>";
    }
    echo " </tbody></table> ";
    if(count($userIDs) == 0){
    	echo '<h5 class=\"text-danger\">No Gold Member</h5>';
    }
    echo "</br></br></br>";

	$dbClient->closeConnection();

}


function redirect($url, $statusCode = 303)
{
   header('Location: ' . $url, true, $statusCode);
   die();
}


function isEmpty($var){
	if($var == ""){
		return True;
	}

	return False;

}

function getHash($var){
	return md5($var);
}
function getEventTable(){
	$dbClient = new DatabaseClient();
	$dbClient->openConnection();
	$htmlCode = "<select class=\"form-control\" id=\"event1\" name=\"event_table\"> ";
	$events =  $dbClient->getEvents();
	$eventIDs = $events["EVENTID"];
	$eventNames = $events["EVENTNAME"];
	$eventInfos = $events["EVENTINFORMATION"];
	$eventDates = $events["EVENTDATE"];


	echo  "<table class=\"table table-dark\"> <thead> <tr> 
              <th scope=\"col\"></th>
          
            
              <th scope=\"col\">Event ID</th>
              <th scope=\"col\">Event Name</th>
              <th scope=\"col\">Event Info</th>
              <th scope=\"col\">Event Date</th>
            </tr>
          </thead>";
    echo  "<tbody>";

    for($i = 0; $i<count($eventIDs); $i++){
	    echo "     <tr>
	      <th scope=\"row\">".($i+1)."</th>
	      <td>".$eventIDs[$i]."</td>
	      <td>".$eventNames[$i]."</td>
	      <td>".$eventInfos[$i]."</td>
	      <td>".$eventDates[$i]."</td>".
	    "</tr>";
    }

    echo " </tbody></table> ";

    echo "</br></br></br>";
    echo "Select id from the list;";


    echo "<form method=\"POST\" action=\"".htmlspecialchars($_SERVER["PHP_SELF"]) ."\"> ";
    echo "<div class=\"form-group\" >";

    echo "<select class=\"form-control\" id=\"sel1\" name=\"evnt_id\"> ";
	for($i = 0 ; count($eventIDs)>$i ; $i++){
		echo '<option>'.( $eventIDs[$i] ).'</option>';
	}
	echo "</select>";

	echo "<button type=\"submit\" id=\"select_button\" name=\"submit\" value=\"select_event\">Select</button>";
	echo "</div>";
	echo "</form>";
	$dbClient->closeConnection();

}


    function getMemberTable(){
        $dbClient = new DatabaseClient();
        $dbClient->openConnection();
        $members = $dbClient->getMembers();
        $memberIDs = $members['USERID'];
        $memberUsernames = $members['USERNAME'];

        echo "<select class=\"form-control\" id=\"members\" name=\"memberData\"> ";
        for($i = 0 ; count($memberIDs)>$i ; $i++){
            echo '<option>'.( $memberIDs[$i] ). "-" . $memberUsernames[$i].'</option>';
        }

        echo "</select>";


        $dbClient->closeConnection();

    }
function validateLogin($username1, $username2, $password1, $password2){
    	if(($username1==$username2) and( $password1 == $password2)){
    		return True;
    	}
    	return False;
    }
function getLogTable($userID){
    $dbClient = new DatabaseClient();
    $dbClient->openConnection();

    $sql_result = $dbClient->getLogs($userID);

    $logIDs = $sql_result['LOGID'];
    $logInfos = $sql_result['LOGINFORMATION'];
    $logDates = $sql_result['LOGDATE'];
    //echo count($logInfos);

    echo "<table class=\"table table-dark\"> <thead> <tr> 
          <th scope=\"col\"></th>
      
        
          <th scope=\"col\">Log ID</th>
          <th scope=\"col\">Log Information</th>
          <th scope=\"col\">Log Date</th>
        </tr>
      </thead>";

    echo  "<tbody>";
    for($i = 0; $i<count($logIDs); $i++){
        echo "     <tr>
          <th scope=\"row\">".($i+1)."</th>
          <td>".$logIDs[$i]."</td>
          <td>".$logInfos[$i]."</td>
          <td>".$logDates[$i]."</td>
        </tr>";
    }


    echo " </tbody></table> ";


}

function getCountryTable(){
	$dbClient = new DatabaseClient();
	$dbClient->openConnection();
	$htmlCode = "<select class=\"form-control\" id=\"sel1\" name=\"reg_country\"> ";
	$countries =  $dbClient->getCountries();
	if ($countries == NULL){
		$htmlCode = $htmlCode.'<option>1</option>';
	}
	for($i = 0 ; count($countries)>$i ; $i++){
		$htmlCode = $htmlCode.'<option>'.strtoupper( $countries[$i] ).'</option>';
	}
	$htmlCode = $htmlCode."</select>";
	$dbClient->closeConnection();
	return $htmlCode;
}

function getEventtypeTable(){
	$dbClient = new DatabaseClient();
	$dbClient->openConnection();
	$htmlCode = "<select class=\"form-control\" id=\"sel2\" name=\"event_type\"> ";
	$locations =  $dbClient->getEventtypes();

	if ($locations == NULL){
		$htmlCode = $htmlCode.'<option>1</option>';
	}
	for($i = 0 ; count($locations)>$i ; $i++){
		$htmlCode = $htmlCode.'<option>'.$locations[$i].'</option>';
	}
	$htmlCode = $htmlCode."</select>";
	$dbClient->closeConnection();
	return $htmlCode;
}


function getLocationTable(){
	$dbClient = new DatabaseClient();
	$dbClient->openConnection();
	$htmlCode = "<select class=\"form-control\" id=\"sel1\" name=\"event_location\"> ";
	$locations =  $dbClient->getLocations();

	if ($locations == NULL){
		$htmlCode = $htmlCode.'<option>1</option>';
	}
	for($i = 0 ; count($locations)>$i ; $i++){
		$htmlCode = $htmlCode.'<option>'. $locations[$i].'</option>';
	}
	$htmlCode = $htmlCode."</select>";
	$dbClient->closeConnection();
	return $htmlCode;
}

function validateRegisterInformation($usernmae, $email, $name, $password, $street, $city, $postCode, $phone){

	$result = True;
	if(isEmpty($usernmae)){
		global $reg_username;
		$reg_username = "Username is requered!";
		$result  = False;
	}

	// email
	if(isEmpty($email)){
		global $reg_email;
		$reg_email = "Email is requered!";
		$result  = False;
	}
	// name
	if(isEmpty($name)){
		global $reg_name;
		$reg_name = "Name is requered!";
		$result  = False;
	}
	// password
	if(isEmpty($password)){
		global $reg_password;
		$reg_password = "Name is requered!";
		$result  = False;
	}
	//street
	if(isEmpty($street)){
		global $reg_street;
		$reg_street	= "Street Address is requered!";
		$result  = False;
	}
	//city
	if(isEmpty($city)){
		global $reg_city;
		$reg_city = "City is requered!";
		$result  = False;
	}

	// postCode
	if(isEmpty($postCode)){
		global $reg_post_code;
		$reg_post_code = "Postcode is requered!";
		$result  = False;
	}

	// phone
	if(isEmpty($phone)){
		global $reg_phone;
		$reg_phone = "Phonenumber is requered!";
		$result  = False;
	}
	return $result;
}


function validateLoginInformation($username, $password){
	$result = True;
	// email
	if(isEmpty($username)){
		global $login_username;
		$login_username = "Username is requered to login!";
		$result  = False;
		
	}
	// name
	if(isEmpty($password)){
		global $login_password;
		$login_password = "Password is requered to login!";
		$result  = False;
	}

	return $result;
}


?>

