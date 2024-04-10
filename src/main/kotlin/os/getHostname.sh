#!/usr/bin/env bash

# this file is the command for the function getHostname in class OS

LC_ALL=C
LANG=C

# shellcheck disable=SC2059
printf "$HOSTNAME"
# get the hostname of the machine, for example: user@hostname <- this