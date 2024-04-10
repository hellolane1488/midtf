#!/usr/bin/env bash

LC_ALL=C
LANG=C

# shellcheck disable=SC2009

wm=$(ps -e | grep -Eo 'xfwm4|mutter|kwin|openbox')

get_shell() {
  shell_name=$(echo "$SHELL" | awk -F '/' '{print $4}')

  case "$shell_name" in # get shell version
    "bash") shell_version=$(bash --version | awk 'NR==1{print $4}') ;;
    "fish") shell_version=$(fish --version | awk '{print $3}') ;;
    "zsh") shell_version=$(zsh --version | awk '{print $2}') ;;
    "tcsh") shell_version=$(tcsh --version | awk '{print $2}') ;;
    *) shell_version="" ;;
  esac

  printf '%s' "$shell_name $shell_version"
}

get_wm_theme() {
  case "$wm" in # get WM theme
    "xfwm4") wm_theme=$(xfconf-query -c xfwm4 -p /general/theme) ;;
    "mutter") wm_theme=$(gsettings get org.gnome.desktop.wm.preferences theme) ;;
    "kwin") wm_theme=$(kreadconfig5 --file kwinrc --group WM --key theme) ;;
    "openbox") wm_theme=$(grep 'window_manager' ~/.config/lxsession/LXDE/desktop.conf | awk -F '=' '{print $2}');;
    *) wm_theme="" ;;
  esac

  printf '%s' "$wm_theme"
}

get_font() {
  case "$wm" in # get font
    "xfwm4") font=$(gsettings get org.gnome.desktop.interface monospace-font-name | awk -F "'" '{print $2}') ;;
    "mutter") font=$(gsettings get org.gnome.desktop.interface monospace-font-name | awk -F "'" '{print $2}') ;;
    "kwin") font=$(gsettings get org.gnome.desktop.interface monospace-font-name | awk -F "'" '{print $2}') ;;
    "openbox") font=$(grep 'font_name=' ~/.config/lxterminal/lxterminal.conf | cut -d= -f2 | tr -d ' ') ;;
    *) font="Not found" ;;
  esac

  printf '%s' "$font"
}

case "$1" in
  "shell") get_shell ;;
  "de") echo "$XDG_CURRENT_DESKTOP" ;;
  "wm") echo "$wm" ;;
  "wmTheme") get_wm_theme ;;
  "gtkTheme") cat < ~/.config/gtk-3.0/settings.ini | grep 'gtk-theme-name' | awk -F '=' '{print $2}' ;; # get GTK 3.0 Theme Name
  "gtkIcon") cat < ~/.config/gtk-3.0/settings.ini | grep 'gtk-icon-theme-name' | awk -F '=' '{print $2}' ;; # get GTK 3.0 Icon Theme Name
  "font") get_font ;;
  "cursor") xrdb -query | awk '/cursor.theme:/ {printf "%s ", $2} /cursor.size:/ {gsub(":", ""); printf "<%s>\n", $2}' ;; # get Cursor name and size
  *)
    echo "error receiving argument"
    exit 1
    ;;
esac