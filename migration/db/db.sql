CREATE DATABASE IF NOT EXISTS olddb;

DROP TABLE IF EXISTS olddb.role;
DROP TABLE IF EXISTS olddb.person;
DROP TABLE IF EXISTS olddb.availability;
DROP TABLE IF EXISTS olddb.competence;
DROP TABLE IF EXISTS olddb.competence_profile;

CREATE TABLE olddb.role (role_id BIGINT PRIMARY KEY,name VARCHAR(255));
CREATE TABLE olddb.person (person_id BIGINT PRIMARY KEY,name VARCHAR(255),surname VARCHAR(255),ssn VARCHAR(255),email VARCHAR(255),password VARCHAR(255),role_id BIGINT REFERENCES role,username VARCHAR(255));
CREATE TABLE olddb.availability (availability_id BIGINT PRIMARY KEY,person_id BIGINT REFERENCES person,from_date DATE,to_date DATE);
CREATE TABLE olddb.competence (competence_id BIGINT PRIMARY KEY,name VARCHAR(255));
CREATE TABLE olddb.competence_profile (competence_profile_id BIGINT PRIMARY KEY,person_id BIGINT REFERENCES person,competence_id BIGINT REFERENCES competence,years_of_experience NUMERIC(4,2));

