#!/usr/bin/env bash
MWSReportsPath="$PWD/Web/Crons/APICalls/"
cd $MWSReportsPath

if [ $1 == 'APICalls' ];
then
     echo 'APICalls'
     java -jar APICalls.jar Calls > logFiles/call.log & java -jar APICalls.jar Sql > logFiles/sql.log 
fi



