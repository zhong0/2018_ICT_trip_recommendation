<?php
	set_time_limit(0);
	error_reporting(E_ERROR);	//不希望顯示錯誤提示
	// 連接 SQL 的 function
	function connect_sql() {
		global $link;
		$hostname= "localhost";		// 主機名稱
		$username= "root";			// 資料庫登入帳號
		$password= "testtest";		// 資料庫登入密碼
		$database= "competitiontest";		// 資料庫名稱
		$link = mysqli_connect( $hostname , $username , $password );
		// 改為 UTF8 以免亂碼
		mysqli_query($link, "SET NAMES 'UTF8'");
		mysqli_select_db($link, $database) or die("無法選擇資料庫");
	}
	
	// 連接 SQL
	
	connect_sql() ;
	$sql2="SELECT gender,age_no,career_no FROM gender_table join age_table join career_table WHERE gender != 'mix' AND age_no < 8";
	$result=mysqli_query($link,$sql2);
	$P="P000";
	
	$i=1;
	while($row=mysqli_fetch_array($result)){
		
		if($i<10){
			$P="P00".$i;
		}
		else if($i<100){
			$P="P0".$i;
		}
		else{
			$P="P".$i;
		}
		/*$sql="INSERT INTO `person_kind`(`person_no`, `gender`, `age_no`, `career_no`) VALUES ('$P','$row[gender]','$row[age_no]','$row[career_no]')";
		mysqli_query($link,$sql);*/
		echo $P." ".$row['gender']." ".$row['age_no']." ".$row['career_no'];
		echo "</br>";
		$i++;
		
	}
	echo $i;
	// 釋放空間
	mysqli_free_result($result);
	
	// 關閉 SQL
	mysqli_close($link);
?>