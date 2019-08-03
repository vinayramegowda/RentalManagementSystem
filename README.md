# RentalManagementSystem
Commands to create a database using HSQLDB
java -classpath lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:hsqldb/ThriftyDatabase --dbname.0 RentalManagementdb

after creating the database please use the following command to start the database engine
java -cp ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:rentalDB/rentalDB --dbname.0 rentalDB

