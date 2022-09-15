package com.narbase.kabbura.kaian.web.views.basePage

import com.narbase.kabbura.kaian.web.utils.views.extern.CmCommands
import com.narbase.kabbura.kaian.web.utils.views.extern.CmState
import com.narbase.kabbura.kaian.web.utils.views.extern.CmView
import com.narbase.kunafa.core.components.Component
import com.narbase.kunafa.core.components.View
import com.narbase.kunafa.core.components.view
import com.narbase.kunafa.core.css.border
import com.narbase.kunafa.core.css.minHeight
import com.narbase.kunafa.core.css.width
import com.narbase.kunafa.core.dimensions.dependent.matchParent
import com.narbase.kunafa.core.dimensions.px
import com.narbase.kunafa.core.lifecycle.LifecycleOwner
import kotlin.js.json

/*
 * NARBASE TECHNOLOGIES CONFIDENTIAL
 * ______________________________
 * [2017] -[2019] Narbase Technologies
 * All Rights Reserved.
 * Created by islam
 * On: 2022/09/15.
 */
class CodeEditor : Component() {

    override fun onViewCreated(lifecycleOwner: LifecycleOwner) {
        super.onViewCreated(lifecycleOwner)
        init()
    }

    override fun View?.getView() = view {
        style {
            width = matchParent
            minHeight = 300.px
            border = "1px solid red"
        }
    }

    private fun init() {
        val rootView = rootView
        if (rootView == null) {
            console.log("Root view is null")
            return
        }
        val options = json(
            "doc" to "Hello World",
            "extensions" to arrayOf(CmView.keymap.of(CmCommands.defaultKeymap))
        )
        val startState = CmState.EditorState.create(options)
        val view = CmView.EditorView(
            json(
                "state" to startState,
                "parent" to rootView.element
            )
        )
    }

}