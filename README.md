# CS 6359 Project - Group 3

### Running the project

  - Start your local MySQL cluster.
  - Run the SQL script present in "DB_OBS.sql" inside the "/DB" folder inside the project directory.
  - Clone the repository
  - Open the project in Eclipse editor.
  - Download Apache Tomcat 9.x
  - Right click on the project folder name and Click "Refactor" and then "Rename"
  - Once you click "Rename" a dialog box will pop up. In the dialog box, make sure Update preferences is checked.
  - In the dialog box, in the "New name:" section, write "obs" and click "OK"
  - Now, right click again on the project "obs" and click on "Build Path", then click on "Configure Build Path", in the "Libraries" section, make sure you have the
    following files:
    1. activation-1.1.1.jar
    2. hamcrest-all-1.3.jar
    3. javax.servlet-api-3.0.1.jar
    4. jsp-api-2.2.jar
    5. jstl-1.2.jar
    6. junit-4.12.jar
    7. mockito-all-1.10.19.jar
    8. mysql-connector-java-8.0.13.jar
  - Now, right click again on the project "obs" and click on "Build Path", then click on "Configure Build Path", go to the "Libraries" section, click on "Add Library",
    then select "JRE System Library" and click "Next" and then select "Alternate JRE" and in the dropdown select Java 1.8 if you have it install on your system, otherwise
    install Java 1.8 and repeat the above steps and click "Finish".
  - Right click again on the project "obs" and click on "Build Path", then click on "Configure Build Path", on the left menu, select "Project Facets", select "Dynamic Web Module" (version 4.0) and
    "Java" and "JavaScript" and then click "Apply and Close". 
  - Right click again on the project "obs" and click on "Build Path", then click on "Configure Build Path", on the left menu, select "Deployment Assembly", then click "Add",
    then select "Folder" and click "Next", then select "WebContent" and click "Finish"
  - Right click on project "obs", select "Run As", then "Run on Server", a window will popup, select "Manually define a new server", click "Tomcat v9.0 Server", click "Add"
    next to "Server runtime environment" and then click "Browse" and select the folder of your downloaded Tomcat. 
  - Click "Finish" and then "Next" and then "Finish". If everything was set up properly, the server will start in the console and redirect you to the Login page.
  - You have successfully run the project

### Relevant Documentation Github

[__Issue Tracking page__](https://github.com/UTDClassroomOrg/courseproject-cs6359-f22-group3/issues)


[__Wiki Page__](https://github.com/UTDClassroomOrg/courseproject-cs6359-f22-group3/wiki)


[__Project Organisation__](https://github.com/orgs/UTDClassroomOrg/projects/17/views/1)



[__GitHub Link__](https://github.com/UTDClassroomOrg/courseproject-cs6359-f22-group3)
