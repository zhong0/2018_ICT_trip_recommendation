# Taipei Instant Trip Recommendation - Hit the Road

Chinese introduction video: https://youtu.be/2IwYbiNdoI8
* International ICT Innovative Services Awards 2018(IP Group, AWS Group)- Nominated
* National Cloud APP Mobile Creative Application Competition 2018 - Second place

Introduction
----
* ### Instant Trip Recommendation<br>
  >Our system provides instant short-time trip recommendation in Taipei and New Taipei City. According to different personal attributes, such as gender, ages, occupation, relationship and favoritve types of activities, to suggest the journey. The recommendation score calculation contains the preferences of person and the same kind of people with user. To avoid to suggest repeated activities, it cotains some random content in the recommendation list. Overall, all the recommended journey would cost within several hours to less a day from current time.

* ### Business Strategy<br>
  >To enhace the attractiveness and broaden the customers, user collect the points via scan the QR code during the trip, and exchange the coupons of several stores in our shopping center. In addition, different advertisement fee would influence the part of random recommendation list. However, the more user use our system, the more specific analysis of customer statistic obtains, which assists the store to understand their marketing position and strategy.  

Techniques
----
* ### Database <br>
  >Our cloud server is Aamazon EC2 virtual machine, which was set up the xampp and used its Apache Server. We collect the initial statistice via Google Forms. Based on relational database concepts, we use SQL with Maria DB framwork to build our database, and manage it with phpMyAdmin.

* ### Data Passing <br>
  >Php files connect all data of each function between user side and database. In php codes, there's a connection to our database and SQL commands. We used POST method to recieve the data from Android side to operate our database, such as query, update, delete and son on. If it's need to return the value to user, the data will return with Json format vie echo command.

* ### Backstage for stores <br>
  >To provide the cutomer statistics to the cooperating stores, we develop the backstage with html, css and php. Also, it passes the data via php files, and the html code is combined to the php files.

* ### User Interface <br>
  >The user interface was developed with Android Studio. The Layout was generated with XML, and the interface operation was coded with Java. The techniques includes CardView, RecyclerView, Barcode Scanner, etc. Besides, we embedded Google Map API to achieve navigation function. Moreover, we used Volley Library to pass the data and Gson Library help Java to depack the Json format data from php files.


Environment
----
* ### Database
  >Firstly, we used Windows os environment on AWS EC2, and set the port to make external network available to connect it. To enter the virtual machine, you need to download the authorized certificate and type the password to login. In the machine, you will download Xampp and set the username and the password. It's necessary to start the functions of Apache and MySQL. Then, you could open the localhost(127.0.0.1) to get access to phpMyAdmin. You can operate the database finally after importing our sql file.

* ### php
  >Xampp contains the environment of php. Therefore, you just need to put all php files and backstage files to the path xampp/htdoc/ in the EC2 machine. It's notified that the username and password in those files need to be modified to your own settings.

* ### Android Studio
  >According to official documnet, you should download Android Studio first, and then open our project files from Android Studio. If your pc doesn't contain Java environment, you need to follow the hint to download it. Moreover, you should synchronous the gradle file with our project. Our server is closed. Therefore, you should modified all the IP links in the projects. The setting of minSdkVersion is 23 and targetSdkVersion is 27 in our project. <br>
  
  >Notification: Our project is only provide to acadamic needs. If you need to download the user interface file, please feel free to send the e-mail to 109753106@g.nccu.edu.tw. We would authorize the access after estimating.

Supplement
----
* ### backstage
* The backstage file contains the code which provide customers statistices of the cooperating stores. There's a jpeg file which is the screenshot of the homepage looks.

* ### document
  >The document files contains the document in the preliminary contest of International ICT Innovative Services Awards 2018. Also, the poster and presentation ppt in finals are attached. All the documents are written in Chinese. If you want to understand the details of our system work, you can click the demo video link.


