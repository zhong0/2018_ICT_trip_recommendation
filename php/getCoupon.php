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
	$cou_no=$_POST['cou_no'];
	//$shop_ID=$_POST['shop_ID'];
	$leafLeft=$_POST['leafLeft'];	
	
	/*$user_ID="c8763";
	$cou_no=1;
	$leafLeft='1000';*/
	//$shop_ID='akDlkf.e3945cldOjdsna948adiekc;';*/
	
	$check=0;
	$checkExist=0;
	$mcouponLeft=0;
	$couponLeft=0;
	
	$sql="SELECT cou_no, cou_num FROM coupon WHERE cou_no='$cou_no'";
	$result = mysqli_query($link,$sql);
	$row=mysqli_fetch_array($result);
	if($row){
		$check=1;											//check whether exist cou_no of coupon
		$couponLeft=$row['cou_num']-1;
	}
	/*$sql="SELECT * FROM my_coupon WHERE user_ID = '$user_ID' AND cou_no = '$cou_no'";
	$result=mysqli_query($link,$sql);
	$row2=mysqli_fetch_array($result);
	if($row2){
		$checkExist=1;
		//$mcouponLeft=$row2['coupon_num']+1;
	}*/
	
	if(isset($user_ID) && $check==1 && isset($leafLeft)){									// 數量夠 可以換
		/*if($checkExist==1){
			$sql="UPDATE coupon SET cou_num = $couponLeft WHERE cou_no = '$cou_no'";
			mysqli_query($link,$sql);
			/*$sql="UPDATE my_coupon SET coupon_num = $mcouponLeft WHERE user_ID = '$user_ID' AND cou_no = '$cou_no'";			
			mysqli_query($link,$sql);
			$sql2="UPDATE user SET leaf_num = $leafLeft  WHERE user_ID = '$user_ID'";			//更新擁有數量
			mysqli_query($link,$sql2);
			//echo 2;
			$response=array();
			array_push($response, array("check_buy"=>"1"));
			echo json_encode($response,JSON_UNESCAPED_UNICODE);
		}
		else{*/
			$sql="SELECT cou_ID FROM all_coupon WHERE cou_no = '$cou_no' AND send != 1";
			$result=mysqli_query($link,$sql);
			if($row=mysqli_fetch_array($result)){
				$cou_ID=$row['cou_ID'];
				$sql="UPDATE all_coupon SET send = 1,user_ID = '$user_ID' WHERE cou_ID='$cou_ID'";
				mysqli_query($link,$sql);
				$sql="UPDATE coupon SET cou_num = $couponLeft WHERE cou_no = '$cou_no'";
				mysqli_query($link,$sql);
				$sql="INSERT INTO my_coupon(user_ID,cou_no,cou_ID) VALUES ('$user_ID','$cou_no','$cou_ID')";			
				mysqli_query($link,$sql);
				$sql2="UPDATE user SET leaf_num = $leafLeft  WHERE user_ID = '$user_ID'";			//更新擁有數量
				mysqli_query($link,$sql2);
				//echo 1;																				//success
				$response=array();
				array_push($response, array("check_buy"=>"1"));
				echo json_encode($response,JSON_UNESCAPED_UNICODE);
			}
			else{
				$response=array();
				array_push($response, array("check_buy"=>"0"));
				echo json_encode($response,JSON_UNESCAPED_UNICODE);
			}
			
			
		//}
		
	}
	else{
		//echo 0;																				//leaf_num is not enough
		$response=array();
		array_push($response, array("check_buy"=>"0"));
		echo json_encode($response,JSON_UNESCAPED_UNICODE);
	}
	
	// 釋放空間
	mysqli_free_result($result);
	
	
	
	/*if(isset($user_ID)&&isset($cou_no)&&isset($leafLeft)){
		/*$sql="SELECT leaves_amount FROM coupon WHERE cou_no='$cou_no'";
		$result=mysqli_query($link,$sql);
		$row=mysqli_fetch_array($result);
		$sql2="SELECT leaf_num FROM user WHERE user_ID='$user_ID'";
		$result2=mysqli_query($link,$sql2);
		$row2=mysqli_fetch_array($result2);
		
		if($row2['leaf_num']-$row['leaves_amount'] >=0){								// 數量夠 可以換
			$sql="INSERT INTO my_coupon(user_ID,cou_no) VALUES ('$user_ID','$cou_no')";			
			mysqli_query($link,$sql);
			//$leafLeft=$row2['leaf_num']-$row['leaves_amount'];
			$sql2="UPDATE user SET leaf_num = $leafLeft  WHERE user_ID = '$user_ID'";			//更新擁有數量
			mysqli_query($link,$sql2);
			echo 1;																				//success
		}
		else{
			echo 0;																				//leaf_num is not enough
		}
	}
	else{
		echo 0;																				//leaf_num is not enough
	}
	
	// 釋放空間
	mysqli_free_result($result);
	mysqli_free_result($result2);*/
	
	
	// 關閉 SQL
	mysqli_close($link);
?>