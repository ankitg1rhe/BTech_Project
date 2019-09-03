<?php

	require_once '../includes/DbOperations.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		
		if(isset($_POST['username']) and isset($_POST['password']) ){
			$db = new DbOperations();
			if($db->userLogin($_POST['username'], $_POST['password'])){
				$user = $db->getUserByEmail($_POST['username']);

				$response['error'] = false;
				$response['name'] = $user['name'];
				$response['username'] = $user['username'];
			}else{
				$response['error'] = true;
				$response['message'] = "Invalid email or password";
			}

		}else{
			$response['error'] = true;
			$response['message'] = "Required fields are missing";
		}

	}else{
		$response['error'] = true;
		$response['message'] = "Invalid Request";
	}

	echo json_encode($response);

?>