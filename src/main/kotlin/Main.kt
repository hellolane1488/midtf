package mid.vet

import java.util.*

fun main(args: Array<String>) {
    if (System.getProperty("os.name").lowercase(Locale.getDefault()) != "linux") {
        return println("Sorry, but the script does not work correctly on your system")
        /* So far, this script only works correctly on Linux distributions */
    }

    if ("--all" in args) {
        return outputInformation("all")
    }

    return outputInformation("")
}