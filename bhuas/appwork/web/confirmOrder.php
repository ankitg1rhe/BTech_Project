<?php

	require_once '../includes/DbOperations.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){

		if(	isset($_POST['mobile']) and isset($_POST['auto_id']) and isset($_POST['source']) and isset($_POST['destination']) 
			and isset($_POST['noofcust']) and isset($_POST['number']) and isset($_POST['customer'])	){
				
			$auto_id = (int)$_POST['auto_id'] ;
			$source = (int)$_POST['source'] ;
			$destination = (int)$_POST['destination'] ;
			$noofcust = (int)$_POST['noofcust'] ;
			$number = (int)$_POST['number'] ;

		$db = new DbOperations();
		$status = $db->checkForInvalid($_POST['number']) ;
		
		if($status == 0){
			$result = $db->confirmByDriver($_POST['mobile'], $auto_id, $source, $destination, $noofcust, $number, $_POST['customer']) ;
			if($result == 1){
				$response['error'] = false;
				$response['message'] = "Order confirmed successfully!";
			}else{
				$response['error'] = true;
				$response['message'] = "Some error occurred";
			}
		}else if($status == 2){
			$response['error'] = true;
			$response['message'] = "Order is cancelled";
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