package mid.vet

import mid.vet.extensions.blue
import mid.vet.extensions.green
import mid.vet.extensions.purple
import mid.vet.os.*

fun output() {
    val os = OS()

    val out = buildString {
        appendLine()
        append("\t" + OSInfo.username.green() + "@" + "${os.getHostname()}\n".green())
        append("\t${Symbols.DASHES}\n")
        append("\tOS: ".purple() + "${OSInfo.osName} ${os.getArchitecture()}\n")
        append("\tDistro: ".purple() + "${os.getDistro()}\n")
        append("\tKernel: ".purple() + "${OSInfo.osVersion}\n")
        append("\tMachine Uptime: ".purple() + "${os.getUptime()}\n")
        append("\tMachine launch in ".purple() + "${os.getLaunchTime()}\n")
        append("\tShell: ".purple() + "${os.getShell()}\n")
        append("\tFont: ".purple() + "${os.getFont()}\n")
        append("\tCursor: ".purple() + "${os.getCursor()}\n")
        append("\tDE: ".purple() + "${os.getDE()}\n")
        append("\tWM: ".purple() + "${os.getWM()}\n")
        append("\tWM Theme: ".purple() + "${os.getWMTheme()}\n")
        append("\tGTK Theme: ".purple() + "${os.getGTKTheme()}\n")
        append("\tGTK Icon: ".purple() + "${os.getGTKIconTheme()}\n")
        append("\tCPU: ".purple() + "${os.getCPU()} @ ${os.getCPUFrequency()}\n")
        append("\t\tAbout CPU ->\n".blue())
        append("\t\t  ${os.getInfoAboutCPU()}")
        append("\tGPU: ".purple() + "${os.getGPU()}\n")
        append("\t\tAbout GPU ->\n".blue())
        append("\t\t  ${os.getInfoAboutGPU()}")
        append("\tDisk(s): ".purple() + os.getDrive())
        append("\tMotherboard: ".purple() + "${os.getMotherboard()}\n")
        append("\tMEM: ".purple() + "${os.getMemory()}\n")
        append("\t${Symbols.DASHES}\n")
        append("\tStorage Usage: ".purple() + "${os.getMemoryUsage()} (root)\n")
        append("\tNetwork: ".purple() + "${os.getNetwork()}\n")
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