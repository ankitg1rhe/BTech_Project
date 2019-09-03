<?php

	require_once '../includes/DbOperations.php';
	require_once '../includes/DbCustomer.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){

		if(
			isset($_POST['name']) and
				isset($_POST['username']) and
					isset($_POST['password'])
			){

			$db = new DbOperations();
			$result = $db->createUser( $_POST['name'], $_POST['username'], $_POST['password']	);
			
			$account = new DbCustomer();
			$result2 = $account->createDB($_POST['username']);
			
			if($result == 1 and $result2 == 1){
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