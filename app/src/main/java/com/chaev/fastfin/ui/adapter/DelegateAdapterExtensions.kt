package com.chaev.fastfin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun <T> delegateDefine(
    check: (T) -> Boolean,
    holder: (ViewGroup) -> BaseViewHolder<T>
): AdapterDelegate<T> {
    return object : AdapterDelegate<T> {
        override fun createViewHolder(parent: ViewGroup): BaseViewHolder<T> {
            return holder(parent)
        }

        override fun isForItem(item: T): Boolean {
            return check(item)
        }

    }
}

fun <T, E : T> holderDefine(
    parent: ViewGroup,
    @LayoutRes layoutId: Int,
    transform: (T) -> E,
    bindItem: BaseViewHolder<T>.(E) -> Unit
): BaseViewHolder<T> {
    return object : BaseViewHolder<T>(
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    ) {
        override fun bind(item: T) {
            bindItem(transform(item))
        }
    }
}