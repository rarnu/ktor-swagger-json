package com.rarnu.ksj.tool.parser

import com.rarnu.ksj.tool.parser.CommonParser.annotationMap
import com.rarnu.ksj.tool.parser.CommonParser.controllerName
import com.rarnu.ksj.tool.parser.CommonParser.convertType
import com.rarnu.ksj.tool.parser.CommonParser.group
import com.rarnu.ksj.tool.part.Operation
import com.rarnu.ksj.tool.part.Opt
import com.rarnu.ksj.tool.part.OptParam
import com.rarnu.ksj.tool.part.OptResp

class ControllerParser(code: String) {

    companion object {
        private val protocalCodeList = listOf(
            "get(",
            "get<",
            "get {",
            "post(",
            "post<",
            "post {",
            "put(",
            "put<",
            "put {",
            "delete(",
            "delete<",
            "delete {",
            "head(",
            "head<",
            "head {",
            "options(",
            "options<",
            "options {",
            "patch(",
            "patch<",
            "patch {",
            "trace(",
            "trace<",
            "trace {"
        )
    }

    var swTag: String = ""
    var swValue: String = ""
    val operations = mutableListOf<Operation>()

    init {
        val arr = code.split("\n").filterNot { it.trim() == "" }
        val annoMap = annotationMap(arr[0])
        swValue = controllerName(arr[1])
        if (annoMap["value"] != null && annoMap["value"] != "") swValue = annoMap["value"] ?: ""
        swTag = annoMap["tag"] ?: ""
        val list = arr.filter { it.trim().startsWith("@SW") || isProtocol(it.trim()) }.map { it.trim() }
        val newList = group(list)
        newList.forEach { l ->
            var oName = ""
            val paramList = mutableListOf<OptParam>()
            var oResp: OptResp? = null
            var oDesc: String? = null
            var oMethod: String? = null
            var oNotes: String? = null
            l.forEach { c ->
                when {
                    c.startsWith("@SWOperation") -> {
                        val cc = c.replace("@SWOperation", "").trim()
                        val amap = annotationMap(cc)
                        oDesc = amap["value"]
                        if (amap["httpMethod"] != null) oMethod = amap["httpMethod"]
                        oNotes = amap["notes"]
                    }
                    c.startsWith("@SWParam") -> {
                        val cc = c.replace("@SWParam", "").trim()
                        val amap = annotationMap(cc)
                        val (cType, cFormat) = convertType(amap["type"] ?: "string")
                        val param = OptParam(
                            amap["name"], "body", amap["value"], amap["required"] == "true",
                            if (amap["allowEmptyValue"] == null) null else amap["allowEmptyValue"] == "true",
                            cType, cFormat, cType == "array", if (cType == "array") cFormat else null,
                            amap["example"]
                        )
                        paramList.add(param)
                    }
                    c.startsWith("@SWReturn") -> {
                        val cc = c.replace("@SWReturn", "").trim()
                        val amap = annotationMap(cc)
                        val (cType, cFormat) = convertType(amap["type"] ?: "string")
                        oResp = OptResp("200", amap["value"], true, if (cType == "array") cFormat else cType)
                    }
                    else -> {
                        oName = c.substring(c.indexOf("\"") + 1).run { substring(0, indexOf("\"")) }
                        if (oMethod == null) oMethod = c.substring(0, c.indexOfAny(listOf("(", "<", "{", " "))).trim().toLowerCase()
                    }
                }
            }
            if (oName != "") {
                val postfix = oName.substringAfterLast("/").toLowerCase()
                val respList = mutableListOf<OptResp>()
                if (oResp != null) respList.add(oResp!!)
                respList.addAll(
                    listOf(
                        OptResp("201", "Created"),
                        OptResp("401", "Unauthorized"),
                        OptResp("403", "Forbidden"),
                        OptResp("404", "Not Found")
                    )
                )
                val opt = Opt(
                    oMethod?.toLowerCase() ?: "",
                    postfix,
                    swTag,
                    oDesc,
                    oNotes,
                    listOf("*/*"),
                    listOf("application/json"),
                    false,
                    paramList,
                    respList
                )
                val oper = Operation(oName)
                when (opt.name) {
                    "get" -> oper.get = opt
                    "post" -> oper.post = opt
                    "put" -> oper.put = opt
                    "delete" -> oper.delete = opt
                    "head" -> oper.head = opt
                    "options" -> oper.options = opt
                    "patch" -> oper.patch = opt
                    "trace" -> oper.trace = opt
                }
                operations.add(oper)
            }
        }
    }

    private fun isProtocol(s: String): Boolean = protocalCodeList.any { s.startsWith(it) }

}