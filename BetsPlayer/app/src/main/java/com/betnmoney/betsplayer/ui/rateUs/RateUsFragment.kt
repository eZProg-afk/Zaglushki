package com.betnmoney.betsplayer.ui.rateUs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.betnmoney.betsplayer.R
import com.betnmoney.betsplayer.databinding.FragmentRateUsBinding

class RateUsFragment : Fragment(R.layout.fragment_rate_us) {

    private val binding by viewBinding<FragmentRateUsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rateUsButton.setOnClickListener {
            val uri = Uri.parse("$GOOGLE_PLAY_URI${requireActivity().packageName}")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            goToMarket.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
            startActivity(goToMarket)
        }
    }

    private companion object {
        private const val GOOGLE_PLAY_URI = "http://play.google.com/store/apps/details?id="
    }
}