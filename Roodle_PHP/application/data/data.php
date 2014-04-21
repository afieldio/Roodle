<?php

function getScores($link){
	$scores = array();

	$results = mysqli_query($link, "select * from Scores order by score desc limit 10");

	while ($record = mysqli_fetch_assoc($results)) {
		array_push($scores, $record);
	}

	return json_encode($scores);
}

function insertScore($link, $score, $user){
	$response = array();

	$results = mysqli_query($link, "insert into Scores(score, user) VALUES ('$score', '$user')");

}