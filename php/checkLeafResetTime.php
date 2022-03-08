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
	$time=date("d H:i:s", strtotime("+6 hours"));
	//$timeTest=date("d", strtotime("+10 hours"));
	//echo $user_ID;
	$sql="SELECT lastUseTime FROM user WHERE user_ID='$user_ID'";
	$result=mysqli_query($link,$sql);
	while($row=mysqli_fetch_array($result)){
		if($time-$row['lastUseTime'] != 0){
			//echo $time-$row['lastUseTime'];					//不同天
			$sql2="UPDATE user_leaf SET leaf_taken=0 WHERE user_ID = '$user_ID'";
			mysqli_query($link,$sql2);
			echo 1;
		}
		else{
			echo 0;											//同天
		}
		$sql2="UPDATE user SET lastUseTime = '$time' WHERE user_ID = '$user_ID'";
		mysqli_query($link,$sql2);
	}
	// 釋放空間
	mysqli_free_result($result);
	
	// 關閉 SQL
	mysqli_close($link);
?>