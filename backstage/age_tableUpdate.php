<?php
	session_start();
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
	$id= $_SESSION["id"];
	$whatID = $_SESSION["whatID"];
	$table = $_SESSION["table"];
	if(isset($_POST["update"])){
		$age = $_POST["age"];
		$age_no = $_POST["age_no"];
		$sql = "UPDATE all_age set age = '$age' , age_no = '$age_no' where age = '$id'";
		if(mysqli_query($link , $sql)){
			echo "success";
		}else{
			echo "Error";
		}
		header("Location:backstageMainPage.php");
	}else{
		$sql = "select * from $table where $whatID = '$id'";
		$rows = mysqli_query($link , $sql);
		$nums = mysqli_num_rows($rows);
		if($nums > 0){
			$row = mysqli_fetch_array($rows);
		}
	}
	
?>
<!DOCTYPE html>
<html>
	<head>
		<meta charset = "utf-8">
	</head>
	<body>
		<form method = "post" action = "">
			<p>age：<input type = "text" name = "age" id = "age" value = <?php echo $row["age"]?>></p>
			<p>age_no：<input type = "text" name = "age_no" id = "age_no" value = <?php echo $row["age_no"]?>></p>
			<input type = "submit" name = "update" id="update" value = "更新資料">
		</form>
		<form method = "post" action = "backstageMainPage.php">
			<input type = "submit" name = "Dtomain" id = "Dtomain" value = "返回首頁"/>
		</form>
	</body>
</html>