#!/bin/bash

if [ "$1" == "" || "$2" == ""]; then
    echo "Usage: ./run.sh <artist_url> <http_request_interval>"
    return
fi

java -cp target/lyricsscraper-1.0-SNAPSHOT-jar-with-dependencies.jar com.cincogajos.lyricsscraper.Main $1 $2