package mid.vet

import mid.vet.extensions.blue
import mid.vet.extensions.green
import mid.vet.extensions.purple
import mid.vet.os.*

fun outputInformation(args: String) {
    val os = OS()
    val component = Components()
    val shell = Shell()

    val distro = os.getDistro()
    val shellInfo = shell.getShell()
    val terminal = os.getTerminal()
    val de = shell.getDEName()
    val font = shell.getFontName()
    val cursor = shell.getCursor()
    val wmName = shell.getWMName()
    val wmTheme = shell.getWMTheme()
    val gtkTheme = shell.getGTKTheme()
    val gtkIcon = shell.getGTKIcon()
    val uptime = os.getUptime()
    val launchTime = os.getLaunchTime()
    val scalingMHzCPU = component.getScalingMHz()
    val maxMHzCPU = component.getCPUMaxMHz()
    val minMHzCPU = component.getCPUMinMHz()
    val cacheSizeCPU = component.getCacheSize()
    val virtualizationCPU = component.getVirtualization()
    val temperatureCPU = component.getCPUTemperature()
    val manufacturerGPU = component.getGPUManufacturer()
    val openGLVersion = component.getOpenGLVersion()
    val kernelDriverGPU = component.getKernelDriver()
    val kernelModulesGPU = component.getKernelModules()
    val resolution = component.getMonitorResolution()
    val motherboard = component.getMotherboard()
    val storageUsage = component.getStorageUsage()
    val network = os.getNetwork()


    val out = buildString {
        appendLine()
        appendLine("\t" + OSInfo.username.green() + "@" + os.getHostname().green())
        appendLine("\t${Symbols.DASHES}")
        appendLine("\tOS: ".purple() + "${OSInfo.osName} ${os.getArchitecture()}")

        if (distro.isNotBlank()) {
            appendLine("\tDistro: ".purple() + distro)
        }

        appendLine("\tKernel: ".purple() + OSInfo.osVersion)

        if (shellInfo.isNotBlank()) {
            appendLine("\tShell: ".purple() + shellInfo)
        }

        if (terminal.isNotBlank()) {
            appendLine("\tTerminal: ".purple() + terminal)
        }

        if (de.isNotBlank()) {
            appendLine("\tDE: ".purple() + shell.getDEName())
        }

        if ("all" in args) {
            if (font.isNotBlank()) {
                appendLine("\tFont: ".purple() + font)
            }

            if (cursor.isNotBlank()) {
                appendLine("\tCursor: ".purple() + cursor)
            }

            if (wmName.isNotBlank()) {
                appendLine("\tWM: ".purple() + wmName)
            }

            if (wmTheme.isNotBlank()) {
                appendLine("\tWM Theme: ".purple() + wmTheme)
            }

            if (gtkTheme.isNotBlank()) {
                appendLine("\tGTK Theme: ".purple() + gtkTheme)
            }

            if (gtkIcon.isNotBlank()) {
                appendLine("\tGTK Icon: ".purple() + gtkIcon)
            }
        }

        if (uptime.isNotBlank()) {
            appendLine("\tMachine Uptime: ".purple() + uptime)
        }

        if (launchTime.isNotBlank()) {
            appendLine("\tMachine launch in ".purple() + launchTime)
        }

        appendLine("\tCPU: ".purple() + "${component.getCPU()} @ ${component.getCPUFrequency()}")
        appendLine("\t\tAbout CPU ->".blue())

        if (scalingMHzCPU.isNotBlank()) {
            appendLine("\t\t  CPU(s) Scaling MHz: ".blue() + scalingMHzCPU)
        }

        if (maxMHzCPU.isNotBlank()) {
            appendLine("\t\t  Max MHz: ".blue() + maxMHzCPU)
        }

        if (minMHzCPU.isNotBlank()) {
            appendLine("\t\t  Min MHz: ".blue() + minMHzCPU)
        }

        if (cacheSizeCPU.isNotBlank()) {
            appendLine("\t\t  Cache Size: ".blue() + cacheSizeCPU)
        }

        if (virtualizationCPU.isNotBlank()) {
            appendLine("\t\t  Virtualization: ".blue() + virtualizationCPU)
        }

        if (temperatureCPU.isNotBlank()) {
            appendLine("\t\t  Temperature: ".blue() + temperatureCPU)
        }

        appendLine("\tGPU: ".purple() + component.getGPU())
        appendLine("\t\tAbout GPU ->".blue())

        if (manufacturerGPU.isNotBlank()) {
            appendLine("\t\t  Manufacturer: ".blue() + manufacturerGPU)
        }

        if (openGLVersion.isNotBlank()) {
            appendLine("\t\t  Open GL Version: ".blue() + openGLVersion)
        }

        if (kernelDriverGPU.isNotBlank()) {
            appendLine("\t\t  Open GL Version: ".blue() + kernelDriverGPU)
        }

        if (kernelModulesGPU.isNotBlank()) {
            appendLine("\t\t  Kernel modules: ".blue() + kernelModulesGPU)
        }

        if (resolution.isNotBlank()) {
            appendLine("\t\t  Monitor resolution: ".blue() + resolution)
        }

        appendLine("\tDisk(s): ".purple() + component.getDisk())

        if (motherboard.isNotBlank()) {
            appendLine("\tMotherboard: ".purple() + motherboard)
        }

        appendLine("\tMEM: ".purple() + component.getMemory())
        appendLine("\t${Symbols.DASHES}")

        if (storageUsage.isNotBlank()) {
            appendLine("\tStorage Usage: ".purple() + "$storageUsage (root)")
        }

        if (network.isNotBlank()) {
            appendLine("\tNetwork: ".purple() + network)
        }
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