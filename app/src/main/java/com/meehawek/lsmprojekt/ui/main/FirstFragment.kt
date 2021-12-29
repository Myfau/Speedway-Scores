package com.meehawek.lsmprojekt.ui.main

import com.meehawek.lsmprojekt.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.meehawek.lsmprojekt.databinding.FragmentScoresBinding
import com.meehawek.lsmprojekt.ui.base.BaseFragment
import com.meehawek.lsmprojekt.ui.main.adapters.ScoreAdapter
import com.meehawek.lsmprojekt.viewmodels.ScoreViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FirstFragment : BaseFragment() {

    private var mBinding: FragmentScoresBinding? = null
    lateinit var adapter: ScoreAdapter
    val scoreViewModel: ScoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentScoresBinding>(
            inflater, R.layout.fragment_scores, container, false
        )
        this.mBinding = binding
        adapter = ScoreAdapter(scoreViewModel)
        subscribeUi(adapter)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            recyclerView.adapter = adapter
            viewModel = scoreViewModel
        }
        observeModelNavigation(scoreViewModel)
    }

    private fun subscribeUi(newAdapter: ScoreAdapter) {
        scoreViewModel.scoresPierwszaliga.observe(viewLifecycleOwner) { result ->
            newAdapter.submitList(result)
            newAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): FirstFragment {
            return FirstFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}