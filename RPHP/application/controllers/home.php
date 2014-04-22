<?php

include(DATA.'scores.php');

if($_GET['page'] == "getScores"){

	$scores = getScores($link);
	print_r($scores);
	return $scores;

} else if($_GET['page'] == "insertScores"){

	if(isset($_GET['score']) && isset($_GET['user'])){
		$score = $_GET['score'];
		$user = $_GET['user'];
	}

	insertScore($link, $score, $user);
	print_r("Score: " . $score . " User: " . $user);
}



include (VIEWS.'home.php');