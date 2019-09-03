<?php

	/**
	 * This example shows settings to use when sending via Google's Gmail servers.
	 * This uses traditional id & password authentication - look at the gmail_xoauth.phps
	 * example to see how to use XOAUTH2.
	 * The IMAP section shows how to save this message to the 'Sent Mail' folder using IMAP commands.
	 */
	//Import PHPMailer classes into the global namespace
	use PHPMailer\PHPMailer\PHPMailer;
	use PHPMailer\PHPMailer\Exception;

	date_default_timezone_set('Etc/UTC');

	class SendMail{
		function __construct(){
			require '../vendor/autoload.php';
		}
		
		public function sendOtp($email, $otp){
			if (!filter_var($email, FILTER_VALIDATE_EMAIL)) return false;
			$msg = '<html>
						<body>
							<p>OTP for your email verification is : <b>'.$otp.'</b> <br>
							Please send it back for verification.</p>
							<br><br><br><br>
							<p>This is a electronically generated mail,
							please do not reply here.</p>
						</body>
					</html>';
			
			//Create a new PHPMailer instance
			$mail = new PHPMailer;
			$mail->isSMTP();
			$mail->SMTPDebug = 1;
			$mail->Host = 'smtp.gmail.com';
			$mail->IsHTML(true); 
			$mail->SMTPSecure = 'tls';
			$mail->Port = 587;
			$mail->SMTPAuth = true;
			$mail->Username = "ankitgirhe02@gmail.com";
			$mail->Password = "Ank1t@1997";
			$mail->setFrom('no_reply@bhuas.com', 'BHU Auto Services');
			$mail->AddAddress($email);
			$mail->Subject = 'Verify your email...';
			$mail->Body = $msg;
			
			return $mail->send();
		}
		
	}

?>
