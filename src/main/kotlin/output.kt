package mid.vet

import mid.vet.extensions.blue
import mid.vet.extensions.green
import mid.vet.extensions.purple
import mid.vet.os.*

fun outputInformation(args: String) {
    val os = OS()
    val component = Components()
    val shell = Shell()

    val out = buildString {
        appendLine()
        appendLine("\t" + OSInfo.username.green() + "@" + os.getHostname().green())
        appendLine("\t${Symbols.DASHES}")
        appendLine("\tOS: ".purple() + "${OSInfo.osName} ${os.getArchitecture()}")
        appendLine("\tDistro: ".purple() + os.getDistro())
        appendLine("\tKernel: ".purple() + OSInfo.osVersion)
        appendLine("\tShell: ".purple() + shell.getShell())

        if ("all" in args) {
            appendLine("\tFont: ".purple() + shell.getFontName())
            appendLine("\tCursor: ".purple() + shell.getCursor())
            appendLine("\tDE: ".purple() + shell.getDEName())
            appendLine("\tWM: ".purple() + shell.getWMName())
            appendLine("\tWM Theme: ".purple() + shell.getWMTheme())
            appendLine("\tGTK Theme: ".purple() + shell.getGTKTheme())
            appendLine("\tGTK Icon: ".purple() + shell.getGTKIcon())
        }

        appendLine("\tMachine Uptime: ".purple() + os.getUptime())
        appendLine("\tMachine launch in ".purple() + os.getLaunchTime())
        appendLine("\tCPU: ".purple() + "${component.getCPU()} @ ${component.getCPUFrequency()}")
        appendLine("\t\tAbout CPU ->".blue())
        appendLine("\t\t  CPU(s) Scaling MHz: ".blue() + component.getScalingMHz())
        appendLine("\t\t  Max MHz: ".blue() + component.getCPUMaxMHz())
        appendLine("\t\t  Min MHz: ".blue() + component.getCPUMinMHz())
        appendLine("\t\t  Cache Size: ".blue() + component.getCacheSize())
        appendLine("\t\t  Virtualization: ".blue() + component.getVirtualization())
        appendLine("\t\t  Temperature: ".blue() + component.getCPUTemperature())
        appendLine("\tGPU: ".purple() + component.getGPU())
        appendLine("\t\tAbout GPU ->".blue())
        appendLine("\t\t  Manufacturer: ".blue() + component.getGPUManufacturer())
        appendLine("\t\t  Open GL Version: ".blue() + component.getOpenGLVersion())
        appendLine("\t\t  Kernel driver: ".blue() + component.getKernelDriver())
        appendLine("\t\t  Kernel modules: ".blue() + component.getKernelModules())
        appendLine("\t\t  Monitor resolution: ".blue() + component.getMonitorResolution())
        appendLine("\tDisk(s): ".purple() + component.getDisk())
        appendLine("\tMotherboard: ".purple() + component.getMotherboard())
        appendLine("\tMEM: ".purple() + component.getMemory())
        appendLine("\t${Symbols.DASHES}")
        appendLine("\tStorage Usage: ".purple() + "${component.getStorageUsage()} (root)")
        appendLine("\tNetwork: ".purple() + os.getNetwork())
    }
    println(out)
}

object Symbols {
    const val DASHES = "------------"
}

object AnsiColor {
    const val RESET = "\u001B[0m"
    const val PURPLE = "\u001B[35m"
    const val BLUE = "\u001B[34m"
    const val GREEN = "\u001b[32m"
}