#!/bin/sh
### BEGIN INIT INFO
# Provides:          vsftpdg
# Required-Start:    $local_fs $remote_fs $network $syslog
# Required-Stop:     $local_fs $remote_fs $network $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# X-Interactive:     true
# Short-Description: Start/stop vsftpdg server
### END INIT INFO

case $1 in
    start)
        echo "Starting Server ..."
        if [ ! -f ~/server-run/pid ]; then
            nohup java -jar ~/server-run/server.jar ~/server-run 2>> ./nohup.out >> ./nohup.out &
            echo $! > ~/server-run/pid
            echo "server started ..."
        else
            echo "server is already running ..."
        fi
    ;;
    stop)
        if [ -f ~/server-run/pid ]; then
            PID=$(cat ~/server-run/pid);
            echo "Stopping server ..."
			echo $PID;
            kill $PID;
            echo "server stopped ..."
            rm ~/server-run/pid
        else
            echo "server is not running ..."
        fi
    ;;
esac
