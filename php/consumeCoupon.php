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
	
	$user_ID=$_POST['user_ID'];
	$cou_ID=$_POST['cou_ID'];
	$shop_ID=$_POST['shop_ID'];
	
	/*$user_ID="c8763";
	$cou_ID='akD2';
	$shop_ID='z;dkfMmocjEb7w2msdj1qeIrpl';*/
	//$shop_ID='akDlkf.e3945cldOjdsna948adiekc;';
	
	$checkDelete=0;
	$check=0;
	
	$sql = "SELECT shop_ID FROM all_coupon WHERE shop_ID = '$shop_ID' AND cou_ID = '$cou_ID'";
	$result = mysqli_query($link,$sql);
	$row=mysqli_fetch_array($result);
	if($row){
		$check++;																			//check whether exist shop_ID of all_coupon
	}
	$sql2="SELECT cou_ID FROM my_coupon WHERE user_ID = '$user_ID' AND cou_ID='$cou_ID'";
	$result2 = mysqli_query($link,$sql2);
	$row=mysqli_fetch_array($result2);
	if($row){
		$check++;																			//check whether exist cou_no of my_coupon
	}
	
	/*$sql="SELECT coupon_num FROM my_coupon WHERE user_ID = '$user_ID' AND cou_no = '$cou_no'";
	$result=mysqli_query($link,$sql);
	$row=mysqli_fetch_array($result);
	if($row){
		$couponLeft=$row['coupon_num']-1;
		if($couponLeft==0){
			$checkDelete=1;
		}
	}*/
	
	if(isset($user_ID)&&$check==2){
		//if($checkDelete==1){
			$sql="UPDATE all_coupon SET used=1 WHERE cou_ID='$cou_ID'";
			mysqli_query($link,$sql);
			//echo 1;
			$response=array();
			array_push($response, array("check_consume"=>"1"));
			echo json_encode($response,JSON_UNESCAPED_UNICODE);
		/*}
		else{
			$sql="UPDATE my_coupon SET coupon_num = $couponLeft WHERE user_ID = '$user_ID' AND cou_no = '$cou_no'";			
			mysqli_query($link,$sql);
			echo 1;
		}*/	
	}
	else{
		//echo 0;
		$response=array();
		array_push($response, array("check_consume"=>"0"));
		echo json_encode($response,JSON_UNESCAPED_UNICODE);
	}
	
	/*$response=array();
	while($row=mysqli_fetch_array($result)){
		
		array_push($response , array("palce_name"=>$row['palce_name'],"cou_intro"=>$row['cou_intro'],"cou_deadline"=>$row['cou_deadline']));
	}
	echo json_encode($response , JSON_UNESCAPED_UNICODE);*/
	
	// 釋放空間
	mysqli_free_result($result);
	
	// 關閉 SQL
	mysqli_close($link);
?>