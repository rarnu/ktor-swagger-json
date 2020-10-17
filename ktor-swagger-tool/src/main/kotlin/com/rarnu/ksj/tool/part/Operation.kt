@file:Suppress("DuplicatedCode")

package com.rarnu.ksj.tool.part

data class Operation(
    val name: String? = null,
    var get: Opt? = null,
    var post: Opt? = null,
    var put: Opt? = null,
    var delete: Opt? = null,
    var head: Opt? = null,
    var options: Opt? = null,
    var patch: Opt? = null,
    var trace: Opt? = null
) {
    fun json(): String {
        var str = "\"$name\":{"
        if (get != null) str += "${get!!.json()},"
        if (post != null) str += "${post!!.json()},"
        if (put != null) str += "${put!!.json()},"
        if (delete != null) str += "${delete!!.json()},"
        if (head != null) str += "${head!!.json()},"
        if (options != null) str += "${options!!.json()},"
        if (patch != null) str += "${patch!!.json()},"
        if (trace != null) str += "${trace!!.json()},"
        str = str.trimEnd(',')
        str += "}"
        return str
    }
}

data class Opt(
    val name: String = "",
    val postfix: String = "",
    val tag: String? = null,
    val summary: String? = null,
    val notes: String? = null,
    val produces: List<String>? = null,
    val consumes: List<String>? = null,
    val deprecated: Boolean = false,
    val parameters: List<OptParam>? = null,
    val responses: List<OptResp>? = null
) {
    fun json(): String {
        var str = "\"$name\":{"
        if (tag != null) str += "\"tags\":[\"$tag\"],"
        if (summary != null) str += "\"summary\":\"$summary\","
        if (notes != null) str += "\"notes\":\"$notes\","
        str += "\"operationId\":\"${postfix}Using${name.toUpperCase()}\","
        str += "\"produces\":[${produces?.joinToString(",") { "\"$it\"" } ?: ""}],"
        str += "\"consumes\":[${consumes?.joinToString(",") { "\"$it\"" } ?: ""}],"
        str += "\"parameters\":[${parameters?.joinToString(",") { it.json() } ?: ""}],"
        str += "\"responses\":{${responses?.joinToString(",") { it.json() }}},"
        str += "\"deprecated\":${if (deprecated) "true" else "false"},"
        str = str.trimEnd(',')
        str += "}"
        return str
    }
}

data class OptParam(
    val name: String? = null,
    val `in`: String? = null,
    val description: String? = null,
    val required: Boolean = false,
    val allowEmptyValue: Boolean?,
    val type: String? = null,
    val format: String? = null,
    val schema: Boolean = false,
    val ref: String? = null,
    val example: String? = null
) {
    fun json(): String {
        var str = "{"
        if (name != null) str += "\"name\":\"$name\","
        if (`in` != null) str += "\"in\":\"${`in`}\","
        if (description != null) str += "\"description\":\"$description\","
        str += "\"required\":${if (required) "true" else "false"},"
        if (allowEmptyValue != null) str += "\"allowEmptyValue\":${if (allowEmptyValue) "true" else "false"},"
        if (schema) {
            if (ref != null) str += "\"schema\":{\"$ref\":\"$ref\"},"
        } else {
            if (type != null) str += "\"type\":\"$type\","
            if (format != null) str += "\"format\":\"$format\""
        }
        if (example != null) str += "\"example\":\"$example\","
        str = str.trimEnd(',')
        str += "}"
        return str
    }
}

data class OptResp(
    val code: String? = null,
    val description: String? = null,
    val schema: Boolean = false,
    val ref: String? = null
) {
    fun json(): String {
        var str = "\"$code\":{"
        if (description != null) str += "\"description\":\"$description\","
        if (schema) {
            if (ref != null) {
                str += if (ref.contains("#/definitions")) "\"schema\":{\"\$ref\":\"$ref\"}" else "schema:{\"type\":\"$ref\"},"
            }
        }
        str = str.trimEnd(',')
        str += "}"
        return str
    }
}