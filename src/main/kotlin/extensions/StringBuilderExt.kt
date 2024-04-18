package mid.vet.extensions

fun StringBuilder.appendIfNotBlank(prefix: String, value: String?) {
    if (!value.isNullOrBlank()) appendLine("$prefix$value")
}