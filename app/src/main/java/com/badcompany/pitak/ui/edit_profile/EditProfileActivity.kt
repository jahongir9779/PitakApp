package com.badcompany.pitak.ui.edit_profile

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.asksira.bsimagepicker.BSImagePicker
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.BaseActivity
import com.badcompany.pitak.util.AppPrefs
import com.badcompany.pitak.util.getRealPathFromURI
import com.badcompany.pitak.util.loadBitmap
import com.badcompany.pitak.util.loadImageUrl
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.File

class EditProfileActivity : BaseActivity(), BSImagePicker.OnSingleImageSelectedListener,
    BSImagePicker.ImageLoaderDelegate {


    private val viewModel: EditProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        setupViews()
        attachListeners()
        subscribeObservers()
    }


    fun attachListeners() {
        edtName.doOnTextChanged { text, start, before, count -> checkInputs() }

        edtSurname.doOnTextChanged { text, start, before, count -> checkInputs() }

        cardAvatar.setOnClickListener {
            val singleSelectionPicker: BSImagePicker =
                BSImagePicker.Builder("com.badcompany.pitak.fileprovider")
                    .setSpanCount(3) //Default: 3. This is the number of columns
                    .setTag("")
                    .build()

            singleSelectionPicker.show(supportFragmentManager, "picker")
        }

        btnUpdate.setOnClickListener {
            viewModel.updateProfile(edtName.text.toString(),
                                    edtSurname.text.toString())
        }


    }

    private fun checkInputs() {
        btnUpdate.isEnabled = !edtName.text.isNullOrBlank() && !edtSurname.text.isNullOrBlank()

    }

    fun subscribeObservers() {

        viewModel.uploadPhotoResp.observe(this) {
            val result = it ?: return@observe
            when (result) {
                is ErrorWrapper.RespError -> {
                    ivAvatar.visibility = View.VISIBLE
                    progressAvatar.visibility = View.INVISIBLE
                    Snackbar.make(llParent,
                                  result.message ?: getString(R.string.system_error),
                                  Snackbar.LENGTH_SHORT).show()
                }
                is ErrorWrapper.SystemError -> {
                    ivAvatar.visibility = View.VISIBLE
                    progressAvatar.visibility = View.INVISIBLE
                    Snackbar.make(llParent, result.err.localizedMessage, Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ResultWrapper.Success -> {
                    ivAvatar.visibility = View.VISIBLE
                    progressAvatar.visibility = View.INVISIBLE
                }
                ResultWrapper.InProgress -> {
                    ivAvatar.visibility = View.INVISIBLE
                    progressAvatar.visibility = View.VISIBLE
                }
            }.exhaustive

        }

        viewModel.isUpdating.observe(this) {
            if (it) {
                tvError.visibility = View.INVISIBLE
                btnUpdate.startAnimation()
            } else {
                btnUpdate.revertAnimation()
            }
        }
        viewModel.errorMessage.observe(this) {
            btnUpdate.revertAnimation()
            tvError.isVisible = true
            tvError.text = it
        }
        viewModel.updateSuccess.observe(this) {
            btnUpdate.doneLoadingAnimation(Color.GREEN,
                                           ContextCompat.getDrawable(this,
                                                                     R.drawable.ic_baseline_check_24)!!
                                               .toBitmap())
            finish()
        }

    }

    fun setupViews() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        edtSurname.setText(AppPrefs.surname)
        edtName.setText(AppPrefs.name)
        if (AppPrefs.avatar.isNotBlank()) ivAvatar.loadImageUrl(AppPrefs.avatar)
    }

    override fun onSingleImageSelected(uri: Uri, tag: String?) {
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        ivAvatar.loadBitmap(bitmap)
        viewModel.uploadAvatar(File(uri.getRealPathFromURI(this)))
    }

    override fun loadImage(imageUri: Uri?, ivImage: ImageView) {
        Glide.with(this).load(imageUri).into(ivImage)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}