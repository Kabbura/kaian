package com.narbase.kabbura.kaian.web.views.basePage

import com.narbase.kunafa.core.components.*
import com.narbase.kunafa.core.css.fontSize
import com.narbase.kunafa.core.css.height
import com.narbase.kunafa.core.css.padding
import com.narbase.kunafa.core.css.width
import com.narbase.kunafa.core.dimensions.dependent.matchParent
import com.narbase.kunafa.core.dimensions.px

/*
 * NARBASE TECHNOLOGIES CONFIDENTIAL
 * ______________________________
 * [2017] -[2019] Narbase Technologies
 * All Rights Reserved.
 * Created by islam
 * On: 2022/09/23.
 */
class TreeVisualiser(val lezerCodeEditor: LezerCodeEditor, val codeEditor: CodeEditor) : Component() {
    private var treeTextView: TextView? = null
    private var builder = StringBuilder()
    private var currentIndent = 0

    private val lineIndentation: String
        get() = buildString {
            repeat(currentIndent - 1) {
                append("|_______")
            }
        }

    override fun View?.getView() = verticalScrollLayout {
        style {
            width = matchParent
            height = matchParent
        }
        treeTextView = textView {
            style {
                padding = 4.px
                fontSize = 12.px
                width = matchParent
            }
        }
    }

    fun visualise() {
        val tree = codeEditor.getTree()
//        console.log("Visualising")
//        console.log(tree)
        builder = StringBuilder()
        val cursor = tree.cursor()

        printTree(cursor)
        treeTextView?.text = builder.toString()


    }

    private fun printTree(cursor: dynamic) {
        printChild(cursor)
        printSiblings(cursor)
    }

    var isDone = false

    private fun printSiblings(cursor: dynamic) {
        p("printSiblings")
        val hasSiblings = cursor.nextSibling()
        if (hasSiblings == false) {
            val hasParent = cursor.parent()
            if (hasParent == false) {
                isDone = true
                return
            }
            currentIndent--
            printSiblings(cursor)
            return
        }
        recordCursor(cursor)
        printChild(cursor)
        printSiblings(cursor)
    }

    private fun printChild(cursor: dynamic) {
        p("printChild")
        val hasChild = cursor.firstChild()
        if (hasChild == false) {
            return
        }
        p("has child")
        currentIndent++
        recordCursor(cursor)
        printChild(cursor)

    }

    private fun recordCursor(cursor: dynamic) {
        val type = cursor.type
        p("type: $type")
//        console.log(cursor)
        builder.append(
            "$lineIndentation${type.name}: ${
                codeEditor.getContent(cursor).slice(0..9).replace('\n', '|')
            }\n"
        )
    }

    private fun p(any: Any) {
//        println("$lineIndentation$any")
    }
}