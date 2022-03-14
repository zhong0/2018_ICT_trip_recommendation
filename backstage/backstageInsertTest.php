<?php
	function connect_sql() {
		global $link;
		$hostname= "localhost";		// 主機名稱
		$username= "root";			// 資料庫登入帳號
		$password= "k50510e04";		// 資料庫登入密碼
		$database= "finaldb";		// 資料庫名稱
		$link = mysqli_connect( $hostname , $username , $password );
		// 改為 UTF8 以免亂碼
		mysqli_query($link, "SET NAMES 'UTF8'");
		mysqli_select_db($link, $database) or die("無法選擇資料庫");
	}
	connect_sql();
	if(isset($_POST["sendInsert"])){
		$insert = $_POST["Select"];
		$coloumn = $_POST["colum"];
		$VALUE = $_POST["Dvalue"];
		$sql = "insert into $insert $coloumn values $VALUE";
		if(!mysqli_query($link , $sql)){
			$resault = "新增失敗";
		}else{
			$resault = "新增成功";
		}
		mysqli_close($link);
	}
?>
<!DOCTYPE html>
<html>
	<head>
		<meta charset = "utf-8">
		<title>backstageInsertTest</title>
	</head>
	<body>
		<h2 style = "text-align:center">新增資料</h2>
		<form method = "post" action = "" name = "myform">
			<select name = "Select">
				<option value = "age_table">age_table</option>
				<option value = "all_activity">all_activity</option>
				<option value = "all_item">all_item</option>
				<option value = "all_type">all_type</option>
				<option value = "career_table">career_table</option>
				<option value = "exhib_detail">exhib_detail</option>
				<option value = "gender_table">gender_table</option>
				<option value = "group_chose">group_chose</option>
				<option value = "group_kind">group_kind</option>
				<option value = "group_type_table">group_type_table</option>
				<option value = "people_trend">people_trend</option>
				<option value = "personal_chose">personal_chose</option>
				<option value = "person_kind">person_kind</option>
				<option value = "type_place">type_place</option>
				<option value = "user">user</option>
			</select>
			<select name = "colum">
				<option value = "(`age`,`age_no`)">(`age`,`age_no`)</option>
				<option value = "(`activity_no`,`place_no`,`item_no`)">(`activity_no`,`place_no`,`item_no`)</option>
				<option value = "(`item_no`,`item`)">(`item_no`,`item`)</option>
				<option value = "(`act_type_name`,`type_no`)">(`act_type_name`,`type_no`)</option>
				<option value = "(`career`,`career_no`)">(`career`,`career_no`)</option>
				<option value = "(`exhib_no`,`exhib_name`,`place_no`,`startDate`,`endDate`,`exhib_intro`)">(`exhib_no`,`exhib_name`,`place_no`,`startDate`,`endDate`,`exhib_intro`)</option>
				<option value = "(`gender`)">(`gender`)</option>
				<option value = "(`group_no`,`activity_no`,`group_fre`,`group_rate`)">(`group_no`,`activity_no`,`group_fre`,`group_rate`)</option>
				<option value = "(`group_no`,`gender`,`age_no`,`group_type_no`)">(`group_no`,`gender`,`age_no`,`group_type_no`)</option>
				<option value = "(`group_type`,`group_type_no`)">(`group_type`,`group_type_no`)</option>
				<option value = "(`person_no`,`activity_no`,`people_fre`,`people_rate`)">(`person_no`,`activity_no`,`people_fre`,`people_rate`)</option>
				<option value = "(`user_ID`,`activity_no`,`user_fre`,`user_rate`)">(`user_ID`,`activity_no`,`user_fre`,`user_rate`)</option>
				<option value = "(`person_no`,`gender`,`age_no`,`career_no`)">(`person_no`,`gender`,`age_no`,`career_no`)</option>
				<option value = "(`place_no`,`typoe_no`,`palce_name`,`addr`,`tel`,`intro`,`net_rate`)">(`place_no`,`typoe_no`,`palce_name`,`addr`,`tel`,`intro`,`net_rate`)</option>
				<option value = "(`user_ID`,`person_no`)">(`user_ID`,`person_no`)</option>
			</select>
			<input type = "text" name = "Dvalue">
			<input type = "submit" name = "sendInsert" id = "sendInsert" value = "新增資料">
		</form>
		<form method = "post" action = "backstageMainPage.php">
			<input type = "submit" name = "toMain" id = "toMain" value = "返回首頁">
		</form>
		<?php
			if(isset($resault)){
				if($resault == "新增失敗")
					echo "<p>新增狀態：".$resault."</p>";
				else{
					echo "<p>新增狀態：".$resault."</p>";
					echo "<p>".$sql."</p>";
				}
			}
			
		?>
	</body>
</html>