<?php
class DatabaseClient
{
    // property declaration
    public	$db = "(DESCRIPTION=(ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = localhost)(PORT = 1521)))(CONNECT_DATA=(SID=dbs)))" ;
	public	$conn = NULL;

	function  openConnection(){
		$this->conn = oci_connect("b21228932", "21228932", $this->db);
	}

	function  closeConnection(){
		$this->conn = oci_close($this->conn);
	}


	function putSavePoint($savePointName){
		$sql = "SAVEPOINT ".$savePointName;
		$stmt = oci_parse($this->conn, $sql);
		return oci_execute($stmt);
	}
	function rollBackToSavePoint(){
		oci_rollback($this->conn);
	}
	function commit(){
		oci_commit($this->conn);
	}

	function getDiscountPercentageByGoldmemberID($userID){
		$sql = "SELECT * FROM GOLDMEMBERS WHERE USERID=".$userID;
		$stmt = oci_parse($this->conn, $sql);

		oci_execute($stmt);
		oci_fetch_all($stmt, $res);
		return $res;
	}

		function addNewPicPath($userid, $picpath){ //new function

        $sql = 'BEGIN ADDNEWPICPATH(:USRID, :PCPATH); COMMIT; END;';
        $stmt = oci_parse($this->conn, $sql);

        //binding variables
        oci_bind_by_name($stmt, ':USRID', $userid);
        oci_bind_by_name($stmt, ':PCPATH', $picpath);

        //execute
        return oci_execute($stmt);

	}

	function addNewMemberLog($userID, $logInfo){
		$sql = 'BEGIN ADDNEWMEMBERLOG(:USRID, :LGINFORMATION); END;';
        $stmt = oci_parse($this->conn, $sql);

        oci_bind_by_name($stmt, ':USRID', $userID);
        oci_bind_by_name($stmt, ':LGINFORMATION', $logInfo);

        return oci_execute($stmt);
	}
    function getPurchased($userid) // edited function
	{
        $sql = "select P.TICKETID ID from PURCHASE P WHERE P.USERID =". (int)$userid . " ORDER BY P.PURCHASEID DESC";
        $stmt = oci_parse($this->conn, $sql);
        oci_execute($stmt);
        oci_fetch_all($stmt, $res);
        //$tickets = $res['ID'];

        return $res;
    }
    	
    function deletePurchase($ticketID) //new function
	{ 
        $sql = 'BEGIN DELETEPURCHASE(:TCKETID); END;';
        $stmt = oci_parse($this->conn, $sql);

        oci_bind_by_name($stmt, ':TCKETID', $ticketID);

        return oci_execute($stmt);
    }
	function getAvailableTickets($eventID){
		$sql = 'SELECT T.TICKETID FROM TICKET T WHERE T.TICKETSTATUSID = 1 AND T.EVENTID='.$eventID;
		$stmt = oci_parse($this->conn, $sql);
		oci_execute($stmt);
		oci_fetch_all($stmt, $res);
		return $res;
	}
	function useDiscount($purchaseID, $userID, $ticketID){
        $sql = 'BEGIN ADDNEWDISCOUNTUSAGE(:PRCHASEID, :USRID, :TCKTID); END;';
        $stmt = oci_parse($this->conn, $sql);
        
        oci_bind_by_name($stmt, ':PRCHASEID', $purchaseID);
        oci_bind_by_name($stmt, ':USRID', $userID);
        oci_bind_by_name($stmt, ':TCKTID', $ticketID);

        return oci_execute($stmt);
	}
	function doPurchase($ticketID, $userID){
        $sql = 'BEGIN DOPURCHASE(:TCKTID, :USRID, :PRCHASEID); END;';
        $stmt = oci_parse($this->conn, $sql);
        
        oci_bind_by_name($stmt, ':TCKTID', $ticketID);
        oci_bind_by_name($stmt, ':USRID', $userID);
        $purchaseID = 0;
        oci_bind_by_name($stmt, ':PRCHASEID', $purchaseID, 20);

        oci_execute($stmt, OCI_NO_AUTO_COMMIT);
		return $purchaseID;
	}
    function getPurhcasedTicketsInfos($userid) //new function
	{
        $sql = "SELECT T.TICKETID, T.TICKETSEAT, E.EVENTNAME, E.EVENTINFORMATION, TO_CHAR(E.EVENTDATE, 'yyyy/mm/dd hh24:mi:ss') EVENTDATE, 
          			ET.EVENTTYPE , L.LOCATIONNAME, L.LOCATIONINFORMATION, LT.LOCATIONTYPE, A.STREETADDRESS, A.CITYNAME, C.COUNTRYNAME 
				FROM PURCHASE P 
				INNER JOIN TICKET T
				ON T.TICKETID = P.TICKETID
				INNER JOIN USERS U
				ON U.USERID = P.USERID
				INNER JOIN EVENT E
				ON T.EVENTID = E.EVENTID
				INNER JOIN LOCATION L
				ON E.LOCATIONID=L.LOCATIONID
				INNER JOIN EVENTTYPE ET
				ON ET.EVENTTYPEID=E.EVENTTYPEID
				INNER JOIN ADDRESS A
				ON A.ADDRESSID=L.ADDRESSID
				INNER JOIN COUNTRY C
				ON A.COUNTRYID=C.COUNTRYID
				INNER JOIN LOCATIONTYPE LT
				ON L.LOCATIONTYPEID=LT.LOCATIONTYPEID
				WHERE U.USERID =". (int)$userid." ORDER BY P.PURCHASEID DESC" ;
        $stmt = oci_parse($this->conn, $sql);

        oci_execute($stmt);
        oci_fetch_all($stmt, $res);
        return $res;
    }
	
