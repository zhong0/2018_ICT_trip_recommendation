<?php
	error_reporting(E_ERROR);	//不希望顯示錯誤提示
	include("condb.php");
	$link=connect_sql();
	
	$place_name=$_POST['place_name'];
	
	
	
	$genNum=array(0,0);
	$carNum=array(0,0,0,0,0,0,0,0,0,0,0);
	$ageNum=array(0,0,0,0,0,0,0);
	$groupCarNum=array(0,0,0,0,0,0,0,0,0,0,0);
	$groupTypeNum=array(0,0,0,0);
	$num=0;
	//$place_name='內湖運動中心';
	
	$sql = "select activity_no,person_no,person_kind.gender as gender, type_place.palce_name as place,people_trend.people_fre as times from people_trend NATURAL JOIN all_activity NATURAL JOIN person_kind NATURAL JOIN type_place WHERE palce_name='$place_name' AND gender='female'  AND people_trend.person_no=person_kind.person_no AND all_activity.place_no=type_place.place_no";
	$rows1 = mysqli_query($link , $sql);
	$sql="select activity_no,person_no,person_kind.gender as gender, type_place.palce_name as place,people_trend.people_fre as times from people_trend NATURAL JOIN all_activity NATURAL JOIN person_kind NATURAL JOIN type_place WHERE palce_name='$place_name' AND gender='male' AND people_trend.person_no=person_kind.person_no AND all_activity.place_no=type_place.place_no";
	$rows2=mysqli_query($link,$sql);			//性別sql
	while($row1=mysqli_fetch_array($rows1)){
		$genNum[0]=$row1['times']+$genNum[0];
	}
	while($row2=mysqli_fetch_array($rows2)){
		$genNum[1]=$row2['times']+$genNum[1];
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
		$sql="select activity_no,person_no,person_kind.gender as gender, type_place.palce_name as place,people_trend.people_fre as times from people_trend NATURAL JOIN all_activity NATURAL JOIN person_kind NATURAL JOIN type_place WHERE palce_name='$place_name' AND career_no='$career_no'  AND people_trend.person_no=person_kind.person_no AND all_activity.place_no=type_place.place_no";
		$result=mysqli_query($link,$sql);							//person career sql
		while($row=mysqli_fetch_array($result)){
			$carNum[$i]=$row['times']+$carNum[$i];
		}
		if($i<4){
			$sql="select activity_no,group_chose.group_no, group_kind.gender as gender, type_place.palce_name as place,group_chose.group_fre as times from group_chose NATURAL JOIN all_activity NATURAL JOIN group_kind NATURAL JOIN type_place WHERE palce_name='$place_name' AND group_type_no='$group_type_no'  AND group_chose.group_no=group_kind.group_no AND all_activity.place_no=type_place.place_no";
			$result=mysqli_query($link,$sql);							//group group_type sql
			while($row=mysqli_fetch_array($result)){
				$groupTypeNum[$i]=$row['times']+$groupTypeNum[$i];
			}
		}
		if($i<7){
			$sql="select activity_no,person_no,person_kind.gender as gender, type_place.palce_name as place,people_trend.people_fre as times from people_trend NATURAL JOIN all_activity NATURAL JOIN person_kind NATURAL JOIN type_place WHERE palce_name='$place_name' AND age_no='$age_no'  AND people_trend.person_no=person_kind.person_no AND all_activity.place_no=type_place.place_no";
			$result=mysqli_query($link,$sql);						//person age sql
			while($row=mysqli_fetch_array($result)){
				$ageNum[$i]=$row['times']+$ageNum[$i];
			}
		}
	}
	
	mysqli_free_result($result);
	mysqli_free_result($rows1);
	mysqli_free_result($rows2);
	//mysqli_close($link);
