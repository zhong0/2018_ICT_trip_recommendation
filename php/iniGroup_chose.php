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
	$G="G000";
	$A="A0000";
	const allActivity_num = 468;
	const allGroupKind_num = 84;
	for($i=1;$i<allGroupKind_num+1;$i++){
		if($i<10){
			$G="G00".$i;
		}
		else if($i<100){
			$G="G0".$i;
		}
		else{
			$G="G".$i;
		}
		for($j=1;$j<allActivity_num+1;$j++){
			/*if($j<10){
				$A="A000".$j;
			}
			else if($j<100){
				$A="A00".$j;
			}
			else{
				$A="A0".$j;
			}*/
			$sql="INSERT INTO group_chose(group_no,activity_no,group_fre,group_rate) VALUES('$G','$j','0','0')";
			$result=mysqli_query($link,$sql);
			echo $i,$j;
			echo "<br>";
		}
	}
	// 釋放空間
	mysqli_free_result($result);
	
	// 關閉 SQL
	mysqli_close($link);
?>