	function getTicketPrice($eventID){
		$sql = "SELECT MAX(T.TICKETPRICE) TICKETPRICE FROM TICKETPRICE T WHERE T.EVENTID=".$eventID;
		$stmt = oci_parse($this->conn, $sql);
		oci_execute($stmt);
		oci_fetch_all($stmt, $res);
		return $res;
	}
	function getAvailableTicketNumberByEventID($eventID){
		$sql = "SELECT A.AVAILABLETICKETNUMBER, E.EVENTID FROM AVAILABLETICKETNUMBER A INNER JOIN EVENT E ON E.EVENTID=A.EVENTID WHERE E.EVENTID=".$eventID;
		$stmt = oci_parse($this->conn, $sql);

		oci_execute($stmt);
		oci_fetch_all($stmt, $res);

		return $res;
	}

	function getEventData($eventID){
		$sql = "SELECT * FROM EVENTDATA WHERE EVENTID =".$eventID;
		$stmt = oci_parse($this->conn, $sql);

		oci_execute($stmt);
		oci_fetch_all($stmt, $res);

		return $res;
	}

	function searchEvents($eventName, $eventInfo, $eventTypeID){
		$sql = "SELECT E.EVENTID, E.EVENTNAME, E.EVENTINFORMATION, TO_CHAR(E.EVENTDATE, 'yyyy/mm/dd hh24:mi:ss') EVENTDATE FROM EVENT E where UPPER(E.EVENTNAME) LIKE UPPER('%".$eventName."%') AND UPPER(E.EVENTINFORMATION) LIKE UPPER('%".$eventInfo."%') AND E.EVENTTYPEID=".$eventTypeID;
		$stmt = oci_parse($this->conn, $sql);

		oci_execute($stmt);
		oci_fetch_all($stmt, $res);

		return $res;
	}

	function getEventsByType($eventTypeID){
		$sql = "select * from EVENT E where E.EVENTTYPEID ='".$eventTypeID."' ";
		$stmt = oci_parse($this->conn, $sql);

        oci_execute($stmt);
	    oci_fetch_all($stmt, $res);

	    return $res;
	}

	// New Fucnktion
	function getEventTypeID($eventTypeName){
		$sql = "select * from EVENTTYPE where EVENTTYPE ='" .$eventTypeName."'";
		$stmt = oci_parse($this->conn, $sql);
        oci_execute($stmt);
	    oci_fetch_all($stmt, $res);

		return $res['EVENTTYPEID'];
	}
        

