<table><tr><td> <em>Assignment: </em> IT114 Chatroom Milestone4</td></tr>
<tr><td> <em>Student: </em> Nabil El Maalem (nre3)</td></tr>
<tr><td> <em>Generated: </em> 5/4/2023 4:13:31 PM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-008-S23/it114-chatroom-milestone4/grade/nre3" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <p>Implement the features from Milestone3 from the proposal document:&nbsp;&nbsp;<a href="https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view">https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view</a></p>
</td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Client can export chat history of their current session (client-side) </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot of related UI</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123345748/236092557-a0051742-49de-4a6b-b56f-3b56937ffc50.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This shows that there is an export button at the top of each<br>client. <br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add screenshot of exported data</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123345748/236092557-a0051742-49de-4a6b-b56f-3b56937ffc50.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This shows that the export button is there and that it works because<br>on the top left there is the new window where it prompts the<br>user on what to name the file and where to save it<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123345748/236093388-aa5c423c-d911-4c2e-b2a3-6e1992f4b2d4.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This image shows the file that was saved via the export (on the<br>left) and it can be seen that the text file worked and contains<br>the chat which can be seen in the clients on the right. (Styling<br>is included)<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123345748/236094860-ac04ad7e-8f33-43c7-ab9b-f18e3c941463.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This is the code. It is explained in Sub-Task 3 below.<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain how you implemented this</td></tr>
<tr><td> <em>Response:</em> <p>I was able to do this by going into &quot;chatpanel&quot; and making a<br>new export button and then using a try-catch block to run my code.<br>In my try block I had it so that all the information inside<br>of chatArea (aka the messages and name) was taken in. I also made<br>sure to use a for loop to iterate over each in the text<br>as well as start a new line so that the data looks clean<br>when the .txt file is opened. I then took that data and applied<br>it to a file (I used &quot;PrintWriter&quot; in order to make it write<br>it out onto a .txt file). Once its done writing it, it closes<br>and then it&#39;s done. If it happens to fail the catch will throw<br>an IOException which tells the user that it failed and to try again.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Client's mute list will persist across sessions (server-side) </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add a screenshot of how the mute list is stored</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123345748/236095803-fa009bf6-a990-4b51-8896-65bb3dcb7cfa.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This shows how a new .txt file is written and how it holds<br>the individuals who are muted<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add a screenshot of the code saving/loading mute list</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123345748/236096373-63cddea6-2eda-4579-91ab-9169108b3c2e.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This shows the Room.java code that has the case for MUTE and how<br>it writes to a file.<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123345748/236096481-9f1b6253-3e8f-4f0e-b866-9efec59738b8.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This shows the Room.java code that has the case for UNMUTE and how<br>it alters a file.<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain how you implemented this</td></tr>
<tr><td> <em>Response:</em> <p>I was able to implement this by inputting the file writing code directly<br>into the cases of MUTE and UNMUTE. In those cases I was able<br>to run through the clients in serverthread and if they are muted or<br>unmuted it then takes that name and also the name of the person<br>who did the muting or unmuting and writes it to a file which<br>is named after the person who did the muting. It is input into<br>a .txt file.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Client's will receive a message when they get muted/unmuted by another user </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add a screenshot showing the related chat messages</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123345748/236095665-6e57ba4a-edbd-45a5-a558-5d80640330ac.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This shows how when user Kevin was muted and unmuted by user Nora,<br>he received a message that told him who muted and unmuted him.<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add a screenshot of the related code snippets</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123345748/236097152-0873a400-914f-47e7-8738-c9d503d6e2f5.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>It can be seen on line 175 that the user is messaged that<br>they are muted by whoever muted them.<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123345748/236097290-0e22f656-bad0-4997-b3de-df952a29a7f6.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>It can be seen on line 203 where the user is messaged that<br>they were unmuted by whoever unmuted them.<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123345748/236097445-0f1dfb95-adf6-4314-aa11-7d5163d8ecb5.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>It can be seen on the right client that they are messaged that<br>they were muted and unmuted and by who<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain how you implemented this</td></tr>
<tr><td> <em>Response:</em> <p>I implemented this just sending via the localclient a message that included a<br>message which tells the user who muted or unmuted them. I made sure<br>that in an if statement so that if the user is actually able<br>to be muted then it will send and not just send no matter<br>what.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> User list should update per the status of each user </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot for Muted users by the client should appear grayed out</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123345748/236317817-5a66db03-a793-4002-9d9f-63d0a1938de2.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>On the right it can be seen that Kevin is grey highlighted after<br>being muted. <br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add screenshot for Last person to send a message gets highlighted</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123345748/236317817-5a66db03-a793-4002-9d9f-63d0a1938de2.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>It can see in each client that the most recent user to send<br>a message has a highlight of light blue. <br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain how you implemented this</td></tr>
<tr><td> <em>Response:</em> <p>It works by taking the clientId of whoever sent the most recent message.<br>It then makes the user on the userlist panel highlight light blue.&nbsp;<br></p><br></td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-008-S23/it114-chatroom-milestone4/grade/nre3" target="_blank">Grading</a></td></tr></table>