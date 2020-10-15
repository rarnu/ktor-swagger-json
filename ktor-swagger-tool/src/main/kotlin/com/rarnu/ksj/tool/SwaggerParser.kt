package com.rarnu.ksj.tool

import com.rarnu.ksj.tool.parser.DataClassParser
import com.rarnu.ksj.tool.part.Definition

object SwaggerParser {


/*
@SWType(value = "MyData", title = "测试数据类")
data class MyData(
        @SWProperty(name = "name", value = "名称", type = "string", required = true)
        val name: String,
        @SWProperty(name = "greeting", value = "招呼本文", type = "string", required = true)
        val greeting: String)

@SWType(value = "MyRetData", title = "返回测试数据类")
data class MyRetData(
        @SWProperty(name = "code", value = "返回码", type = "integer", required = true)
        val code: Int,
        @SWProperty(name = "msg", value = "返回字符串", type = "string", required = true)
        val msg: String)

 */

    fun codeToDefinition(code: String): List<Definition> {
        val list = mutableListOf<Definition>()
        val newCode = code.split("\n").filterNot { it.startsWith("import com.rarnu.ktor.swagger") || it.startsWith("package ") || it.trim() == "" }.joinToString("\n")
        if (newCode.contains("@SWType")) {
            val codeParts = newCode.split("@SWType").filterNot { it.trim() == "" }
            codeParts.forEach { c ->
                val p = DataClassParser(c)
                list.add(Definition(p.swName, p.swTitle, "object", p.requiredList, p.propertyList))
            }
        }
        return list
    }

}