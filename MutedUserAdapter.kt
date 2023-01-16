package com.zat.lexikey.views.adapter

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.zat.lexikey.R
import com.zat.lexikey.base.BaseAdapter
import com.zat.lexikey.base.BaseViewHolder
import com.zat.lexikey.models.muteUsersModel.MutedData
import com.zat.lexikey.utils.IMAGE_BASE_URL
import com.zat.lexikey.utils.singleClick
import com.zat.lexikey.utils.visible
import kotlinx.android.synthetic.main.muted_user_item.view.*

class MutedUserAdapter(var context: Context, var onClick: (Int) -> Unit) :
    BaseAdapter<MutedUserAdapter.MutedUserViewHolder, MutedData>() {

    class MutedUserViewHolder(itemView: View) : BaseViewHolder(itemView) {
    }

    override val layoutId: Int
        get() = R.layout.muted_user_item

    override fun setData(holder: MutedUserViewHolder, model: MutedData, position: Int) {
        var view = holder.itemView

        view.img_unmute.singleClick {
            onClick(model.muted_user?.id!!)
        }

        view.title.text = model.muted_user?.name

        Glide.with(context).load(IMAGE_BASE_URL + model.muted_user?.name)
            .placeholder(R.drawable.dummy_user).into(view.imgProfile)

    }
}