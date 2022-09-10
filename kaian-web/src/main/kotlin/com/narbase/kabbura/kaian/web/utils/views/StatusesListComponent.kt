package com.narbase.kabbura.kaian.web.utils.views

import com.narbase.kunafa.core.components.*
import com.narbase.kunafa.core.components.layout.LinearLayout
import com.narbase.kunafa.core.css.*
import com.narbase.kunafa.core.dimensions.dependent.matchParent
import com.narbase.kunafa.core.dimensions.dimen
import com.narbase.kunafa.core.dimensions.px
import com.narbase.kunafa.core.drawable.Color
import com.narbase.kunafa.core.lifecycle.LifecycleOwner
import com.narbase.kabbura.kaian.dto.common.EnumDtoName
import com.narbase.kabbura.kaian.web.common.AppColors
import com.narbase.kabbura.kaian.web.common.AppFontSizes
import com.narbase.kabbura.kaian.web.storage.bidirectional

import com.narbase.kabbura.kaian.web.utils.verticalFiller
import disableBlurOptions
import tippy
import kotlin.js.Json
import kotlin.js.json

class StatusesListComponent<E>(
    private val values: Array<Pair<E, Color>>,
    private val itemToString: (E) -> String,
    private val defaultItem: Pair<E, Color>? = null,
    private val onSelectStatus: (item: E) -> Unit
) : Component() where E : EnumDtoName {

    private var statusText: TextView? = null
    private var changeStatusTippyInstance: dynamic = null

    override fun onViewCreated(lifecycleOwner: LifecycleOwner) {
        super.onViewCreated(lifecycleOwner)
        createTippyCustomStyles()
    }

    override fun View?.getView(): View = horizontalLayout {
        style {
            alignItems = Alignment.Center
        }
        horizontalLayout {
            style {
                pointerCursor()
                borderRadius = 4.px
                padding = "3px 20px 3px 8px".dimen()
                border = "1px solid ${AppColors.borderColorHex}"
            }
            materialIcon("edit") {
                style {
                    fontSize = 16.px
                    color = AppColors.textDarkGrey
                    marginEnd = 6.px
                    alignSelf = Alignment.Center
                }
            }

            statusText = textView {
                text = itemToString(defaultItem?.first ?: values.first().first)
                style {
                    fontSize = AppFontSizes.smallText
                }
            }

            statusText?.setColor(defaultItem?.second ?: AppColors.kaianColor)
            changeStatusTippyInstance = tippy(
                this.element,
                getStatusTippyOptions(onStatusChanged = { selectedStatus ->
                    changeStatusTippyInstance.hide()
                    statusText?.text = itemToString(selectedStatus)
                    statusText?.setColor(
                        values.find { it.first == selectedStatus }?.second
                            ?: AppColors.kaianColor
                    )
                    onSelectStatus(selectedStatus)
                })
            )
        }
    }

    fun setStatus(status: E) {
        statusText?.text = itemToString(status)
        statusText?.setColor(values.find { it.first == status }?.second ?: AppColors.kaianColor)
    }

    private fun TextView.setColor(color: Color) {
        element.style.color = color.toCss()
    }

    private fun createTippyCustomStyles() {
        try {
            stringRuleSet(".tippy-tooltip.popover-theme") {
                borderRadius = 12.px
                padding = 0.px
                backgroundColor = Color.white
                color = Color.black
                boxShadow = "0px 6px 8px 1px rgba(0,0,0,0.29)"
            }
            stringRuleSet(".tippy-popper[x-placement^=top] .tippy-tooltip.popover-theme .tippy-arrow") {
                borderTopColor = Color.white
            }
            stringRuleSet(".tippy-popper[x-placement^=bottom] .tippy-tooltip.popover-theme .tippy-arrow") {
                borderBottomColor = Color.white
            }
            stringRuleSet(".tippy-popper[x-placement^=left] .tippy-tooltip.popover-theme .tippy-arrow") {
                borderLeftColor = Color.white
            }
            stringRuleSet(".tippy-popper[x-placement^=right] .tippy-tooltip.popover-theme .tippy-arrow") {
                borderRightColor = Color.white
            }
        } catch (e: dynamic) {
            console.log(e)
        }
    }


    private fun getStatusTippyOptions(onStatusChanged: (newStatus: E) -> Unit): Json {
        val contents = statusList(onStatusChanged).element
        val options = json(
            "interactive" to true,
            "trigger" to "click",
            "theme" to "popover",
            "placement" to bidirectional("right", "left"),
            "arrow" to "large",
            "followCursor" to "initial",
            "content" to contents,
            disableBlurOptions
        )

        return options
    }

    private fun statusList(onStatusChanged: (newStatus: E) -> Unit) =
        detached.verticalLayout {
            style {
                padding = 12.px
            }

            values.forEachIndexed { index, status ->

                statusOption(status.first, status.second, onSelectStatus = {
                    onStatusChanged.invoke(it)
                })
                if (index < values.size - 1) {
                    verticalFiller(6)
                }
            }
        }

    private fun LinearLayout.statusOption(status: E, statusColor: Color, onSelectStatus: (selectedStatus: E) -> Unit) {
        textView {
            style {
                width = matchParent
                color = statusColor
                padding = "2px 28px".dimen()
                border = "1px solid ${AppColors.borderColorHex}"
                borderRadius = 4.px
                hover {
                    color = Color.white
                    backgroundColor = statusColor
                    borderColor = statusColor
                }
                pointerCursor()
            }

            onClick = {
                onSelectStatus.invoke(status)
            }

            text = itemToString(status)
        }
    }

}