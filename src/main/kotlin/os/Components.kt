package mid.vet.os

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Serializable

class Components {
    private val runtime: Runtime = Runtime.getRuntime()
    /* initialization by an instance of a class */

    /* ---CPU--- */
    fun getCPU(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "lscpu -p=MODELNAME | tail -n 1"
        ))
    }

    fun getCPUFrequency(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "cpupower frequency-info | grep 'available frequency steps:' | awk -F ':' '{print $2}' | awk -F ',' '{print $1}'"
        ))
    }

    fun getScalingMHz(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "lscpu | grep 'CPU(s) scaling MHz' | awk -F ':' '{print $2}'"
        ))
    }

    fun getCPUMaxMHz(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "lscpu | grep 'CPU max MHz' | awk -F ':' '{print $2}'"
        ))
    }

    fun getCPUMinMHz(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "lscpu | grep 'CPU min MHz' | awk -F ':' '{print $2}'"
        ))
    }

    fun getCacheSize(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "grep 'cache size' /proc/cpuinfo | awk '{print $4 $5}' | tail -n 1"
        ))
    }

    fun getVirtualization(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "lscpu | grep 'Virtualization' | awk -F ':' '{print $2}'"
        ))
    }

    fun getCPUTemperature(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "sensors | grep -iE \"^(tctl|core|temp)\" | awk '{print $2}' | head -n 1"
        ))
    }
    /* ---CPU--- */

    /* ---GPU--- */
    fun getGPU(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "lspci | grep -i 'vga compatible controller' | awk -F ': ' '{split($2, a, \"[][]\"); print a[length(a)-1]}' | awk -F '/' '{print $1}'"
        ))
    }

    fun getGPUManufacturer(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "lspci | grep -i 'vga compatible controller' | sed 's/.*: \\(.*\\)/\\1/' | sed 's/\\[.*//'"
        ))
    }

    fun getOpenGLVersion(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "glxinfo | grep 'OpenGL version' | awk -F ':' '{print $2} '"
        ))
    }

    fun getKernelDriver(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "lspci -vnn | grep -i VGA -A 12 | awk -F 'Kernel driver in use:' '{print $2}'"
        ))
    }

    fun getKernelModules(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "lspci -vnn | grep -i VGA -A 12 | awk -F 'Kernel modules:' '{print $2}'"
        ))
    }

    fun getMonitorResolution(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "xrandr | awk '/\\*/ {print $1}' | perl -pe 's/^\\s+|\\s+\$//g'"
        ))
    }
    /* ---GPU--- */

    /* ---DISK--- */
    fun getDisk(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "lsblk -o NAME,MODEL | grep -E '^(nvme|sd)' | awk '{$1=\"\"; sub(/^[[:space:]]+/, \"\");" +
            "printf \"%s%s\", (NR>1 ? \", \" : \"\"), $0;} END{print \"\";}'"
        ))
    }

    fun getStorageUsage(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "df -a / | awk 'NR==2 {print $5}'"
        ))
    }
    /* ---DISK--- */

    /* ---MOTHERBOARD--- */
    fun getMotherboard(): String {
        return executeCommand(runtime, arrayOf(
            "/bin/bash",
            "-c",
            "cat /sys/class/dmi/id/board_name"
        ))
    }
    /* ---MOTHERBOARD--- */

    /* ---MEMORY--- */
    fun getMemory(): Serializable {
        val process = runtime.exec("free -m")

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        /* in "reader" let's get not a list, but the entire string */
        val lines = reader.readLines()
        /* lines contains the result of reading lines from the reader object */

        val totalMemoryLine = lines.find { it.contains("Mem:") }
        val usedMemoryLine = lines.find { it.contains("Mem:") }
        /* Using a lambda, we extract from a string a substring containing “Mem:” */

        val totalMemory = totalMemoryLine?.split(Regex("\\s+"))?.get(1)?.toIntOrNull() ?: 0
        val usedMemory = usedMemoryLine?.split(Regex("\\s+"))?.get(2)?.toIntOrNull() ?: 0
        /* get an element that is converted to Integer if all is well */

        return "${usedMemory}MiB / ${totalMemory}MiB"
    }
    /* ---MEMORY--- */
}