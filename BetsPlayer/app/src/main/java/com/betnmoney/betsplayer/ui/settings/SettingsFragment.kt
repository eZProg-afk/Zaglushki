package com.betnmoney.betsplayer.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.betnmoney.betsplayer.R
import com.betnmoney.betsplayer.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding<FragmentSettingsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            togglePushesSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    Toast.makeText(requireContext(), "Pushes on!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Pushes off!", Toast.LENGTH_SHORT).show()
                }
            }

            toggleAdvicesSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    Toast.makeText(requireContext(), "Advices on!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Advices off!", Toast.LENGTH_SHORT).show()
                }
            }

            toggleSound.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    Toast.makeText(requireContext(), "Sound on!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Sound off!", Toast.LENGTH_SHORT).show()
                }
            }

            showAllContentSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    Toast.makeText(requireContext(), "All content is showing now!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Not all content is showing now.", Toast.LENGTH_SHORT).show()
                }
            }

            exitButton.setOnClickListener {
                requireActivity().finishAffinity()
            }
        }
    }
}