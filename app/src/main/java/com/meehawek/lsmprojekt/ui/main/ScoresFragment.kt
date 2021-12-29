package com.meehawek.lsmprojekt.ui.main

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import com.meehawek.lsmprojekt.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.meehawek.lsmprojekt.data.MyBroadcastAlarm
import com.meehawek.lsmprojekt.databinding.FragmentScoresBinding
import com.meehawek.lsmprojekt.ui.base.BaseFragment
import com.meehawek.lsmprojekt.ui.main.adapters.ScoreAdapter
import com.meehawek.lsmprojekt.viewmodels.ScoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.junit.Test


@AndroidEntryPoint
class ScoresFragment : BaseFragment() {

    private var mBinding: FragmentScoresBinding? = null
    lateinit var adapter: ScoreAdapter
    val scoreViewModel: ScoreViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.N)
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

        setAlarm()

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
        scoreViewModel.scoresEkstraliga.observe(viewLifecycleOwner) { result ->
            newAdapter.submitList(result)
            newAdapter.notifyDataSetChanged()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setAlarm() {
        createNotificationChannel()
        Log.d("Alarm", "Call setAlarm from ScoresFragment")
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val intent = Intent(context, MyBroadcastAlarm::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        var alarmMgr: AlarmManager? = null
        lateinit var alarmIntent: PendingIntent

        // Set the alarm to start at approximately 10:00 p.m.
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 22)
        }

        alarmMgr?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("20", "Zynik channel", importance)
            channel.description = "Inform about newest results"

            val a : NotificationManager? = null
            val notificationManager = NotificationManagerCompat.from(this.requireContext())
            notificationManager.createNotificationChannel(channel)
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
        fun newInstance(sectionNumber: Int): ScoresFragment {
            return ScoresFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

}