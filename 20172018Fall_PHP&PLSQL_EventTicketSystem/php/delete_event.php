<!DOCTYPE html>
<html lang="en">

<head>


    <?php
    // Loggin Control
    include 'C:/xampp/htdocs/src/myEvents.php';
    include 'C:/xampp/htdocs/src/adminLoginAuthentication.php';

    $delete_result = "";

    if($_SERVER['REQUEST_METHOD'] == "POST"){
        if($_POST['submit'] == "select_event"){
            $selectedEventID = $_POST['evnt_id'];
            if(deleteEvent($selectedEventID)){
                $delete_result = "Event succesfully deleted!";
            }
            $delete_result = "Somethings went wrong!";
        }
    }


    ?>

    <script type="text/javascript">
        function updateButtonText() {
            deleteButton = document.getElementById("select_button");

            deleteButton.innerText = "Delete";
        }
        window.onload = updateButtonText;
    </script>
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
            <h1 style="text-align: center;">Delete Events</h1>
            </br></br>
            <h4 style="text-align: center;">Select Event to Delete</h4>
            </br></br>
            <?php getEventTable();?>

            </br>
            </br>
            <h6 class="text-danger" style="text-align: center;"><php echo $delete_result; ?></h6>
        </div>
    </div>
</div>


<footer style="margin-top: 20%">
</footer>
</body>
</html>