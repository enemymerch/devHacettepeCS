<?php
class Member
{
    public $isLoggedIn = false;
	public $userID = NULL;

	
}

class Admin{

}


	function Login(){
	
	}



	function Register($email, $name, $password, $street, $city, $postcode, $country){
		$result = false;
		
		$db = new Database();
		$db->openConnection();
		$db->addNewMember(,$db->getCountryID($country));


		$db->closeConnection();
	}
?>
