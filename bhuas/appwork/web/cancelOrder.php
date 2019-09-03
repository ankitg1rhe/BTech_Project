<?php

	require_once '../includes/DbOperations.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){

		if(isset($_POST['number']) and isset($_POST['customer'])	){
			$number = (int)$_POST['number'] ;

		$db = new DbOperations();
		$status = $db->checkForInvalid($_POST['number']) ;
		
		if($status == 0){
			$result = $db->cancelInCustomer($number, $_POST['customer']) ;
			if($result == 1){
				$response['error'] = false;
				$response['message'] = "Order canceled successfully!";
			}else{
				$response['error'] = true;
				$response['message'] = "Some error occurred";
			}
		}else{
			$response['error'] = true;
			$response['message'] = "Order placed already";
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