?>
<!DOCTYPE html>
<html>
	<head>
		<meta charset = "utf-8"/>
		<title>testChar</title>
		<script src="https://code.highcharts.com/highcharts.js"></script>
		<script src="http://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
		<!--<link rel = "stylesheet" type = "text/css" href = "style.css">-->
		<style type = "text/css">
			.chartSearchButton { font-family: fantasy ;border-radius: 10px; background-color: rgba(255, 255, 255, 0.76);
								box-shadow: 3px 5px 10px gray;text-shadow: 2px 4px 10px #999999; font-size: 20px; font-weight: bold; color: #696969;}
			.background {background-image: linear-gradient(30deg,#81C7D4,#F596AA); background-image: url('4471.jpg'); text-align: center; background-repeat: repeat; }
			.text				{font-size: 20px;font-weight: bold;text-shadow: 2px 4px 10px #999999;color: #696969;}
			.selectText			{font-size: 20px;font-weight: bold;text-shadow: 2px 4px 10px #999999;color: #696969;
								box-shadow: 3px 5px 10px #999999;}
			.box 				{box-shadow: 3px 5px 10px #999999;}
			.boxCenter			{box-shadow: 3px 5px 10px #999999; margin:30px auto;}
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
		var carnum=new Array(11);
		carnum[0]= <?php echo $carNum[0]?>;
		carnum[1]= <?php echo $carNum[1]?>;
		carnum[2]= <?php echo $carNum[2]?>;
		carnum[3]= <?php echo $carNum[3]?>;
		carnum[4]= <?php echo $carNum[4]?>;
		carnum[5]= <?php echo $carNum[5]?>;
		carnum[6]= <?php echo $carNum[6]?>;
		carnum[7]= <?php echo $carNum[7]?>;
		carnum[8]= <?php echo $carNum[8]?>;
		carnum[9]= <?php echo $carNum[9]?>;
		carnum[10]= <?php echo $carNum[10]?>;
		/*$(document).ready(function () { 
		var myChart = Highcharts.chart('container', {
        chart: {
            type: 'bar'
        },
        title: {
            text: "<?php echo $place_name?>"+"與性別來訪次數統計圖"
        },
        xAxis: {
            categories: <?php echo "['女性', '男性']"?>
        },
        yAxis: {
			allowDecimals: false,
            title: {
                text: '來訪次數'
            }
        },
        series: [{
            name: '性別',
            data: [num1, num2]
			}/*, {
            name: '玩',
            data: [intnum2, 6, 5 , 3 ]
				},{
			name: '文',
			data: [5,5,6,6]
				},{
			name: '閒',
			data: [6,5,5,8]
				},{
			name: '逛',
			data: [7,7,6,4]
				} ]
			});
		});*/
		function chart1(){
			var myChart = Highcharts.chart('container', {
			chart: {
				type: 'bar',
				backgroundColor: '#ffb5b5',
				
				zoomType: 'xy'
			},
			tooltip: {
				borderColor: '#000000'
			},
			title: {
				text: "<?php echo $place_name?>"+"與性別來訪次數統計圖",
				fontSize: '100px',
			},
			xAxis: {
				categories: <?php echo "['女性', '男性']"?>,
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
					text: '來訪次數',
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
				name: '性別',
				data: [num1, num2],
				//type: 'column'
				}/*, {
				name: '玩',
				data: [intnum2, 6, 5 , 3 ]
					},{
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
				text: "<?php echo $place_name?>"+"與職業來訪次數統計圖"
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
					text: '來訪次數',
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
				name: '職業',
				data: [carnum[0], carnum[1], carnum[2], carnum[3], carnum[4], carnum[5], carnum[6], carnum[7], carnum[8], carnum[9], carnum[10]]
				}/*, {
				name: '玩',
				data: [intnum2, 6, 5 , 3 ]
					},{
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
				text: "<?php echo $place_name?>"+"與年齡來訪次數統計圖"
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
					text: '來訪次數',
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
				name: '年齡',
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
				}]
			});
		}
		function chart4(){
			var myChart = Highcharts.chart('container4', {
			chart: {
				type: 'bar',
				backgroundColor: '#A8D8B9',
				zoomType: 'xy'
			},
			title: {
				text: "<?php echo $place_name?>"+"與團體關係來訪次數統計圖"
			},
			xAxis: {
				categories: <?php echo "['心靈避風港的家人', '半生熟的普通朋友', '老膩在一起的麻吉', '甜蜜分不開的情侶']"?>
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
					text: '來訪次數',
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
				name: '團體關係',
				data: <?php 
						$data1="[";
						for($i=0;$i<4;$i++){
							if($i<3)
								$data1=$data1.$groupTypeNum[$i].",";
							else
								$data1.=$groupTypeNum[$i];
						}
						$data1=$data1."]";
						echo $data1;
					?>
				}]
			});
		}
		function express(){
			//location.href="javascriptJump.php?value="+value;
			location.href="couponChart.php";
		}
		window.addEventListener("load" , chart1, false);
		window.addEventListener("load" , chart2, false);
		window.addEventListener("load" , chart3, false);
		window.addEventListener("load" , chart4, false);
		</script>
		<div class = "background">
		<form method = "post" action = "testChart.php">
				<label  for = "allplace" class = "text" >地點:</label>
				<select class = "selectText" name = "place_name" id = "place_name">
					<?php 
						$sql="SELECT DISTINCT palce_name FROM type_place";
						$result=mysqli_query($link,$sql);
						while($row=mysqli_fetch_array($result)){
							$response=$response."<option value = '$row[palce_name]'>$row[palce_name]</option>";
						}
						echo $response;
					?>
				</select>
				<input class = "chartSearchButton" type= "submit" name = "send" id = "send" value = "查詢">
		</form>
		<table border="0" class = "boxCenter">
			<tr>
				<td class = "box"><div id="container" style="width:700px; height:400px;"></div></td>
				<td class = "box"><div id="container2" style="width:700px; height:400px;"></div></td>
			</tr>
			<tr>
				<td class = "box"><div id="container3" style="width:700px; height:400px;"></div></td>
				<td class = "box"><div id="container4" style="width:700px; height:400px;"></div></td>
			</tr>
		</table>
		<form method="post" action="backstageMainPage.php">
		<input class = "chartSearchButton"	type="submit" value ="返回" style = "padding-left:auto ; padding-right: auto" ></input>
		</form>
		<!--<input  type = "button" id="test" name = "test" value="測試看看" onClick="express()"></input>-->
		
		</div>
	</body>
</html>