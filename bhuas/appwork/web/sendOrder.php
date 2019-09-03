<?php

	require_once '../includes/DbOperations.php';

	$response = array();

	if($_SERVER['REQUEST_METHOD'] == 'POST'){

		if(	isset($_POST['customer']) and isset($_POST['source']) and
				isset($_POST['destination']) and isset($_POST['noofcust'])	){
					
			$source = (int)$_POST['source'] ;
			$destination = (int)$_POST['destination'] ;
			$noofcust = (int)$_POST['noofcust'] ;

			$db = new DbOperations();
			$num = $db->pickOrderNo();
			$result1 = $db->orderInData($_POST['customer'], $source, $destination, $noofcust, $num) ;
			$result2 = $db->orderInCustomerAcc($_POST['customer'], $source, $destination, $noofcust, $num) ;

			if($result1 == 1 and $result2 == 1){
				$response['error'] = false;
				$response['message'] = "Order placed successfully!";
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