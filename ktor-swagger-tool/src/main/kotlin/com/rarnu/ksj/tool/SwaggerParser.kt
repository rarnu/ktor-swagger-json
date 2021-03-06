package com.rarnu.ksj.tool

import com.rarnu.ksj.tool.parser.BaseInfoParser
import com.rarnu.ksj.tool.parser.ControllerParser
import com.rarnu.ksj.tool.parser.DataClassParser
import com.rarnu.ksj.tool.part.Base
import com.rarnu.ksj.tool.part.Definition
import com.rarnu.ksj.tool.part.Operation

object SwaggerParser {

    fun codeToDefinition(code: String): List<Definition> {
        val list = mutableListOf<Definition>()
        val newCode = code.split("\n").filterNot { it.startsWith("import ") || it.startsWith("package ") || it.trim() == "" }.joinToString("\n")
        if (newCode.contains("@SWType")) {
            val codeParts = newCode.split("@SWType").filterNot { it.trim() == "" }
            codeParts.forEach { c ->
                val p = DataClassParser(c)
                list.add(Definition(p.swName, p.swTitle, "object", p.requiredList, p.propertyList))
            }
        }
        return list
    }

    fun codeToController(code: String): List<Triple<String, String, List<Operation>>> {
        val list = mutableListOf<Triple<String, String, List<Operation>>>()
        val newCode = code.split("\n").filterNot { it.startsWith("import ") || it.startsWith("package ") || it.trim() == "" }.joinToString("\n")
        if (newCode.contains("@SWRoute")) {
            val codeParts = newCode.split("@SWRoute").filterNot { it.trim() == "" }
            codeParts.forEach { c ->
                val p = ControllerParser(c)
                list.add(Triple(p.swTag, p.swValue, p.operations))
            }
        }
        return list
    }

    fun codeToBaseInfo(code: String): Base? {
        var base: Base? = null
        val newCode = code.split("\n").filterNot { it.startsWith("import ") || it.startsWith("package ") || it.trim() == "" }.joinToString("\n")
        if (newCode.contains("@SWBase")) {
            val codePart = newCode.substring(newCode.indexOf("@SWBase")).replace("@SWBase", "").run { substring(0, indexOf(")")) }.trim()
            val p = BaseInfoParser(codePart)
            base = Base(p.swSwagger, p.swDescription, p.swVersion, p.swTitle, p.swTermsOfService, p.swContactName, p.swContactUrl, p.swContactEmail, p.swHost, p.swBasePath)
        }
        return base
    }


}