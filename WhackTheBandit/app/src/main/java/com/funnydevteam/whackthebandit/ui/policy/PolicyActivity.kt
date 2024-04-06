package com.funnydevteam.whackthebandit.ui.policy

import android.content.ActivityNotFoundException
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import by.kirich1409.viewbindingdelegate.viewBinding
import com.funnydevteam.whackthebandit.R
import com.funnydevteam.whackthebandit.databinding.ActivityPolicyBinding

class PolicyActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityPolicyBinding>()
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
        setContentView(R.layout.activity_policy)
        setUpMatches()
    }

    private fun setUpMatches() = with(binding) {
        gameView.apply {
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

    override fun onBackPressed() {
        if (binding.gameView.canGoBack()) {
            binding.gameView.goBack()
        }
    }
}