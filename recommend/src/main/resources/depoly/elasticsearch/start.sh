#!/bin/bash

FILE_PATH="./elasticsearch-8.12.2"
screen -dmS elasticsearch bash -c "$FILE_PATH/bin/elasticsearch"
