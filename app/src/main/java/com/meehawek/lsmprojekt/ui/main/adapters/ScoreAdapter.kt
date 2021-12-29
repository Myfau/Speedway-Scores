package com.meehawek.lsmprojekt.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.meehawek.lsmprojekt.R
import com.meehawek.lsmprojekt.data.Score
import com.meehawek.lsmprojekt.databinding.ItemScoreBinding
import com.meehawek.lsmprojekt.viewmodels.ScoreViewModel

class ScoreAdapter internal constructor(
    private val mSubjectViewModel : ScoreViewModel
): ListAdapter<Score, ScoreAdapter.ScoreViewHolder>(ScoreDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        return ScoreViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val subject = getItem(position)
        holder.bind(subject, mSubjectViewModel)
    }

    class ScoreViewHolder(val binding: ItemScoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentScore: Score, scoreViewModel: ScoreViewModel) {
            binding.scoreScore = currentScore
            binding.scoreHosts = currentScore
            binding.scoreGuests = currentScore
            binding.scoreDate = currentScore
            binding.scoreViewModel = scoreViewModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ScoreViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ItemScoreBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.item_score,
                    parent, false
                )
                return ScoreViewHolder(binding)

            }
        }
    }
}

private class ScoreDiffCallback : DiffUtil.ItemCallback<Score>() {
    override fun areItemsTheSame(oldItem: Score, newItem: Score): Boolean {
        return oldItem.vid == newItem.vid
    }

    override fun areContentsTheSame(oldItem: Score, newItem: Score): Boolean {
        return oldItem == newItem
    }
}