
<?php

	include 'C:/xampp/htdocs/src/db.php';

	$test = new DatabaseClient();
	$test->openConnection();
	$countries =  $test->getCountries();
	   echo '</br><ul>';
   for($i = 0 ; count($countries)>$i ; $i++){
   		echo '<li>'. $countries[$i] .'</li>';
   }
   echo '</ul>';
	echo 'YEA'; 
?>
