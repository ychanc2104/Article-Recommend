#!/bin/bash

FILE_PATH="src/main/resources/deploy/elasticsearch/elasticsearch-8.12.2"
screen -dmS elasticsearch bash -c "$FILE_PATH/bin/elasticsearch"
