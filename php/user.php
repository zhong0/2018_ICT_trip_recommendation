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
			

		
		$gender=$_POST["gender"];
		
		$age_no=$_POST["age_no"];
		
		$career_no=$_POST["career_no"];
		
		$user_ID=$_POST["user_ID"];
		
		/*$user_ID="c8763";
		$age_no="2";
		$career_no="2";
		$gender="male";*/
		
		$sql="SELECT person_no FROM person_kind WHERE gender='$gender' AND age_no='$age_no' AND career_no='$career_no'" ;
		
		$result=mysqli_query($link,$sql);
		
		$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
		$person_no=$row["person_no"];
		
	
			$sql2="UPDATE  user SET  person_no='$person_no' WHERE user_ID='$user_ID'"; 
			mysqli_query($link,$sql2);
			

	
		
	
			
		
		
		
        
		
		/*$sql2="INSERT INTO user (user_ID,person_no) values('$user_ID','$person_no')"; 
			mysqli_query($link,$sql2);*/
	
	// 釋放空間
		mysqli_free_result($result);
	
		// 關閉 SQL
		mysqli_close($link);
?>
