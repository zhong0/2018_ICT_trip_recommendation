<?php
	error_reporting(E_ERROR);	//不希望顯示錯誤提示
	include("condb.php");
	$link=connect_sql();
	
	$select=$_GET['select'];
	$place_name=$_POST['place_name'];
	$cou_intro=$_POST['cou_intro'];
	
	$genNum=array(0,0);
	$ugenNum=array(0,0);
	$carNum=array(0,0,0,0,0,0,0,0,0,0,0);
	$ucarNum=array(0,0,0,0,0,0,0,0,0,0,0);
	$ageNum=array(0,0,0,0,0,0,0);
	$uageNum=array(0,0,0,0,0,0,0);
	$sql="SELECT palce_name FROM type_place WHERE place_no = '$place_name'";
	$result=mysqli_query($link,$sql);
	$row=mysqli_fetch_array($result);
	$plc_name=$row['palce_name'];
	////持有
	$sql = "SELECT person_kind.person_no,person_kind.gender,person_kind.age_no,person_kind.career_no,my_coupon.cou_ID FROM shop NATURAL JOIN all_coupon NATURAL JOIN my_coupon NATURAL JOIN person_kind NATURAL JOIN user NATURAL JOIN coupon WHERE shop.shop_ID=all_coupon.shop_ID AND all_coupon.send = 1 AND all_coupon.used = 0 AND all_coupon.cou_ID=my_coupon.cou_ID AND person_kind.person_no=user.person_no AND shop.place_no = $place_name AND coupon.cou_intro = '$cou_intro' AND person_kind.gender= 'female'";
	$rows1 = mysqli_query($link , $sql);
	$sql="SELECT person_kind.person_no,person_kind.gender,person_kind.age_no,person_kind.career_no,my_coupon.cou_ID FROM shop NATURAL JOIN all_coupon NATURAL JOIN my_coupon NATURAL JOIN person_kind NATURAL JOIN user NATURAL JOIN coupon WHERE shop.shop_ID=all_coupon.shop_ID AND all_coupon.send = 1 AND all_coupon.used = 0 AND all_coupon.cou_ID=my_coupon.cou_ID AND person_kind.person_no=user.person_no AND shop.place_no = $place_name AND coupon.cou_intro = '$cou_intro' AND person_kind.gender= 'male'";
	$rows2=mysqli_query($link,$sql);			//性別sql
	$i=0;
	while($i<mysqli_num_rows($rows1)){
		$genNum[0]++;
		$i++;
	}
	$i=0;
	while($i<mysqli_num_rows($rows2)){
		$genNum[1]++;
		$i++;
	}
	for($i=0;$i<11;$i++){						//職業與年齡sql
		switch($i){
			case 0:{
				$career_no=1;
				$group_type_no=1;
				$age_no=1;
				break;
			}
			case 1:{
				$career_no=2;
				$group_type_no=2;
				$age_no=2;
				break;
			}
			case 2:{
				$career_no=3;
				$group_type_no=3;
				$age_no=3;
				break;
			}
			case 3:{
				$career_no=4;
				$group_type_no=4;
				$age_no=4;
				break;
			}
			case 4:{
				$career_no=5;
				$age_no=5;
				break;
			}
			case 5:{
				$career_no=6;
				$age_no=6;
				break;
			}
			case 6:{
				$career_no=7;
				$age_no=7;
				break;
			}
			case 7:{
				$career_no=8;
				break;
			}
			case 8:{
				$career_no=9;
				break;
			}
			case 9:{
				$career_no=10;
				break;
			}
			case 10:{
				$career_no=11;
				break;
			}
		}
		$sql="SELECT person_kind.person_no,person_kind.gender,person_kind.age_no,person_kind.career_no,my_coupon.cou_ID FROM shop NATURAL JOIN all_coupon NATURAL JOIN my_coupon NATURAL JOIN person_kind NATURAL JOIN user NATURAL JOIN coupon WHERE shop.shop_ID=all_coupon.shop_ID AND all_coupon.send = 1 AND all_coupon.used = 0 AND all_coupon.cou_ID=my_coupon.cou_ID AND person_kind.person_no=user.person_no AND shop.place_no = $place_name AND coupon.cou_intro = '$cou_intro' AND person_kind.career_no= '$career_no'";
		$result=mysqli_query($link,$sql);							//person career sql
		$j=0;
		while($j<mysqli_num_rows($result)){
			$carNum[$i]++;
			$j++;
		}
		if($i<7){
			$sql="SELECT person_kind.person_no,person_kind.gender,person_kind.age_no,person_kind.career_no,my_coupon.cou_ID FROM shop NATURAL JOIN all_coupon NATURAL JOIN my_coupon NATURAL JOIN person_kind NATURAL JOIN user NATURAL JOIN coupon WHERE shop.shop_ID=all_coupon.shop_ID AND all_coupon.send = 1 AND all_coupon.used = 0 AND all_coupon.cou_ID=my_coupon.cou_ID AND person_kind.person_no=user.person_no AND shop.place_no = $place_name AND coupon.cou_intro = '$cou_intro' AND person_kind.age_no= '$age_no'";
			$result=mysqli_query($link,$sql);						//person age sql
			$j=0;
			while($j<mysqli_num_rows($result)){
				$ageNum[$i]++;
				$j++;
			}
		}
	}
	////使用
	$sql = "SELECT person_kind.person_no,person_kind.gender,person_kind.age_no,person_kind.career_no,my_coupon.cou_ID FROM shop NATURAL JOIN all_coupon NATURAL JOIN my_coupon NATURAL JOIN person_kind NATURAL JOIN user NATURAL JOIN coupon WHERE shop.shop_ID=all_coupon.shop_ID AND all_coupon.send = 1 AND all_coupon.used = 1 AND all_coupon.cou_ID=my_coupon.cou_ID AND person_kind.person_no=user.person_no AND shop.place_no = $place_name AND coupon.cou_intro = '$cou_intro' AND person_kind.gender= 'female'";
	$rows1 = mysqli_query($link , $sql);
	$sql="SELECT person_kind.person_no,person_kind.gender,person_kind.age_no,person_kind.career_no,my_coupon.cou_ID FROM shop NATURAL JOIN all_coupon NATURAL JOIN my_coupon NATURAL JOIN person_kind NATURAL JOIN user NATURAL JOIN coupon WHERE shop.shop_ID=all_coupon.shop_ID AND all_coupon.send = 1 AND all_coupon.used = 1 AND all_coupon.cou_ID=my_coupon.cou_ID AND person_kind.person_no=user.person_no AND shop.place_no = $place_name AND coupon.cou_intro = '$cou_intro' AND person_kind.gender= 'male'";
	$rows2=mysqli_query($link,$sql);			//性別sql
	$i=0;
	while($i<mysqli_num_rows($rows1)){
		$ugenNum[0]++;
		$i++;
	}
	$i=0;
	while($i<mysqli_num_rows($rows2)){
		$ugenNum[1]++;
		$i++;
	}
	for($i=0;$i<11;$i++){						//職業與年齡sql
		switch($i){
			case 0:{
				$career_no=1;
				$group_type_no=1;
				$age_no=1;
				break;
			}
			case 1:{
				$career_no=2;
				$group_type_no=2;
				$age_no=2;
				break;
			}
			case 2:{
				$career_no=3;
				$group_type_no=3;
				$age_no=3;
				break;
			}
			case 3:{
				$career_no=4;
				$group_type_no=4;
				$age_no=4;
				break;
			}
			case 4:{
				$career_no=5;
				$age_no=5;
				break;
			}
			case 5:{
				$career_no=6;
				$age_no=6;
				break;
			}
			case 6:{
				$career_no=7;
				$age_no=7;
				break;
			}
			case 7:{
				$career_no=8;
				break;
			}
			case 8:{
				$career_no=9;
				break;
			}
			case 9:{
				$career_no=10;
				break;
			}
			case 10:{
				$career_no=11;
				break;
			}
		}
		$sql="SELECT person_kind.person_no,person_kind.gender,person_kind.age_no,person_kind.career_no,my_coupon.cou_ID FROM shop NATURAL JOIN all_coupon NATURAL JOIN my_coupon NATURAL JOIN person_kind NATURAL JOIN user NATURAL JOIN coupon WHERE shop.shop_ID=all_coupon.shop_ID AND all_coupon.send = 1 AND all_coupon.used = 1 AND all_coupon.cou_ID=my_coupon.cou_ID AND person_kind.person_no=user.person_no AND shop.place_no = $place_name AND coupon.cou_intro = '$cou_intro' AND person_kind.career_no= '$career_no'";
		$result=mysqli_query($link,$sql);							//person career sql
		$j=0;
		while($j<mysqli_num_rows($result)){
			$ucarNum[$i]++;
			$j++;
		}
		if($i<7){
			$sql="SELECT person_kind.person_no,person_kind.gender,person_kind.age_no,person_kind.career_no,my_coupon.cou_ID FROM shop NATURAL JOIN all_coupon NATURAL JOIN my_coupon NATURAL JOIN person_kind NATURAL JOIN user NATURAL JOIN coupon WHERE shop.shop_ID=all_coupon.shop_ID AND all_coupon.send = 1 AND all_coupon.used = 1 AND all_coupon.cou_ID=my_coupon.cou_ID AND person_kind.person_no=user.person_no AND shop.place_no = $place_name AND coupon.cou_intro = '$cou_intro' AND person_kind.age_no= '$age_no'";
			$result=mysqli_query($link,$sql);						//person age sql
			$j=0;
			while($j<mysqli_num_rows($result)){
				$uageNum[$i]++;
				$j++;
			}
		}
	}
