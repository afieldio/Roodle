<?php

include(DATA.'scores.php');


	if(isset($_GET['score']) && isset($_GET['user'])){
		$score = $_GET['score'];
		$user = $_GET['user'];
	}

	insertScore($link, $score, $user);
	print_r("Score: " . $score . " User: " . $user);
	

include(VIEWS.'home.php');