package com.tgirard12.jugkotlinandroid

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.graphics.drawable.VectorDrawableCompat
import android.view.View
import android.widget.TextView
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified V : View> Activity.bindView(resId: Int): ReadOnlyProperty<Activity, V> = Delegate { this.findViewById(resId) as V }

class Delegate<in A, out V>(val funFindView: (A) -> V) : ReadOnlyProperty<A, V> {

    override fun getValue(thisRef: A, property: KProperty<*>): V {
        return funFindView(thisRef)
    }
}

fun Activity.createVector(resId: Int) = VectorDrawableCompat.create(this.resources, resId, this.theme)

infix fun String?.printIn(textView: TextView) {
    textView.text = this
}

fun Activity.startActivity(lambda: () -> Intent) {
    this.startActivity(lambda())
}

fun intent(lambda: IntentB.() -> Unit): Intent {
    val intentB = IntentB()
    intentB.lambda()
    return intentB.build()
}

class IntentB() {
    private var actionStr: String = ""
    private var url: String = ""

    infix fun IntentB.action(init: () -> String) {
        this.actionStr = init()
    }

    infix fun IntentB.url(init: () -> String) {
        this.url = init()
    }

    fun build(): Intent = Intent(actionStr, Uri.parse(url))
}