Spring Batch meets SAP Hana - Showcase 
====================================================================================


Author: Christian Laboranowitsch

Level: Advanced

Technologies: Spring Boot, Spring JDBC, Spring Batch, Spring Rest, Spring Profiles, Spring Testing, SAP Hana, Mockito, Tomcat, Maven

Summary: An example that shows how to integrate Spring Batch and SAP Hana

Source: <https://github.com/claboran/spring-boot-jndi-multi>

What is it?
-----------

It is a sample - showcase , a deployable Maven 3 project to help you to set up a complete example running Spring Batch in conjunction with SAP Hana.
It is full blown real world application focusing on showing several important aspects of integrating SAP Hana and Spring Batch. SAP Hana is an unsupported
database platform for Spring Batch. So several challenges needs to be addressed:

1. Implement Sequence generation for Hana
2. Extend Spring Batch infrastructure to support SAP Hana
3. Provide Spring Batch Tables for SAP Hana
4. Usage of the SAP Hana Cloud platform Developer Center <https://hcp.sap.com/developers.html>

Another important point is the ability to do cross database platform development, seamless switching from H2 to SAP Hana should be possible.

A few words about the functional model. It is a real world use case taken from the utility industry. Importing timeseries data from CSV files to a datamarts is 
a common use case. The example data has been taken from AEMO an Australian service provider: 

<http://www.aemo.com.au/Electricity/Data/Price-and-Demand/Price-and-Demand-Graphs/Current-Trading-Interval-Price-and-Demand-Graph-NSW>
   
Fact data are Total Demand and Trading Interval Price on a 30 min. base for a certain control area (in the example New South Wales NSW).
 
A little Star Schema is going to be used. One Fact Table holding the Regional Reference Price RPR and the Total Demand for the control area:
    
    F_ENERGY_PRICE_DEMAND

The Dimensions a splitted across three other tables:

    D_REGION -> control area
    D_PERIOD_TYPE -> Period type (actual trading or forecast)
    D_DATE_TIME -> Holding the date time attributes (very simplified)

For the Fact table a last update win strategy has been taken into account.
Batch Jobs will be started in parallel, to overcome "DuplicateKey" problems when Dimensions are going to be written the first time, Spring Batch is configured with
Retries. So in case of an error of that kind a retry usually solves the problem.


System requirements
-------------------

1. All you need to build this project is Java 8.0 (Java SDK 1.8), Maven 3.1 or better.
2. An SAP Hana developer account.

Firs things first: Sign up for a free SAP Hana Developer account here: <https://hcp.sap.com/developers.html>

If everything worked out as expected, you will end up with a cryptic user account like p194194567trial.
Create a shared HANA database schema (eg. spring-batch) unfortunately there is no more detail information 
in the Hanatrial-cockpit available. I would expect to see my tables or something else useful (we will get to that later).

You need to download the SAP Hana Cloud Platform SDK from here: <https://tools.hana.ondemand.com/#cloud>

Take the latest "Java Web Tomcat 8" from the download section (a package starting with _neo-_).

Unzip the archive to an arbitrary location on your devbox.

Extract the JDBC driver (ngdbc.jar) from the archive (you will find the driver in the archive under: repository/.archive/lib/ngdbc.jar)
The driver is closed source, so it is NOT available from public Maven repositories!

Put the driver either to your local maven repository with:

```
mvn install:install-file -Dfile=<path-to-file> -DgroupId=com.sap.db \
    -DartifactId=ngdbc -Dversion=1.0.0 -Dpackaging=jar
```

or even better upload it as third party dependency to your own Nexus or Artifactory Repository.

To connect with your Hana cloud instance you have to launch a so called "tunnel".

Go to the tools folder of the extracted archive.

Start the tunnel with the following command (here your user account and the schema you once created gets into the game):

```
./neo.sh open-db-tunnel -h hanatrial.ondemand.com -u P194194567 -a p194194567trial --id spring-batch
```
Provide the password of your SAP Hana developer account.

You should see something like:

```
Opening tunnel...

Tunnel opened.

Use these properties to connect to your schema:
  Host name        : localhost
  Database type    : HANA
  JDBC Url         : jdbc:sap://localhost:30015/?currentschema=NEO_5TANXS63TTZXC106VSBR7IFT7
  Instance number  : 00
  User             : DEV_78OP47S1OEYHZPCKB6JGVZQ7F
  Password         : Nz95izoPft6B2qp
  Schema name      : NEO_5TANXS63TTZXC106VSBR7IFT7

This tunnel will close automatically in 24 hours on May 31, 2016 at 10:30 AM or when you close the shell.
Press ENTER to close the tunnel now.
```

You can copy the important JDBC properties directly from the output. The only things that changes on each connect is the password (what I have seen so far).

##Important step

Add the database properties to the application.properties file in the root folder of the project.
 
Besides that feel free to take a first test drive with your favourite database tool... :-) 

The application of this project is designed to run on Tomcat (tested only with Spring Boot Embedded Tomcat).

 
Build Deploy and Run
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line._

1. Run the Test with H2 database - mvn clean install -Pint-test-h2
2. Run the tests with Hana database - mvn clean install -Pint-test-hana
4. Configure the input directory for uploading the files
5. Copy example files from src/main/resources/testdata to the input directory  
6. Run the spring batch application on H2 - use the following parameter: spring.profiles.active=dev-h2 debug=true 
7. Use a Rest client of choice for starting the import operation
8. Rest POST operation is started via: <http://localhost:8080/api/launch>
9. POST data is a simple list of the files for importing ["GRAPH_30NSW1-220316.csv", "GRAPH_30NSW1-270516.csv", "GRAPH_30NSW1-300516.csv"]
10. Repeat steps 6-9 for the Hana Dev Profile: Run the Spring Batch application - use the following parameter: spring.profiles.active=dev-hana debug=true
11. Use your database tool of choice to create the PROD data structures on SAP Hana - use src/main/resources/sql/create-tables-prod.sql
12. Repeat steps 6-9 for the Hana Prod Profile: Run the Spring Batch application - use the following parameter: spring.profiles.active=prod debug=true
