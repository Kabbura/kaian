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

    class Compartment {
        fun of(options: dynamic): dynamic
        fun reconfigure(options: dynamic): dynamic
    }
}

@JsModule("@codemirror/view")
@JsNonModule
external object CmView {
    class EditorView(options: dynamic) {
        val state: dynamic
        fun dispatch(options: dynamic): dynamic
    }

    val keymap: dynamic
    fun lineNumbers(): dynamic
}

@JsModule("@codemirror/commands")
@JsNonModule
external object CmCommands {
    val defaultKeymap: dynamic
}


@JsModule("@codemirror/language")
@JsNonModule
external object CmLanguage {
    val defaultKeymap: dynamic
    val foldNodeProp: dynamic
    val foldInside: dynamic
    val indentNodeProp: dynamic

    object LRLanguage {
        fun define(options: dynamic): dynamic
    }

    class LanguageSupport(lang: dynamic, options: dynamic)
}


@JsModule("@codemirror/autocomplete")
@JsNonModule
external object CmAutocomplete {
    fun completeFromList(options: dynamic): dynamic
}


@JsModule("codemirror")
@JsNonModule
external object Cm {
    val basicSetup: dynamic
}

@JsModule("@codemirror/lang-lezer")
@JsNonModule
external object CmLezer {
    fun lezer(): dynamic
}



