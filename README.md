# JtmConsole
##  About
JtmConsole or Java Task Manager Console, this is my simple project which is useful for recording Tasks and these tasks will then be stored on the Database Server, for the database itself I use MySQL, this is very simple and this also uses Apache Maven as my Project Manager. Unlike other Java projects, this is only built and developed through the Android Terminal Emulator (Termux) but you can still use it on your favorite PC/Laptop.

## Requirements
Before you try it, please pay attention to the availability of the resources needed to avoid errors:
* openjdk-17-jdk or later
* openjdk-17-jre or later
* apache-maven
* mariadb

## Installation
* First of all you download the zip file from this repository or you can also clone this repository
* Then run your MySQL server and import database `mysql.sql` to your database server machine
* Then please go to the project's main directory and run this command `mvn package`, then if after you run the command there is no error you can run the following command `mvn exec:exec` to run the actual program

## Licence
All JtmConsole source code is licensed under the GNU General Public License v3. Please [see](https://www.gnu.org/licenses) the original document for more details.
