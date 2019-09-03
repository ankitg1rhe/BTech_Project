<?php

	require_once '../includes/DbOperations.php';
	require_once '../includes/DbDriver.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){

		if(
			isset($_POST['name']) and
				isset($_POST['mobile']) and
					isset($_POST['auto_id']) and
						isset($_POST['password'])
			){

			$db = new DbOperations();
			$result = $db->createUser( $_POST['name'], "ab".$_POST['mobile'], $_POST['auto_id'], $_POST['password']);
			
			if($result == 1){
				$response['error'] = false;
				$response['message'] = "User registered successfully!";
			}else{
				$response['error'] = true;
				$response['message'] = "Some error occurred";
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