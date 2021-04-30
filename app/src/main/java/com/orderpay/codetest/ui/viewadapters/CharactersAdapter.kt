package com.orderpay.codetest.ui.viewadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.orderpay.codetest.R
import com.orderpay.codetest.database.CharacterEntity
import com.orderpay.codetest.databinding.ItemCharacterBinding

class CharactersAdapter(val callback: CharacterClick) : RecyclerView.Adapter<CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
    = CharacterViewHolder(DataBindingUtil.inflate(
        LayoutInflater.from(parent.context)
        ,R.layout.item_character,parent,
        false))

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
      holder.viewDataBinding.also {
         it.results = results[position]
          // to handle onClick
          it.resultsCallBack = callback
      }
    }

    override fun getItemCount()= results.size


    /**
     * The characters that our Adapter will show
     */
    var results: List<CharacterEntity> = emptyList()
        set(value) {
            field = value
            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }
}

    class CharacterViewHolder(val viewDataBinding: ItemCharacterBinding) :
      RecyclerView.ViewHolder(viewDataBinding.root)

/**
 * Click listener for Character. By giving the block a name it helps a reader understand what it does.
 *
 */
class CharacterClick(val block: (CharacterEntity) -> Unit) {
    /**
     * Called when a character is clicked
     *
     * @param character the character that was clicked
     */
    fun onClick(character: CharacterEntity) = block(character)
}



