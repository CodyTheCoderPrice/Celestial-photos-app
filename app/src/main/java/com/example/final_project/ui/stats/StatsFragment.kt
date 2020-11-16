package com.example.final_project.ui.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.final_project.R
import com.example.final_project.database.StreakRepository
import com.example.final_project.model.Streak
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class StatsFragment : Fragment() {

    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var streakRepository: StreakRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stats, container, false)

        scope.launch(Dispatchers.Default) {
            streakRepository = StreakRepository(view.context)
            setStatsView(view)
        }

        return view
    }

    /**
     * Gets stats from the repository and sets the view with that data
     */
    private suspend fun setStatsView(view: View) {
        when(val streak = streakRepository.getRecentStreak()) {
            null -> {
                withContext(Dispatchers.Default) {
                    streakRepository.addStreak(Streak(UUID.randomUUID(), Date(), 1))
                }
            }
            else -> {
                withContext(Dispatchers.Main) {
                    view.findViewById<TextView>(R.id.stats_streak).text = streak.streak.toString()
                }
            }
        }
    }
}