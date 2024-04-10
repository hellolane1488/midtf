package mid.vet.os

object OSInfo {
    val osName: String = System.getProperty("os.name") // get your OS System name
    val osVersion: String = System.getProperty("os.version") // get the kernel version
    val username: String = System.getProperty("user.name") // get your machine name
}