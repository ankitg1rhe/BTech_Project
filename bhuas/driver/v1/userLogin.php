<?php

	require_once '../includes/DbOperations.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){
		
		if(isset($_POST['mobile']) and isset($_POST['password']) ){
			$db = new DbOperations();
			if($db->userLogin($_POST['mobile'], $_POST['password'])){
				$user = $db->getUserByMobile($_POST['mobile']);

				$response['error'] = false;
				$response['auto_id'] = $user['auto_id'];
				$response['name'] = $user['name'];
				$response['mobile'] = $user['mobile'];
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