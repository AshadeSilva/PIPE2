# PIPE — Fire Alarm PEEP Communication

**PIPE** is an independent software project exploring how the coordination of Personal Emergency Evacuation Plans (PEEPs) during fire alarms in university buildings can by improved. PIPE seeks to improve communication and coordination of students, fire wardens and security staff to increase stability and trust in response to fire alarms for disabled students.

> **Status:** Active development
> **Project:** Independent project
> **Platform:** Android / iOS mobile application

## The problem

During a fire alarm, wardens may need to coordinate multiple students with different PEEPs. Important information and actions can also be spread across different systems.

From research with disabled students, accommodation staff and safety staff, I identified several problems:

* PEEPs can be lengthy documents without clear, actionable instructions for wardens.
* Information needed during an alarm may be distributed across multiple systems.
* Wardens may need to coordinate several students simultaneously, each with different requirements.
* Students may need to communicate non-urgent information with their warden during an evacuation.
* Manual processes can create additional workload for security staff.

The existing process therefore contains many actions that could potentially be automated or systemised.

## The approach

PIPE models each student's PEEP as a **pipeline of digital actions**.

When an alarm occurs, a warden can start the pipelines for all students. Each pipeline progresses through its actions until the student's safety is verified or an escalation is required.

For example:

```text
Check location
     ↓
Student detected in building?
     ↓
Contact student
     ↓
Contact buddy
     ↓
Warden task: contact student
     ↓
Safety verified ────────┐
                        │
All actions fail → ALERT
```

This turns a lengthy PEEP into a sequence of **clear, trackable actions**.

The warden can see the progress and safety status of students, while actions, messages and outstanding tasks remain associated with the relevant student.

<table>
  <tr>
    <td align="center">
      <img src="docs/figma%20prototype/warden/warden.studentPage3.png" width="150"><br>
      <em>Warden dashboard</em>
    </td>
    <td align="center">
      <img src="docs/figma%20prototype/student/student.self-register2.png" width="150"><br>
      <em>Student status</em>
    </td>
    <td align="center">
      <img src="docs/figma%20prototype/student/student.profile1.png" width="150"><br>
      <em>Student profile</em>
    </td>
  </tr>
</table>


## Documentation

The [`docs/`](docs/) directory contains additional project material, including:

* project presentation
* prototype screenshots under "figma prototype"
* design documentation
* supporting explanations

Start with the project presentation for an overview of the problem, research and prototype.

## Key goals

PIPE is designed around five main goals:

1. **Automate** as much of each PEEP as possible.
2. **Centralise information** for wardens into one clear system.
3. **Reduce dependency on security staff** for routine actions.
4. **Improve communication** between students and wardens, including non-urgent communication.
5. **Ensure actions are completed and recorded**, with problems escalated when necessary.

## Student experience

Students can receive requests from their PEEP pipeline directly on their phone.

For example, a "call student" action can trigger a status interface asking the student to register their evacuation status. Students can also update their status manually through their **My Status** page.

The aim is to make communication accessible without requiring students to navigate the same workflow as wardens.

## System Criteria

As PIPE is intended to be a tool for responding to emergencies, there are important conditions for the system to be reliable.

### Offline-first design

A fire alarm is not a situation where reliable connectivity can be assumed. PIPE therefore prioritises offline-first architecture, with local state and synchronisation rather than assuming continuous access to the backend.

The current prototype is designed around Wi-Fi/mobile connectivity, with queued synchronisation and clear fallback instructions when a phone is offline. In the future we may explore further fallback connections like a bluetooth mesh.

### Real-time coordination

Multiple users need to see changes to an alarm as they happen. The system therefore needs to handle:

* real-time event updates
* changing student safety statuses
* warden tasks
* student-to-warden messages
* persistent event logging
* synchronisation between local and remote state

### Reliability and escalation

The system should not simply automate actions and assume they succeeded. Each pipeline needs to track its progress and provide a clear escalation path when an action times out or fails.

This makes **state, failure handling and observability** important parts of the system rather than secondary features.

## Development

I have developed PIPE over several months as an independent project, iterating from user research and interface prototypes towards a working mobile system.

The development process has involved:

* user research with students and staff
* translating fire safety procedures into software workflows
* prototyping and usability design
* mobile application development
* local and remote data storage
* real-time event communication
* offline-first state management
* designing for unreliable connectivity
* considering scalability and failure modes

The project will continue to evolve through further iterations and feedback from Imperial safety and accommodation stakeholders.

## Current scope

The current system is designed around a single accommodation building containing:

* a security office
* a duty warden
* students with PEEPs

The architecture is intended to be scalable to larger locations (for example, across a campus) and different configurations of wardens and staff.

## Future development

Areas I am exploring include:

* showing students which wardens are currently on duty
* allowing students to record where they are so they can move away from alarm noise
* a web interface for situations where a student does not have access to their phone
* supporting multiple accommodation buildings
* adapting the system for faculty buildings, where student locations and warden responsibilities are less predictable
* supporting an "everybody responds" model for PEEPs

## Further Notes

PIPE is an ongoing project, and the goal is not simply to digitise existing PEEPs, but to model how the underlying process could be made more reliable, accessible and manageable through software. PIPE cannot be considered an official emergency communication method because of its reliance on internet connection; it is meant to be a free information method to work alongside radio communication.
