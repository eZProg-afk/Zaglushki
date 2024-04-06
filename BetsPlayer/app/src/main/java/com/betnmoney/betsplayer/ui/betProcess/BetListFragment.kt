package com.betnmoney.betsplayer.ui.betProcess

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.betnmoney.betsplayer.R
import com.betnmoney.betsplayer.databinding.FragmentBetListBinding
import com.betnmoney.betsplayer.ui.adapters.diffCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.betnmoney.betsplayer.ui.adapters.ItemMatch
import com.betnmoney.betsplayer.ui.adapters.matchesDelegate

class BetListFragment : Fragment(R.layout.fragment_bet_list) {

    private val binding by viewBinding<FragmentBetListBinding>()
    private val adapter = AsyncListDifferDelegationAdapter(diffCallback, matchesDelegate {
        startActivity(
            Intent(requireContext(), SelectTeamBetActivity::class.java).apply {
                putExtra("itemBet", it)
            }
        )
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            adapter.items = items.shuffled()
            fullBetListRecyclerView.adapter = adapter
        }
    }

    private companion object {
        private val items = listOf(
            ItemMatch(
                firstTeamName = "Manchester city",
                firstTeamLogo = "https://i.pinimg.com/originals/f1/46/84/f14684933450d589b4907f2537fcd297.png",
                secondTeamName = "Ajax",
                secondTeamLogo = "https://slm-assets.secondlife.com/assets/14397710/view_large/ajax_logo.jpg?1466351274",
                firstCoeff = 1.24f,
                secondCoeff = 0.34f,
                resultCoeff = 3.1f
            ),
            ItemMatch(
                firstTeamName = "Barcelona",
                firstTeamLogo = "https://www.comprarbanderas.es/images/banderas/400/16852-espana-cataluna-fc-barcelona_400px.jpg",
                secondTeamName = "Real Madrid",
                secondTeamLogo = "https://i.pinimg.com/originals/65/c9/90/65c990f0f251e5545a8ff5412a7d6b97.jpg",
                firstCoeff = 2.28f,
                secondCoeff = 0.22f,
                resultCoeff = 1.11f
            ),
            ItemMatch(
                firstTeamName = "Napoli",
                firstTeamLogo = "https://img.besthqwallpapers.com/Uploads/28-8-2018/63404/ssc-napoli-4k-italian-football-club-logo-2d-art.jpg",
                secondTeamName = "PSG",
                secondTeamLogo = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtftkFOLSGkWfRoc1G9zWqlOb9c4KbZ7dwFQ&usqp=CAU",
                firstCoeff = 3.12f,
                secondCoeff = 1.23f,
                resultCoeff = 0.72f
            ),
            ItemMatch(
                firstTeamName = "Manchester United",
                firstTeamLogo = "https://i.pinimg.com/originals/57/00/2d/57002df28dc9df1e0a3da70b082feb38.jpg",
                secondTeamName = "Marsel",
                secondTeamLogo = "https://thumbs.dreamstime.com/b/flag-marseille-france-also-known-as-marseilles-english-city-131893477.jpg",
                firstCoeff = 1.12f,
                secondCoeff = 3.0f,
                resultCoeff = 0.0f
            ),
            ItemMatch(
                firstTeamName = "Liverpool",
                firstTeamLogo = "http://cdn.shopify.com/s/files/1/0269/6246/0753/products/liverpoolflag_c48f6ab1-ee54-4632-a21c-7a30fd34824a_1200x1200.jpg?v=1610714888",
                secondTeamName = "Bayern Munich",
                secondTeamLogo = "https://w0.peakpx.com/wallpaper/39/79/HD-wallpaper-fc-bayern-munich-munich-logo-flag-bayern.jpg",
                firstCoeff = 2.65f,
                secondCoeff = 1.89f,
                resultCoeff = 0.0f
            ),
            ItemMatch(
                firstTeamName = "Arsenal",
                firstTeamLogo = "https://i.pinimg.com/originals/6a/4f/f4/6a4ff4760d36b823e2c295ab24f2d191.png",
                secondTeamName = "Chelsea",
                secondTeamLogo = "https://cdnb.artstation.com/p/assets/images/images/041/189/377/large/chg_studio2021-229e35de-bfc6-4a3f-b796-ece3a5b86d96-rw-1920.jpg?1631018001",
                firstCoeff = 1.11f,
                secondCoeff = 0.98f,
                resultCoeff = 0.0f
            ),
            ItemMatch(
                firstTeamName = "Villarreal",
                firstTeamLogo = "https://i.pinimg.com/originals/b9/3a/ab/b93aabb3fd82f197f4fe6f3bc7d52186.jpg",
                secondTeamName = "Juventus",
                secondTeamLogo = "https://img3.goodfon.ru/wallpaper/nbig/7/35/logo-wallpaper-football-sport-juventus-fc.jpg",
                firstCoeff = 2.98f,
                secondCoeff = 1.45f,
                resultCoeff = 0.0f
            ),
            ItemMatch(
                firstTeamName = "Milan",
                firstTeamLogo = "https://img5.goodfon.ru/wallpaper/nbig/d/46/wallpaper-sport-logo-football-ac-milan.jpg",
                secondTeamName = "Roma",
                secondTeamLogo = "https://www.sports.ru/dynamic_images/post/309/234/7/share/2e35f1_no_logo_no_text.jpg",
                firstCoeff = 2.15f,
                secondCoeff = 1.16f,
                resultCoeff = 3.12f
            )
        ).shuffled()
    }
}