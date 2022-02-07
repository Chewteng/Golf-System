## Requirements for Group Project
[Read the instruction](https://github.com/STIW3054-A202/Assignments_and_Project/blob/main/GroupProject.md)

## Group Info:
| Photo | Matric Number | Name |
| --- | --- | --- |
| <img src="https://user-images.githubusercontent.com/61402813/113498023-c8d5dc80-953b-11eb-8861-44abf681797a.jpg" width="180" height="220">| 262122 | Yong Kai Yi (Leader) |
| <img src="https://user-images.githubusercontent.com/60950663/113497852-4567bb80-953a-11eb-8edb-b64a2a6ad2b1.png" width="180" height="220">| 263421 | Lai Yip Hang |
| <img src="https://user-images.githubusercontent.com/51124053/113501971-b9ff2200-955b-11eb-9662-4ade00467d86.jpg" width="180" height="220">| 263117 | H’ng Chew Teng |
| <img src="https://user-images.githubusercontent.com/60843862/113497943-f1110b80-953a-11eb-8361-b139db2ba06c.png" width="180" height="220">| 261307 | Lim Wei Yi |
|   | 262212 | Lee Wei Yen |
  
## Introduction
<p align="justify">This group project is to develop a program with Telegram Bots by using MAVEN and Java programming language. Our program will have two users which are the system administrator and golf players. Each tournament will only have six (6) players and the maximum holes are 18 in UUM. However, every hole has different PAR value. PAR value range is between three (3) and five (5) and its total value is 72. In the tournament, handicap is the expertise level of a golfer. The highest handicap is 24 and the lowest handicap is 0. Better players are those with the lowest handicaps. Besides, our program will have six database table, namely hole, golfer, tournament, player, player’s tournament and player’s score. For the system administrator, he or she can manage (create, read, update, delete) the record of hole, golfer and tournament table by selecting the given three options in the console output. Meanwhile, for the players, six of them are also the opponent's markers with the condition is that a marker cannot mark two or more players. They have the function of choosing the tournament and registering their personal details such as name and telegram ID via the Telegram Bots. In addition, they are also required to submit all their score after the tournament ends in order to get approval from the golf marker as well as the system administrator. If the players get the notification by both of them, it indicates that their scores have been approved or vice versa. Lastly, the total score and the net score for each golf player will be calculated to determine who is the winner of the game.</p>
  
## Flow Diagram of the requirements
a) System Administrator
<img width="1500" height="1200" alt="flow_diagram" src="https://user-images.githubusercontent.com/51124053/120296083-59415b00-c2fa-11eb-9f78-0894c5720717.png">
b) Golfer
![Flow Diagram(Golfer)](https://user-images.githubusercontent.com/60950663/120321429-df1ecf80-c315-11eb-8278-39d313fe8e01.jpg)

## User manual/guideline for testing the system
<div align="justify"">Clone this repository: https://github.com/STIW3054-A202/group-project-goal-diggers.git to your Java IDE.

For the system administrator – CRUD (hole, golfer, tournament):
1.	Go to the program file which has named as App.java located in the src/main/java/my/uum/ folder.
2.	Run that program file and three database table options will be displayed.
3.	To manage the record of the hole table in the database, please enter “1” in the console output or else an error message will be displayed and prompted you to select again.
4.	After entering “1”, four options of create, read, update and delete will be displayed.
5.	To create new hole details, enter “1” and insert the hole number, hole index (only accept number 1 to 20) and hole PAR (only accept number 1 to 5) or else an error message will be displayed and prompted you to select again. Then, if you plan to add another new hole details, please enter “Y” or “y” to continue the insertion process, otherwise you can just enter "N" or "n" to terminate the process. After that, if a success message is displayed, it means that the entered hole details have been saved into the database.
6.	After entering "N" or "n", please repeat the process starting from the number 3 if you plan to manage the record of the hole table in the database again.
7.	To read all the data in the hole database, please enter “2” and you will able to view it. You may repeat the process starting from the number 3 if you plan to manage the record of the hole table in the database again.
8.	To update the specified data in the hole database, please enter “3” and the hole number you wish to update. If the entered hole number is not in the database, an error message will be displayed and prompted you to enter again or else three options of hole number, hole index and hole PAR will be displayed. Please enter “1” to update hole number, enter “2” to update hole index and enter “3” to update hole PAR. If the entered data is successfully updated in the database, it will be directly displayed below with a certain format. You may repeat the process starting from the number 3 if you plan to manage the record of the hole table in the database again.
9.	To delete the specified data in the hole database, please enter “4” and the hole number you wish to delete. If the entered hole number is not in the database, an error message will be displayed and prompted you to enter again, otherwise that data will be deleted from the database and a success message will be displayed.
10.	If you plan to manage the record of the golfer table in the database, please enter “2” in the console output or else an error message will be displayed and prompted you to select again. 
11.	If you plan to manage the record of the tournament table in the database, please enter “3” in the console output or else an error message will be displayed and prompted you to select again. 
12.	These two process are similar to the process of managing the hole database.

For the golf players:
1.	Access the bot by searching the bot name STIW3054_A202_GoalDiggers_bot, or using the link “t.me/STIW3054_A202_GoalDiggers_bot”.
2.  You can tap on "start" to begin the bot with prompted menu consist of register, tournament, record and approval function.
3.  When you select "/register" in the main menu, the bot will bring you to the register page. The telegram first and last name will be identified as your golfer name by default. 
4.  You can long press or key in "/handicap" and add your handicap number behind to register, if you fail to input correctly, error message remind you to key in again. If you successfully register, a notification will be displayed to you and the bot will bring you back to the main menu.
5.  You can also select "/cancelReg" to cancel your registration, the bot will then bring you back to main menu.
6.  When you select "/tournament" in the main menu, the bot will bring you to the tournament page. In the tournament page, the list of functions includes /view,/take_part,/viewParticipant and /viewTournament. 
7.  You can select "/view" to view the tournament details such as tournament id, date and mode of play.
8.  You can select "/take_part" to join the available tournament. You can join certain tournament by long press or key in "/take_part" and add the desired tournament id. If you fail to input correctly, error message remind you to key in again. If you are not register at this stage, error message will remind to register first. If you have registered, a succeed notification will be displayed to you and bot will bring you back to the main menu.
9.  When you select "/record" in the main menu, the bot will bring you to the Score page. In the tournament page, the list of functions includes /viewScore, /setMarker, /set Score and /submitScore. 
10. You can long press or key in "/viewScore" and add the tournament id behind to see the score details include golfers' id, scores for each holes and the total score.
11. You can long press or key in "/setMarker" and tournament id behind, followed by the golfer's id that you want he/she to be your marker. If the marker has been used by another player in the tournament or marker id is not exist in the tournament, an error message will be displayed. If succeed, a successful notification will be displayed.
12. You can long press or key in "/setScore", tournament id behind, hole number, and score. 
13. You can long press or key in "/submitScore" followed by tournament id. If the input is invalid, error message will be displayed. If the input is valid, submitted message will be seen, and followed by approved message.
14. When you select "/approval" from the main menu, you will received the approval message from your golfer once your golfer submitted the score to you. You can either tap on "yes" to approve the score, and the accept message is displayed or tap on "no" to reject the score, and the reject message is displayed.
 </div>

## Result/Output (Screenshot of the output)

**1. Main Menu**



![Menu](https://user-images.githubusercontent.com/60843862/120310604-25216680-c309-11eb-9f3e-52a7a93be70a.PNG)


**2. Menu for Golf Hole Management**


![Menu_GolfHoleManagement](https://user-images.githubusercontent.com/60843862/120310712-41250800-c309-11eb-998e-069ce85fddbb.PNG)


(i) Create 

![GolfHole_Create (1)](https://user-images.githubusercontent.com/60843862/120311673-59495700-c30a-11eb-94aa-9246b86677df.PNG)


![GolfHole_Create (2)](https://user-images.githubusercontent.com/60843862/120311692-5ea6a180-c30a-11eb-8118-53f756cf03ea.PNG)


(ii) Read 


![GolfHole_Read](https://user-images.githubusercontent.com/60843862/120311807-839b1480-c30a-11eb-9a0d-950a5969cb31.PNG)


(iii) Update 


![GolfHole_Update_Index (1)](https://user-images.githubusercontent.com/60843862/120311885-9f061f80-c30a-11eb-93e1-a4f049d835a0.PNG)


![GolfHole_Update_Index (2)](https://user-images.githubusercontent.com/60843862/120311888-a0cfe300-c30a-11eb-8467-1e6fa2be1ba5.PNG)


![GolfHole_Update_PAR (1)](https://user-images.githubusercontent.com/60843862/120311895-a3323d00-c30a-11eb-97ed-044805379234.PNG)


![GolfHole_Update_PAR (2)](https://user-images.githubusercontent.com/60843862/120311901-a4fc0080-c30a-11eb-8262-4991f45ee85f.PNG)

(iv) Delete


![GolfHole_Delete](https://user-images.githubusercontent.com/60843862/120311999-c1983880-c30a-11eb-8827-2246da49c2d2.PNG)

![Menu_GolfHoleManagement](https://user-images.githubusercontent.com/60843862/120310712-41250800-c309-11eb-998e-069ce85fddbb.PNG)

**3. Menu for Golfer Info Management**


![Menu_GolferInfoManagement](https://user-images.githubusercontent.com/60843862/120310816-5ef26d00-c309-11eb-9dfb-d91c7af37899.PNG)

(i) Create 


![Golfer_Create](https://user-images.githubusercontent.com/60843862/120312171-f6a48b00-c30a-11eb-9a75-eeba5b5cfccf.PNG)

(ii) Read


![Golfer_Read](https://user-images.githubusercontent.com/60843862/120312252-0fad3c00-c30b-11eb-9fba-27614d362900.PNG)

(iii)  Update


![Golfer_Update_Name](https://user-images.githubusercontent.com/60843862/120312365-37040900-c30b-11eb-89f8-f4242415beb8.PNG)


![Golfer_Update_TelegramID](https://user-images.githubusercontent.com/60843862/120312392-3e2b1700-c30b-11eb-88cc-661a1fd7e82a.PNG)


![Golfer_Update_Handicap](https://user-images.githubusercontent.com/60843862/120312399-3ff4da80-c30b-11eb-8696-63b5f70c0fa2.PNG)

(iv)  Delete


![Golfer_Delete](https://user-images.githubusercontent.com/60843862/120312479-59962200-c30b-11eb-923f-2ff088602355.PNG)

**4. Menu for Tournament Info Management**


![Menu_TournamentInfoManagement](https://user-images.githubusercontent.com/60843862/120311280-e4761d00-c309-11eb-8068-109db5c0366c.PNG)

(i) Create


![Tournament_Create](https://user-images.githubusercontent.com/60843862/120312510-6286f380-c30b-11eb-8a1f-0690b7ed2ac7.PNG)

(ii) Read


![Tournament_Read](https://user-images.githubusercontent.com/60843862/120312564-76caf080-c30b-11eb-9ba3-4f1aa0d857e4.PNG)
![Menu_GolferInfoManagement](https://user-images.githubusercontent.com/60843862/120310816-5ef26d00-c309-11eb-9dfb-d91c7af37899.PNG)


(iii) Update


![Tournament_Update_Date](https://user-images.githubusercontent.com/60843862/120312604-88ac9380-c30b-11eb-95cf-2a76a4250baa.PNG)


![Tournament_Update_Mode](https://user-images.githubusercontent.com/60843862/120312608-8b0eed80-c30b-11eb-9318-f7566c622e26.PNG)

(iv) Delete


![Tournament_Delete](https://user-images.githubusercontent.com/60843862/120312669-9b26cd00-c30b-11eb-8240-98c80148cc77.PNG)


**5. Quit System**


![Menu_Quit](https://user-images.githubusercontent.com/60843862/120312859-d5906a00-c30b-11eb-8141-96f9185d438a.PNG)

**Telegram Bot**
**Main Menu**

![telegrambot1-1](https://user-images.githubusercontent.com/60950663/120339383-a7b91e80-c327-11eb-8b7a-38429392799d.png)

**1. /register**
  
![telegrambot1-2](https://user-images.githubusercontent.com/60950663/120339512-c1f2fc80-c327-11eb-92ee-7dfd84f1bd77.png)

(ii) /handicap
                               
![telegrambot1-3](https://user-images.githubusercontent.com/60950663/120339613-d6cf9000-c327-11eb-86f7-82114879f3a1.png)

(iii) /cancelReg
                               
![telegrambot1-4](https://user-images.githubusercontent.com/60950663/120340513-ae946100-c328-11eb-995d-2c7b591c422b.png)
                               
**2. /tournament**
                               
![telegrambot2-1](https://user-images.githubusercontent.com/60950663/120339748-f5358b80-c327-11eb-8241-967b190394fa.png)
                               
(i) /view
                               
![telegrambot2-2](https://user-images.githubusercontent.com/60950663/120340657-d1267a00-c328-11eb-8113-ac0a40b9177e.png)

(ii) /take_part       
                               
![telegrambot2-3](https://user-images.githubusercontent.com/60950663/120340629-ca980280-c328-11eb-8d3c-2a966d209ebb.png)
                               
(iii) /viewParticipant      

![telegrambot2-4](https://user-images.githubusercontent.com/60950663/120340752-e1d6f000-c328-11eb-87dd-07d2853136f6.png)

(iv) /viewTournament                
                               
![telegrambot2-5](https://user-images.githubusercontent.com/60950663/120340805-e8fdfe00-c328-11eb-8621-f7f4c63ec153.png)
                              
 **3. /record**

![telegrambot3-1](https://user-images.githubusercontent.com/60950663/120340961-0df27100-c329-11eb-8de2-3046467bf97d.jpeg)
                     
(i) /viewScore               
                               
![telegrambot3-2](https://user-images.githubusercontent.com/60950663/120340965-0e8b0780-c329-11eb-807b-ac019df81311.png)
                               
(ii) /setMarker 
                               
![telegrambot3-3](https://user-images.githubusercontent.com/60950663/120340967-0f239e00-c329-11eb-9730-c0a0cde3c940.png)
                     
(iii) /setScore           
                               
![telegrambot3-4](https://user-images.githubusercontent.com/60950663/120340970-0fbc3480-c329-11eb-91ff-4cddd0b16cef.png)
                               
(iv) /submitScore   

![telegrambot4-1](https://user-images.githubusercontent.com/60950663/120340843-f0bda280-c328-11eb-9057-5b1135145a62.png)

                               
## UML Class Diagram
![ClassDiagram](https://user-images.githubusercontent.com/60950663/120343815-9bcf5b80-c32b-11eb-9b5c-27ae3146137f.jpeg)

## Database Design
<img width="599" alt="Database_Design" src="https://user-images.githubusercontent.com/51124053/120342514-7130d300-c32a-11eb-9764-98a44c026473.png"> 
                                                                                                                                                  
## Youtube Presentation
[<img src ='https://user-images.githubusercontent.com/51124053/115151536-d93f9880-a09f-11eb-830f-9999abe30238.png' width=100 height=100 />](https://youtu.be/LwtrBVmzxZk)                                                                                                                                                  
## References (Not less than 20)
1. A beginner's guide to keeping score in golf. (n.d.). LiveAbout. Retrieved from https://www.liveabout.com/how-does-golf-scoring-work-1560492
                                                                                                                                                  
2. Edpresso Team. (2019, June 21). How to convert an integer to a string in Java. Educative: Interactive Courses for Software Developers. Retrieved from https://www.educative.io/edpresso/how-to-convert-an-integer-to-a-string-in-java
                                                                                                                                                  
3. Golf Monthly. (2019, September 15). 7 Most Important Rules Of Golf I Golf Monthly. YouTube. Retrieved from https://www.youtube.com/watch?v=gMKfGD5fM6I
                                                                                                                                                  
4. iWanamaker (n.d.). Digital Scorecard Exchange and Attest. Retrieved from https://www.youtube.com/watch?v=nZkP82gyvpk&ab_channel=iWanamaker
                                                                                                                                                  
5. Java Regex | Regular Expression - javatpoint. (n.d.). Www.Javatpoint.Com. Retrieved from https://www.javatpoint.com/java-regex
                                                                                                                                                  
6. Javarevisited. (n.d.). How to split String in Java by WhiteSpace or tabs? Example Tutorial. Retrieved from https://javarevisited.blogspot.com/2016/10/how-to-split-string-in-java-by-whitespace-or-tabs.html#axzz6wVEz2whQ
                                                                                                                                                  
7. JavaTpoint. (n.d.). Java Scanner hasNextInt() Method. Retrieved from https://www.javatpoint.com/post/java-scanner-hasnextint-method#:~:text=hasNextInt(int%20radix)%20Method%3A,using%20the%20nextInt()%20method
                                                                                                                                                  
8. JavaTpoint. (n.d.).Java Scanner hasNext() Method. Retrieved from https://www.javatpoint.com/post/java-scanner-hasnext-method
                                                                                                                                                  
9. Jenkov, J. (n.d.). Java Regex - Java Regular Expressions. Java Regex - Java Regular Expressions. Retrieved from http://tutorials.jenkov.com/java-regex/index.html                                                                                                                                                
                                                                                                                                                  
10. Kelley, B. (2019, January 3). On Your Marker: Just Who Is That In Golf, and What Are Marker’s Duties? LiveAbout. Retrieved from https://www.liveabout.com/the-marker-what-or-who-is-it-1561386
                                                                                                                                                  
11. Ly, N. (2017, July 23). The Rules of Golf - EXPLAINED!. Retrieved from https://www.youtube.com/watch?v=IcaFTHeVQ7w
                                                                                                                                                  
12. Mertes, A. (2021, April 19). How Do You Play Golf? A Beginner’s Guide. Retrieved from https://www.qualitylogoproducts.com/blog/how-do-you-play-golf-a-beginners-guide/
                                                                                                                                                  
13. Mr.Animate. (2020, October 26). Penalties in Golf : Penalty in Golf For Beginners EXPLAINED: Golf Rules. YouTube. Retrieved from https://www.youtube.com/watch?v=xE_GQgvX3L0
                                                                                                                                                  
14. Pattern (Java Platform SE 7 ). (2020, June 24). Class Pattern - Java.Util.Regex. Retrieved from https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
                                                                                                                                                  
15. R. (2019, August 2). Send Telegram Bot Notifications with Java. Rieckpil. Retrieved from https://rieckpil.de/howto-send-telegram-bot-notifications-with-java/
                                                                                                                                                  
16. RulesofSport. (n.d.). Golf Rules. Retrieved from https://www.rulesofsport.com/sports/golf.html
                                                                                                                                                  
17. SQLite Tutorial. (2021). SQLite Java: Update Data. Retrieved from https://www.sqlitetutorial.net/sqlite-java/update/
                                                                                                                                                  
18. Stack Overflow. (2014, October 23). Java: do-while loop with switch statement. Retrieved from https://stackoverflow.com/questions/26520452/java-do-while-loop-with-switch-statement
                                                                                                                                                  
19. The Minimalist Golf Swing System. (2020, October 14). Introduction to Golf - How the game is played. Retrieved from https://www.youtube.com/watch?v=w7PFNleLMI0
                                                                                                                                                  
20. Valentin, B. (n.d.). Understanding How to Calculate Your Golf Handicap in 2020. Understanding How to Calculate Your Golf Handicap in 2020. Retrieved from https://www.gcoftheeverglades.com/blog/44-understanding-how-to-calculate-your-golf-handicap-in-2020
                                                                                                                                                  
21. Wikipedia. (2021, May 18). Golf. Retrieved from Wikipedia, the free encyclopedia: https://en.wikipedia.org/wiki/Golf
                               
## Java Doc
### Link
https://yongkaiyi1998.github.io/project-javadoc/

                                                                                                                                                  
