package mid.vet.os

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Serializable

class OS {
    private val runtime: Runtime = Runtime.getRuntime()
    /* initialization by an instance of a class, what else can I say? */

//    private val path: Process = runtime.exec(arrayOf("pwd"))
//    private val pwd = String(path.inputStream.readAllBytes(), Charsets.UTF_8).split("\n")[0]
    /* get path to root of this program */


    /* in "cmd" lists - immediate lines for later calling commands */
    /* in the "process", calling instances of classes and applying the .exec() functions to them, where we insert our command */
    /* as a "result" we get the output from the standard stream */
    /* in "reader" let's get not a list, but the entire string */
    /* lines contains the result of reading lines from the reader object */
    /* The perl regular expression "perl -pe 's/^\s+|\s+$//g'" is used to ensure that there are no extra spaces in the output */

    private fun executeCommand(command: Array<String>): String{
        val process = runtime.exec(command)
        val result = process.inputStream.bufferedReader().use { it.readText() }

        process.waitFor()
        return result
    }

    fun getMemory(): Serializable {
        val process = runtime.exec("free -m")

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val lines = reader.readLines()

        val totalMemoryLine = lines.find { it.contains("Mem:") }
        val usedMemoryLine = lines.find { it.contains("Mem:") }
        /* Using a lambda, we extract from a string a substring containing “Mem:” */

        val totalMemory = totalMemoryLine?.split(Regex("\\s+"))?.get(1)?.toIntOrNull() ?: 0
        val usedMemory = usedMemoryLine?.split(Regex("\\s+"))?.get(2)?.toIntOrNull() ?: 0
        /* get an element that is converted to Integer if all is well */

        return "${usedMemory}MiB / ${totalMemory}MiB"
    }

    fun getArchitecture(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "uname -m | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getDistro(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "lsb_release -d | cut -f2- | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getUptime(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "uptime -p | awk -F 'up ' '{print \$2}' | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
        /* Did you know that the record for failure-free operation as of 2024 is 31 years? */
    }

    fun getLaunchTime(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "uptime -s | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getHostname(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "bash /usr/share/MIDTF/src/main/kotlin/os/getHostname.sh"
            /* adding the full path to call this script correctly  */
        ))
    }

    fun getCPU(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "lscpu -p=MODELNAME | awk -F ':' '{print $2}' | sed -e 's/^[ \t]*//' | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getCPUFrequency(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "cpupower frequency-info | grep 'available frequency steps:' | awk -F ':' '{print $2}' | awk -F ',' '{print $1}' | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getInfoAboutCPU(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "bash /usr/share/MIDTF/src/main/kotlin/os/regComponentInfo.sh cpu"
        ))
    }

    fun getGPU(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "bash /usr/share/MIDTF/src/main/kotlin/os/regComponentInfo.sh gpuName | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getInfoAboutGPU(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "bash /usr/share/MIDTF/src/main/kotlin/os/regComponentInfo.sh gpu"
        ))
    }

    fun getMotherboard(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "bash /usr/share/MIDTF/src/main/kotlin/os/regComponentInfo.sh motherboardName | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getDrive(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "bash /usr/share/MIDTF/src/main/kotlin/os/regComponentInfo.sh HDName"
        ))
    }

    fun getShell(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "bash /usr/share/MIDTF/src/main/kotlin/os/regTERMInfo.sh shell | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getDE(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "bash /usr/share/MIDTF/src/main/kotlin/os/regTERMInfo.sh de | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getWM(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "bash /usr/share/MIDTF/src/main/kotlin/os/regTERMInfo.sh wm | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getWMTheme(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "bash /usr/share/MIDTF/src/main/kotlin/os/regTERMInfo.sh wmTheme | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getGTKTheme(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "bash /usr/share/MIDTF/src/main/kotlin/os/regTERMInfo.sh gtkTheme | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getGTKIconTheme(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "bash /usr/share/MIDTF/src/main/kotlin/os/regTERMInfo.sh gtkIcon | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getFont(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "bash /usr/share/MIDTF/src/main/kotlin/os/regTERMInfo.sh font | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getCursor(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "bash /usr/share/MIDTF/src/main/kotlin/os/regTERMInfo.sh cursor | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getMemoryUsage(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "df -a / | awk 'NR==2 {print $5}' | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }

    fun getNetwork(): String {
        return executeCommand(arrayOf(
            "/bin/bash",
            "-c",
            "ifconfig | grep 'inet ' | awk '{print $2}' | head -n 1 | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }
}