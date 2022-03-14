# 雙北即時旅遊App─說走就走

Demo Video: https://youtu.be/2IwYbiNdoI8
* 2018大專校院資訊應用服務創新競賽入圍：資訊應用組、AWS雲端科技趨勢應用組
* 2018全國雲端APP行動創意應用競賽第二名

專案內容
----
* ### 即時旅程推薦<br>
  >本系統提供短時程於雙北市內空閒之輕旅行推薦。其依照不同個人屬性，例如性別、年齡層、職業、旅伴關係、活動性質等，推薦不同行程，推薦分數計算包含個人使用習慣、同屬性大眾分數計算，為避免推薦一塵不變，納入隨機成分於推薦列表內，其皆會參考目前時間推薦數小時至一天內的行程。

* ### 商業模式<br>
  >為增加黏著性及拓展商家客群，本系統利用掃QRcode的方式集點，並可兌換商城內各店家之優惠卷，此外，推薦列表內之部分隨機內容也會依照合作金額不同有所高低。然而，越多使用者使用本平台時，進而能提供各商家客群數據，協助商家了解自我定位及研討各自經營模式。

技術說明
----
* ### 資料庫 <br>
  >使用Amazon EC2虛擬機，於其內安裝xampp套件，搭配其中的Apache Server作為本專案之雲端伺服器。初始資料庫數據則利用Google表單搜集，以關聯式資料庫設計整體架構，進而以Maria DB架構使用SQL語言建置資料庫，且利用phpMyAdmin網頁介面管理資料庫。

* ### 資料傳接 <br>
  >本專案以php作為各功能傳接資料的橋樑，在php檔案內連至資料庫，並在其中編寫SQL指令，且以POST接收從Android端傳送的資料，進而做到查詢、更新、刪除等資料庫相關操作。相關操作達成後，若要回傳至使用者端，會將資料包裝成Json格式，以echo回傳至Android端。

* ### 商家後台建置 <br>
  >為提供商家客群數據，本專案以html、css設計後台介面，以php傳接資料，其html程式碼寫入於後台相關之php檔案內。

* ### 使用者介面 <br>
  >本專案於Android Studio開發使用者介面，以XML繪製Layout，Java編寫介面操作，包含CardView、Recyclerview、Barcode Scanner等，並嵌入Google Map API加入導航功能。此外，在傳遞資料的部分，使用Volley Library傳接資料，利用Gson於Java語言中接收來自php的Json格式的資料。


環境建置
----
* ### Database
  >登入AWS educate選擇EC2類型，我們使用windows作業系統的環境，並設定對外連接的port，再使用金鑰登入。在虛擬機上下載xampp，設定使用者名稱與密碼，並啟用Apache與MySQL功能，即可對外連接。在虛擬機上開啟localhost(127.0.0.1)進入phpMyAdmin，將本專案sql檔import即可開始操作資料庫。

* ### php
  >xampp套件中已包含php環境，將php與backstage之所有檔案放置EC2虛擬機上的xampp/htdoc/路徑底下，檔案內資料庫使用者名稱與密碼需改為各自設定名稱。

* ### Android Studio
  >依照官方說明下載Android Studio，從Android Studio開啟本專案檔案，若電腦尚未下載Java需依照環境提示安裝，並同步Gradle，完成專案設定。目前本專案之雲端伺服器IP已關閉，因此，在傳接資料的網址，需更換成各自的伺服器IP。本專案設定gradle(Module:app)，minSdkVersion為23、targetSdkVersion為27。<br>
  
  >注意：本專案只提供學術需求，若需要完整介面檔案，請傳送email至109753106@g.nccu.edu.tw，經評估後，將給予下載權限。

補充說明
----
* ### backstage
  >backstage資料夾為提供各商家數據後台程式檔案，內有一張jpeg檔案為後台設計首頁截圖。

* ### document
  >document資料夾含有2018大專校院資訊應用服務創新競賽初賽文件、複賽海報及簡報，想知道更詳細內容可查看資料夾文件或是點選上述demo影片連結。


