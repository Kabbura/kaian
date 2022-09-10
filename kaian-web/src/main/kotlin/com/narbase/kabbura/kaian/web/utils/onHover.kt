package com.narbase.kabbura.kaian.web.utils

import com.narbase.kunafa.core.css.RuleSet
import com.narbase.kunafa.core.css.hover

fun RuleSet.onHover(ruleSet: RuleSet, rules: RuleSet.() -> Unit) {
    addCompoundRuleSet(ruleSet.selector.hover, rules)
}
