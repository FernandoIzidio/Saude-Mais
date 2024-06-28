#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: $0 <subdomain>"
    exit 1
fi

hosts_file="/etc/hosts"

if [ ! -f "$hosts_file" ]; then
    echo "Hosts file not found. Please check your system configuration."
    exit 1
fi


subdomain="$1"
entry="127.0.0.1 $subdomain"
echo "$entry" >> "$hosts_file"

echo "Subdomain entry added to the hosts file successfully: $subdomain"