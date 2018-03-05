<?php 

		// Loggin Control
		session_start();
		if(isset($_SESSION)){
			if(isset($_SESSION["isLoggedIn"])){
				if( ($_SESSION["isLoggedIn"] )and ($_SESSION["USERTYPE"] == 2)){
					// OKAY !
				}else{
					//echo "1";
					redirect("index.php");
				}
			}else{
				//echo "2";
				redirect("index.php");
			}
		}else{
			//echo "3";
			redirect("index.php");
		}

?>