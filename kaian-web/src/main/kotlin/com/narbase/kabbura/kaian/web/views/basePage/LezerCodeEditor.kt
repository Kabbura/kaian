package com.narbase.kabbura.kaian.web.views.basePage

import com.narbase.kabbura.kaian.web.common.AppColors
import com.narbase.kabbura.kaian.web.utils.views.extern.Cm
import com.narbase.kabbura.kaian.web.utils.views.extern.CmLezer
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
class LezerCodeEditor : Component() {

    private lateinit var editor: CmView.EditorView
    override fun onViewCreated(lifecycleOwner: LifecycleOwner) {
        super.onViewCreated(lifecycleOwner)
        init()
    }

    override fun View?.getView() = view {
        style {
            width = matchParent
            minHeight = 300.px
            border = "1px solid ${AppColors.borderColor}"
        }
    }

    private fun init() {
        val rootView = rootView
        if (rootView == null) {
            console.log("Root view is null")
            return
        }

        editor = CmView.EditorView(
            json(
                "state" to CmState.EditorState.create(
                    json(
                        "doc" to initialDocContent,
                        "extensions" to arrayOf(
                            Cm.basicSetup,
                            CmLezer.lezer()
                        )
                    )
                ),
                "parent" to rootView.element
            )
        )

    }

    fun getContent(): String {
        return editor.state.doc.toString()
    }

    private val initialDocContent = """
@top Program { expression* }

expression {
  Identifier |
  String |
  Boolean |
  Application { "(" expression* ")" }
}

@tokens {
  Identifier { ${'$'}[a-zA-Z_0-9]+ }

  String { '"' (!["\\] | "\\" _)* '"' }

  Boolean { "#t" | "#f" }

  LineComment { ";" ![\n]* }

  space { ${'$'}[ \t\n\r]+ }

  "(" ")"
}

@skip { space | LineComment }
@detectDelim

    """.trimIndent()
}