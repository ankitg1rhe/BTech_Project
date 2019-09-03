<?php
	class DbOperations{
		private $con ;
		
		function __construct(){
			
			require_once dirname(__FILE__).'/DbConnect.php' ;
			
			$db = new DbConnect();
			$this->con = $db->connect();
		}
		
		public function userLogin($mobile, $password){
			$stmt = $this->con->prepare("SELECT `_id` FROM `drivers` WHERE `mobile` = ? AND `password` = ?;") ;
			$stmt->bind_param("ss", $mobile, $password);
			$stmt->execute();
			$stmt->store_result();
			
			return $stmt->num_rows > 0;
		}

		public function getUserByMobile($mobile){
			$stmt = $this->con->prepare("SELECT * FROM `drivers` WHERE `mobile` = ?;");
			$stmt->bind_param("s", $mobile);
			$stmt->execute();
			
			return $stmt->get_result()->fetch_assoc();
		}
		
		/* CREATE */ 
		public function createUser($name, $mobile, $auto_id, $password){
			$stmt = $this->con->prepare("INSERT INTO `drivers` (`name`, `mobile`, `password`, `auto_id`) 
														VALUES (?, ?, ?, ?);");
			$stmt->bind_param("sssd", $name, $mobile, $password, $auto_id);

			if($stmt->execute()){
				require_once dirname(__FILE__).'/DbDriver.php' ;
				$dbd = new DbDriver();
				$res = $dbd->createDB($mobile);
				if($res === 1)
					return 1;
				else
					return 0;
			}else{
				return 2;
			}
		}

		public function isUserExist($mobile){
			$stmt = $this->con->prepare("SELECT `_id` FROM `drivers` WHERE `mobile` = ?;");
			$stmt->bind_param("s", $mobile) ;
			$stmt->execute();
			$stmt->store_result();
			
			return $stmt->num_rows > 0;
		}
		
	}
	
	
?>