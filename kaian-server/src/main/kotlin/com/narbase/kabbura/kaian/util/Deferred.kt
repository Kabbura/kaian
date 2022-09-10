package com.narbase.kabbura.kaian.util

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

@Suppress("DeferredIsResult")
fun <T> T.deferred(): Deferred<T> = CompletableDeferred(this)

@Suppress("DeferredIsResult")
fun <T> deferred(block: () -> T): Deferred<T> = CompletableDeferred(block())