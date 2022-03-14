<?php
	function connect_sql() {
		global $link;
		$hostname= "localhost";		// 主機名稱
		$username= "root";			// 資料庫登入帳號
		$password= "k50510e04";		// 資料庫登入密碼
		$database= "finaldb";		// 資料庫名稱
		$link = mysqli_connect( $hostname , $username , $password );
		// 改為 UTF8 以免亂碼
		mysqli_query($link, "SET NAMES 'UTF8'");
		mysqli_select_db($link, $database) or die("無法選擇資料庫");
	}
	connect_sql();
	$id = $_GET["id"];
	$table = $_GET["dtable"];
	$whatID = $_GET["wid"];
	$sql = "delete from $table where $whatID = $id";
	if(mysqli_query($link , $sql)){
		header("Location:backstageMainPage.php");
	}else{
		echo "錯誤，請檢查外鍵";
	}
	mysqli_close($link);
?>
<!DOCTYPE html>
<html>
	<head>
		<meta charset = "utf-8">
		<title>backstageDelete</title>
	</head>
	<body>
		<form method = "post" action = "backstageMainPage.php">
			<input type = "submit" name = "Dtomain" id = "Dtomain"/>
		</form>
	<body>
</html>