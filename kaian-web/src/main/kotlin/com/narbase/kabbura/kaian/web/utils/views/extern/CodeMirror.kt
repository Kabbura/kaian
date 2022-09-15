package com.narbase.kabbura.kaian.web.utils.views.extern

/*
 * NARBASE TECHNOLOGIES CONFIDENTIAL
 * ______________________________
 * [2017] -[2019] Narbase Technologies
 * All Rights Reserved.
 * Created by islam
 * On: 2022/09/14.
 */

@JsModule("@codemirror/state")
@JsNonModule
external object CmState {
    object EditorState {
        fun create(options: dynamic): dynamic
    }
}

@JsModule("@codemirror/view")
@JsNonModule
external object CmView {
    class EditorView(options: dynamic)

    val keymap: dynamic
}

@JsModule("@codemirror/commands")
@JsNonModule
external object CmCommands {
    val defaultKeymap: dynamic
}


//@JsModule("@lezer/generator")
//@JsNonModule
//external object LezerGenerator {
//    fun buildParser(text: String, option: dynamic): dynamic
//}


