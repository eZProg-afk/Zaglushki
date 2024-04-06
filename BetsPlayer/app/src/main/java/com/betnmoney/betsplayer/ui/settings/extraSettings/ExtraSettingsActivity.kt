package com.betnmoney.betsplayer.ui.settings.extraSettings

import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.betnmoney.betsplayer.R
import com.betnmoney.betsplayer.databinding.ActivityExtraSettingsBinding

class ExtraSettingsActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityExtraSettingsBinding>()
    private var uploadMessage: ValueCallback<Array<Uri>>? = null
    private val selectedGame by lazy { intent.getStringExtra("game") ?: "" }
    private val uploadCallback =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                if (uploadMessage == null) return@registerForActivityResult
                val results: Array<Uri>? =
                    WebChromeClient.FileChooserParams.parseResult(activityResult.resultCode, activityResult.data)
                uploadMessage?.onReceiveValue(results)
                uploadMessage = null
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extra_settings)
        setUpMatches()
    }

    override fun onBackPressed() = with(binding.betView) {
        if (canGoBack()) goBack()
    }

    private fun setUpMatches() = with(binding) {
        betView.apply {
            webChromeClient = object : WebChromeClient() {
                override fun onPermissionRequest(request: PermissionRequest?) {
                    /* no-op */
                }

                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    uploadMessage?.onReceiveValue(null)
                    uploadMessage = null

                    uploadMessage = filePathCallback

                    val intent = fileChooserParams!!.createIntent()
                    try {
                        uploadCallback.launch(intent)
                    } catch (e: ActivityNotFoundException) {
                        uploadMessage = null
                        Toast.makeText(
                            applicationContext,
                            "Cannot Open File Chooser",
                            Toast.LENGTH_LONG
                        ).show()
                        return false
                    }
                    return true
                }
            }
            setGame(selectedGame)
        }
    }
}