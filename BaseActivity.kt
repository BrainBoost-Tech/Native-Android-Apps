package com.zat.lexikey.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.kaopiz.kprogresshud.KProgressHUD
import com.zat.lexikey.R
import com.zat.lexikey.utils.SOMETHING_WENT_WRONG


abstract class BaseActivity : AppCompatActivity() {

    var progressBar: KProgressHUD? = null

    private lateinit var options: NavOptions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressBar = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(true)
            .setBackgroundColor(Color.TRANSPARENT)
            .setAnimationSpeed(1)
            .setDimAmount(0.5f)

        options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.fade_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out)
            .build()

    }

    abstract fun attachViewMode()


    fun showLoadingBar() {
        progressBar?.show()
    }

    fun hideLoadingBar() {
        progressBar?.dismiss()
    }


    fun replaceFragmentInAuth(action: Int) {
        Navigation.findNavController(this, R.id.authHostFragment).navigate(action, null, options)
    }

    fun replaceFragmentInAuth(action: Int, bundle: Bundle) {
        Navigation.findNavController(this, R.id.authHostFragment).navigate(action, bundle, options)
    }

    fun replaceAndRemoveFragmentInAuth(action1: Int, action2: Int, bundle: Bundle? = null) {
        Navigation.findNavController(this, R.id.authHostFragment).popBackStack(action1, true)
        Navigation.findNavController(this, R.id.authHostFragment).navigate(action2, bundle, options)
    }


    fun replaceFragmentInMain(action: Int) {
        Navigation.findNavController(this, R.id.mainHostFragment).navigate(action, null, options)
    }

    fun replaceFragmentInMain(action: Int, bundle: Bundle) {
        Navigation.findNavController(this, R.id.mainHostFragment).navigate(action, bundle, options)
    }

    fun replaceAndRemoveFragmentInMain(action1: Int, action2: Int, bundle: Bundle? = null) {
        Navigation.findNavController(this, R.id.mainHostFragment).popBackStack(action1, true)
        Navigation.findNavController(this, R.id.mainHostFragment).navigate(action2, bundle, options)
    }


    fun replaceFragmentInProfile(action: Int) {
        Navigation.findNavController(this, R.id.profileHostFragment).navigate(action, null, options)
    }

    fun replaceFragmentInProfile(action: Int, bundle: Bundle) {
        Navigation.findNavController(this, R.id.profileHostFragment)
            .navigate(action, bundle, options)
    }

    fun replaceAndRemoveFragmentInProfile(action1: Int, action2: Int, bundle: Bundle? = null) {
        Navigation.findNavController(this, R.id.profileHostFragment).popBackStack(action1, true)
        Navigation.findNavController(this, R.id.profileHostFragment)
            .navigate(action2, bundle, options)
    }

    fun replaceFragmentInLanguage(action: Int) {
        Navigation.findNavController(this, R.id.languageHostFragment)
            .navigate(action, null, options)
    }

    fun replaceFragmentInLanguage(action: Int, bundle: Bundle) {
        Navigation.findNavController(this, R.id.languageHostFragment)
            .navigate(action, bundle, options)
    }

    fun replaceAndRemoveFragmentInLanguage(action1: Int, action2: Int, bundle: Bundle? = null) {
        Navigation.findNavController(this, R.id.languageHostFragment).popBackStack(action1, true)
        Navigation.findNavController(this, R.id.languageHostFragment)
            .navigate(action2, bundle, options)
    }


    fun showToast(message: String?) {
        message?.let {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } ?: kotlin.run {
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT)
                .show()
        }

    }

    fun showSnackbar(view: View, message: String?) {
        message?.let {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
        } ?: kotlin.run {
            Snackbar.make(view, SOMETHING_WENT_WRONG, Snackbar.LENGTH_SHORT).show()
        }

    }
}