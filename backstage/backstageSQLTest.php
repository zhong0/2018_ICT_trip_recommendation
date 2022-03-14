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
	$record_per_page = 20;
	if(isset($_GET["Pages"])){
		$Pages = $_GET["Pages"];
		$table = $_GET["cerTable"];
	}else{
		$Pages = 1;
		$table = $_POST["allTable"];
	}
	
	connect_sql();
	$sql = "select * from $table";
	$rows = mysqli_query($link , $sql);
	$num = mysqli_num_rows($rows);
	$total_page = ceil($num/$record_per_page);
	$offset = ($Pages - 1 )*$record_per_page;
	mysqli_data_seek($rows, $offset);
	mysqli_close($link);
?>
<!DOCTYPE html>
<html>
	<head>
		<meta charset = "utf-8"/>
		<title>backstageTest</title>
		<link rel = "stylesheet" type = "text/css" href = "style.css">
	</head>
	<body>
		<form name = "search">
			
		</form>
		<table border = "1">
			<tr>
				<?php
					$whatID = "";
					switch($table){
						case 'age_table':
							echo "<th>age</th>";
							echo "<th>age_no</th>";
							echo "<th>操作</th>";
							$whatID = "age";
							break;
						case 'all_activity':
							echo "<th>activity_no</th>";
							echo "<th>place_no</th>";
							echo "<th>item_no</th>";
							echo "<th>操作</th>";
							$whatID = "activity_no";
							break;
						case 'all_item':
							echo "<th>item_no</th>";
							echo "<th>item</th>";
							echo "<th>操作</th>";
							$whatID = "item_no";
							break;
						case 'all_type':
							echo "<th>act_type_name</th>";
							echo "<th>type_no</th>";
							echo "<th>操作</th>";
							$whatID = "act_type_name";
							break;
						case 'career_table':
							echo "<th>career</th>";
							echo "<th>career_no</th>";
							echo "<th>操作</th>";
							$whatID = "career";
							break;
						case 'exhib_detail':
							echo "<th>exhib_no</th>";
							echo "<th>exhib_name</th>";
							echo "<th>place_no</th>";
							echo "<th>startDate</th>";
							echo "<th>endDate</th>";
							echo "<th>exhib_intro</th>";
							echo "<th>操作</th>";
							$whatID = "exhib_no";
							break;
						case 'gender_table':
							echo "<th>gender</th>";
							echo "<th>操作</th>";
							$whatID = "gender";
							break;
						case 'group_chose':
							echo "<th>group_no</th>";
							echo "<th>activity_no</th>";
							echo "<th>group_fre</th>";
							echo "<th>group_rate</th>";
							echo "<th>操作</th>";
							$whatID = "group_no";
							break;
						case 'group_kind':
							echo "<th>group_no</th>";
							echo "<th>gender</th>";
							echo "<th>age_no</th>";
							echo "<th>group_type_no</th>";
							echo "<th>操作</th>";
							$whatID = "group_no";
							break;
						case 'group_type_table':
							echo "<th>group_type</th>";
							echo "<th>group_type_no</th>";
							echo "<th>操作</th>";
							$whatID = "group_type";
							break;
						case 'people_trend':
							echo "<th>person_no</th>";
							echo "<th>activity_no</th>";
							echo "<th>people_fre</th>";
							echo "<th>people_rate</th>";
							echo "<th>操作</th>";
							$whatID = "person_no";
							break;
						case 'personal_chose':
							echo "<th>user_ID</th>";
							echo "<th>activity_no</th>";
							echo "<th>user_fre</th>";
							echo "<th>user_rate</th>";
							echo "<th>操作</th>";
							$whatID = "user_ID";
							break;
						case 'person_kind':
							echo "<th>person_no</th>";
							echo "<th>gender</th>";
							echo "<th>age_no</th>";
							echo "<th>career_no</th>";
							echo "<th>操作</th>";
							$whatID = "person_no";
							break;
						case 'type_place':
							echo "<th>place_no</th>";
							echo "<th>type_no</th>";
							echo "<th>place_name</th>";
							echo "<th>addr</th>";
							echo "<th>tel</th>";
							echo "<th>intro</th>";
							echo "<th>net_rate</th>";
							echo "<th>操作</th>";
							$whatID = "place_no";
							break;
						case 'user':
							echo "<th>user_ID</th>";
							echo "<th>person_no</th>";
							echo "<th>操作</th>";
							$whatID = "user_ID";
							break;
						default :
							echo "<th>Error</th>";
					}
				?>
			</tr>
			<?php
				if($num > 0){
					$i = 1;
					while($row = mysqli_fetch_row($rows) and $i <= $record_per_page){
						echo "<tr>";
						foreach($row as $value){
							echo "<td>".$value."</td>";
						}
						echo "<td><a href = 'backstageDelete.php?wid=".$whatID."&dtable=".$table."&id=".$row[0]."'>刪除</a>
						<a href = 'backstageUpdate.php?wid=".$whatID."&dtable=".$table."&id=".$row[0]."'>編輯</a></td>";
						$i++;
						echo "</tr>";
					}
				}
				mysqli_free_result($rows);
			?>
		</table>
		<form method = "post" action = "backstageMainPage.php">
			<input type = "submit" name = "toMain" id = "toMain" value = "返回首頁">
		</form>
		<?php
			if($Pages > 1){
				echo "<a href='backstageSQLTest.php?cerTable=".$table."&Pages=".($Pages-1)."'>上一頁</a>|";
			}
			for($i=1 ; $i < $total_page ; $i++){
				if($i != $Pages){
					echo "<a href='backstageSQLTest.php?cerTable=".$table."&Pages=".$i."'> $i </a>";
				}
			}
			if($Pages < $total_page){
				echo "|<a href='backstageSQLTest.php?cerTable=".$table."&Pages=".($Pages+1)."'>下一頁</a> ";
			}
		?>
	</body>
</html>