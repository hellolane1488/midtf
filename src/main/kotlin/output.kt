package mid.vet

import mid.vet.extensions.blue
import mid.vet.extensions.green
import mid.vet.extensions.purple
import mid.vet.extensions.tree
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
    val memoryGPU = component.getGPUmemoryInfo()
    val motherboard = component.getMotherboard()
    val storageUsage = component.getStorageUsage()
    val network = os.getNetwork()

    val dashes = Symbols.DASH.repeat(hostname.length + OSInfo.username.length + 1)

    val out = buildString {
        appendLine()
        appendLine("${OSInfo.username.green()}@${hostname.green()}".tree().purple())
        appendLine(dashes.tree().purple())

        if (OSInfo.osName.isNotBlank() || architecture.isNotBlank()) appendLine("├──OS: ".purple() + "${OSInfo.osName} $architecture")
        if (distro.isNotBlank()) appendLine("├──Distro: ".purple() + distro)
        if (OSInfo.osVersion.isNotBlank()) appendLine("├──Kernel: ".purple() + OSInfo.osVersion)
        if (shellInfo.isNotBlank()) appendLine("├──Shell: ".purple() + shellInfo)
        if (terminal.isNotBlank()) appendLine("├──Terminal: ".purple() + terminal)
        if (de.isNotBlank()) appendLine("├──DE: ".purple() + shell.getDEname())

        if ("all" in args) {
            if (font.isNotBlank()) appendLine("├──Font: ".purple() + font)
            if (cursor.isNotBlank()) appendLine("├──Cursor: ".purple() + cursor)
            if (wmName.isNotBlank()) appendLine("├──WM: ".purple() + wmName)
            if (wmTheme.isNotBlank()) appendLine("├──WM Theme: ".purple() + wmTheme)
            if (gtkTheme.isNotBlank()) appendLine("├──GTK Theme: ".purple() + gtkTheme)
            if (gtkIcon.isNotBlank()) appendLine("├──GTK Icon: ".purple() + gtkIcon)
        }

        if (uptime.isNotBlank()) appendLine("├──Machine Uptime: ".purple() + uptime)
        if (launchTime.isNotBlank()) appendLine("├──Machine launch in ".purple() + launchTime)

        appendLine("├──CPU: ".purple() + "${component.getCPU()} @ ${component.getCPUfrequency()}")

        if (scalingMHzCPU.isNotBlank()) appendLine("├──CPU(s) Scaling MHz: ".tree().blue() + scalingMHzCPU)
        if (maxMHzCPU.isNotBlank()) appendLine("├──Max MHz: ".tree().blue() + maxMHzCPU)
        if (minMHzCPU.isNotBlank()) appendLine("├──Min MHz: ".tree().blue() + minMHzCPU)
        if (cacheSizeCPU.isNotBlank()) appendLine("├──Cache Size: ".tree().blue() + cacheSizeCPU)
        if (virtualizationCPU.isNotBlank()) appendLine("├──Virtualization: ".tree().blue() + virtualizationCPU)
        if (temperatureCPU.isNotBlank()) appendLine("└──Temperature: ".tree().blue() + temperatureCPU)

        appendLine("├──GPU: ".purple() + component.getGPU())

        if (manufacturerGPU.isNotBlank()) appendLine("├──Manufacturer: ".tree().blue() + manufacturerGPU)
        if (openGLVersion.isNotBlank()) appendLine("├──Open GL Version: ".tree().blue() + openGLVersion)
        if (kernelDriverGPU.isNotBlank()) appendLine("├──Kernel driver: ".tree().blue() + kernelDriverGPU)
        if (kernelModulesGPU.isNotBlank()) appendLine("├──Kernel modules: ".tree().blue() + kernelModulesGPU)
        if (resolution.isNotBlank()) appendLine("└──Monitor resolution: ".tree().blue() + resolution)

        appendLine("├──Disk(s): ".purple() + component.getDisk())

        if (motherboard.isNotBlank()) appendLine("├──Motherboard: ".purple() + motherboard)

        appendLine("├──Memory: ".purple() + component.getMemory())

        if (memoryGPU.isNotBlank()) appendLine("├──Memory GPU: ".purple() + component.getGPUmemoryInfo())

        appendLine(dashes.tree().purple())

        if (storageUsage.isNotBlank()) appendLine("├──Storage Usage: ".purple() + "$storageUsage (root)")
        if (network.isNotBlank()) appendLine("└──Network: ".purple() + network)
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