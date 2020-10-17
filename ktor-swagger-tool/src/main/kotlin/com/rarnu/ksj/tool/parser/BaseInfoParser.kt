package com.rarnu.ksj.tool.parser

import com.rarnu.ksj.tool.parser.CommonParser.annotationMap

class BaseInfoParser(code: String) {

    var swSwagger: String = ""
    var swDescription: String? = null
    var swVersion: String? = null
    var swTitle: String? = null
    var swTermsOfService: String? = null
    var swContactName: String? = null
    var swContactUrl: String? = null
    var swContactEmail: String? = null
    var swHost: String? = null
    var swBasePath: String = "/"

    init {
        val annoMap = annotationMap(code)
        swSwagger = annoMap["swagger"] ?: "2.0"
        swDescription = annoMap["description"]
        swVersion = annoMap["version"]
        swTitle = annoMap["title"]
        swTermsOfService = annoMap[""]
        swContactName = annoMap[""]
        swContactUrl = annoMap[""]
        swContactEmail = annoMap[""]
        swHost = annoMap[""]
        swBasePath = annoMap[""] ?: "/"
    }
}