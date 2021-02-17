# About
This code is only meant to connect to an old database take the information and generate a JSON file conataining the information but adjusted to the schema of the the new database.

## Creating the database
### Schema
`cat ./db/db.sql | mysql -u <username> -p`
### Insert data
`cat ./db/dump.sql | mysql -u <username> -p`
### Used databse
This script was created to use MariaDB/MySQL db connectors woyúld have to be changed if another DB is to be used.

## Running the script
Run this script using the following command: `mvn clean compile exec:java -Dexec.cleanupDaemonThreads=false`
