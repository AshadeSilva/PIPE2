**The Context**  
For every building in universities (and other public buildings) there is a set of people who may struggle to get out of the building during fires, usually due to a disability. We can use the case of accommodation buildings, with a warden and a set of PEEP-holding residents.  
Let’s simplify by taking the most drastic case: imagine there is a person who always faints when they hear a fire alarm.

Residents have a Personalised Emergency Evacuation Plan (PEEP), a document held by the buildings fire warden and any emergency response teams, which outlines a fire safety protocol specific to the person’s needs, to be actioned by the warden, security and any emergency response teams (firefighters). 

A PEEP outlines 2 things

* How the warden can tell if the resident is trapped in the building  
* How the responsible parties will get the resident out if needed

**The Problem**  
There isn’t a universal system to tell if a resident is in the building, partially because its individual to the person’s abilities. PEEPs end up being a complicated patchwork of the solutions. (See current PEEPs)

The system relies on non-automatic processes. The warden needs to check for PEEPs, communicate with students and security, whilst managing other security duties. Security need to check safezone, respond to safezone calls and carry out standard fire alarm protocols. It is difficult to trust that a convoluted PEEP will get seen through.

Convoluted PEEPs create multiple single-point-of-failures and significantly increase the time the resident may be trapped in the building without help. Because the duty moves between many hall supervisors and wardens PEEP owners are relying on the fact that all of them do the protocol exactly. Wardens seem to have varying levels of knowledge about meltdown responses.

**The Solution**

The fire alarm response app would have 2 sides. An admin side, used by the wardens and security. And a resident side, used by the residents and buddies. During a fire alarm the following sequence would occur

Admin side:

1. The warden activates the fire response from the app on the warden phone  
2. This activates the response app for all residents and other admin (probably using wifi), with a time out  
3. If there is a timeout, we can activate the app for the resident’s buddy or set status to help required. This is eg if the phone is no-power  
4. Anyone with the admin side sees a dashboard containing a list of residents and their status (safely evacuated, help required, not in building, waiting for response). This remains until the response is deactivated  
5. We could possibly include more information (like check-in information) for after the fire alarm

Resident side:

1. The response app is activated from the admin side  
2. The app checks which wifi router they are connected to do check their location (most android phones facilitate this, not sure if its harder with iphones). If away from building, set status to not in building and turn back off  
3. If near the building, the app has a pop (over the lock screen, like a call) asking the resident to set their status once evacuated.  
4. After a time out (set by user) if no response the app can be activate the buddy’s response app (this is if the phone is on, but the resident has not responded \- maybe they fainted)  
5. After another time out, the status is set to help required

This allows anyone with the admin side of the app to all the original systems without jumping over hoops.

