package com.rarnu.ksj.tool

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.rarnu.common.fileWalk
import java.io.File

fun printJson(jsonstr: String) {
    val jsonObject = JsonParser.parseString(jsonstr).asJsonObject
    val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    val str = gson.toJson(jsonObject)
    println(str)
}

fun main(args: Array<String>) {
    val path = args[0]
    val fPath = File(path)
    val fSrc = File(fPath, "src")
    if (!fPath.exists() || !fSrc.exists()) {
        println("Project not exists.")
        return
    }

    fileWalk(fSrc.absolutePath) { f ->
        if (f.extension == "kt") {
            collectSWType(f)
        }
    }

    val jsonstr = swTypeList.joinToString(",", prefix = "{", postfix = "}") { it.json() }
    printJson(jsonstr)

}

