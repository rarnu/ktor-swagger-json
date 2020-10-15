package com.rarnu.ksj.tool.parser

import com.rarnu.common.Septuple

object CommonParser {

    fun annotationMap(a: String): Map<String, String> =
        a.replace("(", "").replace(")", "").split(",").map { it.trim() }
            .map { it.split("=").run { this[0].trim() to this[1].replace("\"", "").trim() } }.toMap()

    fun fieldInfo(c: String): Pair<String, String> {
        val cc = c.replace("val ", "").replace("var ", "")
        val name = cc.split(":")[0].trim()
        val typ = cc.split(":")[1].trim().run { substring(0, indexOfAny(listOf(",", "?", ")"))) }.trim()
        return name to typ
    }

    fun dataClassName(a: String): String =
        a.replace("data class", "").run { substring(0, indexOf("(")) }.trim()

    fun annotationProperty(anno: String, c: String): Septuple<String, String?, String?, String, String?, String?, String?> {
        val aMap = annotationMap(anno.replace("@SWProperty", ""))
        val (name, type) = fieldInfo(c)
        val swName = if (aMap["name"] == null || aMap["name"]?.trim() == "") name else aMap["name"] ?: ""
        val swValue = aMap["value"]
        val swRequired = aMap["required"]
        val swType = if (aMap["type"] == null || aMap["type"]?.trim() == "") type else aMap["type"] ?: ""
        val swNotes = aMap["notes"]
        val swAllowEmptyValue = aMap["allowEmptyValue"]
        val swExample = aMap["example"]
        return Septuple(swName, swValue, swRequired, swType, swNotes, swAllowEmptyValue, swExample)
    }

    fun convertType(t: String): Pair<String?, String?> {
        if (t.equals("byte", ignoreCase = true)) return "integer" to "int32"
        if (t.equals("short", ignoreCase = true)) return "integer" to "int32"
        if (t.equals("int", ignoreCase = true)) return "integer" to "int32"
        if (t.equals("integer", ignoreCase = true)) return "integer" to "int32"
        if (t.equals("long", ignoreCase = true)) return "integer" to "int64"
        if (t.equals("float", ignoreCase = true)) return "float" to null
        if (t.equals("double", ignoreCase = true)) return "double" to null
        if (t.equals("char", ignoreCase = true)) return "string" to null
        if (t.equals("string", ignoreCase = true)) return "string" to null
        if (t.contains("List<", ignoreCase = true)) {
            val genTyp = t.replace("List<", "").replace(">", "")
            val (cType) = convertType(genTyp)
            return "array" to cType
        }
        return "#/definitions/$t" to null

    }
}