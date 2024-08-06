#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: $0 <subdomain> <platform>"
    exit 1
fi

if [ -z "$2" ]; then
    echo "Usage: $0 <subdomain> <platform>"
    exit 1
fi


subdomain="$1"
platform="$2"


case "$platform" in
    Linux)
        hosts_file="/etc/hosts"
        ;;
    Windows)
        hosts_file="C:\Windows\System32\drivers\etc"
        ;;
    *)
        echo "Unsupported platform. Supported platforms are: linux, windows."
        exit 1
        ;;
esac


if [ ! -w "$hosts_file" ]; then
    echo "Cannot write to the hosts file at $hosts_file. Please check permissions."
    exit 1
fi



entry="127.0.0.1 $subdomain"
echo "$entry" >> "$hosts_file"

echo "Subdomain entry added to the hosts file successfully: $subdomain"