<?php
error_reporting(E_ERROR);
$type=$_FILES['fileField']['type'];
$size=$_FILES['fileField']['size'];
$name=iconv("UTF-8","BIG-5",$_FILES['fileField']['name']);
$nameEcho=$_FILES['fileField']['name'];
$tmp_name=$_FILES['fileField']['tmp_name'];
?>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上傳結果</title>
</head>

<body>
	<form action="uploadfile_test.php" method="post" enctype="multipart/form-data">
	filename：<input type="file" name="fileField"/><br/>
	<input type="submit" name="submit" value="上傳檔案">
	</form>
<?php
$sizemb=round($size/1024000,2);
$i=1;
echo $i."</br>";
echo "檔案類型：".$type."</br>";
echo "檔案大小：".$sizemb."MB</br>";
echo "檔案名稱：".$nameEcho."</br>";
echo "暫存名稱：".$tmp_name."</br>";
if($type=="application/pdf" || $type=="application/vnd.ms-excel"){
 if($sizemb < 3){
  $file=explode(".",$name);
  //$new_name=$file[0]."-".date('ymdhis')."-".rand(0,10);
  $new_name="pic".$i;
  $chi_name=iconv("BIG-5","UTF-8",$new_name);
  echo "</br>已修改為新檔名:".$chi_name."後上傳成功";
  while(file_exists("up/".$new_name.".".$file[1])){
	$i++;
	$new_name="pic".$i;
	$chi_name=iconv("BIG-5","UTF-8",$new_name);
	echo "</br>已修改為新檔名:".$chi_name."後上傳成功";
  }
  move_uploaded_file($tmp_name,"up/".$new_name.".".$file[1]);
  echo "上傳成功";
 }else{
  echo "檔案太大，上傳失敗";
 }
}else{
 echo "檔案格式錯誤，上傳失敗";
}
?>
</body>
</html>