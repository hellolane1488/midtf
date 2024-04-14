package mid.vet.os

class Shell {
    private val runtime: Runtime = Runtime.getRuntime()

    private val wm = executeCommand(runtime, arrayOf(
        "/bin/bash",
        "-c",
        "ps aux | grep -Eo 'xfwm4|mutter|kwin|openbox|enlightenment|lxqt|mate' | head -n 1"
    ))
    private val gtkPath = executeCommand(runtime, arrayOf(
        "/bin/bash",
        "-c",
        "echo \$GTK2_RC_FILES"
    ))

    fun getShell(): String {
        val shellName = executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "echo \"\$SHELL\" | awk -F '/' '{print $4}'"
        ))

        val shellVersion = when (shellName) {
            "bash" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "bash --version | awk 'NR==1{print $4}'"))
            "fish" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "fish --version | awk '{print $3}'"))
            "zsh" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "zsh --version | awk '{print $2}'"))
            "tcsh" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "tcsh --version | awk '{print $2}'"))
            else -> ""
        }

        return "$shellName $shellVersion"
    }

    fun getWMname(): String {
        return wm
    }

    fun getWMtheme(): String {
        val wmThemeName = when (wm) {
            "xfwm4" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "xfconf-query -c xfwm4 -p /general/theme"))
            "mutter" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "gsettings get org.gnome.desktop.wm.preferences theme"))
            "kwin" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "kreadconfig5 --file kwinrc --group WM --key theme"))
            "openbox" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "grep 'window_manager' ~/.config/lxsession/LXDE/desktop.conf | awk -F '=' '{print $2}'"))
            "enlightenment" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "enlightenment_remote -e 'window_manager'"))
            "lxqt", "mate" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "xprop -root | grep -o '_NET_WM_NAME(UTF8_STRING) = \"[^\"]*\"' | cut -d'\"' -f2"))
            else -> ""
        }

        return wmThemeName
    }

    fun getFontName(): String {
        val gsettingsCommand = "gsettings get org.gnome.desktop.interface monospace-font-name | awk -F \"'\" '{print $2}'"

        val fontName = when (wm) {
            "xfwm4", "mutter", "kwin" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", gsettingsCommand))
            "openbox" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "grep 'font_name=' ~/.config/lxterminal/lxterminal.conf | cut -d= -f2 | tr -d ' '"))
            "enlightenment" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "enlightenment_remote -e 'font' "))
            "lxqt" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "grep 'FontName' ~/.config/lxqt/globalkeyshortcuts.conf"))
            "mate" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "gsettings get org.mate.interface font-name"))
            else -> ""
        }

        return fontName
    }

    fun getCursor(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "xrdb -query | awk '/cursor.theme:/ {theme=$2} /cursor.size:/ {size=$2} END {printf \"%s, %s\\n\", theme, size}'"
        ))
    }

    fun getGTKtheme(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "cat $gtkPath | grep 'gtk-theme-name' | awk -F '=\"' '{print $2 }' | awk -F '\"' '{print $1}'"
        ))
    }

    fun getGTKicon(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "cat $gtkPath | grep 'gtk-icon-theme-name' | awk -F '=\"' '{print $2 }' | awk -F '\"' '{print $1}'"
        ))
    }

    fun getDEname(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "echo \"\$XDG_CURRENT_DESKTOP\""
        ))
    }
}