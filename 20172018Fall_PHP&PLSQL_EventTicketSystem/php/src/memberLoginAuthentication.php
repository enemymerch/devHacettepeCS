<?php

        $included_files = get_included_files();
        $isIncluded = False;
        foreach ($included_files as $filename) {
            if($filename == "C:\\xampp\\htdocs\\src\\utils.php"){
                $isIncluded = True;
            }

        }
        if(!$isIncluded){
            include "utils.php";
        }

        // Loggin Control
		session_start();
		if(isset($_SESSION)){
			if(isset($_SESSION["isLoggedIn"])){
				if( ($_SESSION["isLoggedIn"] )and ($_SESSION["USERTYPE"] == 1)){
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