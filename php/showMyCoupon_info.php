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
	
	//$user_ID="c8763";
	
	
	$response=array();
	$sql="SELECT palce_name, cou_intro, cou_deadline, cou_no, cou_ID FROM my_coupon NATURAL JOIN coupon NATURAL JOIN type_place NATURAL JOIN all_coupon WHERE user_ID='$user_ID' AND used = 0";
	$result=mysqli_query($link,$sql);
	while($row=mysqli_fetch_array($result)){
		//for($i=0;$i<$row['coupon_num'];$i++){
			array_push($response , array("palce_name"=>$row['palce_name'],"cou_intro"=>$row['cou_intro'],"cou_deadline"=>$row['cou_deadline'],"cou_no"=>$row['cou_no'],"cou_ID"=>$row['cou_ID']));
		//}
		
	}
	echo json_encode($response , JSON_UNESCAPED_UNICODE);
	
	// 釋放空間
	mysqli_free_result($result);
	
	// 關閉 SQL
	mysqli_close($link);
?>