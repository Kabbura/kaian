package com.narbase.kabbura.kaian.web.views.basePage

import com.narbase.kabbura.kaian.web.common.AppColors
import com.narbase.kabbura.kaian.web.utils.views.extern.*
import com.narbase.kabbura.kaian.web.utils.views.extern.CmAutocomplete.completeFromList
import com.narbase.kabbura.kaian.web.utils.views.extern.CmLanguage.foldInside
import com.narbase.kabbura.kaian.web.utils.views.extern.CmLanguage.foldNodeProp
import com.narbase.kabbura.kaian.web.utils.views.extern.CmLanguage.indentNodeProp
import com.narbase.kabbura.kaian.web.utils.views.extern.LezerHighlight.styleTags
import com.narbase.kabbura.kaian.web.utils.views.extern.LezerHighlight.tags
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
    private lateinit var editor: CmView.EditorView
    private val languageCompartment by lazy { CmState.Compartment() }

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
                            languageCompartment.of(arrayOf<dynamic>()),
                            Cm.basicSetup,
                        )
                    )
                ),
                "parent" to rootView.element
            )
        )
    }

    fun updateLanguageSupport(grammar: String) {
        val support = generateLanguageSupport(grammar)
        editor.dispatch(
            json(
                "effects" to languageCompartment.reconfigure(support)
            )
        )
    }

    private fun generateLanguageSupport(grammar: String): CmLanguage.LanguageSupport {
        val parser = LezerGenerator.buildParser(grammar, json())

        val exampleLanguage = CmLanguage.LRLanguage.define(
            json(
                "parser" to parser.configure(
                    json(
                        "props" to arrayOf(
                            styleTags(
                                json(
                                    "Identifier" to tags.variableName,
                                    "Boolean" to tags.bool,
                                    "String" to tags.string,
                                "LineComment" to tags.lineComment,
                                "( )" to tags.paren
                            )
                        ),
                        indentNodeProp.add(
                            json(
                                "Application" to { context: dynamic -> context.column(context.node.from) + context.unit }
                            )),
                        foldNodeProp.add(
                            json(
                                "Application" to foldInside
                            )
                        )
                    )
                )),
                "languageData" to {
                    "commentTokens" to json("line" to ";")
                })
        )


        val exampleCompletion = exampleLanguage.data.of(
            json(
                "autocomplete" to completeFromList(
                    arrayOf(
                        json("label" to "defun", "type" to "keyword"),
                        json("label" to "defvar", "type" to "keyword"),
                        json("label" to "let", "type" to "keyword"),
                        json("label" to "cons", "type" to "function"),
                        json("label" to "car", "type" to "function"),
                        json("label" to "cdr", "type" to "function")
                    )
                )
            )
        )

        return CmLanguage.LanguageSupport(exampleLanguage, arrayOf(exampleCompletion))
    }

    private val initialDocContent = """
(defun check-login (name password) ; absolutely secure
  (if (equal name "admin")
    (equal password "12345")
    #t))
    """
}