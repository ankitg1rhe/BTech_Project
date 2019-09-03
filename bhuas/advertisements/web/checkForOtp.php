<?php

	require_once '../includes/DbOperations.php';
	require_once '../includes/SendMail.php' ;

	$response = array();
	
	if($_SERVER['REQUEST_METHOD'] == 'POST'){

		if(
			isset($_POST['username']) and
			isset($_POST['email']) and
			isset($_POST['otp'])
		){
			$db = new DbOperations();
			if($db->isUserExist($_POST['username'])){
				$response['error'] = true;
				$response['message'] = "User already exist!";
			}else{
				$sm = new SendMail();
				$result = $sm->sendOtp($_POST['email'], $_POST['otp']);
				if($result){
					$response['error'] = false;
					$response['message'] = "OTP sent to your email";
				}else{
					$response['error'] = false;
					$response['message'] = "Some error occurred";
				}
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