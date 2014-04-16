#!/bin/bash

if [ -z "$1" ]
  then
    echo "Need the DB name"
    exit 1
fi

$DB_NAME=$1

#The directory /u01/app/oracle/oradata/mynewdb exists on Linux

mkdir -p /u01/app/oracle/oradata/${DB_NAME}

#The directories /u01/logs/my and /u02/logs/my exists on Linux

mkdir -p /u01/logs/${DB_NAME}
mkdir -p /u02/logs/${DB_NAME}

echo > oracle_env.sh <<EOF
ORACLE_SID=${DB_NAME}
export ORACLE_SID
ORACLE_HOME=/u01/app/oracle
export ORACLE_HOME
EOF

./oracle_env.sh