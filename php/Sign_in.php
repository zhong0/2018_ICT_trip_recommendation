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
			
		$user_ID=$_POST["user_ID"];
		$password=$_POST["password"];
		/*$user_ID='c8763';
		$password='13456';*/
		
	if(isset($user_ID) && isset($password))	
	{	
		$x="SELECT user_ID,password FROM user WHERE user_ID='$user_ID'";
		
		$result2=mysqli_query($link,$x);
		
		
		$y=0;
		while( $row2=mysqli_fetch_array($result2))
		{
			if($row2['user_ID']==$user_ID && $row2["password"]==$password)
			{
				$y++;
				$sql="SELECT gender,age,career,leaf_num FROM person_kind
				NATURAL JOIN user
				NATURAL JOIN age_table
				NATURAL JOIN career_table
				NATURAL JOIN gender_table
				WHERE user.user_ID='$user_ID' AND user.password='$password'";
				$result1 =mysqli_query($link,$sql);
				$response=array();
				while($row=mysqli_fetch_array($result1))
				{
					array_push($response,array("gender"=>$row["gender"],"age"=>$row["age"],"career"=>$row["career"],"judgment"=>'1',"leaf_num"=>$row['leaf_num']));
				}
	
				echo  json_encode($response,JSON_UNESCAPED_UNICODE); 
				
				break;
			}
			else if($row2['user_ID']==$user_ID && $row2["password"]!=$password)
			{
				$y++;
				$response=array();
				array_push($response,array("gender"=>$row["gender"],"age"=>$row["age"],"career"=>$row["career"],"judgment"=>'2',"leaf_num"=>$row['leaf_num']));
				echo  json_encode($response,JSON_UNESCAPED_UNICODE);
			}
			
		}
		
		if($y==0)
		{
			$response=array();
			
				array_push($response,array("gender"=>'NULL',"age"=>'NULL',"career"=>'NULL',"judgment"=>'0',"leaf_num"=>$row['leaf_num']));
			

				echo  json_encode($response,JSON_UNESCAPED_UNICODE); 
	
		}			
	}	
	
			
		
	
	// 釋放空間
		mysqli_free_result($result2);
		mysqli_free_result($result1);
	
		// 關閉 SQL
		mysqli_close($link);
?>
