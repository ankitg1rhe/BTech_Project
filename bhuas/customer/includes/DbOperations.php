<?php
	class DbOperations{
		private $con ;

		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php' ;

			$db = new DbConnect();
			$this->con = $db->connect();
		}

		public function userLogin($username, $password){
			$stmt = $this->con->prepare("SELECT `_id` FROM `customers` WHERE `username` = ? AND `password` = ?;") ;
			$stmt->bind_param("ss", $username, $password);
			$stmt->execute();
			$stmt->store_result();

			return $stmt->num_rows > 0;
		}

		public function getUserByEmail($username){
			$stmt = $this->con->prepare("SELECT * FROM `customers` WHERE `username` = ?;");
			$stmt->bind_param("s", $username);
			$stmt->execute();
			
			return $stmt->get_result()->fetch_assoc();
		}


		/* CREATE */
		public function createUser($name, $username, $password){
			$stmt = $this->con->prepare("INSERT INTO `customers` (`name`, `username`, `password`) 
														VALUES (?, ?, ?);");
			$stmt->bind_param("sss", $name, $username, $password);

			if($stmt->execute()){
				return 1;
			}else{
				return 2;
			}
		}
		
		public function isUserExist($username){
				$stmt = $this->con->prepare("SELECT `_id` FROM `customers` WHERE `username` = ?;") ;					
				$stmt->bind_param("s", $username) ;
				$stmt->execute();
				$stmt->store_result();
				
				return $stmt->num_rows > 0;
			
			
		}
		
	}
	/*
	$db = new DbOperations();
	$db->getUserByEmail("girheankitgulabmec15");
*/
?>