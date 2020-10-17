package com.rarnu.ksj.tool

import com.rarnu.ksj.tool.part.Base
import com.rarnu.ksj.tool.part.Definition
import com.rarnu.ksj.tool.part.Operation
import com.rarnu.ksj.tool.part.Tag
import java.io.File

val swTypeList = mutableListOf<Definition>()
val swControllerList = mutableListOf<Triple<String, String, List<Operation>>>()
val swTagList = mutableListOf<Tag>()
var swBaseInfo: Base? = null

fun collectSWType(f: File) {
    val list = SwaggerParser.codeToDefinition(f.readText())
    swTypeList.addAll(list)
}

fun collectSWController(f: File) {
    val list = SwaggerParser.codeToController(f.readText())
    swControllerList.addAll(list)
}

fun collectSWTag() {
    swTagList.addAll(swControllerList.map { Tag(it.first, it.second) })
}

fun collectBaseInfo(f: File) {
    swBaseInfo = SwaggerParser.codeToBaseInfo(f.readText())
}