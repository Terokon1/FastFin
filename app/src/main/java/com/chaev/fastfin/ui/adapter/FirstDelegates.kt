package com.chaev.fastfin.ui.adapter

import android.annotation.SuppressLint
import com.chaev.fastfin.R
import com.chaev.fastfin.databinding.HolderHeaderBinding
import com.chaev.fastfin.databinding.HolderItemBinding

object FirstDelegates {
    fun headerDelegate() = delegateDefine<ListItem>(
        { it is ListItem.Header },
        { parent ->
            holderDefine(parent, R.layout.holder_header, { it as ListItem.Header }) {
                val binding = HolderHeaderBinding.bind(itemView)
                binding.date.text = it.date

            }
        }
    )

    fun itemDelegate() = delegateDefine<ListItem>(
        { it is ListItem.Item },
        { parent ->
            holderDefine(parent, R.layout.holder_item, { it as ListItem.Item }) {
                val binding = HolderItemBinding.bind(itemView)
                binding.code.text = it.code
                binding.rate.text = it.rate.toString()
            }
        }
    )
}