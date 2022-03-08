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
		
		//$Mode='personalStranger';	
		$Mode=$_POST["Mode"];
		$activity_no=$_POST["activity_no"];
		
		
		//$activity_no='4';
		//$user_ID='11111';
		$user_ID=$_POST["user_ID"];
		
		
		//$gender='male';
		$gender=$_POST["gender"];
		// $age_no='2';
	    $age_no=$_POST["age_no"];
		
		$group_type_no=$_POST["group_type_no"];
		//$group_type_no='1';
		
		
		
		$career_no=$_POST["career_no"];
		 //$career_no='1';
		
		
		switch($Mode)
		{
			case'personalUser':
				$sql="SELECT person_no FROM user  WHERE user_ID='$user_ID'";
			
					$result=mysqli_query($link,$sql);
				
					$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
					$person_no=$row["person_no"];
				
					
				//更新頻率	
				$sql="SELECT people_fre FROM people_trend WHERE person_no='$person_no' AND activity_no='$activity_no' ";
				
					$result=mysqli_query($link,$sql);
		
					$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
				$people_fre=$row["people_fre"];
					
					$people_fre++;  
			
				//改同種人的的頻率
				$sql2="UPDATE people_trend SET people_fre='$people_fre'  WHERE activity_no='$activity_no' AND person_no='$person_no' ";	
					$result=mysqli_query($link,$sql2);
				
				
		
				//加個人選擇活動頻率喔		開找
				$sql="SELECT user_fre FROM personal_chose WHERE user_ID='$user_ID' AND activity_no='$activity_no' ";
		
					$result=mysqli_query($link,$sql);
		
					$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
					$user_fre=$row["user_fre"];
					
					$user_fre++;
					
				//改變個人的頻率喔		
				$sql="UPDATE personal_chose SET user_fre='$user_fre' WHERE user_ID='$user_ID' AND activity_no='$activity_no' ";
		
					$result=mysqli_query($link,$sql);
		
			
			break;
			case'Group':
					$sql="SELECT group_no FROM group_kind WHERE gender='$gender' AND age_no='$age_no' AND group_type_no='$group_type_no'" ;
		
						$result=mysqli_query($link,$sql);
		
						$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
						$group_no=$row["group_no"];
						//echo $group_no;
			
		//找團體頻率更新
					$sql="SELECT group_fre FROM group_chose WHERE group_no='$group_no' AND activity_no='$activity_no'";	
		
						$result=mysqli_query($link,$sql);
		
						$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
						$group_fre=$row["group_fre"];
						//echo $group_fre;
						$group_fre++;
						//echo $group_fre;
			
		//回傳頻率更新
			
				$sql=" UPDATE group_chose SET group_fre='$group_fre' WHERE group_no='$group_no' AND activity_no='$activity_no'";
		
					$result=mysqli_query($link,$sql);
			
			
			
			
			
			
			
			break;
			case'personalStranger':
					//找出陌生人是哪種人喔
		
				$sql="SELECT person_no FROM person_kind WHERE gender='$gender' AND age_no='$age_no' AND career_no='$career_no'" ;
		
					$result=mysqli_query($link,$sql);
		
					$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
					$person_no=$row["person_no"];
				
				//找出並更新那種人的頻率喔
				
				$sql="SELECT people_fre FROM people_trend WHERE person_no='$person_no' AND activity_no=$activity_no"	;
		
					$result=mysqli_query($link,$sql);
		
					$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
					$people_fre=$row["people_fre"];
				 //echo $people_fre;
					$people_fre++;
					//echo $people_fre;
				//更改回傳喔
		
				$sql="UPDATE people_trend SET people_fre='$people_fre'  WHERE activity_no='$activity_no' AND person_no='$person_no' ";	
					$result=mysqli_query($link,$sql);
				
				break;
		}
	/*function personalUser($user_ID,$activity_no)
	{
		/* Mode
		$user_ID=$_POST["user_ID"];
		
		$activity_no=$_POST["activity_no"];
	
		$user_rate=$_POST["user_rate"]; 
		
		//找出同種人的頻率
		
		
		
		
	}
	function Group($gender,$age_no,$group_type_no,$activity_no)
	{
		/* $Mode
		$gender=$_POST["gender"];
		
	    $age_no=$_POST["age_no"];
		
		$group_type_no=$_POST["group_type_no"];
		
		$activity_no=$_POST["activity_no"];
		
		//找哪種團體
		
		
		
		
		
	}
	function personalStranger($gender,$age_no,$career_no,$activity_no)
	{
		/*$Mode
		$gender=$_POST["gender"];
		
	    $age_no=$_POST["age_no"];
		
		$career_no=$_POST["career_no"];
		
		$activity_no=$_POST["activity_no"];
		
		
				
		
			
		
	}*/
	// 釋放空間
		mysqli_free_result($result);
	
		// 關閉 SQL
		mysqli_close($link);
?>
