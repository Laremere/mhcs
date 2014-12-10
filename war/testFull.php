<?php
header('Content-Type: application/json');

$case = $_GET["case"];
$callback = $_GET["callback"];

$response = @file_get_contents("./TestLandings/FullConfig" . $case . ".txt");
$response = str_replace("\"","\\\"", $response);
$response = str_replace("\n","", $response);
$response = str_replace("\r","", $response);

echo $callback;
echo "(\"";
echo $response;
echo "\");";

?>