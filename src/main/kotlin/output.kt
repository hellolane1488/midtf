package mid.vet

import mid.vet.extensions.*
import mid.vet.os.*

fun outputInformation(args: String) {
    val os = OS()
    val component = Components()
    val shell = Shell()

    val hostname = os.getHostname()
    val architecture = os.getArchitecture()
    val distro = os.getDistro()
    val shellInfo = shell.getShell()
    val terminal = os.getTerminal()
    val de = shell.getDEname()
    val font = shell.getFontName()
    val cursor = shell.getCursor()
    val wmName = shell.getWMname()
    val wmTheme = shell.getWMtheme()
    val gtkTheme = shell.getGTKtheme()
    val gtkIcon = shell.getGTKicon()
    val uptime = os.getUptime()
    val launchTime = os.getLaunchTime()
    val scalingMHzCPU = component.getScalingMHz()
    val maxMHzCPU = component.getCPUmaxMHz()
    val minMHzCPU = component.getCPUminMHz()
    val cacheSizeCPU = component.getCacheSize()
    val virtualizationCPU = component.getVirtualization()
    val temperatureCPU = component.getCPUtemperature()
    val manufacturerGPU = component.getGPUmanufacturer()
    val openGLVersion = component.getOpenGLversion()
    val kernelDriverGPU = component.getKernelDriver()
    val kernelModulesGPU = component.getKernelModules()
    val resolution = component.getMonitorResolution()
    val disk = component.getDisk()
    val memory = component.getMemory()
    val memoryGPU = component.getGPUmemoryInfo()
    val motherboard = component.getMotherboard()
    val storageUsage = component.getStorageUsage()
    val network = os.getNetwork()

    val dashes = Symbols.DASH.repeat(hostname.length + OSInfo.username.length + 1)

    val out = buildString {
        appendLine()
        appendLine("${OSInfo.username.green()}@${hostname.green()}".tree().purple())
        appendLine(dashes.tree().purple())

        appendIfNotBlank("├──OS: ".purple(), "${OSInfo.osName} $architecture")
        appendIfNotBlank("├──Distro: ".purple(), distro)
        appendIfNotBlank("├──Kernel: ".purple(), OSInfo.osVersion)
        appendIfNotBlank("├──Shell: ".purple(), shellInfo)
        appendIfNotBlank("├──Terminal: ".purple(), terminal)
        appendIfNotBlank("├──DE: ".purple(), de)

        if ("all" in args) {
            appendIfNotBlank("├──Font: ".purple(), font)
            appendIfNotBlank("├──Cursor: ".purple(), cursor)
            appendIfNotBlank("├──WM: ".purple(), wmName)
            appendIfNotBlank("├──WM Theme: ".purple(), wmTheme)
            appendIfNotBlank("├──GTK Theme: ".purple(), gtkTheme)
            appendIfNotBlank("├──GTK Icon: ".purple(), gtkIcon)
        }

        appendIfNotBlank("├──Machine Uptime: ".purple(), uptime)
        appendIfNotBlank("├──Machine launch in ".purple(), launchTime)

        appendLine("├──CPU: ".purple() + "${component.getCPU()} @ ${component.getCPUfrequency()}")

        appendIfNotBlank("├──CPU(s) Scaling MHz: ".tree().blue(), scalingMHzCPU)
        appendIfNotBlank("├──Max MHz: ".tree().blue(), maxMHzCPU)
        appendIfNotBlank("├──Min MHz: ".tree().blue(), minMHzCPU)
        appendIfNotBlank("├──Cache Size: ".tree().blue(), cacheSizeCPU)
        appendIfNotBlank("├──Virtualization: ".tree().blue(), virtualizationCPU)
        appendIfNotBlank("└──Temperature: ".tree().blue(), temperatureCPU)

        appendLine("├──GPU: ".purple() + component.getGPU())

        appendIfNotBlank("├──Manufacturer: ".tree().blue(), manufacturerGPU)
        appendIfNotBlank("├──Open GL Version: ".tree().blue(), openGLVersion)
        appendIfNotBlank("├──Kernel driver: ".tree().blue(), kernelDriverGPU)
        appendIfNotBlank("├──Kernel modules: ".tree().blue(), kernelModulesGPU)
        appendIfNotBlank("└──Monitor resolution: ".tree().blue(), resolution)

        appendLine("├──Disk(s): ".purple() + disk)

        appendIfNotBlank("├──Motherboard: ".purple(), motherboard)

        appendLine("├──Memory: ".purple() + memory)

        appendIfNotBlank("├──Memory GPU: ".purple(), memoryGPU)

        appendLine(dashes.tree().purple())

        appendIfNotBlank("├──Storage Usage: ".purple(), "$storageUsage (root)")
        appendIfNotBlank("└──Network: ".purple(), network)
    }
    println(out)
}

object Symbols {
    const val DASH = "-"
    const val TREE = "│"
}

object AnsiColor {
    const val RESET = "\u001B[0m"
    const val PURPLE = "\u001B[35m"
    const val BLUE = "\u001B[34m"
    const val GREEN = "\u001b[32m"
}