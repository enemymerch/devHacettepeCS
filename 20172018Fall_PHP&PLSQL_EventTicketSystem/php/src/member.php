<?php

  function getDiscountPercentage($userID){
    $dbClient = new DatabaseClient();
    $dbClient->openConnection();
    $result = $dbClient->getDiscountPercentageByGoldmemberID($userID);
    $discountPercentage = $result['DISCOUNTPERCENTAGE']; 
    $dbClient->closeConnection(); 
    if(count($discountPercentage )==0){
      return 0;
    }
    return $discountPercentage[0];
  }

  function getPurchasedTicketsSelection() //edited function
  {
    $dbClient = new DatabaseClient();
    $dbClient->openConnection();
    $purchases = $dbClient->getPurchased($_SESSION['USERID']);
    $ticketIDs = $purchases['ID'];

    echo "<select class=\"form-control\" id=\"ticketIDs\" name=\"ticketData\"> ";
    for($i = 0 ; count($ticketIDs)>$i ; $i++){
        echo '<option>'.( $ticketIDs[$i] ).'</option>';
    }

    echo "</select>";


    $dbClient->closeConnection();

  }
  
  function giveBackPurchase($userID, $ticketID) //new function
  { 
        $dbClient = new DatabaseClient();
        $dbClient->openConnection();
        $result = $dbClient->deletePurchase($ticketID);
        $log = "Gived back ticket with ID: ".$ticketID;
        $dbClient->addNewMemberLog($userID, $log);
        $dbClient->closeConnection();

        return $result;
  }


function addNewPicPath($userid, $picpath){ // new function
    // creating a db Client
    $dbClient = new DatabaseClient();

    // opening the db connection
    $dbClient->openConnection();

    $result = $dbClient->addNewPicPath($userid, $picpath);

    $dbClient->closeConnection();
    return $result;

}

