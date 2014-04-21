<?php

include('config/config.php');

opcache_reset();

if (isset($_GET['page'])) {
    $page = $_GET['page'];
} else {
    $page = 'home';
}

include (CONTROLLERS. $page.'.php');