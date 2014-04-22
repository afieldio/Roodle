<?php

include(DATA.'scores.php');


	$scores = getScores($link);
	print_r($scores);
	
	return json_encode($scores);


include(VIEWS.'home.php');