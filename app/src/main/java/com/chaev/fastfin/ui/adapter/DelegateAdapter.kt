package com.chaev.fastfin.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DelegateAdapter<T>(vararg delegates: AdapterDelegate<T>) :
    RecyclerView.Adapter<BaseViewHolder<T>>() {

    var items: List<T> = emptyList()
    private val delegates: List<AdapterDelegate<T>> = delegates.toList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return delegates[viewType].createViewHolder(parent)
    }

    override fun getItemViewType(position: Int): Int {
        return delegates.indexOfFirst { it.isForItem(items[position]) }
    }


    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }
}