	//New function
	function addNewGoldMember($userID, $discountID){
        $sql = 'BEGIN ADDNEWGOLDMEMBER(:USRID, :DSCOUNTID); END;';
        $stmt = oci_parse($this->conn, $sql);
        

        oci_bind_by_name($stmt, ':USRID', intval($userID));
        oci_bind_by_name($stmt, ':DSCOUNTID', intval($discountID));

        return oci_execute($stmt);
	}

	function getNormalMembers(){
		$sql = "select * from NORMALMEMBERS";
        $stmt = oci_parse($this->conn, $sql);
        oci_execute($stmt);
	    oci_fetch_all($stmt, $res);
	    return $res;
	}


	function getGoldMembers(){
		$sql = "select * from goldmembers";
        $stmt = oci_parse($this->conn, $sql);
        oci_execute($stmt);
	    oci_fetch_all($stmt, $res);
	    return $res;
	}

	function addDiscount($discountPercentage){
        $sql = 'BEGIN ADDNEWDISCOUNT(:PERCENTAGE); END;';
        $stmt = oci_parse($this->conn, $sql);

        oci_bind_by_name($stmt, ':PERCENTAGE', $discountPercentage);

        return oci_execute($stmt);
    }
    function getDiscounts(){
	    $sql = "select * from DISCOUNT order by DISCOUNTID";
	    $stmt = oci_parse($this->conn, $sql);

	    oci_execute($stmt);
	    oci_fetch_all($stmt, $res);
	    return $res;
    }
	function getLogs($userID){
		$sql = "select ML.LOGID, ML.LOGINFORMATION, TO_CHAR(ML.LOGDATE, 'yyyy/mm/dd hh24:mi:ss') LOGDATE from MEMBERLOG ML where ML.USERID=" . (int)$userID ." ORDER BY ML.LOGID DESC";
		$stmt = oci_parse($this->conn, $sql);

		oci_execute($stmt);
		oci_fetch_all($stmt, $res);
		return $res;
	}

	function updateUserPassword($userID, $password){
    	$sql = 'BEGIN UPDATEUSERPASSWORD(:USRID, :USRPASSWORD); END;';
  		$stmt = oci_parse($this->conn, $sql);

		oci_bind_by_name($stmt, ':USRID', $userID);
		oci_bind_by_name($stmt, ':USRPASSWORD', $password);

		//execution
		return oci_execute($stmt);
	}

	function updateMemberInfo($userID, $name, $phone, $email){
    	$sql = 'BEGIN UPDATEMEMBERINFO(:USRID, :MBRNAME, :MBRPHONENUMBER, :MBREMAIL); END;';
    	$stmt = oci_parse($this->conn, $sql);

    	oci_bind_by_name($stmt, ':USRID', $userID);
    	oci_bind_by_name($stmt, ':MBRNAME', $name);
    	oci_bind_by_name($stmt, ':MBRPHONENUMBER', $phone);
    	oci_bind_by_name($stmt, ':MBREMAIL', $email);

    	oci_execute($stmt);

	}

	function updateMember($userID, $userName, $userPassword, $addresID, $memberName, $memberPhone, $memberMail){
    	$sql = 'BEGIN UPDATEMEMBER(:USRID, :USRNAME, :USRPASSWORD, :USRTYPEID, :MBRTYPEID, :ADDID, :MBRNAME, :MBRPHONENUMBER, :MBREMAIL); END;';
  		$stmt = oci_parse($this->conn, $sql);

  		// TODO
  		oci_execute($stmt);
  	}


