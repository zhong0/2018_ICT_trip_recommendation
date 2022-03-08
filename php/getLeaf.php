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
	$user_ID=$_POST['user_ID'];
	$shop_ID=$_POST['shop_ID'];
	
	/*$user_ID="c8763";
	$shop_ID="akDlkf.e3945cldOjdsna948adiekc;";*/
	
	$lastUseTime=date('d H:i:s',strtotime("+6 hours"));
	
	if(isset($user_ID)&&isset($shop_ID)){
		$sql="SELECT leaf_taken , leaf_limit FROM user_leaf NATURAL JOIN shop WHERE user_ID = '$user_ID' AND shop_ID = '$shop_ID'";
		$result=mysqli_query($link,$sql);
		if($row=mysqli_fetch_array($result)){
			if($row['leaf_taken']==1){
				echo 2;						//repeat
			}
			else{
				if($row['leaf_limit']>0){
					$sql2="UPDATE user_leaf SET leaf_taken = 1 WHERE user_ID = '$user_ID' AND shop_ID = '$shop_ID'";				//找user 並增加leaf_num
					mysqli_query($link,$sql2);
					$sql3="SELECT leaf_num FROM user WHERE user_ID = '$user_ID'";
					$result2=mysqli_query($link,$sql3);
					$row2 = mysqli_fetch_array($result2);
					$leafPlus=$row2['leaf_num']+1;
					$sql2="UPDATE user SET lastUseTime = '$lastUseTime' , leaf_num = $leafPlus  WHERE user_ID = '$user_ID'";		
					mysqli_query($link,$sql2);
					$sql4="SELECT leaf_limit FROM shop WHERE shop_ID = '$shop_ID'";												//找shop並減少leaf_limit
					$result3=mysqli_query($link,$sql4);
					$row3 = mysqli_fetch_array($result3);
					$leafMinus=$row3['leaf_limit']-1;
					$sql2="UPDATE shop SET leaf_limit = $leafMinus  WHERE shop_ID = '$shop_ID'";
					mysqli_query($link,$sql2);
					echo 1;     				//success
				}
				else{
					echo 3;						//no leaf left
				}					
			}
		}
		else{
			echo 0;							//user_ID or shop_ID error
		}
	}
	
	
	// 釋放空間
	mysqli_free_result($result);
	
	// 關閉 SQL
	mysqli_close($link);
?>