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
		
	
			

	
		
	
			
		
		$activity_no=$_POST["activity_no"];
		//$activity_no='420';
	if(isset($activity_no))		
	{	
		$sql="SELECT place_no,item_no FROM all_activity WHERE activity_no='$activity_no'";
		
		$result=mysqli_query($link,$sql);
		
		$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
		$place_no=$row["place_no"];
		
		$item_no=$row["item_no"];
		
		
		
		
		//echo $place_no,$item_no;
		//是否為看展 要改
		if($item_no=='4')
		{
			$sql="SELECT exhib_no,exhib_name,place_no,startDate,endDate,exhib_intro,palce_name,addr,tel,intro
					FROM exhib_detail
					NATURAL JOIN type_place
					WHERE place_no='$place_no'  ORDER BY RAND()";
			$result1 =mysqli_query($link,$sql);
			$response=array();
			while($row=mysqli_fetch_array($result1))
			{
				array_push($response,array("exhib_no"=>$row["exhib_no"],"exhib_name"=>$row["exhib_name"],"place_no"=>$row["place_no"],"startDate"=>$row["startDate"],"endDate"=>$row["endDate"],"exhib_intro"=>$row["exhib_intro"],"palce_name"=>$row["palce_name"],"addr"=>$row["addr"],"tel"=>$row["tel"] ,"intro"=>$row["intro"],"net_rate"=>$row["net_rate"]));
			}
			$sql2="SELECT * FROM type_place WHERE place_no='$place_no'";
			$result2 =mysqli_query($link,$sql2);
			$response1=array();
			while($row=mysqli_fetch_array($result2))
			{
				array_push($response1,array("place_no"=>$row["place_no"],"type_no"=>$row["type_no"],"palce_name"=>$row["palce_name"],"addr"=>$row["addr"],"tel"=>$row["tel"] ,"intro"=>$row["intro"],"net_rate"=>$row["net_rate"]));
			}
			if($response){
				echo json_encode($response,JSON_UNESCAPED_UNICODE);
			}
			else{
				echo  json_encode($response1,JSON_UNESCAPED_UNICODE);
			}
			
		}
		else
			 //等資料
		{  
				$sql="SELECT * FROM type_place WHERE place_no='$place_no'";
				$result1 =mysqli_query($link,$sql);
				$response=array();
				while($row=mysqli_fetch_array($result1))
				{
					array_push($response,array("place_no"=>$row["place_no"],"type_no"=>$row["type_no"],"palce_name"=>$row["palce_name"],"addr"=>$row["addr"],"tel"=>$row["tel"] ,"intro"=>$row["intro"],"net_rate"=>$row["net_rate"]));
				}
			
				echo  json_encode($response,JSON_UNESCAPED_UNICODE); 
				
		}	
		
		
		
	}	
		
	
	
	
	// 釋放空間
		mysqli_free_result($result);
		mysqli_free_result($result2);
		// 關閉 SQL
		mysqli_close($link);
?>
