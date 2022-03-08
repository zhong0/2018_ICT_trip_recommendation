<?php
	set_time_limit(0);
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
	$user_ID=$_POST['user_ID'];
	
	//$user_ID="c8763";
	
	$lastUseTime=date('d H:i:s',strtotime("+6 hours"));
	
	if(isset($user_ID)){
		$sql="SELECT leaf_num FROM user WHERE user_ID='$user_ID'";
		$result=mysqli_query($link,$sql);
		if($row=mysqli_fetch_array($result)){
			echo $row['leaf_num'];
		}
		else{
			echo -1;
		}
	}
	
	
	// 釋放空間
	mysqli_free_result($result);
	
	// 關閉 SQL
	mysqli_close($link);
?>