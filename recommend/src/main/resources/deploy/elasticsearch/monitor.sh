#!/bin/bash

# Run 'screen -ls' and save the output
output=$(screen -ls)

# Use 'grep' to find the line with '.python_travel_rec'
line=$(echo "$output" | grep 'elasticsearch')

# Use 'cut' to extract the session ID
session_id=$(echo "$line" | cut -d '.' -f 1)

screen -r $session_id