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
		//$Mode="personalUser";
		//$activity_no='20';
		
		$user_ID=$_POST["user_ID"];
		//$user_ID='zzzz';
		$rate=$_POST["rate"]; 
		//$rate='100';
		
		$gender=$_POST["gender"];
		//$gender='male';
	    $age_no=$_POST["age_no"];
		
		//$age_no='1';
		$group_type=$_POST["group_type"];
	//$group_type_no='1';
		
	
		
		$career_no=$_POST["career_no"];
		//$career_no='1';
	$currentTime = date("Y-m-d H:i:s" , strtotime("+6 hours"));
	if(isset($Mode))
	{	
		
		switch($Mode)
		{
			case'personalUser':
				$sql="SELECT person_no FROM user  WHERE user_ID='$user_ID'";
			
					$result=mysqli_query($link,$sql);
		
					$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
					$person_no=$row["person_no"];
				
					//echo $person_no;
				
				//改變個人的分喔		
				$sql="UPDATE personal_chose SET user_rate='$rate' WHERE user_ID='$user_ID' AND activity_no='$activity_no' ";
		
					$result=mysqli_query($link,$sql);		
			
			
				//找種類頻分
				$sql="SELECT people_rate FROM people_trend WHERE person_no='$person_no' AND activity_no='$activity_no' ";
		
					$result=mysqli_query($link,$sql);
		
					$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
					$people_rate=$row["people_rate"];
				
					//echo $people_rate;
				
				
					$people_rate+=$rate;
				
					//echo $people_rate;
				
				//改分
				$sql="UPDATE people_trend SET people_rate='$people_rate'  WHERE person_no='$person_no' AND activity_no='$activity_no' ";
		
				$result=mysqli_query($link,$sql);
				$sql="INSERT INTO history (`user_ID`, `activity_no`, `date`, `thoughts`) VALUES ('$user_ID','$activity_no','$currentTime','')";
				mysqli_query($link,$sql);
			break;
			case'Group':
					//找哪種團體
					$sql="SELECT group_type_no FROM group_type_table WHERE group_type='$group_type'";
					
						$result=mysqli_query($link,$sql);
		
						$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
						$group_type_no=$row["group_type_no"];
					
					
					$sql="SELECT group_no FROM group_kind WHERE gender='$gender' AND age_no='$age_no' AND group_type_no='$group_type_no'" ;
		
						$result=mysqli_query($link,$sql);
		
						$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
						$group_no=$row["group_no"];
						
						//echo $group_no;
			
	
					//回傳評分更新
			
					$sql=" UPDATE group_chose SET group_rate='$rate' WHERE group_no='$group_no' AND activity_no='$activity_no' ";
					$result=mysqli_query($link,$sql);
					$sql="INSERT INTO history (`user_ID`, `activity_no`, `date`, `thoughts`) VALUES ('$user_ID','$activity_no','$currentTime','')";
					mysqli_query($link,$sql);
			break;
			case'personalStranger':
					//找出陌生人是哪種人喔
		
					$sql="SELECT person_no FROM person_kind WHERE gender='$gender' AND age_no='$age_no' AND career_no='$career_no'" ;
		
						$result=mysqli_query($link,$sql);
		
						$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
						$person_no=$row["person_no"];
						
						//echo $person_no;
				
					//找出並更新那種人的評分喔
				
					$sql="SELECT people_rate FROM people_trend WHERE person_no='$person_no' AND activity_no='$activity_no'"	;
		
						$result=mysqli_query($link,$sql);
		
						$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
				
						$people_rate=$row["people_rate"];
						
						//echo $people_rate;
						
						$people_rate+=$rate;
						
						//echo $people_rate;
					//更改回傳喔
		
					$sql="UPDATE people_trend SET people_rate='$people_rate'  WHERE activity_no='$activity_no' AND person_no='$person_no' ";	
					$result=mysqli_query($link,$sql);
					$sql="INSERT INTO history (`user_ID`, `activity_no`, `date`, `thoughts`) VALUES ('$user_ID','$activity_no','$currentTime','')";
					mysqli_query($link,$sql);
			break;
		}
	}	
	function personalUser($user_ID,$activity_no,$user_rate)
	{
		/*$user_ID=$_POST["user_ID"];
		
		$activity_no=$_POST["activity_no"];
		
		$user_rate=$_POST["user_rate"]; */
		
		//找出同種人的頻率
		
		
				
				
		
		
		
		
				
				
				
		
		
		
		
		
		
	}
	function Group($gender,$age_no,$group_type_no,$activity_no)
	{
		
		/*$gender=$_POST["gender"];
		
	    $age_no=$_POST["age_no"];
		
		$group_type_no=$_POST["group_type_no"];
		
		$activity_no=$_POST["activity_no"];*/
		
		
			
			
			
			
		
		
		
	}
	function personalStranger($gender,$age_no,$career_no,$activity_no)
	{
		/*$gender=$_POST["gender"];
		
	    $age_no=$_POST["age_no"];
		
		$career_no=$_POST["career_no"];
		
		$activity_no=$_POST["activity_no"];*/
		
		//找出陌生人是哪種人喔
		
		$sql="SELECT person_no FROM person_kind WHERE gender='$gender' AND age_no='$age_no' AND career_no='$career_no'" ;
		
				$result=mysqli_query($link,$sql);
		
				$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
				$person_no=$row["person_no"];
				
		//找出並更新那種人的頻率喔
				
		$sql="SELECT people_fre FROM people_trend WHERE person_no='$person_no'"	;
		
				$result=mysqli_query($link,$sql);
		
				$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
				$people_fre=$row["people_fre"];
				
				$people_fre++;
				
		//更改回傳喔
		
		$sql="UPDATE people_trend SET people_fre='$people_fre'  WHERE activity_no='$activity_no' AND person_no='$person_no' ";	
				$result=mysqli_query($link,$sql);
				
				
		
			
		
	}
	// 釋放空間
		mysqli_free_result($result);
	
		// 關閉 SQL
		mysqli_close($link);
?>