function createPurchasedTable() //new function
{
    $dbClient = new DatabaseClient();
    $dbClient->openConnection();

    if(isset($_SESSION)){
        //echo $_SESSION["USERID"];
        $sql_result = $dbClient->getPurhcasedTicketsInfos($_SESSION["USERID"]);

        // T.TICKETID, T.TICKETSEAT, E.EVENTNAME, E.EVENTINFORMATION, TO_CHAR(E.EVENTDATE, 'yyyy/mm/dd hh24:mi:ss') EVENTDATE,
        //ET.EVENTTYPE , L.LOCATIONNAME, L.LOCATIONINFORMATION, LT.LOCATIONTYPE, A.STREETADDRESS, A.CITYNAME, C.COUNTRYNAME

        $ticketID = $sql_result['TICKETID'];
        $ticketSeat = $sql_result['TICKETSEAT'];
        $eventName = $sql_result['EVENTNAME'];
        $eventInfos = $sql_result['EVENTINFORMATION'];
        $eventDate = $sql_result['EVENTDATE'];
        $eventType = $sql_result['EVENTTYPE'];
        $locName = $sql_result['LOCATIONNAME'];
        $locInfos = $sql_result['LOCATIONINFORMATION'];
        $locType = $sql_result['LOCATIONTYPE'];
        $streetAddress = $sql_result['STREETADDRESS'];
        $cityName = $sql_result['CITYNAME'];
        $countryName = $sql_result['COUNTRYNAME'];

        echo "<table class=\"table table-dark\"> <thead> <tr> 
              <th scope=\"col\"></th>
          
            
              <th scope=\"col\">Ticket ID</th>
              <th scope=\"col\">Ticket Seat</th>
              <th scope=\"col\">Event Name</th>
              <th scope=\"col\">Event Information</th>
              <th scope=\"col\">Event Date</th>
              <th scope=\"col\">Event Type</th>
              <th scope=\"col\">Location Name</th>
              <th scope=\"col\">Location Information</th>
              <th scope=\"col\">Location Type</th>
              <th scope=\"col\">Address</th>
              <th scope=\"col\">City</th>
              <th scope=\"col\">Country</th>
              
            </tr>
          </thead>";

        echo  "<tbody>";
        for($i = 0; $i<count($ticketID); $i++){
            echo "     <tr>
              <th scope=\"row\">".($i+1)."</th>
              <td>".$ticketID[$i]."</td>
              <td>".$ticketSeat[$i]."</td>
              <td>".$eventName[$i]."</td>
              <td>".$eventInfos[$i]."</td>
              <td>".$eventDate[$i]."</td>
              <td>".$eventType[$i]."</td>
              <td>".$locName[$i]."</td>
              <td>".$locInfos[$i]."</td>
              <td>".$locType[$i]."</td>
              <td>".$streetAddress[$i]."</td>
              <td>".$cityName[$i]."</td>
              <td>".$countryName[$i]."</td>
            </tr>";
        }


        echo " </tbody></table> ";
    }

}


  function purchaseTickets($ticketNumber, $eventID, $userID, $isGoldMember){
    $dbClient = new DatabaseClient();
    $dbClient->openConnection();
    //$dbClient->autoCommitOff();
    $tickets = $dbClient->getAvailableTickets($eventID);
    $ticketIDs = $tickets['TICKETID'];
    if(count($ticketIDs)<$ticketNumber){
      $dbClient->closeConnection();
      return 0;
    }
    for($i=0;$i<$ticketNumber;$i++){
      $ticketID = $ticketIDs[$i];
      $purchaseID = $dbClient->doPurchase($ticketID, $userID);
      if(!$purchaseID){
        $dbClient->rollBackToSavePoint();
        $dbClient->closeConnection();
        return 0;
      }else{
        if($isGoldMember){
          $temp = $dbClient->useDiscount($purchaseID, $userID, $ticketID);
          if(!$temp){
            $dbClient->rollBackToSavePoint();
            $dbClient->closeConnection();
            return 0;
          }
        }
      }
    }
    $log = "Purchased ".$ticketNumber." tickets to eventID: ". $eventID;
    $result = $dbClient->addNewMemberLog($userID, $log);
    $dbClient->commit();
    $dbClient->closeConnection();
    return 1;
  }

  function isGoldMember($userID){
    $dbClient = new DatabaseClient();
    $dbClient->openConnection();
    $result = $dbClient->getGoldMembers();
    $userIDs = $result['USERID'];

    for($i=0;$i<count($userIDs);$i++){
      if($userIDs[$i]==$userID){
        $dbClient->closeConnection();
        return True;
      }
    }
    $dbClient->closeConnection();
    return False;
  }

  function getTicketPrice($eventID){
    $dbClient = new DatabaseClient();;
    $dbClient->openConnection();
    $result = $dbClient->getTicketPrice($eventID);
    $ticketPrice = $result['TICKETPRICE'][0];
    if(is_null($ticketPrice)){
      return 0;
    }
    $dbClient->closeConnection();
    return $ticketPrice;
  }
  function getAvailableTicketNumber($eventID){
    $dbClient = new DatabaseClient();;
    $dbClient->openConnection();
    $result = $dbClient->getAvailableTicketNumberByEventID($eventID);

    $ticketNumber = $result['AVAILABLETICKETNUMBER'];
    if(count($ticketNumber)==0){
      return 0;
    }


    $dbClient->closeConnection();

    return $ticketNumber[0];
  }
  function getEventData($eventID){
    $dbClient = new DatabaseClient();
    $dbClient->openConnection();
    
    $data = $dbClient->getEventData($eventID);

    $dbClient->closeConnection();

    return $data;
  }


  //new function
  function displaySearchedEvents($eventName, $eventInfo, $eventType){
    $dbClient = new DatabaseClient();
    $dbClient->openConnection();
    $eventTypeID = $dbClient->getEventTypeID($eventType);
    $eventTypeID = $eventTypeID[0];

    $result = $dbClient->searchEvents($eventName, $eventInfo, $eventTypeID);
    $eventIDs = $result['EVENTID'];
    $eventNames = $result['EVENTNAME'];
    $eventInformations = $result['EVENTINFORMATION'];
    $eventDates = $result['EVENTDATE'];

    for($i=0;$i<count($eventIDs);$i++){
      echo getEventDiv($eventIDs[$i], $eventNames[$i], $eventInformations[$i], $eventDates[$i]);
    }

    $dbClient->closeConnection();

  }

    function getSelectedCategoryEvents($selectedEventTypeName){
    // TODO;
    $dbClient =  new DatabaseClient();
    $dbClient->openConnection();
    $eventTypeID =  $dbClient->getEventTypeID($selectedEventTypeName);

    if(is_null($eventTypeID)){
      return 0;
    }else{
      $result = $dbClient->getEventsByType($eventTypeID[0]);
      
      $eventIDs = $result['EVENTID'];
      $eventNames = $result['EVENTNAME'];
      $eventInformations = $result['EVENTINFORMATION'];
      $eventDates = $result['EVENTDATE'];

      if(count($eventIDs)==0){
        echo "There's no event to display";
      }else{
          for($i=0;$i<count($eventIDs); $i++){
            echo getEventDiv($eventIDs[$i], $eventNames[$i], $eventInformations[$i], $eventDates[$i]);
          }        
      }

    }

    $dbClient->closeConnection();
  }

