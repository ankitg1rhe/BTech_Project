<?php
	class DbOperations{
		private $con ;

		function __construct(){
			require_once dirname(__FILE__).'/DbConnect.php' ;
			include_once dirname(__FILE__).'/Constants.php';
		}

		// selecting order number
		public function pickOrderNo(){
			$db = new DbConnect();
			$this->con = $db->connect();
			$sql = "SELECT orderno FROM currentorder WHERE current = 'CURRENT_NO'" ;
			$result = $this->con->query($sql);
			if ($result->num_rows > 0){
				$row = $result->fetch_assoc() ;
				$num = ++$row["orderno"] ;
				
				$sql = "UPDATE `currentorder` SET `orderno`= ".$num." WHERE current = 'CURRENT_NO'";
				if ($this->con->query($sql) === TRUE) {
					$this->con->close();
					return $num;
				}
			}
			$this->con->close();
			return 0;
		}
		
		// putting order in all level database for view by driver
		public function orderInData($customer, $source, $destination, $noofcust, $number){
			$db = new DbConnect();
			$this->con = $db->connect();
			$stmt = $this->con->prepare("INSERT INTO orders (customer, source, destination, number, noofcust) 
											VALUES (?,?,?,?,?)" );
			$stmt->bind_param("sdddd", $customer, $source, $destination, $number, $noofcust);

			if($stmt->execute()){
				$this->con->close();
				return 1;
			}
			$this->con->close();
			return 0;
		}

		// order in database of particular customer
		public function orderInCustomerAcc($customer, $source, $destination, $noofcust, $number){
			/* Create connection */
			$this->con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, $customer) ;
			if(mysqli_connect_errno()){
				echo "Failed to connect with database ".mysqli_connect_error();
			}
			$stmt = $this->con->prepare("INSERT INTO orders ( source, destination, number, noofcust) 
											VALUES (?,?,?,?)" );
			$stmt->bind_param("dddd", $source, $destination, $number, $noofcust);

			if($stmt->execute()){
				$this->con->close();
				return 1;
			}
			$this->con->close();
			return 0;
		}
		
		// order seen by driver which are not placed
		public function viewByDriverFromData(){
			$db = new DbConnect();
			$this->con = $db->connect();
			$sql = "SELECT * FROM orders WHERE status = 0" ;
			$result = $this->con->query($sql);
			$answer = array();
			$i = 0;
			if ($result->num_rows > 0){
				while($row = $result->fetch_assoc()){
					$answer[$i] = array();
					$answer[$i]["customer"] = $row["customer"];
					$answer[$i]["source"] = $row["source"];
					$answer[$i]["destination"] = $row["destination"];
					$answer[$i]["reg_date"] = $row["ordertime"];
					$answer[$i]["number"] = $row["number"];			// order_number
					$answer[$i]["noofcust"] = $row["noofcust"];
					$i++;
				}
			}
			$this->con->close();
			$ans = array();
			$ans["orders"] = $answer;
			return $ans;
		}
	
		// confirmation of order by driver to particular request
		public function confirmByDriver($mobile, $auto_id, $source, $destination, $noofcust, $number, $customer){
			$db = new DbOperations();
			$db->confirmInData($auto_id, $number);
			$db->confirmToCustomer($auto_id, $number, $customer);
			
			/* Create connection */
			$this->con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, $mobile) ;
			if(mysqli_connect_errno()){
				echo "Failed to connect with database ".mysqli_connect_error();
			}
			$stmt = $this->con->prepare("INSERT INTO orders (source, destination, customer, status, number, noofcust)
										VALUES (?,?,?,?,?,?)" );
			$status = 1;
			$stmt->bind_param("ddsddd", $source, $destination, $customer, $status, $number, $noofcust);

			if($stmt->execute()){
				$this->con->close();
				return 1;
			}
			$this->con->close();
			return 0;
		}
		
		// getting confirmation in all level database by driver
		private function confirmInData($auto_id, $number){
			$db = new DbConnect();
			$this->con = $db->connect();
			$sql = "UPDATE orders SET auto_id = ".$auto_id.", ordertime= CURRENT_TIMESTAMP, status = 1 WHERE number = ".$number;
			if ($this->con->query($sql) === TRUE) {
				$this->con->close();
				return 1;
			}
			$this->con->close();
			return 0;
		}
		
		// getting confirmation to customer for his order
		private function confirmToCustomer($auto_id, $number, $customer){
			/* Create connection */
			$this->con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, $customer) ;
			if(mysqli_connect_errno()){
				echo "Failed to connect with database ".mysqli_connect_error();
			}
			$sql = "UPDATE orders SET auto_id = ".$auto_id.", status = 1, reg_date = CURRENT_TIMESTAMP WHERE number = ".$number;
			if ($this->con->query($sql) === TRUE) {
				$this->con->close();
				return 1;
			}
			$this->con->close();
			return 0;
		}
		
		private function cancelInData($number){
			$db = new DbConnect();
			$this->con = $db->connect();
			$sql = "UPDATE orders SET status = 2 WHERE number = ".$number;
			if ($this->con->query($sql) === TRUE) {
				$this->con->close();
				return 1;
			}
			$this->con->close();
			return 0;
		}

		public function cancelInCustomer($number, $customer){
			$db = new DbOperations();
			$db->cancelInData($number);
			/* Create connection */
			$this->con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, $customer) ;
			if(mysqli_connect_errno()){
				echo "Failed to connect with database ".mysqli_connect_error();
			}
			$sql = "UPDATE orders SET status = 2 WHERE number = ".$number;
			if ($this->con->query($sql) === TRUE) {
				$this->con->close();
				return 1;
			}
			$this->con->close();
			return 0;
		}
		
		// order seen by particular customer his own
		public function viewCustomerOrder($customer){
			/* Create connection */
			$this->con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, $customer) ;
			if(mysqli_connect_errno()){
				echo "Failed to connect with database ".mysqli_connect_error();
			}
			
			$sql = "SELECT * FROM orders WHERE 1" ;
			$result = $this->con->query($sql);
			$answer = array();
			$i = 0;
			if ($result->num_rows > 0){
				while($row = $result->fetch_assoc()){
					$answer[$i] = array();
					$answer[$i]["auto_id"] = $row["auto_id"];
					$answer[$i]["source"] = $row["source"];
					$answer[$i]["destination"] = $row["destination"];
					$answer[$i]["reg_date"] = $row["reg_date"];
					$answer[$i]["number"] = $row["number"];			// order_number
					$answer[$i]["noofcust"] = $row["noofcust"];
					$answer[$i]["status"] = $row["status"];
					$i++;
				}
			}
			$this->con->close();
			$ans = array();
			$ans["orders"] = $answer;
			return $ans;
		}
	
		// order seen by driver which he has accepted
		public function viewDriverOrder($mobile){
			/* Create connection */
			$this->con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, $mobile) ;
			if(mysqli_connect_errno()){
				echo "Failed to connect with database ".mysqli_connect_error();
			}
			
			$sql = "SELECT * FROM orders WHERE 1" ;
			$result = $this->con->query($sql);
			$answer = array();
			$i = 0;
			if ($result->num_rows > 0){
				while($row = $result->fetch_assoc()){
					$answer[$i] = array();
					$answer[$i]["customer"] = $row["customer"];
					$answer[$i]["source"] = $row["source"];
					$answer[$i]["destination"] = $row["destination"];
					$answer[$i]["reg_date"] = $row["reg_date"];
					$answer[$i]["number"] = $row["number"];			// order_number
					$answer[$i]["noofcust"] = $row["noofcust"];
					$i++;
				}
			}
			$this->con->close();
			$ans = array();
			$ans["orders"] = $answer;
			return $ans;
		}
		
		// checking if the order is already placed
		public function checkForInvalid($number){
			$db = new DbConnect();
			$this->con = $db->connect();
			$sql = "SELECT status FROM orders WHERE number = ".$number ;
			$result = $this->con->query($sql);
			if ($result->num_rows > 0){
				$row = $result->fetch_assoc() ;
				$num = $row["status"] ;
				return $num;
			}
			$this->con->close();
			return -1;
		}
	}

/*	$db = new DbOperations();
	$response = $db->viewCustomerOrder("ankitg1rhe") ;
	echo json_encode($response);

	$result = $db->checkForInvalid(1002);
	if($result == 1) echo 'Order placed already';
	if($result == 0) echo 'Order is available';
	if($result == -1) echo 'Invalid request';
*/
?>