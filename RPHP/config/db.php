<?php

$db_name = "Roodle";
$db_host = "localhost";
$db_pass = "root";
$db_user = "root";

$link = mysqli_connect($db_host, $db_user, $db_pass, $db_name);


if(mysqli_connect_errno()){
	echo "Failed to connect to MySQL: " . mysqli_connect_errno();
}