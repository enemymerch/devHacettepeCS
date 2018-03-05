<!DOCTYPE html>
<html lang="en">

<head>


	<?php 
		// Loggin Control
        include 'C:/xampp/htdocs/src/myEvents.php';
		include 'C:/xampp/htdocs/src/memberLoginAuthentication.php';

		$update_password = "";
		$update_name = "";
		$update_email = "";
		$update_phone = "";


		$update_password_result = "";
		$update_info_result = "";


		$update_pic_path_result = "";


	    if( $_SERVER['REQUEST_METHOD'] == "POST" ){
	    	if($_POST['submit'] == "updatePassword"){
	    		$password = $_POST['update_password'];
	   		if( !isEmpty($password)){
	   			if(updateMemberPassword($_SESSION['USERID'], $password)){
	   				$update_password_result = "Password updated";
	   			}else{
	   				$update_password_result = "Password is not updated, Somethings went wrong!";
	   			}
	   		}else{
	   			$update_password = "Password is requered !";
	   		}
	    	}else if( $_POST['submit'] == "updateInfo"){
	    		$email = $_POST['update_email'];
	    		$name = $_POST['update_name'];
	    		$phone = $_POST['update_phone'];

	    		if( isEmpty($email)){
	    			$update_email = "Email is requered!";
	    		}else if( isEmpty($name)){
	    			$update_name = "Name is requered!";
	    		}else if( isEmpty($phone) ){
	    			$update_phone = "Phone number is requered!";
	    		}else{
	    			// every input is valid!
	    			if(updateMemberInfo($_SESSION['USERID'], $name, $phone, $email)){
	    				$update_info_result = "Informations updated";
	    			}else{
	    				$update_info_result = "Informations is not updated, Somethings went wrong!";
	    			}
	    		}
	    	}else if( $_POST['submit'] == "updateFile"){  //new control for upload picture
	    	    $userid = $_SESSION['USERID'];
	    	    $TARGET_PATH = "images/";
	    	    $uploadedPic = $_FILES["update_pic_path"]['name'];

               if(isEmpty($uploadedPic)){
                   $update_pic_path_result = "Picture is requered";
               }else{
                   $path =   pathinfo($uploadedPic);
                   $ext = strtolower($path['extension']);
                   $newPic = "C:/xampp/htdocs/images/".$userid.".".$ext;
                   move_uploaded_file($_FILES["update_pic_path"]['tmp_name'], $newPic);
                   if(addNewPicPath($_SESSION['USERID'], $newPic)){
	    	           $update_pic_path_result = "Profile Picture updated";
                   }else{
	    	           $update_pic_path_result = "Profile picture is not updated, Somethings went wrong!";
                   }
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

		<div class="container" style="margin-top: 10%; ">
			<div class="row">
				<div class="col-xs-3 col-xs-offset-1">
					<h2 class="text-info" style="text-align: center;">Update Your Password</h2></br>
					<h6 class="text-danger" style="text-align: center"> <?php echo $update_password_result ?></h6>
					<form method="POST" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
						<div class="form-group">
							<label for="pws">Password: <?php echo "<p>". $update_password."</p>"?></label>
		 					<input type="password" class="form-control" id="pwd" name="update_password">			
	 						</br>
	 						<button name="submit" value="updatePassword" type="submit" class="btn btn-default">Update Password</button>

						</div>
					</form>
                    <h2 class="text-info" style="text-align: center;">Update Your Information</h2>
                    <h6 class="text-danger" style="text-align: center"> <?php echo $update_info_result ?></h6>
                    </br>
                    <form method="POST" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>">
                        <div class="form-group">
                            <label for="name">Name: <?php echo "<p>". $update_name."</p>"?></label>
                            <input type="name" class="form-control" id="pwd" name="update_name">

                            <label for="email">Email: <?php echo "<p>". $update_email."</p>"?></label>
                            <input type="email" class="form-control" id="pwd" name="update_email">

                            <label for="number">PhoneNumber: <?php echo "<p>". $update_phone."</p>"?></label>
                            <input type="number" class="form-control" id="pwd" name="update_phone">

                            <button name="submit" value="updateInfo" type="submit" class="btn btn-default">Update Information</button>
                            <p>  </p>
                            <p>  </p>
                        </div>
                    </form>
				</div>
				<div class="col-xs-3 col-xs-offset-3">
                    <div class = "container-fluid">
                        <h2 class="text-info" style="text-align: center;">Profile Picture</h2>
                        </br>
                        </br>
                        <img src=<?php echo getPicPath(); ?> class="img-circle" alt="Profile Picture" width="304" height="236">
                    </div>
                    <form method="POST" action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]);?>" enctype="multipart/form-data">
                        <div class="form-group">
                            </br>
                            <label for="file">Select image to upload: </label>
                            <input type="file" class="form-control" id="pwd" name="update_pic_path">
                            </br>
                            <button name="submit" value="updateFile" type="submit" class="btn btn-default">Update Profile Picture</button>
                            <p>  </p>
                            <p>  </p>
                            <h4 class="text-danger" style="text-align: center;"><?php echo $update_pic_path_result;?></h4>
                        </div>
                    </form>
				</div>
			</div>
		</div>


	 	<footer style="margin-top: 20%">
	 	</footer>
	</body>
</html>