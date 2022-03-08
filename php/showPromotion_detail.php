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
	
	$pro_no=$_POST['pro_no'];
	
	//$pro_no=1;
	
	$response=array();
	$sql="SELECT  pro_deadline, pro_intro FROM promotion WHERE pro_no='$pro_no'";
	$result=mysqli_query($link,$sql);
	while($row=mysqli_fetch_array($result)){
		
		array_push($response , array("pro_intro"=>$row['pro_intro'],"pro_deadline"=>$row['pro_deadline']));
	}
	echo json_encode($response , JSON_UNESCAPED_UNICODE);
	
	// 釋放空間
	mysqli_free_result($result);
	
	// 關閉 SQL
	mysqli_close($link);
?>