?>
<!DOCTYPE html>
<html>
	<head>
		<meta charset = "utf-8"/>
		<title>testChar</title>
		<script src="https://code.highcharts.com/highcharts.js"></script>
		<script src="http://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
		<style>
			.chartSearchButton { font-family: fantasy ;border-radius: 10px; background-color: rgba(255, 255, 255, 0.76);
								box-shadow: 3px 5px 10px gray; text-shadow: 2px 4px 10px #999999; font-size: 20px; font-weight: bold; color: #696969;}
			.background {background-image: linear-gradient(30deg,#81C7D4,#F596AA); background-image: url('4471.jpg'); text-align: center; background-repeat: repeat; }
			.text				{font-size: 20px;font-weight: bold;text-shadow: 2px 4px 10px #999999;color: #696969;}
			.selectText			{font-size: 20px;font-weight: bold;text-shadow: 2px 4px 10px #999999;color: #696969;
								box-shadow: 3px 5px 10px #999999;}
			.box 				{box-shadow: 3px 5px 10px #999999;}
			.boxCenter			{box-shadow: 3px 5px 10px #999999; margin:30px auto;}
			.boxCenterMx			{box-shadow: 3px 5px 10px #999999; margin:30px 25%;}
			.paddingauto		{padding: auto;}
			@import 'https://code.highcharts.com/css/highcharts.css';

			#container {
						height: 400px;
						max-width: 800px;
						margin: 0 auto;
						}
			.highcharts-title {
						fill: #434348;
						font-weight: bold;
						letter-spacing: 0.3em;
						font-size: 3em;
						}
			.highcharts-subtitle {
						font-family: 'Courier New', monospace;
						font-style: italic;
						fill: #7cb5ec;
						}
		</style>
	</head>
	<body>
		<script type="text/JavaScript">
		var num1 = 0;
		num1 = <?php echo $genNum[0]?>;
		var num2=0;
		num2 = <?php echo $genNum[1]?>;
		var unum1 = <?php echo $ugenNum[0]?>;
		var unum2 = <?php echo $ugenNum[1]?>;
		function chart1(){
			var myChart = Highcharts.chart('container', {
			chart: {
				type: 'bar',
				backgroundColor: '#ffb5b5',
				
				zoomType: 'xy'
			},
			title: {
				text: "<?php echo $plc_name?>"+"與性別來訪次數統計圖"
			},
			xAxis: {
				categories: <?php echo "['女性', '男性']"?>
				,
				lineWidth: 2,
				lineColor: '#8e8e8e',
				tickWidth: 2,
				tickColor: '#8e8e8e',
				labels: {
					style: {
						color: 'black',
						fontSize:'15px',
						fontWeight:'bold'
					}
				}
			},
			yAxis: {
				allowDecimals: false,
				title: {
					text: '數量',
					style: {
						color: 'black',
						fontSize:'15px',
						fontWeight:'bold'
					}
				},
				lineWidth: 2,
				lineColor: '#8e8e8e',
				tickWidth: 2,
				tickColor: '#8e8e8e',
				gridLineWidth: 2,
				gridLineColor: '#8e8e8e',
				labels: {
					style: {
						color: 'black',
						fontSize:'15px',
						fontWeight:'bold'
					}
				}
			},
			series: [{
				name: '持有',
				data: [num1, num2]
				}, {
				name: '使用',
				data: [unum1, unum2]
					}/*,{
				name: '文',
				data: [5,5,6,6]
					},{
				name: '閒',
				data: [6,5,5,8]
					},{
				name: '逛',
				data: [7,7,6,4]
					}*/]
			});
		}
		function chart2(){
			var myChart = Highcharts.chart('container2', {
			chart: {
				type: 'bar',
				backgroundColor: '#ffd1a4',
				zoomType: 'xy'
			},
			title: {
				text: "<?php echo $plc_name?>"+"與職業來訪次數統計圖"
			},
			xAxis: {
				categories: <?php echo "['科技人', '工業工程人', '設計人', '美妝美髮人', '醫療人', '餐飲人', '保險人', '商管人', '軍公教人', '學生', '其他']"?>
				,
				lineWidth: 2,
				lineColor: '#8e8e8e',
				tickWidth: 2,
				tickColor: '#8e8e8e',
				labels: {
					style: {
						color: 'black',
						fontSize:'15px',
						fontWeight:'bold'
					}
				}
			},
			yAxis: {
				allowDecimals: false,
				title: {
					text: '數量',
					style: {
						color: 'black',
						fontSize:'15px',
						fontWeight:'bold'
					}
				},
				lineWidth: 2,
				lineColor: '#8e8e8e',
				tickWidth: 2,
				tickColor: '#8e8e8e',
				gridLineWidth: 2,
				gridLineColor: '#8e8e8e',
				labels: {
					style: {
						color: 'black',
						fontSize:'15px',
						fontWeight:'bold'
					}
				}
			},
			series: [{
				name: '持有',
				data: <?php 
						$data="[";
						for($i=0;$i<7;$i++){
							if($i<6)
								$data=$data.$carNum[$i].",";
							else
								$data.=$carNum[$i];
						}
						$data=$data."]";
						echo $data;
					?>
				}, {
				name: '使用',
				data: <?php 
						$data="[";
						for($i=0;$i<7;$i++){
							if($i<6)
								$data=$data.$ucarNum[$i].",";
							else
								$data.=$ucarNum[$i];
						}
						$data=$data."]";
						echo $data;
					?>
					}/*,{
				name: '文',
				data: [5,5,6,6]
					},{
				name: '閒',
				data: [6,5,5,8]
					},{
				name: '逛',
				data: [7,7,6,4]
					}*/]
			});
		}
		function chart3(){
			var myChart = Highcharts.chart('container3', {
			chart: {
				type: 'bar',
				backgroundColor: '#ffed97',
				zoomType: 'xy'
			},
			title: {
				text: "<?php echo $plc_name?>"+"與年齡來訪次數統計圖"
			},
			xAxis: {
				categories: <?php echo "['未滿18歲', '18-25歲', '26-35歲', '36-45歲', '46-55歲', '56-65歲', '66歲以上']"?>
				,
				lineWidth: 2,
				lineColor: '#8e8e8e',
				tickWidth: 2,
				tickColor: '#8e8e8e',
				labels: {
					style: {
						color: 'black',
						fontSize:'15px',
						fontWeight:'bold'
					}
				}
			},
			yAxis: {
				allowDecimals: false,
				title: {
					text: '數量',
					style: {
						color: 'black',
						fontSize:'15px',
						fontWeight:'bold'
					}
				},
				lineWidth: 2,
				lineColor: '#8e8e8e',
				tickWidth: 2,
				tickColor: '#8e8e8e',
				gridLineWidth: 2,
				gridLineColor: '#8e8e8e',
				labels: {
					style: {
						color: 'black',
						fontSize:'15px',
						fontWeight:'bold'
					}
				}
			},
			series: [{
				name: '持有',
				data: <?php 
						$data="[";
						for($i=0;$i<7;$i++){
							if($i<6)
								$data=$data.$ageNum[$i].",";
							else
								$data.=$ageNum[$i];
						}
						$data=$data."]";
						echo $data;
					?>
				},{
				name: '使用',
				data: <?php 
						$data="[";
						for($i=0;$i<7;$i++){
							if($i<6)
								$data=$data.$uageNum[$i].",";
							else
								$data.=$uageNum[$i];
						}
						$data=$data."]";
						echo $data;
					?>
				}]
			});
		}
		function selectCoupon(){
			//var value=document.getElementById("place_name");
			var index = document.getElementById("place_name").selectedIndex;
			var  currSelectValue  =  document.getElementById("place_name").options[index].value;
			//document.writeln("<p>"+currSelectValue+"</p>");
			location.href="couponChart.php?select="+currSelectValue;
		}
		window.addEventListener("load" , chart1, false);
		window.addEventListener("load" , chart2, false);
		window.addEventListener("load" , chart3, false);
		</script>
		<div class = "background">
		<form method = "post" action = "couponChart.php">
				<label for = "allplace" class = "text">地點</label>
				<select class = "selectText" name = "place_name" id = "place_name" onChange = "selectCoupon()">
					<?php 
						$sql="SELECT palce_name FROM type_place WHERE place_no = '$select'";
						$result=mysqli_query($link,$sql);
						$row=mysqli_fetch_array($result);
						$plc_name=$row['palce_name'];
						$sql="SELECT  shop.place_no,palce_name FROM shop NATURAL JOIN type_place WHERE shop.place_no=type_place.place_no";
						$result=mysqli_query($link,$sql);
						$response="<option value = '$select'>$plc_name</option>";
						while($row=mysqli_fetch_array($result)){
							$response=$response."<option value = '$row[place_no]'>$row[palce_name]</option>";
						}
						echo $response;
						/*if(isset($select)){
							$setSelectDefault=
							"<script type='text/JavaScript'>function setSelectDefault(){
								var i = null;
								var obj = document.getElementById('place_name');
								for(i=0; i< obj.options.length; i++){
									if(obj.options[i] == $select){
										obj.selectedIndex = i;
										return;
									}
								}
							}
							window.addEventListener('load' , setSelectDefault, false);
							</script>";
							echo $setSelectDefault;
						}*/
					?>
				</select>
				<label for = "allCoupon" style = "text-size:50px">優惠券</label>
				<select class = "selectText" name = "cou_intro" id = "cou_intro" >
					<?php 
						if(isset($select)){
							
							$sql="SELECT  cou_intro FROM shop NATURAL JOIN coupon WHERE coupon.place_no='$select'";
							$result=mysqli_query($link,$sql);
							while($row=mysqli_fetch_array($result)){
								$response1=$response1."<option value = '$row[cou_intro]'>$row[cou_intro]</option>";
							}
							echo $response1;
						}
					?>
				</select>
				<input class = "chartSearchButton" type= "submit" name = "send" id = "send" value = "查詢">
		</form>
		<table border="0" class = "boxCenter">
			<tr>
				<td class = "box"><div id="container" style="width:700px; height:400px;"></div></td>
				<td class = "box"><div id="container2" style="width:700px; height:400px;"></div></td>
			</tr>
		</table>
		<div class = "boxCenterMx" id="container3"  rowspan = "2"></div></td>
		<form method="post" action="backstageMainPage.php">
		<input class = "chartSearchButton" type="submit" value ="返回" style = "padding-left:10px ; padding-right: 10px" ></input>
		</form>
		</div>
	</body>
</html>
