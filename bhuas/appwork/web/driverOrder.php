<?php

	require_once '../includes/DbOperations.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){

		if(	isset($_POST['mobile']) ){
			$db = new DbOperations();
			$response = $db->viewDriverOrder($_POST['mobile']) ;
		}else{
			$response['error'] = true;
			$response['message'] = "Error occurred";
		}

	}else{
		$response['error'] = true;
		$response['message'] = "Invalid Request";
	}

	echo json_encode($response);

?>