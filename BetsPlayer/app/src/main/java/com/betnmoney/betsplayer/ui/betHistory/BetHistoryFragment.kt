package com.betnmoney.betsplayer.ui.betHistory

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.betnmoney.betsplayer.R
import com.betnmoney.betsplayer.data.HistoryDao
import com.betnmoney.betsplayer.databinding.FragmentBetHistoryBinding
import com.betnmoney.betsplayer.ui.adapters.diffCallback
import com.betnmoney.betsplayer.ui.adapters.historyDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class BetHistoryFragment : Fragment(R.layout.fragment_bet_history) {

    private val binding by viewBinding<FragmentBetHistoryBinding>()
    private val historyDao by inject<HistoryDao>()
    private val adapter = AsyncListDifferDelegationAdapter(diffCallback, historyDelegate {
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.betMatchesRecyclerView.adapter = adapter
        fetchHistory()
    }

    private fun fetchHistory() {
        lifecycleScope.launch(Dispatchers.IO) {
            historyDao.fetchHistory()
                .onEach {
                    withContext(Dispatchers.Main) {
                        if (it.isNotEmpty()) {
                            adapter.items = it
                            binding.betMatchesRecyclerView.isVisible = true
                            binding.nothingHereTextView.isVisible = false
                        } else {
                            binding.betMatchesRecyclerView.isVisible = false
                            binding.nothingHereTextView.isVisible = true
                        }
                    }
                }
                .launchIn(lifecycleScope)
        }
    }
}