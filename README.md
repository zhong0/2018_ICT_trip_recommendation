# 雙北即時旅遊App─說走就走

demo: https://youtu.be/2IwYbiNdoI8

專案內容
----
* ### 即時旅程推薦<br>


* ### 商業模式<br>

技術說明
----
* ### 使用者介面 <br>
  >本專案於Android Studio開發使用者介面，以XML繪製Layout，Java編寫介面操作，包含CardView、Recyclerview、Barcode Scanner等，並嵌入Google Map Api增加導航功能。此外，在傳遞資料的部分，使用Volley Library傳接資料，利用Gson於Java語言中接收來自php的Json格式的資料。

* ### 資料傳接 <br>
  >本專案以php作為各功能傳接資料的橋樑，在php檔案內連至資料庫，並在其中編寫SQL指令，且以POST接收從Android端傳送的資料，進而做到查詢、更新、刪除等資料庫相關操作。相關操作達成後，若要回傳至使用者端，會將資料包裝成Json格式，以echo回傳至Android端。

* ### 資料庫 <br>
  >使用Amazon EC2虛擬機，於其內安裝xampp套件，搭配其中的Apache Server作為本專案之雲端伺服器。初始資料庫數據則利用Google表單搜集，進而以Maria DB架構使用SQL語言建置資料庫，且利用phpMyAdmin網頁介面管理資料庫。


環境建置
----
* ### Android Studio
* ### php
* ### database


