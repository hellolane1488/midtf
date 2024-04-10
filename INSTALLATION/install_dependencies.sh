#!/usr/bin/env bash

# this script will read the list of dependencies and pass them to your system's package manager for installation
# If you don't know what this file is, don't touch it.

if ! [ -x "$(command -v sudo)" ]; then
  echo 'Utility "sudo" not found, install sudo.' >&2
  exit 1
fi

if [ -f /etc/debian_version ]; then
  sudo apt update
  # shellcheck disable=SC2024
  sudo apt install -y - < "$(pwd)"/INSTALLATION/packages.txt

elif [ -f /etc/redhat-release ]; then
  # shellcheck disable=SC2024
  sudo yum install -y - < "$(pwd)"/INSTALLATION/packages.txt

elif [ -f /etc/arch-release ]; then
  # shellcheck disable=SC2024
  sudo pacman -S --needed --noconfirm - < "$(pwd)"/INSTALLATION/packages.txt

elif [ -f /etc/SuSE-release ]; then
  # shellcheck disable=SC2024
  sudo zypper install -y - < "$(pwd)"/INSTALLATION/packages.txt

else
  echo 'The script did not recognize your distribution, try running: "sudo <manager> -<flag> - < packages.txt".' >&2
  exit 1
fi