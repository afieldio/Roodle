<?php

include('config/init.php');

if(isset($_GET['page'])){
	$page = $_GET['page'];
}else{
	$page = 'home';
}

$controller_path = CONTROLLERS . $page . '.php';
if (file_exists($controller_path)) {
	// file does exist
	include($controller_path);
} else {
	// file does not exist
	echo "NOT WORKING";
}