package com.example.marvelapplication.features.character.view

import android.view.View
import coil.load
import com.example.marvelapplication.R
import com.example.marvelapplication.features.character.model.MarvelCharacters
import com.example.marvelapplication.databinding.ListItemCharacterBinding

class CharacterAdapter : BaseAdapter<MarvelCharacters>() {

    var clickListener: (characterId: Long) -> Unit = { }
    var favoriteListener: (character: MarvelCharacters) -> Unit = { }

    override fun getLayoutRes(): Int = R.layout.list_item_character

    override fun View.bindView(item: MarvelCharacters, viewHolder: ViewHolder) {
        val binding = ListItemCharacterBinding.bind(this)

        setOnClickListener { clickListener(item.id) }

        binding.characterName.text = item.name
        binding.characterImage.load(item.thumbnail.getUrl())
        binding.characterFavorite.setOnClickListener {
            favoriteListener(item)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.setIsRecyclable(false)
    }
}