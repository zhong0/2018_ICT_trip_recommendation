<!DOCTYPE html>
<html>
	<head>
		<meta charset = "utf-8"/>
		<title>MainPage</title>
		<style>
			.chartSearchButton { font-family: fantasy ;border-radius: 10px; background-color: rgba(255, 255, 255, 0.76);
								box-shadow: 3px 5px 10px gray;text-shadow: 2px 4px 10px #999999; font-size: 20px; font-weight: bold; color: #696969;}
			.background {background-image: linear-gradient(30deg,#81C7D4,#F596AA);background-image: url('4471.jpg') ;text-align: center; background-repeat: repeat; padding: 100px 250px;  background-color: #81C7D4;}
			.text				{font-size: 20px;font-weight: bold;text-shadow: 2px 4px 10px #999999;color: #696969;}
			.selectText			{font-size: 20px;font-weight: bold;text-shadow: 2px 4px 10px #999999;color: #696969;
								box-shadow: 3px 5px 10px #999999;}
			.box 				{box-shadow: 3px 5px 10px #999999;}
			.boxCenter			{box-shadow: 3px 5px 10px #999999; margin:30px auto;}
			.paddingauto		{padding: 5.6% 250px;}
		</style>
	</head>
	<body>
		<div class="background">
			<form class = "paddingauto" method = "post" action = "backstageSQLTest.php">
				<label class="text" for = "allTable" style = "text-size:50px">後臺查詢系統</label>
				<select class="selectText" name = "allTable" id = "allTable">
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
				<input class = "chartSearchButton" type= "submit" name = "send" id = "send" value = "查詢表格">
			</form>
			<form class = "paddingauto" method = "post" action = "backstageInsertTest.php">
				<input class = "chartSearchButton" type = "submit" name = "goInsert" id = "goInsert" value = "新增資料">
			</form>
			<form class = "paddingauto" method = "post" action = "testChart.php">
				<input class = "chartSearchButton" type = "submit" name = "goChart" id = "goChart" value = "地點分析圖表">
			</form>
			<form class = "paddingauto" method = "post" action = "couponChart.php">
				<input class = "chartSearchButton" type = "submit" name = "goChart" id = "goChart" value = "商家優惠券分析圖表">
			</form>
		</div>
	</body>
</html>