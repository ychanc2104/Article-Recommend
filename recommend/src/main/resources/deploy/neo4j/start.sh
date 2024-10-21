#!/bin/bash

screen -dmS neo4j sudo bash -c "docker run --publish=7474:7474 --publish=7687:7687 --volume=/var/neo4j/data:/data -e 'NEO4J_AUTH=neo4j/chang951' neo4j:5"

#sudo docker run --publish=7474:7474 --publish=7687:7687 -e 'NEO4J_AUTH=neo4j/chang951' neo4j:5