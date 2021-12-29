package com.meehawek.lsmprojekt.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.meehawek.lsmprojekt.ui.main.FavouritesFragment
import com.meehawek.lsmprojekt.ui.main.FirstFragment
import com.meehawek.lsmprojekt.ui.main.ScoresFragment
import com.meehawek.lsmprojekt.ui.main.SecondFragment


class ViewPagerAdapter(
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        if (position == 0) fragment = ScoresFragment() else if (position == 1) fragment =
            FirstFragment() else if (position == 2) fragment = SecondFragment()
            else if (position == 3) fragment = FavouritesFragment()
        return fragment!!
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title : String? = null
        if (position == 0) title = "EKSTRALIGA" else if (position == 1) title =
            "I LIGA" else if (position == 2) title = "II LIGA"
        else if (position == 3) title = "ULUBIONE"
        return title
    }
}