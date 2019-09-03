<?php
	class DbConnect{

		private $con ;

		function __construct(){

		}

		function getAdId(){
			include_once dirname(__FILE__).'/Constants.php';
			$this->con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME) ;

			if(mysqli_connect_errno()){
				echo "Failed to connect with database ".mysqli_connect_error();
			}

			$sql = "SELECT * FROM `admob` WHERE 1" ;
			$result = $this->con->query($sql);
			$answer = array();
			$i = 0;
			if ($result->num_rows > 0){
				while($row = $result->fetch_assoc()){
					$answer[$i] = array();
					$answer[$i]["company"] = $row["company"];
					$answer[$i]["advertise"] = $row["advertise"];
					$i++;
				}
			}
			$this->con->close();
			return $answer;
		}
	}

	$db = new DbConnect();
	$result = $db->getAdId();
	echo json_encode($result) ;

?>