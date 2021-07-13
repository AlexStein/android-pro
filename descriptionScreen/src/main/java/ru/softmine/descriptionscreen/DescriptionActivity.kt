package ru.softmine.descriptionscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.CircleCropTransformation
import org.koin.android.viewmodel.ext.android.viewModel
import ru.softmine.descriptionscreen.databinding.ActivityDescriptionBinding
import ru.softmine.utils.network.isOnline
import ru.softmine.utils.ui.AlertDialogFragment
import ru.softmine.utils.ui.EquilateralImageView

class DescriptionActivity : AppCompatActivity() {

    private val ui: ActivityDescriptionBinding by lazy {
        ActivityDescriptionBinding.inflate(layoutInflater)
    }
    private lateinit var model: DescriptionViewModel
    private fun initViewModel() {
        val vm: DescriptionViewModel by viewModel()
        model = vm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.root)

        setActionbarHomeButtonAsUp()
        ui.descriptionScreenSwipeRefreshLayout.setOnRefreshListener { startLoadingOrShowError() }
        ui.checkFavorite.setOnCheckedChangeListener { _, checked ->
            setFavorite(checked)
        }
        initViewModel()
        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setData() {
        val bundle = intent.extras
        val word = bundle?.getString(WORD_EXTRA)
        ui.descriptionHeader.text = word
        ui.descriptionTextview.text = bundle?.getString(DESCRIPTION_EXTRA)
        ui.transcriptionTextview.text = bundle?.getString(TRANSCRIPTION)
        ui.alternativeTranslationTextview.text = bundle?.getString(ALT_TRANSLATION)
        val imageLink = bundle?.getString(URL_EXTRA)
        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            loadCoinPhoto(ui.descriptionImageview, imageLink)
        }

        ui.checkFavorite.isChecked = model.isFavorite(word!!)
    }

    private fun setFavorite(isChecked: Boolean) {
        val bundle = intent.extras
        val word = bundle?.getString(WORD_EXTRA)

        if (word.isNullOrEmpty()) {
            return
        }
        model.saveFavorite(word, isChecked)
    }

    private fun loadCoinPhoto(descriptionImageview: EquilateralImageView, imageLink: String) {
        descriptionImageview.load("https:$imageLink") {
            placeholder(R.drawable.ic_no_photo_vector)
            error(R.drawable.ic_load_error_vector)
            transformations(CircleCropTransformation())
        }
    }

    private fun startLoadingOrShowError() {
        if (isOnline(applicationContext)) {
            setData()
        } else {
            AlertDialogFragment.newInstance(
                getString(R.string.dialog_title_device_is_offline),
                getString(R.string.dialog_message_device_is_offline)
            ).show(
                supportFragmentManager,
                DIALOG_FRAGMENT_TAG
            )
            stopRefreshAnimationIfNeeded()
        }
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (ui.descriptionScreenSwipeRefreshLayout.isRefreshing) {
            ui.descriptionScreenSwipeRefreshLayout.isRefreshing = false
        }
    }

    companion object {

        private const val DIALOG_FRAGMENT_TAG = "8c7dff51-9769-4f6d-bbee-a3896085e76e"

        private const val WORD_EXTRA = "WORD_EXTRA"
        private const val DESCRIPTION_EXTRA = "DESCRIPTION_EXTRA"
        private const val URL_EXTRA = "URL_EXTRA"
        private const val TRANSCRIPTION = "TRANSCRIPTION"
        private const val ALT_TRANSLATION = "ALT_TRANSLATION"

        // TODO: Use Parcelable

        fun getIntent(
            context: Context,
            word: String,
            description: String,
            url: String?,
            transcription: String?,
            alt_translation: String?
        ): Intent = Intent(context, DescriptionActivity::class.java).apply {
            putExtra(WORD_EXTRA, word)
            putExtra(DESCRIPTION_EXTRA, description)
            putExtra(URL_EXTRA, url)
            putExtra(TRANSCRIPTION, transcription)
            putExtra(ALT_TRANSLATION, alt_translation)
        }
    }
}