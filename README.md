# Taipei Instant Trip Recommendation - Hit the Road

Chinese introduction video: https://youtu.be/2IwYbiNdoI8
* International ICT Innovative Services Awards 2018(IP Group, AWS Group)- Nominated
* National Cloud APP Mobile Creative Application Competition 2018 - Second place

Introduction
----
* ### Instant Trip Recommendation
  >Our system provides instant short-time trip recommendation in Taipei and New Taipei City. According to different personal attributes, such as gender, ages, occupation, relationship and favorite types of activities, the system will suggest the journey from current time. The recommendation score is calculated with the preferences of the person and the similar kind of people with user. To avoid to suggest repeated activities, the recommendation list contains some random factors. All the suggested journey would take within several hours to less a day.

* ### Business Strategy 
  >To enhance the attractiveness and expand customer base, the users are able to collect the points via scanning the QR code during the trip, and exchange the coupons from several cooperative stores in our shopping center. In addition, the higher ad fee leads to higher exposure opportunity of the random part. In fact, the more users use our system, the more precise statistics our system obtains, which assists the store to understand their marketing position and strategy. 

Techniques
----
* ### Database
  > We applied Amazon EC2 virtual machine, which was installed the Xampp and used its Apache server, as our cloud server. The initial statistics was collected via Google Forms. Based on the relational database concepts, we used SQL with Maria DB framework to build our database, and managed it with phpMyAdmin.

* ### Data Transmission
  >Php files connected all data of each function between user side and database. The connection to the database and SQL commands were written in php files. We used POST method to receive the data from Android side to manipulate our database, such as querying, updating, deleting and so on. If it needs to send the value to Android side, we will form the data to Json format and return it with echo commands.

* ### Backstage for stores
  >To provide the customer statistics to the cooperative stores, we developed the backstage with html, css and php. Also, the data was transmitted via php files, and the html code was combined to the php files.

* ### User Interface
  >The user interface was developed with Android Studio. The Layout was generated with XML, and the interface operation was coded with Java. The techniques included CardView, RecyclerView, Barcode Scanner, etc. Besides, we embedded Google Map API to achieve navigation function. Volley library was applied to pass the data. Gson library helped in Java to parse the Json format data from php files.


Environment
----
* ### Database
  >We used Windows os environment on AWS EC2, and set the port to make external network available to connect it. To enter the virtual machine, you need to download the authorized certificate and type the password to login. In the machine, you need to download Xampp and set the username and the password. It's necessary to start the functions of Apache and MySQL. Then, you can be access to phpMyAdmin after linking to the localhost(127.0.0.1), and import our sql file to implement the database.

* ### php
  >Xampp contains the php environment. Therefore, you just need to move all php files and backstage files to the path /xampp/htdoc/ in the EC2 machine. It's noticed that the username and password in those files should be modified to your own settings.

* ### Android Studio
  >Follow the official document to download Android Studio, and open our project from Android Studio. If your pc doesn't contain Java environment, you need to follow the hints to download it. Also, you should synchronous the gradle file with our project. Our server is closed. Therefore, you need to modify all the IP links in the projects to your own settings. The setting of minSdkVersion is 23 and targetSdkVersion is 27 in our project. <br>
  
  >Notification: Our project only provides to academic purpose. If you need to download the user interface file, please feel free to contact us via the e-mail 109753106@g.nccu.edu.tw. We would authorize the access after an assessment.

Supplement
----
* ### Business Strategy
  >The business strategy is only a concept. There’s no real cooperative store in our project. The function we developed is a simulation to the expected situation.

* ### backstage
  >The backstage file contains the code which is the user interface of backstage for cooperative stores. The picture in the file is the screenshot of the homepage looks.

* ### document
  >The document file contains the introduction document for the preliminary contest of International ICT Innovative Services Awards 2018. Also, the poster and presentation ppt for finals are attached. All the documents are written in Chinese. If you want to understand our systems work in details, you can watch the introduction video via above link.
