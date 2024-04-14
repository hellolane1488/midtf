package mid.vet.os

class OS {
    private val runtime = Runtime.getRuntime()

    fun getArchitecture(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "uname -m"
        ))
    }

    fun getDistro(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "lsb_release -d | cut -f2-"
        ))
    }

    fun getTerminal(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "xprop -id \$WINDOWID | grep 'WM_CLASS(STRING)' | awk -F '[\",]+' '{print $2}'"
        ))
    }

    fun getUptime(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "uptime -p | awk -F 'up ' '{print $2}'"
        ))
        /* Did you know that the record for failure-free operation as of 2024 is 31 years? */
    }

    fun getLaunchTime(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "uptime -s"
        ))
    }

    fun getHostname(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "echo \$HOSTNAME"
        ))
    }

    fun getNetwork(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "ifconfig | grep 'inet 192' | awk '{print $2}' | head -n 1"
        ))
    }
}