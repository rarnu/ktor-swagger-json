package com.rarnu.ksj.tool.part

data class Base(
    val swagger: String = "2.0",
    val description: String? = null,
    val version: String? = null,
    val title: String? = null,
    val termsOfService: String? = null,
    val contactName: String? = null,
    val contactUrl: String? = null,
    val contactEmail: String? = null,
    val host: String? = "",
    val basePath: String = "/"
) {
    fun json(): String {
        var str = "\"swagger\":\"$swagger\","
        str += "\"info\":{"
        str += "\"description\":\"$description\","
        str += "\"version\":\"$version\","
        str += "\"title\":\"$title\","
        str += "\"termsOfService\":\"$termsOfService\","
        str += "\"contact\":{"
        str += "\"name\":\"$contactName\","
        str += "\"url\":\"$contactUrl\","
        str += "\"email\":\"$contactEmail\""
        str += "}"
        str += "},"
        str += "\"host\":\"$host\","
        str += "\"basePath\":\"$basePath\","
        return str
    }
}
