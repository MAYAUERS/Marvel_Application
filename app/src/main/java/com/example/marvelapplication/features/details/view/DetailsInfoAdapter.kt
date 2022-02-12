package com.example.marvelapplication.features.details.view

import android.view.View
import coil.load
import com.example.marvelapplication.R
import com.example.marvelapplication.databinding.ListItemDetailsInfoBinding
import com.example.marvelapplication.features.character.view.BaseAdapter
import com.example.marvelapplication.features.details.model.DetailInfo
import com.example.marvelapplication.features.details.model.Thumbnail


class DetailsInfoAdapter : BaseAdapter<DetailInfo>() {

    override fun getLayoutRes(): Int = R.layout.list_item_details_info

    override fun View.bindView(item: DetailInfo, viewHolder: ViewHolder) {
        val binding = ListItemDetailsInfoBinding.bind(this)

        binding.detailInfoName.text = item.title
        binding.detailInfoImage.load(item.thumbnail.getUrl(Thumbnail.ImageType.PORTRAIT))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.setIsRecyclable(false)
    }
}
