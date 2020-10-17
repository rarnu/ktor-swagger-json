package com.rarnu.ksj.tool.parser

import com.rarnu.ksj.tool.parser.CommonParser.annotationMap
import com.rarnu.ksj.tool.parser.CommonParser.annotationProperty
import com.rarnu.ksj.tool.parser.CommonParser.convertType
import com.rarnu.ksj.tool.parser.CommonParser.dataClassName
import com.rarnu.ksj.tool.part.Property

class DataClassParser(code: String) {

    var swName: String? = ""
    var swValue: String? = ""
    var swTitle: String? = ""
    var requiredList = mutableListOf<String>()
    var propertyList = mutableListOf<Property>()

    init {
        val arr = code.split("\n").filterNot { it.trim() == "" }
        val annoMap = annotationMap(arr[0])
        swName = dataClassName(arr[1])
        swValue = if (annoMap["value"] == null || annoMap["value"]?.trim() == "") swName else annoMap["value"]
        swTitle = annoMap["title"]

        (2 until arr.size - 1 step 2).forEach {
            val (tName, tValue, tRequired, tType, tNotes, tAllowEmptyValue, tExample) = annotationProperty(arr[it], arr[it + 1])
            val (cType, cFormat) = convertType(tType)
            val prop = Property(
                tName, cType, cFormat, tValue,
                if (tRequired == null) null else tRequired == "true",
                if (tAllowEmptyValue == null) null else tAllowEmptyValue == "true",
                tNotes,
                tExample,
                if (cType == "array") true else null,
                if (cType == "array") cFormat else null
            )
            if (tRequired == "true") requiredList.add(tName)
            propertyList.add(prop)
        }
    }

}