package com.chaev.fastfin.ui.adapter

import android.view.ViewGroup

interface AdapterDelegate<T> {
    fun createViewHolder(parent: ViewGroup): BaseViewHolder<T>
    fun isForItem(item: T): Boolean
}