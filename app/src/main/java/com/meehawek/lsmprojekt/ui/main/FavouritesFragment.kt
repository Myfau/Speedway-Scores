package com.meehawek.lsmprojekt.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.meehawek.lsmprojekt.R
import com.meehawek.lsmprojekt.databinding.FragmentFavouritesBinding
import com.meehawek.lsmprojekt.ui.base.BaseFragment
import com.meehawek.lsmprojekt.ui.main.adapters.FavouriteScoresAdapter
import com.meehawek.lsmprojekt.viewmodels.FavouriteScoresViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : BaseFragment() {

    private var mBinding: FragmentFavouritesBinding? = null
    lateinit var adapter: FavouriteScoresAdapter
    val subjectViewModel: FavouriteScoresViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentFavouritesBinding>(
            inflater, R.layout.fragment_favourites, container, false
        )
        this.mBinding = binding
        adapter = FavouriteScoresAdapter(subjectViewModel)
        subscribeUi(adapter)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            recyclerView.adapter = adapter
            viewModel = subjectViewModel
        }
        observeModelNavigation(subjectViewModel)
    }

    private fun subscribeUi(newAdapter: FavouriteScoresAdapter) {
        subjectViewModel.favScores.observe(viewLifecycleOwner) { result ->
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
        fun newInstance(sectionNumber: Int): FavouritesFragment {
            return FavouritesFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

}