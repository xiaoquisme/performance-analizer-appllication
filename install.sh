#!/bin/bash

/root/.jabba/bin/jabba use system@1.11

FLINK_HOME=/root/flink-1.15.0/bin
# repeat the cmd 5 time
for i in {1..5}; do
 $FLINK_HOME/start-cluster.sh
done

$FLINK_HOME/flink run /root/git_log_extract-1.0-all.jar

for i in {1..5}; do
 $FLINK_HOME/stop-cluster.sh
done

java -jar /root/git_log_extract-1.0-all.jar