function addNewMember($username, $email, $name, $password, $street, $city, $postcode, $phone, $country){
	// creating a db Client
	$dbClient = new DatabaseClient();

	// opening the db connection
	$dbClient->openConnection();
  $savePoint = "beforeRegister";
  $dbClient->putSavePoint($savePoint);

	// first going to check if the email address if used ort not!
	
	if( (int)$dbClient->isEmailTaken($email)) { // is email taken ? 
    $dbClient->closeConnection();
    return -9; // means Email is taken
	}else if((int)$dbClient->isUsernameTaken($username)){
    $dbClient->closeConnection();
    return -10; // means username is taken
	}else{
		//first going to create address for user !
		// but going to need the countryIDü
		$countryID = $dbClient->getCountryID($country);
		$addressID = $dbClient->addNewAddress($street, $city, $countryID, $postcode ); 
		//now , going to add the new user
		$result = $dbClient->addNewMember($username, $email, $name, getHash($password), $street, $city, $phone, $postcode, $addressID);
		if(!$result){
      $dbClient->rollBackToSavePoint();
    }
	}
  $dbClient->commit();
	$dbClient->closeConnection();
	return 0;
}

function createLogTable(){
    $dbClient = new DatabaseClient();
    $dbClient->openConnection();

    if(isset($_SESSION)){
      //echo $_SESSION["USERID"];
      $sql_result = $dbClient->getLogs($_SESSION["USERID"]);

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

}

  function updateMemberPassword($userID, $password){
    $dbClient = new DatabaseClient();
    $dbClient->openConnection();

    if($dbClient->updateUserPassword($userID, getHash($password))){
      $log = "Changed Password.";
      $dbClient->addNewMemberLog($userID, $log);
      $dbClient->commit();
      $dbClient->closeConnection();
      return 1;
    }
    $dbClient->closeConnection();
    return 0;
  }

  
  function updateMemberInfo($userID, $name, $phone, $email){
    $dbClient = new DatabaseClient();
    $dbClient->openConnection();

    if( $dbClient->updateMemberInfo($userID, $name, $phone, $email) ){
      $log = "Updated Info.";
      $dbClient->addNewMemberLog($userID, $log);
      $dbClient->commit();
      $dbClient->closeConnection();
      return 1;
    }
    return 0;
  }


function authenticateMember($username, $password){
    $dbClient = new DatabaseClient();
    $dbClient->openConnection();

    $result = $dbClient->getMembers();

    $userids = $result['USERID'];
    $usernames = $result['USERNAME'];
    $userpasswords= $result['USERPASSWORD'];
    //$i = 0;
    for($i = 0; $i<count($userids); $i++){
    	$r = $i;
    	if(validateLogin($username, $usernames[$i], getHash($password), $userpasswords[$i])){
   		    // YES
   		    session_start();
   		    $_SESSION["isLoggedIn"] = True;
   		    $_SESSION["USERTYPE"] = 1;
   		    //echo $usernames[$r];
   		    $_SESSION["USERID"] = $userids[$i];
   		    $_SESSION["USERNAME"] = $username;
   		    $_SESSION["USERPASSWORD"] = $userpasswords[$i];
   		    $log = "Logged In.";
          $dbClient->addNewMemberLog($_SESSION["USERID"], $log);
          $dbClient->commit();
   		    $dbClient->closeConnection();
			
    		return True;
    	}
    }

	$dbClient->closeConnection();
	return False;
}



?>