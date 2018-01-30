# DropCube
DropCube is simplified version of Dropbox.
It is a web application created in Spring MVC framework, so in order to run it you'll need a servlet container(Tested on Tomcat).

Application uses RDBMS to persist user registration data and all files. Driver's class name, URL, username and password used
to connect with database are present in "DropCube/src/main/resources/dataSource.properties" file, so just change entries in
it to connect to choosen database(Tested on MySQL).
