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
	
	$id = $_GET["id"];
	$table = $_GET["dtable"];
	$whatID = $_GET["wid"];
	$_SESSION["id"] = $id;
	$_SESSION["table"] = $table;
	$_SESSION["whatID"] = $whatID;
	switch($table){
		case 'age_table':
			header("Location:age_tableUpdate.php");
			break;
		case 'all_activity':
			header("Location:all_activityUpdate.php");
			break;
		case 'all_item':
			header("Location:all_itemUpdate.php");
			break;
		case 'all_type':
			header("Location:all_typeUpdate.php");
			break;
		case 'career_table':
			header("Location:career_tableUpdate.php");
			break;
		case 'exhib_detail':
			header("Location:exhib_detailUpdate.php");
			break;
		case 'gender_table':
			header("Location:gender_tableUpdate.php");
			break;
		case 'group_chose':
			header("Location:group_choseUpdate.php");
			break;
		case 'group_kind':
			header("Location:group_kindUpdate.php");
			break;
		case 'group_type_table':
			header("Location:group_type_tableUpdate.php");
			break;
		case 'people_trend':
			header("Location:people_trendUpdate.php");
			break;
		case 'personal_chose':
			header("Location:personal_choseUpdate.php");
			break;
		case 'person_kind':
			header("Location:person_kindUpdate.php");
			break;
		case 'type_place':
			header("Location:type_placepdate.php");
			break;
		case 'user':
			header("Location:userUpdate.php");
			break;
		default :
			echo "<th>Error</th>";
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
	<body>
</html>