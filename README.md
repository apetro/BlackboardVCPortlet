The Blackboard Collaborate portlet provides an integration point with uPortal and Blackboard Collaborate. 

NOTE: In order to use this Portlet, you will have to have been given credentials and connection details by Blackboard.

Via this portlet, you can set up and join Collaborate sessions, invite both internal and external participants, and edit and view recordings.

Firstly, Blackboard will provide the details of how to integrate with Collaborate. This will give you the information required to set up the WSDL, and username/password in the configuration files.

Once you have that information you will have to edit the following files in the WEB-INF directory:

* webapp.properties - See webapp.properties.SAMPLE .  You will need to set Blackboard Collaborate username/password, database information
* log4.properties - The portlet will log to <web_container>/logs/BlackboardVCPortlet.log, and by default is set to DEBUG

NOTE: The portlet makes use of a callback feature in order to retrieve recordings when a session closes. In order for this to work, the portlet must be callable from an external source (the Collaborate servers). 
If you use https, you must have a global certificate which Collaborate can perform a valid SSL handshake with.


**This portlet has been tested and deployed against uPortal 3.2.x**


# Security Configuration Summary

## Roles
* ROLE_ADMIN - Users that have full access to all functionality
* ROLE_FULL_ACCESS - Users that have full access to the session creation form (TODO flush out all access)

## Permissions

### org.jasig.portlet.blackboardvcportlet.data.Session
* view
* edit
* delete