	function isUsernameTaken($username){
		$sql = 'BEGIN ISUSERNAMEUSED(:UNAME, :RES); END;';
		$stmt = oci_parse($this->conn, $sql);

		$result;
		//binding variables
		oci_bind_by_name($stmt, ':UNAME', $username);
		oci_bind_by_name($stmt, ':RES', $result);

		//execution
		oci_execute($stmt);

		return $result;
	}
	function isEmailTaken($email){
		$sql = 'BEGIN ISEMAILUSED(:EMAIL, :RES); END;';
		$stmt = oci_parse($this->conn, $sql);

		$result;
		//binding variables
		oci_bind_by_name($stmt, ':EMAIL', $email);
		oci_bind_by_name($stmt, ':RES', $result);

		//execution
		oci_execute($stmt);

		return $result;
	}
	function addNewAddress($street, $city, $countryID, $postcode){
		$result = False;

		$sql = 'BEGIN ADDNEWADDRESS(:STADD, :CITY, :COUNTRYID, :POSTCODE, :AID); END;';
		$stmt = oci_parse($this->conn, $sql);

		//binding variables		
		oci_bind_by_name($stmt, ':STADD', $street);
		oci_bind_by_name($stmt, ':CITY', $city);
		oci_bind_by_name($stmt, ':COUNTRYID', $countryID);
		oci_bind_by_name($stmt, ':POSTCODE', $postcode);
		$addressID  = 20 ;
		oci_bind_by_name($stmt, ':AID', $addressID,20);
		
		//execute
		oci_execute($stmt);
		return $addressID;
		
	}
    function deleteEventByID($eventID){
        $sql = 'BEGIN DELETEEVENT(:EVNTID); END;';
        $stmt = oci_parse($sql);

        oci_bind_by_name($stmt, ':EVNTID', $eventID);

        return oci_execute($stmt);
    }
	function getMembers(){
		$sql = 'Select * FROM MEMBERS';
		$stmt = oci_parse($this->conn, $sql);

		oci_execute($stmt);
		oci_fetch_all($stmt, $res);


		return $res;
		
	}

	function getAdmins(){
		$sql = 'Select * FROM ADMINS';
		$stmt = oci_parse($this->conn, $sql);

		oci_execute($stmt);
		oci_fetch_all($stmt, $res);


		return $res;
		
	}

	function updateEventByID($eventID, $eventName, $eventInfo){
	    $sql = 'BEGIN UPDATEEVENT(:EVNTID, :EVNTNAME, :EVNTINFORMATION); END;';
	    $stmt = oci_parse($this->conn, $sql);

        oci_bind_by_name($stmt, ':EVNTID', $eventID);
        oci_bind_by_name($stmt, ':EVNTNAME', $eventName);
        oci_bind_by_name($stmt, ':EVNTINFORMATION', $eventInfo);

        return oci_execute($stmt);
    }
    function getEventByID($eventID){
	    $sql = 'select * from event where EVENTID = '. $eventID;
	    $stmt = oci_parse($this->conn, $sql);

	    oci_execute($stmt);
	    oci_fetch_all($stmt, $res);

	    return $res;
    }
	function getEvents(){
		$sql = "select * from event";
		$stmt = oci_parse($this->conn, $sql);

		oci_execute($stmt);
		oci_fetch_all($stmt, $res);

		return $res;
	}

	function getEventtypeIDByeventtype($eventType){
		$sql = "select ET.EVENTTYPEID  ETID FROM EVENTTYPE ET WHERE ET.EVENTTYPE='".$eventType."'";
		$stmt = oci_parse($this->conn, $sql);

		oci_execute($stmt);
		oci_fetch_all($stmt, $res);

		return $res['ETID'];
	}
	function getLocationidBylocationname($locationName){
		$sql = "select L.LOCATIONID LID from LOCATION L WHERE L.LOCATIONNAME='".$locationName."'";
		$stmt = oci_parse($this->conn, $sql);
		oci_execute($stmt);
		oci_fetch_all($stmt, $res);
		return $res['LID'];
	}
	function getEventtypes(){
		$sql = 'select * from eventtype';
		$stmt = oci_parse($this->conn, $sql);

		oci_execute($stmt);
		oci_fetch_all($stmt, $res);
		//$id = $res['ID'];
		return $res['EVENTTYPE'];
	}
	function getLocations(){
		$sql = 'select * from location';
		$stmt = oci_parse($this->conn, $sql);

		oci_execute($stmt);
		oci_fetch_all($stmt, $res);
		//$id = $res['ID'];
		return $res['LOCATIONNAME'];
	}



