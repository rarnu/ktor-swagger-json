package com.rarnu.ksj.tool.part

data class Property(
    val name: String? = null,
    val type: String? = null,
    val format: String? = null,
    val description: String? = null,
    val required: Boolean? = null,
    val allowEmptyValue: Boolean? = null,
    val notes: String? = null,
    val example: String? = null,
    val items: Boolean? = null,
    val itemRef: String? = null
) {
    fun json(): String {
        var str = "\"$name\":{"
        if (type != null) str += "\"type\":\"$type\","
        if (format != null) str += "\"format\":\"$format\","
        if (description != null) str += "\"description\":\"$description\","
        if (required != null) str += "\"required\":${if (required) "true" else "false"},"
        if (allowEmptyValue != null) str += "\"allowEmptyValue\":${if (allowEmptyValue) "true" else "false"},"
        if (notes != null) str += "\"notes\":\"$notes\","
        if (example != null) str += "\"example\":\"$example\","
        if (items != null) {
            if (itemRef != null) {
                str += if (itemRef.contains("#/definitions")) {
                    "\"items\":{\"\$ref\":\"$itemRef\"},"
                } else {
                    "\"items\":{\"type\":\"$itemRef\"},"
                }
            }
        }
        str = str.trimEnd(',')
        str += "}"
        return str
    }
}

data class Definition(
    val name: String? = null,
    val title: String? = null,
    val type: String? = null,
    val required: List<String>? = null,
    val properties: List<Property>? = null
) {
    fun json(): String {
        var str = "\"$name\":{"
        str += if (type == null) "\"type\":\"object\"," else "\"type\":\"$type\","
        str += "\"required\":[${required?.map { "\"$it\"" }?.joinToString(",") ?: ""}],"
        str += "\"properties\":{${properties?.joinToString(",") { it.json() } ?: ""}},"
        str += if (title == null) "\"title\":\"$name\"" else "\"title\":\"$title\""
        str += "}"
        return str
    }
}