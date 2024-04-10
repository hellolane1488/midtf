#!/usr/bin/env bash

# this file is the method of obtaining information through the shell in class OS
# shellcheck disable=SC1009

LC_ALL=C
LANG=C

# \u001b[34m | BLUE
# \u001b[0m | RESET TO NORMAL
# ANSI-codes


# if you want to add anything to this hardcode,
# then remember that you will need to use escape characters for better output later

get_CPU_info() {
  printf "\u001b[34mCPU(s) scaling MHZ:\u001b[0m "
  lscpu | grep 'CPU(s) scaling MHz' | awk -F ':' '{print $2}' | sed -e 's/^[ \t]*//'
  # get the CPU(s) scaling MHz and del <TAB's>

  printf "\t\t  \u001b[34mCPU max MHz:\u001b[0m "
  lscpu | grep 'CPU max MHz' | awk -F ':' '{print $2}' | sed -e 's/^[ \t]*//'
  # get the CPU max MHZ and del <TAB's>

  printf "\t\t  \u001b[34mCPU min MHz:\u001b[0m "
  lscpu | grep 'CPU min MHz' | awk -F ':' '{print $2}' | sed -e 's/^[ \t]*//'
  # get the CPU min MHz and del <TAB's>

  printf "\t\t  \u001b[34mCache size:\u001b[0m "
  grep 'cache size' /proc/cpuinfo | awk '{print $4 $5}' | tail -n 1
  # get latest Cache size

  printf "\t\t  \u001b[34mVirtualization:\u001b[0m "
  lscpu | grep 'Virtualization' | awk -F ':' '{print $2}' | sed -e 's/^[ \t]*//'
  # get the Virtualization and del <TAB's>

  printf "\t\t  \u001b[34mTemperature:\u001b[0m "
  sensors | grep -E "^(Core|temp)" | awk '{print $2}' | head -n 1
  # get the latest processor temperature by key string Core or temp
}

get_GPU_info() {
  printf "\u001b[34mManufacturer:\u001b[0m "
  lspci -vnn | grep -i VGA -A 4 | awk -F 'Subsystem:' '{print $2}' | awk -F '[' '{print $1}' | perl -pe 's/^\s+|\s+$//g'
  # get the GPU manufacturer and del <SPACE's>

  printf "\n\t\t  \u001b[34mOpenGL Version:\u001b[0m "
  glxinfo | grep 'OpenGL version' | awk -F ':' '{print $2} ' | perl -pe 's/^\s+|\s+$//g'

  printf "\n\t\t  \u001b[34mKernel driver:\u001b[0m "
  lspci -vnn | grep -i VGA -A 12 | awk -F 'Kernel driver in use:' '{print $2}' | perl -pe 's/^\s+|\s+$//g'
  # get the Kernel driver and del <SPACE's>

  printf "\n\t\t  \u001b[34mKernel modules:\u001b[0m "
  lspci -vnn | grep -i VGA -A 12 | awk -F 'Kernel modules:' '{print $2}' | perl -pe 's/^\s+|\s+$//g'
  # get the Kernel modules and del <SPACE's>

  printf "\n\t\t  \u001b[34mMonitor resolution:\u001b[0m "
  xrandr | awk '/\*/ {print $1}'
  # get current monitor resolution
}

case "$1" in
  "cpu") get_CPU_info ;;
  "gpu") get_GPU_info ;;
  "gpuName") lspci | grep -i vga | awk -F '[' '{print $2}' | awk -F ']' '{print $1}' ;;
    # get the GPU name
  "motherboardName")
      cat /sys/class/dmi/id/board_name
      # get the motherboard name
    ;;
  "HDName")
      lsblk -o NAME,MODEL | grep -E '^(nvme|sd)' | awk '{$1=""; sub(/^[[:space:]]+/, ""); printf "%s%s", (NR>1 ? ", " : ""), $0;} END{print "";}'
      # get the names and models of the disk(s)
      # by the key lines nvme or sd and if there are several of them,
      # then separate them with a comma
    ;;
  *)
    echo "error receiving argument"
    exit 1
    ;;
esac