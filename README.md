[![Build Status](https://travis-ci.org/RomanMozhaev/job4j_tracker.svg?branch=master)](https://travis-ci.org/RomanMozhaev/job4j_tracker)
[![codecov](https://codecov.io/gh/RomanMozhaev/job4j_tracker/branch/master/graph/badge.svg)](https://codecov.io/gh/RomanMozhaev/job4j_tracker)
# job4j
# Tracker & Tracker SQL

This is a console application. The tracker contains tickets.
The tracker interface has functions for adding, deleting, editing, finding by ticket name or id, or reviewing all tickets.
Each ticket has id, name and description. 
The tracker has two realizations which differ by tickets storage:
1) Storage in memory (ArrayList). Use Tracker in StartUI.
2) Storage in database (PostgreSQL). Use TrackerSQL in StartUI.

The following technologies were used in the application:
1) Liquidbase - version control for database
2) PostgreSQL - relational database management system
3) Log4j & Slf4j - the logger LOG4J with SLF4J wrapper
4) JUnit - unit tasting 

JAVA facilities as collections, JDBC, interfaces etc. were used.
