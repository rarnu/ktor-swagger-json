package com.rarnu.ksj.tool.part

data class Tag(
    val name: String? = null,
    val description: String? = null
) {
    fun json(): String {
        var str = "{"
        if (name != null) str += "\"name\":\"$name\","
        if (description != null) str += "\"description\":\"$description\","
        str = str.trimEnd(',')
        str += "}"
        return str
    }
}

