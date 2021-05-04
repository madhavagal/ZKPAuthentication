# ZKPAuthentication

Capstone Project done by students of PES University, Bangalore. This project aims to explore the use of Zero Knowledge Proof as an alternative to the traditional username-password based authentication. We have done security analysis of Schnorr's non-interactive ZKP protocol and demonstrated the Replay Attack vulnerability present in the system. Following that, we have mitigated the vulnerability as well. 

This project is aimed to be an extenshion of the work by MIT students: https://github.com/vonderhaar/6857-PasswordManager and we give them credits for the base java code.

## Code Execution:
The setup requires 3 VMs – Server, User and Attacker, all connected to the same NAT-network.
Software/Technologies used – Java, Python, Jpype, Flask, Postman, Scapy

Steps to execute code:

A.	Demonstration of Replay Attack
1.	The “Demonstration” folder is placed in the Server VM
2.	Change the path used in line 7 and 8 in Demonstration/vulnerableAttempt.py according to the path in your machine
3.	Change the path used in line 23 and 24 in Demonstration/vulnerableServer.py according to the path in your machine
4.	Change the path used in line 23 in Demonstration/vulnerable/Verifier.java according to the path in your machine
5.	Delete all the .class files present in Demonstration/vulnerable and recompile the java files by running `javac -sourcepath ./* ECPoint.java`  in a terminal opened in the Demonstration/vulnerable folder
6.	Run the vulnerableServer using the command `sudo python vulnerableServer.py`
7.	Place the attacker.py in the Attacker VM
8.	Replace the IP address in line 21 and 28 in attacker.py with the Server’s IP address and run the file using `sudo python attacker.py`
9.	The “prover” folder is placed in the User VM. 
10.	Paste this json in the raw input field in postman: 
{“data”:”55066263022277343669578718895168534326250603453777594175500187360389116729240,32670510020758816978083085130507043184471273380659243275938904335757337482424,109411527425683331270165226420103869431692157677456199532124334609606382664292,81625651194024298122854340455444955605122045933594624416355720567783179868544,108045784876447599210972997030619144038122098317918256676858587404815170283673,31216517415843029335852717207691181832954458851405626440092277832327428261835,25422002623926171179342530059798878124688852604405052741590027467970387380454,fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141,050997”}
11.	Set the target url as “http://serverIP:3999/api/auth” and the method as POST
12.	Send the packet in postman. 
13.	You can see that in the server two requests are received (one from user and one from attacker) and both are successful. On Attacker VM you can see the response to the spoofed packet which is authentication successful. Hence Replay attack is successfully demonstrated.

B.	Mitigation of Replay Attack
1.	The “Mitigation” folder is placed in the Server VM
2.	Change the path used in line 7 and 8 in Mitigation/secureAttempt.py according to the path in your machine
3.	Change the path used in line 46 and 47 in Mitigation/secureServer.py according to the path in your machine
4.	Change the path used in line 21 and 38 in Mitigation/secure/Verifier.java according to the path in your machine
5.	Delete all the .class files present in Mitigation/secure and recompile the java files by running `javac -sourcepath ./* ECPoint.java`  in a terminal opened in the Mitigation/secure folder
6.	Run the secureServer using the command `sudo python secureServer.py`
7.	Place the attacker.py in the Attacker VM
8.	Replace the IP address in line 21 and 28 in attacker.py with the Server’s IP address and run the file using `sudo python attacker.py`
9.	The “prover” folder is placed in the User VM. 
10.	Change the path used in line 71 in prover /Prover.java according to the path in your machine.
11.	Delete all the .class files present in the prover folder and recompile the java files by running `javac -sourcepath ./* ECPoint.java`  in a terminal opened in the prover folder
12.	Run the command `java prover.PrintPacket` in a terminal opened in the parent folder of the prover folder.
13.	Copy the comma separated packet generated and run postman in the User VM.
14.	Create a json object {“data”:<packet>} in the raw input field in postman and set the target url as “http://serverIP:3999/api/auth” and the method as POST
15.	Send the packet in postman. 
16.	You can see that in the server two requests are received (one from user and one from attacker) but only the request from the user is successful whereas the request from the attacker is rejected. On Attacker VM you can see the response to the spoofed packet which is authentication failed. Hence Replay attack is successfully mitigated.
