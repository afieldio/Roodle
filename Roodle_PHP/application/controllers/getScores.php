<?php

include( DATA .'data.php');

$scores = getScores($link);


print_r($scores);

return $scores;