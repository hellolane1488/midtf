package mid.vet.os

fun executeCommand(runtime: Runtime, command: Array<String>): String {
    val process = runtime.exec(command)
    /* in the "process", calling instances of classes and applying the .exec() functions to them, where we insert our command */
    val result = process.inputStream.bufferedReader().use { it.readText() }.trim()
    /* as a "result" we get the output from the standard stream */

    process.waitFor()
    return result
}
