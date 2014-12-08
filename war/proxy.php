<?php
header('Content-Type: application/json');

$case = $_GET["case"];
echo "{";
//echo "\"code\":";
//echo $case;
//echo ",";

$response = file_get_contents("http://www.d.umn.edu/~abrooks/SomeTests.php?q=" . $case);
echo "\"response\":\"";
$response = str_replace("\"","\\\"", $response);
$response = str_replace("\n","\\n", $response);
$response = str_replace("\r","\\r", $response);
echo $response;
echo "\"}";

?>