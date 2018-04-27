<!DOCTYPE html>
<html lang="en">

<head>


    <?php
    // Loggin Control
    include 'C:/xampp/htdocs/src/myEvents.php';
    include 'C:/xampp/htdocs/src/adminLoginAuthentication.php';

    $isMemberSelected = False;
    $selectedMemberID = "";


    if($_SERVER['REQUEST_METHOD'] == "POST"){
        if($_POST['submit'] == "selectMember"){
            $memberData = $_POST['memberData'];
            $tokens= explode("-",$memberData);
            $selectedMemberID= $tokens[0];
            $selectedMembername = $tokens[1];
            $isMemberSelected = True;
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
        <div class="col-xs-8 col-xs-offset-2">
            <h1 style="text-align: center;">Member History</h1>
            </br></br>
            <h4 style="text-align: center;">Select Member</h4>
            </br></br>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-3 col-xs-offset-1">
            <div class="form-group">
                <h3 class="text-info" style="text-align: center;">Members</h3>
                <form action="members_history.php" method="POST">
                    <h6 class="text-succes" style="text-align: center;">USERID-USERNAME</h6>
                    <?php getMemberTable();?>
                    <button class="btn btn-default" type="submit" name="submit" value="selectMember">Select</button>
                </form>
            </div>
        </div>
        <div class="col-xs-7 col-xs-offset-1">
            <?php
            if($isMemberSelected){
                echo "<h6 class='text-danger'>Selected Members userID: ". $selectedMemberID."</h6>";
                echo "<h6 class='text-danger'>Selected Members username: ". $selectedMembername."</h6>";
                getLogTable($selectedMemberID);
            }
            ?>
        </div>
    </div>
</div>


<footer style="margin-top: 20%">
</footer>
</body>
</html>