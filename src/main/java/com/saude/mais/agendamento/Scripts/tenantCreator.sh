#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: $0 <tenant_name>"
    exit 1
fi


base_domain="saude-mais.com.br"
tenant_name="$1"
hosts_file="/etc/hosts"


if [ ! -f "$hosts_file" ]; then
    echo "Hosts file not found. Please check your system configuration."
    exit 1
fi


subdomain="www.$tenant_name.$base_domain"
entry="127.0.0.1 $subdomain"
echo "$entry" >> "$hosts_file"

echo "Subdomain entry added to the hosts file successfully: $subdomain"