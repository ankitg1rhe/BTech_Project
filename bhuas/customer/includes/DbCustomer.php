<?php
	
	class DbCustomer{
		
		private $conn ;
	
		function __construct(){
			
			// Create connection
			$this->conn = new mysqli("localhost", "root", "");
			
			// Check connection
			if ($this->conn->connect_error) {
				die("Connection failed: " . $this->conn->connect_error);
			}
			
		}

		public function createDB($db_name){

			// Create database
			$sql = "CREATE DATABASE ".$db_name;
			if ($this->conn->query($sql) === TRUE) {
				$result = $this->createTable($db_name);
				if($result == 1) return 1;
			} else {
				echo "Error creating database: " . $this->conn->error;
			}
			
			return 0;
		}


		private function createTable($db_name){
			mysqli_select_db($this->conn, $db_name);

			// sql to create table
			$sql = 'CREATE TABLE orders (
						_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY, 
						source INT NOT NULL,
						destination INT NOT NULL,
						auto_id INT DEFAULT 0 ,
						status INT DEFAULT 0 ,
						number INT NOT NULL,
						noofcust INT DEFAULT 0,
						reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
					)';

			if ($this->conn->query($sql) === TRUE) {
				return 1;
			} else {
				return 0;
			}

		}
		
		
	}



?>