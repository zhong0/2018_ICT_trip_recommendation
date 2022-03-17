# Taipei Instant Trip Recommendation - Hit the Road

Chinese introduction video: https://youtu.be/2IwYbiNdoI8
* International ICT Innovative Services Awards 2018(IP Group, AWS Group)- Nominated
* National Cloud APP Mobile Creative Application Competition 2018 - Second place

Introduction
----
* ### Instant Trip Recommendation <br>
  >Our system provides instant short-time trip recommendation in Taipei and New Taipei City. According to different personal attributes, such as gender, ages, occupation, relationship and favorite types of activities, to suggest the journey. The recommendation score is calculated with the preferences of the person and the similar kind of people with user. To avoid to suggest repeated activities, it contains some random factors in the recommendation list. Overall, all the suggested journey would cost within several hours to less a day from current time.

* ### Business Strategy <br>
  >To enhance the attractiveness and broaden the customers, the users collect the points via scanning the QR code during the trip, and exchange the coupons of several stores in our shopping center. In addition, different ad fee would influence the part of random factors. However, the more users use our system, the more precise analysis of customer statistic obtains, which assists the store to understand their marketing position and strategy. 

Techniques
----
* ### Database <br>
  > We applied Amazon EC2 virtual machine, which was set up the Xampp and used its Apache server, as the cloud server. We collected the initial statistics via Google Forms. Based on relational database concepts, we used SQL with Maria DB framework to build our database, and managed it with phpMyAdmin.

* ### Data Passing <br>
  >Php files connected all data of each function between user side and database. In php codes, there were the connection to the database and SQL commands. We used POST method to receive the data from Android side to operate our database, such as query, update, delete and so on. If it was need to send the value to user, we formed the data to Json format and returned it with echo commands.

* ### Backstage for stores <br>
  >To provide the customer statistics to the cooperative stores, we developed the backstage with html, css and php. Also, it passed the data via php files, and the html code was combined to the php files.

* ### User Interface <br>
  >The user interface was developed with Android Studio. The Layout was generated with XML, and the interface operation was coded with Java. The techniques included CardView, RecyclerView, Barcode Scanner, etc. Besides, we embedded Google Map API to achieve navigation function. Moreover, we used Volley Library to pass the data and Gson Library help Java to depack the Json format data from php files.


Environment
----
* ### Database
  >We used Windows os environment on AWS EC2, and set the port to make external network available to connect it. To enter the virtual machine, you need to download the authorized certificate and type the password to login. In the machine, you will download Xampp and set the username and the password. It's necessary to start the functions of Apache and MySQL. Then, you could open the localhost(127.0.0.1) to get the access to phpMyAdmin. You can implement the database after importing our sql file.

* ### php
  >Xampp contains the php environment. Therefore, you just need to make all php files and backstage files under the path xampp/htdoc/ in the EC2 machine. It's notified that the username and password in those files need to be modified to your own settings.

* ### Android Studio
  >Download Android Studio first according to the official document, and open our project files from Android Studio. If your pc doesn't contain Java environment, you need to follow the hints to download it. Also, you should synchronous the gradle file with our project. Our server is closed. Therefore, you should modified all the IP links in the projects to your own settings. The setting of minSdkVersion is 23 and targetSdkVersion is 27 in our project. <br>
  
  >Notification: Our project only provides to academic needs. If you need to download the user interface file, please feel free to contact us via send the e-mail to 109753106@g.nccu.edu.tw. We would authorize the access after estimating.

Supplement
----
* ### backstage
  >The backstage file contains the code which is providing customers statistics of the cooperative stores. There's a picture which is the screenshot of the homepage looks.

* ### document
  >The document file contains the introduction document for the preliminary contest of International ICT Innovative Services Awards 2018. Also, the poster and presentation ppt for finals are attached. All the documents are written in Chinese. If you want to understand our systems work in details, you can access the demo video via above link.
