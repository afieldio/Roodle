<?php 

include(DATA.'data.php');

if(isset($_GET['score']) && isset($_GET['user'])){
		$score = $_GET['score'];
		$user = $_GET['user'];
}

insertScore($link, $score, $user);


include(VIEWS.'inserted.php');