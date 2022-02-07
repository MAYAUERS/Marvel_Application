package com.example.marvelapplication.features.favorite.view

import android.view.View
import coil.load
import com.example.marvelapplication.R
import com.example.marvelapplication.databinding.ListItemFavoriteBinding
import com.example.marvelapplication.features.character.view.BaseAdapter
import com.example.marvelapplication.features.favorite.database.FavoriteDto


class FavoriteAdapter : BaseAdapter<FavoriteDto>() {

    var clickListener: (favoriteId: Long) -> Unit = { }
    var clickListenerFavorite: (favorite: FavoriteDto) -> Unit = { }

    override fun getLayoutRes(): Int = R.layout.list_item_favorite

    override fun View.bindView(item: FavoriteDto, viewHolder: ViewHolder) {
        val binding = ListItemFavoriteBinding.bind(this)

        setOnClickListener { clickListener(item.favoriteId) }

        binding.favoriteName.text = item.favoriteName
        binding.favoriteImage.load(item.favoriteUrl)
        binding.favoriteFavorite.setOnClickListener {
            clickListenerFavorite(item)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.setIsRecyclable(false)
    }
}
