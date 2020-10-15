package com.rarnu.ksj.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class SWRoute(val value: String = "", val tag: String = "")

@Target(AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
annotation class SWOperation(val value: String = "", val httpMethod: String = "GET", val notes: String = "")

@Target(AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
annotation class SWParam(val name: String = "", val value: String = "", val required: Boolean = false, val type: String = "", val allowEmptyValue: Boolean = false, val example: String = "")

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class SWProperty(val name: String = "", val value: String = "", val required: Boolean = false, val type: String = "", val notes: String = "", val allowEmptyValue: Boolean = false, val example: String = "")

@Target(AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
annotation class SWReturn(val value: String = "", val type: String = "")

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class SWType(val value: String = "", val title: String = "")