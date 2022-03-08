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
	function insideTime($openTime,$endTime){
		$t=6;
		$t1=$t+1;
		$t2=$t+2;
		$t3=$t+3;
		$nowTime=date('H:i:s', strtotime("+$t hours"));
		$nowTime1=date('H:i:s', strtotime("+$t1 hours"));
		$nowTime2=date('H:i:s', strtotime("+$t2 hours"));
		$nowTime3=date('H:i:s', strtotime("+$t3 hours"));
		if((strtotime($nowTime)-strtotime($openTime))<0 || (strtotime($nowTime)-strtotime($endTime))>=0){
				return false;
		}
		if((strtotime($nowTime1)-strtotime($openTime))<0 || (strtotime($nowTime1)-strtotime($endTime))>=0){
				return false;
		}
		if((strtotime($nowTime2)-strtotime($openTime))<0 || (strtotime($nowTime2)-strtotime($endTime))>=0){
				return false;
		}	
		if((strtotime($nowTime3)-strtotime($openTime))<0 || (strtotime($nowTime3)-strtotime($endTime))>=0){
				return false;
		}
		return true;
	}
	
		$time_str=microtime(true);
		connect_sql() ;
			
		//POST gruop
		$Mode=$_POST["Mode"];
		
		$gender=$_POST["gender"];
	    
	    $age_no=$_POST["age_no"];
		
	    $group_type_no=$_POST["group_type_no"];
		
		$career_no=$_POST["career_no"];
		
		$user_ID=$_POST["user_ID"];
		
		$ActTypeForData=$_POST["ActTypeForData"];
		
		/*$Mode='personalUser';
		$gender='male';
		$age_no='1';
		$group_type_no='1';
		$career_no='1';
		$user_ID='c8763';
		$ActTypeForData='5';*/
		
		
	    $lengh= strlen($ActTypeForData);
		 
		const allActivity_num = 468;	
		if(isset($Mode))	
	{
		$Y=preg_split('//', $ActTypeForData, -1, PREG_SPLIT_NO_EMPTY);
		
		/*$X="INSERT INTO test23(Mode,user_ID,ActTypeForData) values('$Mode','$user_ID','$ActTypeForData')";
		
		$result=mysqli_query($link,$X);*/
		
		
		//$D1= date('H:i:s', strtotime('+6 hours'));
		//$D3= date('H:i:s', strtotime('+9 hours'));
			
		
		switch($Mode)
		{
			case'personalUser':
				$sql3="SELECT person_no FROM user  WHERE user_ID='$user_ID'";
			
				$result3=mysqli_query($link,$sql3);
		
				$row=mysqli_fetch_array($result3,MYSQLI_ASSOC);
		
				$person_no1=$row["person_no"];
				
				switch($lengh)
				{
					case '1':
						$sql4="SELECT type_place.net_rate+people_trend.people_fre+people_trend.people_rate+personal_chose.user_fre+personal_chose.user_rate  AS total_grade  ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN people_trend
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						 NATURAL JOIN all_item
						WHERE  person_no ='$person_no1' AND personal_chose.user_ID='$user_ID' AND(type_no='$Y[0]')
						ORDER BY total_grade DESC ";
						break;
					case '2':
						$sql4="SELECT type_place.net_rate+people_trend.people_fre+people_trend.people_rate+personal_chose.user_fre+personal_chose.user_rate  AS total_grade  ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN people_trend
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE person_no ='$person_no1' AND  personal_chose.user_ID='$user_ID' AND(type_no='$Y[0]' OR type_no='$Y[1]')
						ORDER BY total_grade DESC  ";
						break;
					case '3':
						$sql4="SELECT type_place.net_rate+people_trend.people_fre+people_trend.people_rate+personal_chose.user_fre+personal_chose.user_rate  AS total_grade  ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN people_trend
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item																
						WHERE   person_no ='$person_no1' AND personal_chose.user_ID='$user_ID' AND(type_no='$Y[0]'OR  type_no='$Y[1]' OR type_no='$Y[2]')
						ORDER BY total_grade DESC  ";		//原本沒有 NATURAL JOIN all_item這行= =
						break;
					case '4':
						$sql4="SELECT type_place.net_rate+people_trend.people_fre+people_trend.people_rate+personal_chose.user_fre+personal_chose.user_rate  AS total_grade  ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN people_trend
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE person_no ='$person_no1' AND personal_chose.user_ID='$user_ID' AND(type_no='$Y[0]' OR  type_no='$Y[1]' OR type_no='$Y[2]' OR type_no='$Y[3]')
						ORDER BY total_grade DESC  ";
						break;
					case '5':
						$sql4="SELECT type_place.net_rate+people_trend.people_fre+people_trend.people_rate+personal_chose.user_fre+personal_chose.user_rate  AS total_grade  ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN people_trend
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE person_no ='$person_no1' AND personal_chose.user_ID='$user_ID' AND(type_no='$Y[0]' OR  type_no='$Y[1]' OR type_no='$Y[2]' OR type_no='$Y[3]'OR type_no='$Y[4]')
						ORDER BY total_grade DESC  ";
						break;
					default:
						break;		
				}	
				$result1 =mysqli_query($link,$sql4);
				$response=array();
				$z=array();		//record activity_no in z[]
				$i=1;
				while($row=mysqli_fetch_array($result1))
				{
					$D1= date('H:i:s', strtotime('+6 hours'));
					$D3= date('H:i:s', strtotime('+9 hours'));
					$A=$row["openTime"];
					$B=$row["endTime"];
					/*$D=(strtotime($D1) - strtotime($A));
					$D2=(strtotime($B) - strtotime($D3));
					if($D3>$D1)
					{
						if($D>=0&&$D2>=0)
						{
							$z[$i]=$row["activity_no"];
						
							array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
							$i++;
							//echo "+";
						}
					}
					else																				//判斷是否過日 是 用openTime去減結束的時段
					{
						$D2=(strtotime($D3) - strtotime($A));
						if($D>=0&&$D2>=0)																	//D2>0代表結束時段比openTime後面 (可能bug 中間有停止營業時無法知道)
						{
							$z[$i]=$row["activity_no"];
							array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
							$i++;
							//echo "-";
						}
					}*/
					if(insideTime($A,$B)){
						$z[$i]=$row["activity_no"];
						array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
						$i++;
					}
					if($i>=4)
					{
						break;
					}
				}
				//echo  json_encode($response,JSON_UNESCAPED_UNICODE); 	
				$x=array();
				$y=array();		//for random, activity_no
				for($i=1;$i<allActivity_num+1;$i++)
				{
					$x[$i]=0;
				}
				for($i=1;$i<4;$i++)
				{
					$x[$z[$i]]=1;		//three rate							//將上面照排名順序選過的設為1
				}
														//type random three
				switch($lengh)		
				{
					case '1':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE (type_no='$Y[0]')
						ORDER BY rand()";
						break;
					case '2':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE (type_no='$Y[0]' OR type_no='$Y[1]')
						ORDER BY rand()";
						break;
					case '3':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item																
						WHERE (type_no='$Y[0]'OR  type_no='$Y[1]' OR type_no='$Y[2]')
						ORDER BY rand()";		//原本沒有 NATURAL JOIN all_item這行= =
						break;
					case '4':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE (type_no='$Y[0]' OR  type_no='$Y[1]' OR type_no='$Y[2]' OR type_no='$Y[3]')
						ORDER BY rand()";
						break;
					case '5':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE (type_no='$Y[0]' OR  type_no='$Y[1]' OR type_no='$Y[2]' OR type_no='$Y[3]'OR type_no='$Y[4]')
						ORDER BY rand()";
						break;
					default:
						break;		
				}
				$result3 =mysqli_query($link,$sql4);
				$response3=array();
				$i=1;
				while($row=mysqli_fetch_array($result3))
				{
					$D1= date('H:i:s', strtotime('+6 hours'));
					$D3= date('H:i:s', strtotime('+9 hours'));
					$A=$row["openTime"];
					$B=$row["endTime"];
					/*$D=(strtotime($D1) - strtotime($A));
					$D2=(strtotime($B) - strtotime($D3));
					if($D3>$D1)
					{
						if($D>=0&&$D2>=0)
						{
							$z[$i]=$row["activity_no"];
						
							array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
							$i++;
							//echo "+";
						}
					}
					else																				//判斷是否過日 是 用openTime去減結束的時段
					{
						$D2=(strtotime($D3) - strtotime($A));
						if($D>=0&&$D2>=0)																	//D2>0代表結束時段比openTime後面 (可能bug 中間有停止營業時無法知道)
						{
							$z[$i]=$row["activity_no"];
							array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
							$i++;
							//echo "-";
						}
					}*/
					$z[$i]=$row["activity_no"];
					if($x[$z[$i]]==0 && insideTime($A,$B)){
						array_push($response3,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
						$x[$z[$i]]=1;
						$i++;
					}
					if($i>=4)
					{
						break;
					}
				}
				//echo  json_encode($response,JSON_UNESCAPED_UNICODE);
				
				for($i=1;$i<5;$i++)			//all random four
				{
					$k=0;
					while(true)
					{
						$y[$i]=rand(1,allActivity_num);
						$sql="SELECT openTime,endTime FROM type_place NATURAL JOIN all_activity WHERE activity_no='$y[$i]'";
						$result4=mysqli_query($link,$sql);
						$row=mysqli_fetch_array($result4);
						$D1= date('H:i:s', strtotime('+6 hours'));
						$D3= date('H:i:s', strtotime('+9 hours'));
						$A=$row["openTime"];
						$B=$row["endTime"];
						/*$startCheck=$D1-$A;
						$endCheck=$B-$D3;
						if($D3>$D1)
						{
							if($x[$y[$i]]==0 && $startCheck>=0 && $endCheck>=0)
							{
								$x[$y[$i]]=1;
								break;
							}
						}
						else
						{
							$endCheck=$D3-$A;
							if($x[$y[$i]]==0 && $startCheck>=0 && $endCheck>=0)
							{
								$x[$y[$i]]=1;
								break;
							}
						}*/
						if($x[$y[$i]]==0 && insideTime($A,$B)){
							$x[$y[$i]]=1;
							break;
						}
						$k++;
						/*if($k>999){
							break;
						}*/
					}	
			
				}
				$sql2="SELECT all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no
					   FROM all_activity
					   NATURAL JOIN type_place
					   NATURAL JOIN all_item
					   WHERE (activity_no='$y[1]' OR activity_no='$y[2]' OR activity_no='$y[3]'OR activity_no='$y[4]'OR activity_no='$y[5]')";
					   
				$result2 =mysqli_query($link,$sql2);
				$response1=array();
				while($row=mysqli_fetch_array($result2))
				{
					array_push($response1,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
				}
					
				$response2=array_merge($response,$response3,$response1);		//merge array
				
				echo  json_encode($response2,JSON_UNESCAPED_UNICODE); 	
				break;
				
				
	//GROUP NO要改			
			case 'Group':
				$sql2="SELECT group_no FROM group_kind WHERE gender='$gender' AND age_no='$age_no' AND group_type_no='$group_type_no'" ;
		
				$result2=mysqli_query($link,$sql2);
		
				$row=mysqli_fetch_array($result2,MYSQLI_ASSOC);
		
				$group_no=$row["group_no"];	
				switch($lengh)
				{
					case '1':
						$sql4="SELECT type_place.net_rate+group_fre+group_rate AS total_grade ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM group_chose
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE group_no ='$group_no'
						AND(type_no='$Y[0]')
						ORDER BY total_grade DESC  ";
						break;
					case'2':
						$sql4="SELECT type_place.net_rate+group_fre+group_rate AS total_grade ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM group_chose
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						 NATURAL JOIN all_item
						WHERE group_no ='$group_no'
						AND(type_no='$Y[0]' OR type_no='$Y[1]')
						ORDER BY total_grade DESC  ";
						break;
					case '3':
						$sql4="SELECT type_place.net_rate+group_fre+group_rate AS total_grade ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM group_chose
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						 NATURAL JOIN all_item
						WHERE group_no ='$group_no'
						AND(type_no='$Y[0]'OR  type_no='$Y[1]' OR type_no='$Y[2]')
						ORDER BY total_grade DESC  ";
						break;
					case '4':
						$sql4="SELECT type_place.net_rate+group_fre+group_rate AS total_grade ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM group_chose
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						 NATURAL JOIN all_item
						WHERE group_no ='$group_no'
						AND(type_no='$Y[0]' OR  type_no='$Y[1]' OR type_no='$Y[2]' OR type_no='$Y[3]')
						ORDER BY total_grade DESC  ";
						break;
					case '5':
						$sql4="SELECT type_place.net_rate+group_fre+group_rate AS total_grade ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM group_chose
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						 NATURAL JOIN all_item
						WHERE group_no ='$group_no'
						AND(type_no='$Y[0]' OR  type_no='$Y[1]' OR type_no='$Y[2]' OR type_no='$Y[3]'OR type_no='$Y[4]')
						ORDER BY total_grade DESC  ";
						break;
					default:
						break;		
				}
				$result1 =mysqli_query($link,$sql4);
				$response=array();
				$z=array();
				$i=1;
				while($row=mysqli_fetch_array($result1))
				{
					$D1=date('H:i:s', strtotime('+6 hours'));
					$D3=date('H:i:s', strtotime('+9 hours'));
					$A=$row["openTime"];
					$B=$row["endTime"];
					/*$D=(strtotime($D1) - strtotime($A));
					$D2=(strtotime($B) - strtotime($D1));
					
					if($D3>$D1)
					{
						if($D>0&&$D2>0)
						{
							$z[$i]=$row["activity_no"];
						
							array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
							$i++;
						}
					}
					else																				//判斷是否過日 是 用openTime去減結束的時段
					{
						$D2=(strtotime($D3) - strtotime($A));
						if($D>0&&$D2>0)																	//D2>0代表結束時段比openTime後面 (可能bug 中間有停止營業時無法知道)
						{
							$z[$i]=$row["activity_no"];
							array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
							$i++;
						}
					}*/
					if(insideTime($A,$B)){
						$z[$i]=$row["activity_no"];
						array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
						$i++;
					}
					if($i==4)
					{
						break;
					}
				}
				//echo  json_encode($response,JSON_UNESCAPED_UNICODE); 	
				$x=array();
				$y=array();
				for($i=1;$i<allActivity_num+1;$i++)
				{
					$x[$i]=0;
				}
				for($i=1;$i<4;$i++)
				{
					$x[$z[$i]]=1;
				}
														//type random three
				switch($lengh)		
				{
					case '1':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE (type_no='$Y[0]')
						ORDER BY rand()";
						break;
					case '2':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE (type_no='$Y[0]' OR type_no='$Y[1]')
						ORDER BY rand()";
						break;
					case '3':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item																
						WHERE (type_no='$Y[0]'OR  type_no='$Y[1]' OR type_no='$Y[2]')
						ORDER BY rand()";
						break;
					case '4':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE (type_no='$Y[0]' OR  type_no='$Y[1]' OR type_no='$Y[2]' OR type_no='$Y[3]')
						ORDER BY rand()";
						break;
					case '5':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE (type_no='$Y[0]' OR  type_no='$Y[1]' OR type_no='$Y[2]' OR type_no='$Y[3]'OR type_no='$Y[4]')
						ORDER BY rand()";
						break;
					default:
						break;		
				}	
				$result3 =mysqli_query($link,$sql4);
				$response3=array();
				$i=1;
				while($row=mysqli_fetch_array($result3))
				{
					$D1= date('H:i:s', strtotime('+6 hours'));
					$D3= date('H:i:s', strtotime('+9 hours'));
					$A=$row["openTime"];
					$B=$row["endTime"];
					/*$D=(strtotime($D1) - strtotime($A));
					$D2=(strtotime($B) - strtotime($D3));
					if($D3>$D1)
					{
						if($D>=0&&$D2>=0)
						{
							$z[$i]=$row["activity_no"];
						
							array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
							$i++;
							//echo "+";
						}
					}
					else																				//判斷是否過日 是 用openTime去減結束的時段
					{
						$D2=(strtotime($D3) - strtotime($A));
						if($D>=0&&$D2>=0)																	//D2>0代表結束時段比openTime後面 (可能bug 中間有停止營業時無法知道)
						{
							$z[$i]=$row["activity_no"];
							array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
							$i++;
							//echo "-";
						}
					}*/
					$z[$i]=$row["activity_no"];
					if($x[$z[$i]]==0 && insideTime($A,$B)){
						array_push($response3,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
						$x[$z[$i]]=1;
						$i++;
					}
					if($i>=4)
					{
						break;
					}
				}
				//echo  json_encode($response,JSON_UNESCAPED_UNICODE);
				for($i=1;$i<5;$i++)
				{
					$k=0;
					while(true)
					{
						$y[$i]=rand(1,allActivity_num);
						$sql="SELECT openTime,endTime FROM type_place NATURAL JOIN all_activity WHERE activity_no='$y[$i]'";
						$result4=mysqli_query($link,$sql);
						$row=mysqli_fetch_array($result4);
						$D1= date('H:i:s', strtotime('+6 hours'));
						$D3= date('H:i:s', strtotime('+9 hours'));
						$A=$row["openTime"];
						$B=$row["endTime"];
						/*$startCheck=$D1-$A;
						$endCheck=$B-$D3;
						if($D3>$D1)
						{
							if($x[$y[$i]]==0 && $startCheck>=0 && $endCheck>=0)
							{
								$x[$y[$i]]=1;
								break;
							}
							
						}
						else
						{
							$endCheck=$D3-$A;
							if($x[$y[$i]]==0 && $startCheck>=0 && $endCheck>=0)
							{
								$x[$y[$i]]=1;
								break;
							}
						}*/
						if($x[$y[$i]]==0 && insideTime($A,$B)){
							$x[$y[$i]]=1;
							break;
						}
						$k++;
						/*if($k>999){
							break;
						}*/
					}	
			
				}
				$sql2="SELECT all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no
					   FROM all_activity
					   NATURAL JOIN type_place
					   NATURAL JOIN all_item
					   WHERE (activity_no='$y[1]' OR activity_no='$y[2]' OR activity_no='$y[3]'OR activity_no='$y[4]'OR activity_no='$y[5]')";
					   
				$result2 =mysqli_query($link,$sql2);
				$response1=array();
				while($row=mysqli_fetch_array($result2))
				{
					array_push($response1,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
				}
					
				$response2=array_merge($response,$response3,$response1);
				
				echo  json_encode($response2,JSON_UNESCAPED_UNICODE); 
				break;
				
			case 'personalStranger' :
				$sql1="SELECT person_no FROM person_kind WHERE gender='$gender' AND age_no='$age_no' AND career_no='$career_no'" ;
		
				$result=mysqli_query($link,$sql1);
		
				$row=mysqli_fetch_array($result,MYSQLI_ASSOC);
		
				$person_no=$row["person_no"];
				switch($lengh)
				{
					case '1':
						$sql4="SELECT type_place.net_rate+people_trend.people_fre+people_trend.people_rate  AS total_grade  ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM people_trend
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						 NATURAL JOIN all_item
						WHERE person_no ='$person_no'  AND(type_no='$Y[0]')
						ORDER BY total_grade DESC  ";
						break;
					case '2':
						$sql4="SELECT type_place.net_rate+people_trend.people_fre+people_trend.people_rate  AS total_grade  ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM people_trend
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						 NATURAL JOIN all_item
						WHERE person_no ='$person_no'  AND(type_no='$Y[0]' OR type_no='$Y[1]')
						ORDER BY total_grade DESC  ";
						break;
					case '3':
						$sql4="SELECT type_place.net_rate+people_trend.people_fre+people_trend.people_rate  AS total_grade  ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM people_trend
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE person_no ='$person_no'  AND(type_no='$Y[0]'OR  type_no='$Y[1]' OR type_no='$Y[2]')
						ORDER BY total_grade DESC  ";
						break;
					case '4':
						$sql4="SELECT type_place.net_rate+people_trend.people_fre+people_trend.people_rate  AS total_grade  ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM people_trend
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						 NATURAL JOIN all_item
						WHERE person_no ='$person_no'  AND(type_no='$Y[0]' OR  type_no='$Y[1]' OR type_no='$Y[2]' OR type_no='$Y[3]')
						ORDER BY total_grade DESC  ";
						break;
					case '5':
						$sql4="SELECT type_place.net_rate+people_trend.people_fre+people_trend.people_rate AS total_grade  ,all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no,openTime,endTime
						FROM people_trend
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						 NATURAL JOIN all_item
						WHERE person_no ='$person_no'  AND(type_no='$Y[0]' OR  type_no='$Y[1]' OR type_no='$Y[2]' OR type_no='$Y[3]'OR type_no='$Y[4]')
						ORDER BY total_grade DESC  ";
						break;
					default:
						break;
				}
				$result1 =mysqli_query($link,$sql4);
				$response=array();
				$z=array();
				$i=1;
				while($row=mysqli_fetch_array($result1))
				{
					$D1=date('H:i:s', strtotime('+6 hours'));
					$D3=date('H:i:s', strtotime('+9 hours'));
					$A=$row["openTime"];
					$B=$row["endTime"];
					/*$D=(strtotime($D1) - strtotime($A));
					$D2=(strtotime($B) - strtotime($D1));
					
					if($D3>$D1)
					{
						if($D>0&&$D2>0)
						{
							$z[$i]=$row["activity_no"];
						
							array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
							$i++;
						}
					}
					else																				//判斷是否過日 是 用openTime去減結束的時段
					{
						$D2=(strtotime($D3) - strtotime($A));
						if($D>0&&$D2>0)																	//D2>0代表結束時段比openTime後面 (可能bug 中間有停止營業時無法知道)
						{
							$z[$i]=$row["activity_no"];
							array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
							$i++;
						}
					}*/
					if(insideTime($A,$B)){
						$z[$i]=$row["activity_no"];
						array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
						$i++;
					}
					if($i==4)
					{
						break;
					}
				}
				//echo  json_encode($response,JSON_UNESCAPED_UNICODE); 	
				$x=array();
				$y=array();
				for($i=1;$i<allActivity_num+1;$i++)
				{
					$x[$i]=0;
				}
				for($i=1;$i<4;$i++)
				{
					$x[$z[$i]]=1;
				}
														//type random three
				switch($lengh)		
				{
					case '1':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE (type_no='$Y[0]')
						ORDER BY rand()";
						break;
					case '2':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE (type_no='$Y[0]' OR type_no='$Y[1]')
						ORDER BY rand()";
						break;
					case '3':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item																
						WHERE (type_no='$Y[0]'OR  type_no='$Y[1]' OR type_no='$Y[2]')
						ORDER BY rand()";
						break;
					case '4':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE (type_no='$Y[0]' OR  type_no='$Y[1]' OR type_no='$Y[2]' OR type_no='$Y[3]')
						ORDER BY rand()";
						break;
					case '5':
						$sql4="SELECT all_activity.activity_no as activity_no, type_place.palce_name as name, all_item.item as item, all_activity.item_no as item_no, openTime, endTime
						FROM personal_chose
						NATURAL JOIN user
						NATURAL JOIN all_activity
						NATURAL JOIN type_place
						NATURAL JOIN all_item
						WHERE (type_no='$Y[0]' OR  type_no='$Y[1]' OR type_no='$Y[2]' OR type_no='$Y[3]'OR type_no='$Y[4]')
						ORDER BY rand()";
						break;
					default:
						break;		
				}	
				$result3 =mysqli_query($link,$sql4);
				$response3=array();
				$i=1;
				while($row=mysqli_fetch_array($result3))
				{
					$D1= date('H:i:s', strtotime('+6 hours'));
					$D3= date('H:i:s', strtotime('+9 hours'));
					$A=$row["openTime"];
					$B=$row["endTime"];
					/*$D=(strtotime($D1) - strtotime($A));
					$D2=(strtotime($B) - strtotime($D3));
					if($D3>$D1)
					{
						if($D>=0&&$D2>=0)
						{
							$z[$i]=$row["activity_no"];
						
							array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
							$i++;
							//echo "+";
						}
					}
					else																				//判斷是否過日 是 用openTime去減結束的時段
					{
						$D2=(strtotime($D3) - strtotime($A));
						if($D>=0&&$D2>=0)																	//D2>0代表結束時段比openTime後面 (可能bug 中間有停止營業時無法知道)
						{
							$z[$i]=$row["activity_no"];
							array_push($response,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
							$i++;
							//echo "-";
						}
					}*/
					$z[$i]=$row["activity_no"];
					if($x[$z[$i]]==0 && insideTime($A,$B)){
						array_push($response3,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
						$x[$z[$i]]=1;
						$i++;
					}
					if($i>=4)
					{
						break;
					}
				}
				//echo  json_encode($response,JSON_UNESCAPED_UNICODE);
				for($i=1;$i<5;$i++)
				{
					
					$k=0;
					while(true)
					{
						$y[$i]=rand(1,allActivity_num);
						$sql="SELECT openTime,endTime FROM type_place NATURAL JOIN all_activity WHERE activity_no='$y[$i]'";
						$result4=mysqli_query($link,$sql);
						$row=mysqli_fetch_array($result4);
						$D1= date('H:i:s', strtotime('+6 hours'));
						$D3= date('H:i:s', strtotime('+9 hours'));
						$A=$row["openTime"];
						$B=$row["endTime"];
						/*$startCheck=$D1-$A;
						$endCheck=$B-$D3;
						if($D3>$D1)
						{
							if($x[$y[$i]]==0 && $startCheck>=0 && $endCheck>=0)
							{
								$x[$y[$i]]=1;
								break;
							}
						}
						else
						{
							$endCheck=$D3-$A;
							if($x[$y[$i]]==0 && $startCheck>=0 && $endCheck>=0)
							{
								$x[$y[$i]]=1;
								break;
							}
						}*/
						if($x[$y[$i]]==0 && insideTime($A,$B)){
							$x[$y[$i]]=1;
							break;
						}
						$k++;
						/*if($k>999){
							break;
						}*/
					}	
			
				}
				$sql2="SELECT all_activity.activity_no as activity_no ,type_place.palce_name as name,all_item.item as item,all_activity.item_no as item_no
					   FROM all_activity
					   NATURAL JOIN type_place
					   NATURAL JOIN all_item
					   WHERE (activity_no='$y[1]' OR activity_no='$y[2]' OR activity_no='$y[3]'OR activity_no='$y[4]'OR activity_no='$y[5]')";
					   
				$result2 =mysqli_query($link,$sql2);
				$response1=array();
				while($row=mysqli_fetch_array($result2))
				{
					array_push($response1,array("total_grade"=>$row["total_grade"],"activity_no"=>$row["activity_no"],"name"=>$row["name"],"item"=>$row["item"],"item_no"=>$row["item_no"]));
				}
					
				$response2=array_merge($response,$response3,$response1);
				
				echo  json_encode($response2,JSON_UNESCAPED_UNICODE); 
			default:
				break;	
		}
		
	}	
		$time_end=microtime(true);
		$time = $time_end-$time_str;
		//echo $time;
		//POST
		
			/*$sql1="SELECT * FROM people_trend WHERE person_no='P0015'";
			$result1 =mysqli_query($link,$sql1);
			$number_of_rows=mysqli_num_rows($result1);
			$response=array();
			while($row=mysqli_fetch_array($result1))
			{
				array_push($response,array("person_no"=>$row["person_no"],"activity_no"=>$row["activity_no"],"people_fre"=>$row["people_fre"],"people_rate"=>$row["people_rate"]));
			}
			echo $number_of_rows;
			echo  json_encode($response,JSON_UNESCAPED_UNICODE); */		
		
		
	
	// 釋放空間
		mysqli_free_result($result);
		
		mysqli_free_result($result1);
		
		mysqli_free_result($result2);
		
		mysqli_free_result($result3);
		// 關閉 SQL
		mysqli_close($link);
?>