	function addNewTicket($eventID, $ticketPrice, $ticketSeat){
		$sql = 'BEGIN ADDNEWTICKET(:EVNTID, :TCKTSTATUSID, :TCKTPRICE, :TCKTSEAT); END;';
		$stmt = oci_parse($this->conn, $sql);
		
		$ticketStatusID = 1;
		oci_bind_by_name($stmt, ':EVNTID', $eventID);
		oci_bind_by_name($stmt, ':TCKTSTATUSID', $ticketStatusID);
		oci_bind_by_name($stmt, ':TCKTPRICE', $ticketPrice);
		oci_bind_by_name($stmt, ':TCKTSEAT', $ticketSeat);

		oci_execute($stmt);
	}
	function addNewEvent($eventTypeID, $locationID, $eventName, $eventInfo, $eventDate){
		$sql = 'BEGIN ADDNEWEVENT(:LCATIONID, :EVNTTYPEID, :EVNTNAME, :EVNTINFORMATION , :EVNTDATE, :EID); END;';
		$stmt = oci_parse($this->conn, $sql);
		$tokens = explode("T", $eventDate);
		$tempDate = $tokens[0]. " " . $tokens[1] ;
		$eventID;
		oci_bind_by_name($stmt, ':LCATIONID', $locationID);
		oci_bind_by_name($stmt, ':EVNTTYPEID', $eventTypeID);
		oci_bind_by_name($stmt, ':EVNTNAME', $eventName);
		oci_bind_by_name($stmt, ':EVNTINFORMATION', $eventInfo);
		oci_bind_by_name($stmt, ':EVNTDATE', $tempDate);
		oci_bind_by_name($stmt, ':EID', $eventID, 20);

		oci_execute($stmt);

		return $eventID;
	}

	function addNewMember($username, $email, $name, $password, $street, $city, $phone, $postcode, $addID){
		$result = False;

		$sql = 'BEGIN ADDNEWMEMBER(:USRNAME, :USRPASSWORD, :USRTYPEID, :MBRTYPEID, :ADDID, :MBRNAME, :MBRPHONENUMBER, :MBREMAIL); END;';
		$stmt = oci_parse($this->conn, $sql);
		// email
		oci_bind_by_name($stmt, ':USRNAME', $username);
		// password
		oci_bind_by_name($stmt, ':USRPASSWORD', $password);
		//USRTYPEID
		$temp = 2;
		oci_bind_by_name($stmt, ':USRTYPEID', $temp);
		//membertypeid
		$temp = 1;
		oci_bind_by_name($stmt, ':MBRTYPEID', $temp);
		// addressid
		oci_bind_by_name($stmt, ':ADDID', $addID);
		//	memberName
		oci_bind_by_name($stmt, ':MBRNAME', $name);
		//MBRPHONENUMBER
		oci_bind_by_name($stmt, ':MBRPHONENUMBER', $phone);
		//email
		oci_bind_by_name($stmt, ':MBREMAIL', $email);
		
		//execute
		$result = oci_execute($stmt);
		
		return $result;
	}

	function  addNewCountry($country){
		$sql = 'BEGIN ADDNEWCOUNTRY(:CTRYNAME); END;';
		$stmt = oci_parse($this->conn,$sql);
		oci_bind_by_name($stmt,':CTRYNAME',$country,32);
		oci_execute($stmt);
	}


	function getCountryID($country){
		$sql = "select C.COUNTRYID ID from COUNTRY C Where C.COUNTRYNAME ='". $country."'";
		$stmt = oci_parse($this->conn, $sql);
		oci_execute($stmt);
		oci_fetch_all($stmt, $res);
		$id = $res['ID'];
		return $id[0];
	}

	function getCountries(){
		$stmt = oci_parse($this->conn, $this->getSelectSQL('COUNTRY'));
		oci_execute($stmt);
		oci_fetch_all($stmt, $res);
		$countries = $res['COUNTRYNAME'];

		return $countries;
	}

	function getSelectSQL($tableName){
		return 'select * from '.$tableName;
	}
}

?>
