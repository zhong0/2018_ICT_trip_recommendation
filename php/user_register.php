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
			
	/*	$query="select * FROM pokemonlibrary";
		$result =mysqli_query($link,$query);
		$number_of_rows=mysqli_num_rows($result);
		$temp_array=array();
		if(mysqli_num_rows>0)
		{
			while($row=mysqli_fetch_assoc($result))
			{
				$temp_array[]=$row;
			}
		}
		header('Content-Type:application/json');
		echo  json_encode(array("pokemons"=>$temp_array)); */
		
		$gender=$_POST["gender"];
		
		$age_no=$_POST["age_no"];
		
		$career_no=$_POST["career_no"];
		
		$user_ID=$_POST["user_ID"];
		
		$password=$_POST["password"];					//新變數 記錄使用者的密碼
		
		$question=$_POST["question"];					//新變數 記錄使用者的問題
		
		$forgot_answer=$_POST["forgot_answer"];			//新變數 記錄忘記密碼的答案
		
		/*$gender='male';
		$age_no=2;
		$career_no=1;
		$password='13456';
		$question="GG?";
		$forgot_answer="yes";
		$user_ID='c8763';*/
		
		
		$lastUseTime=date('d H:i:s',strtotime("+6 hours"));
		
		const allActivity_num = 462;
		
		$x="SELECT user_ID FROM user";
		
		$result2=mysqli_query($link,$x);
		
		$y=0;
		
		while( $row2=mysqli_fetch_array($result2))
		{
			if($row2['user_ID']==$user_ID)
			{
				$y++;
				
				
				break;
			}
			
		}
		//var_dump($y);
		if($y == 1){
			$response=array();
			array_push($response, array("checkAccount"=>"alreadyhad"));
			echo JSON_ENCODE( $response , JSON_UNESCAPED_UNICODE);
		}else{
			$response=array();
			array_push($response, array("checkAccount"=>"ok"));
			echo JSON_ENCODE( $response , JSON_UNESCAPED_UNICODE);
		}
		
		//echo $y;
		if($y==0)
		{
			$sql="SELECT person_no FROM person_kind WHERE gender='$gender' AND age_no='$age_no' AND career_no='$career_no'" ;
		
			$result=mysqli_query($link,$sql);
		
			$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
			$person_no=$row["person_no"];
			
			$sql2="INSERT INTO user (user_ID,person_no,password,question,forgot_answer,lastUseTime) values('$user_ID','$person_no','$password','$question','$forgot_answer','$lastUseTime')"; 
			mysqli_query($link,$sql2);
			
			for($G=1;$G<allActivity_num+1;$G++)
			{
				$sql2="INSERT INTO personal_chose(user_ID,activity_no,user_fre,user_rate) values('$user_ID','$G','0','0')"; 
				mysqli_query($link,$sql2);
			}
			
			$sql2="SELECT shop_ID FROM shop";
			$result=mysqli_query($link,$sql2);
			
			while($row=mysqli_fetch_array($result)){
				$sql2="INSERT INTO user_leaf(user_ID,shop_ID,leaf_taken) values('$user_ID','$row[shop_ID]','0')"; 
				mysqli_query($link,$sql2);
			}
			
		}
			

	
		
	
			
		
		
		
	
	// 釋放空間
		mysqli_free_result($result);
		mysqli_free_result($result2);
	
		// 關閉 SQL
		mysqli_close($link);
?>
