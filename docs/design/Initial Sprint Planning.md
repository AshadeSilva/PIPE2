Set Up

| Android/Compose base app set up | Android Studio Directly from figma? Make git base app conv  | 21/06/26 |
| :---- | :---- | :---- |
| iOS base app set up | XCode Get developer license Get iphone Ci cd |  |
| Finish set up and pipeline **System Architecture**  | For android Get ci/cd working.  Firebase CI token (stored on git): \*\*\*  |  |

Calling

| Database build students and wardens Create single profile for each see last bit about setting up the database  System Architecture  | Firebase might take some time to understand Instead of signing in have sign in out as warden, student etc Different user types have diffferent pages  |
| :---- | :---- |
| Create call on firealarm alert | Warden front end: single alert button Warden presses button \- edge function Edge function broadcasts to student Student front end: single call button Edge function to update student Wardens phone is automatically updated |
| Calls time out |  |
| Multi sensory calling |  |

Frontend \- warden

| Students page \- students arranged in blocks |  |
| :---- | :---- |
| Alarm mode: Blocks are colour coded to status and automatically update Blocks order in terms of priorities |  |
| Alarm end: blocks still coloured till wipe |  |
| Students profile pages | Information at the top Change status block |

Pipelines

| pipelines/actions in database |  |
| :---- | :---- |
| Pipeline objects / actions in code |  |
| Database function now broadcasts pipelines | Including updating statuses |
| Offline online-first updates | Students logging statuses Wardens sending alerts All messages Doesnt apply to calls |
| Pipeline end | looks like just keep in table with "active" / "archive" status |

Multiple profiles

| Multiple students | Page to set up profiles \- online? |
| :---- | :---- |
| Buddies |  |
| Multiple wardens |  |
| Security? |  |
| Buildings? |  |

Comms

| Logging actions | Warden log page Database event logging Logging tags |
| :---- | :---- |
| Messaging students / security | Message block on student profile Message block of student call screen Log updated |
| Tasks | Students can mark feeling off Some pipeline stages need confirmation Messages need replying to |
| Final page | What went wrong Any notes |
| Fire brigade report |  |

Various Pipeline actions

| Calling implemented fully | Call page for students Task generated |
| :---- | :---- |
| Location checking \- wifi based | (wifi access point association |
| Location gps tracking |  |
| Buddy calls | Buddy profile |
| Final clause | Always check on student etc |
| Connectivity fallbacks   | connectivity fallbacks  |

PEEP files

| Attach files |  |
| :---- | :---- |
| Special peep format |  |
| Offline mode |  |
| Learning module |  |
| Pipeline history / stats |  |

