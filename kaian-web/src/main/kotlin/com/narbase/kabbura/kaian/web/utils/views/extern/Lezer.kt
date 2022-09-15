package com.narbase.kabbura.kaian.web.utils.views.extern

/*
 * NARBASE TECHNOLOGIES CONFIDENTIAL
 * ______________________________
 * [2017] -[2019] Narbase Technologies
 * All Rights Reserved.
 * Created by islam
 * On: 2022/09/15.
 */

@JsModule("@lezer/generator")
@JsNonModule
external object LezerGenerator {
    fun buildParserFile(text: String, options: dynamic): dynamic
    fun buildParser(text: String, option: dynamic): dynamic
}

@JsModule("@lezer/highlight")
@JsNonModule
external object LezerHighlight {
    val styleTags: dynamic
    val tags: dynamic
}