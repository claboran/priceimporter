Spring Boot example project showing multiple Jndi Datasource in different profiles 
====================================================================================


Author: Christian Laboranowitsch
Level: Intermediate
Technologies: Spring Boot, Spring JDBC, JNDI, Tomcat, Spring Rest, Spring Profiles, Spring Testing
Summary: An example that shows how to handle multiple JNDI datasources in Spring Boot
Source: <https://github.com/claboran/spring-boot-jndi-multi>

What is it?
-----------

It is a sample, deployable Maven 3 project to help you to set up multiple JNDI datasources in Spring Boot. Partitioning (in the sense of schemas and users) of databases 
is often not in the hand of the Java developer. So the production setup differs from the one you use for integration testing or the one on your local devbox.
Tables are scattered across schemas and technical users accessing different schemas are isolated due to security considerations.
Dealing with multiple datasources in Spring Boot is another story. Transactions spanning multiple datasources is not covered. The application is based on pure JDBC,
it could be extended to have an environment with multiple EntityManagerFactories per datasource, maybe in the next example...

This project is setup to allow you to create a Spring application using Spring Boot, Spring Rest, Spring JDBC and Tomcat 8

System requirements
-------------------

All you need to build this project is Java 8.0 (Java SDK 1.8), Maven 3.1 or better.

The application this project produces is designed to be run on Tomcat 8.

 
Build and Deploy
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line._

1. Run mvn install to build the WAR
2. Configure JNDI and database
3. Deploy the artefact
 
JNDI and Database
-------------------------

Configuration of JNDI: We have two JNDI Datasources (emulating the production scenario)
Configuration is done under Tomcat _conf_ directory in the _conf.xml_ file:

    <Context>
    
        <!-- Default set of monitored resources. If one of these changes, the    -->
        <!-- web application will be reloaded.                                   -->
        <WatchedResource>WEB-INF/web.xml</WatchedResource>
        <WatchedResource>${catalina.base}/conf/web.xml</WatchedResource>
    
        <!-- Uncomment this to disable session persistence across Tomcat restarts -->
        <!--
        <Manager pathname="" />
        -->
    
        <!-- Uncomment this to enable Comet connection tacking (provides events
             on session expiration as well as webapp lifecycle) -->
        <!--
        <Valve className="org.apache.catalina.valves.CometConnectionManagerValve" />
        -->
    	<Resource
        name="jdbc/db-one"
        auth="Container"
        type="javax.sql.DataSource"
        maxTotal="100"
        maxIdle="30"
        maxWaitMillis="10000"
        driverClassName="org.h2.Driver"
        url="jdbc:h2:tcp://localhost/~/testdb"
        username="sa"
        password=""
        />
    	<Resource
        name="jdbc/db-two"
        auth="Container"
        type="javax.sql.DataSource"
        maxTotal="100"
        maxIdle="30"
        maxWaitMillis="10000"
        driverClassName="org.h2.Driver"
        url="jdbc:h2:tcp://localhost/~/testdb"
        username="sa"
        password=""
        />	
    </Context>

Don't forget to add the h2 jar file to the bootstrap classpath of tomcat (the _lib_ folder under the tomcat installation).

Start h2 in server mode: java -jar <your path to h2-jar>/<h2.jar>.

Create and insert test data with the scripts under src/main/resources/sql.


Run it in the IDE
--------------------

1. Install Tomcat 8 in your IDE
2. Deploy the application
3. Start tomcat with -Dspring.profiles.active=prod

Access the application
-------------------------

The application will be running at the following URL: <http://localhost:8080/api/echo/{item}>.
You can send a GET request adding and item to the database and retrieve some example data (okay the GET semantic does not exactly match here).
The Rest interface is not on focus here, so its just to allow some interaction with user. 

Run the integration test
--------------------

This example provides integration tests. By default, you can run it with maven without special consideration

        mvn clean install

For integration testing a h2 in-memory database is going to configured (Embedded h2) 

You can run the test also within the IDE

Here you need to add -Dspring.profiles.active=int-test to the Testrunner