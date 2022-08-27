package com.chaev.fastfin.ui.adapter

sealed class ListItem {
    data class Header(
        val date: String
    ) : ListItem()

    data class Item(
        val code: String,
        val rate: Float
    ) : ListItem()
}
