package com.meehawek.lsmprojekt.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meehawek.lsmprojekt.R
import com.meehawek.lsmprojekt.data.Score
import com.meehawek.lsmprojekt.databinding.ItemFavouriteScoreBinding
import com.meehawek.lsmprojekt.viewmodels.FavouriteScoresViewModel

class FavouriteScoresAdapter internal constructor(
    private val mSubjectViewModel : FavouriteScoresViewModel
): ListAdapter<Score, FavouriteScoresAdapter.FavouriteScoresViewHolder>(FavouriteScoresDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteScoresViewHolder {
        return FavouriteScoresViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavouriteScoresViewHolder, position: Int) {
        val score = getItem(position)
        holder.bind(score, mSubjectViewModel)
    }

    class FavouriteScoresViewHolder(val binding: ItemFavouriteScoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentScore: Score, favouriteScoresViewModel: FavouriteScoresViewModel) {
            binding.scoreScore = currentScore
            binding.scoreHosts = currentScore
            binding.scoreGuests = currentScore
            binding.scoreDate = currentScore
            binding.favouriteScoresViewModel = favouriteScoresViewModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FavouriteScoresViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ItemFavouriteScoreBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.item_favourite_score,
                    parent, false
                )
                return FavouriteScoresViewHolder(binding)

            }
        }
    }
}

private class FavouriteScoresDiffCallback : DiffUtil.ItemCallback<Score>() {
    override fun areItemsTheSame(oldItem: Score, newItem: Score): Boolean {
        return oldItem.vid == newItem.vid
    }

    override fun areContentsTheSame(oldItem: Score, newItem: Score): Boolean {
        return oldItem == newItem
    }
}