package mid.vet.extensions

import mid.vet.AnsiColor
import mid.vet.Symbols

/* here are extension functions of the String class */

fun String.purple(): String {
    return "${AnsiColor.PURPLE}$this${AnsiColor.RESET}"
}

fun String.green(): String {
    return "${AnsiColor.GREEN}$this${AnsiColor.RESET}"
}

fun String.blue(): String {
    return "${AnsiColor.BLUE}$this${AnsiColor.RESET}"
}

fun String.tree(): String {
    return "${Symbols.TREE}\t$this"
}