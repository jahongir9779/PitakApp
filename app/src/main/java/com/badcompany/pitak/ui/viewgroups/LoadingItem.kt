package com.badcompany.pitak.ui.viewgroups

import com.badcompany.pitak.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

class LoadingItem : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }

    override fun getLayout()= R.layout.item_loading

}
