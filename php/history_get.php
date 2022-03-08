<?php
	error_reporting(E_ERROR);	//不希望顯示錯誤提示
	// 連接 SQL 的 function
	function connect_sql() {
		global $link;
		$hostname= "localhost";		// 主機名稱
		$username= "root";			// 資料庫登入帳號
		$password= "testtest";		// 資料庫登入密碼
		$database= "competition";		// 資料庫名稱
		$link = mysqli_connect( $hostname , $username , $password );
		// 改為 UTF8 以免亂碼
		mysqli_query($link, "SET NAMES 'UTF8'");
		mysqli_select_db($link, $database) or die("無法選擇資料庫");
	}
	
	// 連接 SQL
	
	
	connect_sql() ;
	//$count=0;
	$activity_no=$_POST['activity_no'];
	$user_ID=$_POST['user_ID'];
	//$user_ID = "iiii";
	$sql="SELECT item , history.activity_no , palce_name , date FROM history NATURAL JOIN all_activity NATURAL JOIN all_item NATURAL JOIN type_place WHERE history.user_ID = '$user_ID' ORDER BY `history`.`date`  DESC";
	$result=mysqli_query($link,$sql);
	$response=array();
	while($row=mysqli_fetch_array($result)){
		
		array_push($response , array("item"=>$row["item"], "activity_no"=>$row["activity_no"], "palce_name"=>$row["palce_name"], "date"=>$row["date"]));
		//,"activity_count"=>(int)$row["activity_count"]
	}
	echo json_encode($response, JSON_UNESCAPED_UNICODE);
	// 釋放空間
	mysqli_free_result($result);
	
		// 關閉 SQL
	mysqli_close($link);
?>
