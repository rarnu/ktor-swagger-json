package com.rarnu.ksj.tool

import com.rarnu.ksj.tool.part.Definition
import java.io.File

val swTypeList = mutableListOf<Definition>()

fun collectSWType(f: File) {
    val list = SwaggerParser.codeToDefinition(f.readText())
    swTypeList.addAll(list)
}
