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
	
	connect_sql();
	
	$user_ID=$_POST['user_ID'];
	$forgot_answer=$_POST['forgot_answer'];
	//$user_ID="c8763";
	//$forgot_answer="yes";
	if(isset($user_ID) && isset($forgot_answer))
	{
		$sql="SELECT password FROM user WHERE user_ID='$user_ID' AND forgot_answer='$forgot_answer'";
		$result=mysqli_query($link, $sql);
		$y=0;
		while($row=mysqli_fetch_array($result))
		{
			/*$y++;
			$response=array();
			array_push($response, array("password"=>$row["password"]));
			echo JSON_ENCODE($response, JSON_UNESCAPED_UNICODE);*/
			$y++;
			$response=array();
			array_push($response, array("password"=>$row["password"], "reply"=>"1"));
			echo JSON_ENCODE($response, JSON_UNESCAPED_UNICODE);
		}
		if($y==0)
		{
			/*$response="answer輸入錯誤";
			echo JSON_ENCODE($response, JSON_UNESCAPED_UNICODE);*/
			$response=array();
			array_push($response, array("password"=>$row["password"], "reply"=>"0"));
			echo JSON_ENCODE($response, JSON_UNESCAPED_UNICODE);
		}
	}
	else
	{
		$response=array();
		array_push($response, array("password"=>$row["password"], "reply"=>"2"));
		echo JSON_ENCODE($response, JSON_UNESCAPED_UNICODE);
	}
	// 釋放空間
	mysqli_free_result($result);
	
	// 關閉 SQL
	mysqli_close($link);
?>