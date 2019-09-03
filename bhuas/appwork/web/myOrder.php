<?php

	require_once '../includes/DbOperations.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){

		if(	isset($_POST['customer']) ){
			$db = new DbOperations();
			$response = $db->viewCustomerOrder($_POST['customer']) ;
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