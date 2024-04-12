package mid.vet.os

import java.awt.Scrollbar
import java.util.stream.Stream

class Shell {
    private val runtime: Runtime = Runtime.getRuntime()

    private val wm = executeCommand(runtime, arrayOf(
        "/bin/bash",
        "-c",
        "export wm=$(ps -e | grep -Eo 'xfwm4|mutter|kwin|openbox'); echo \$wm"
    ))
    /* get WM Name */

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

    fun getWMName(): String {
        return wm
    }

    fun getWMTheme(): String {
        val wmThemeName = when (wm) {
            "xfwm4" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "xfconf-query -c xfwm4 -p /general/theme"))
            "mutter" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "gsettings get org.gnome.desktop.wm.preferences theme"))
            "kwin" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "kreadconfig5 --file kwinrc --group WM --key theme"))
            "openbox" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "grep 'window_manager' ~/.config/lxsession/LXDE/desktop.conf | awk -F '=' '{print $2}'"))
            else -> "Not found"
        }

        return wmThemeName
    }

    fun getFontName(): String {
        val gsettingsCommand = "gsettings get org.gnome.desktop.interface monospace-font-name | awk -F \"'\" '{print $2}'"

        val fontName = when (wm) {
            "xfwm4" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", gsettingsCommand))
            "mutter" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", gsettingsCommand))
            "kwin" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", gsettingsCommand))
            "openbox" -> executeCommand(runtime, arrayOf("/bin/bash", "-c", "grep 'font_name=' ~/.config/lxterminal/lxterminal.conf | cut -d= -f2 | tr -d ' '"))
            else -> "Not found"
        }

        return fontName
    }

    fun getCursor(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "xrdb -query | awk '/cursor.theme:/ {printf \"%s \", $2} /cursor.size:/ {gsub(\":\", \"\"); printf \"<%s>\\n\", $2}'"
        ))
    }

    fun getGTKTheme(): String {
        val themeGTK = executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "cat < ~/.config/gtk-3.0/settings.ini | grep 'gtk-theme-name' | awk -F '=' '{print $2}'"
        ))

        return if (themeGTK.isEmpty()) {
            "Not found"
        } else {
            themeGTK
        }
    }

    fun getGTKIcon(): String {
        val iconGTK = executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "cat < ~/.config/gtk-3.0/settings.ini | grep 'gtk-icon-theme-name' | awk -F '=' '{print $2}'"
        ))

        return if (iconGTK.isEmpty()) {
            "Not found"
        } else {
            iconGTK
        }
    }

    fun getDEName(): String {
        val nameDE = executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "echo \"\$XDG_CURRENT_DESKTOP\""
        ))

        return if (nameDE.isEmpty()) {
            "Not found"
        } else {
            nameDE
        }
    }
}