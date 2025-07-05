package com.rams.catatanpenduduk.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.withStyledAttributes
import com.rams.catatanpenduduk.R

class CustomButtonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val button: Button
    private val progressBar: ProgressBar
    private var originalText: String = ""

    init {
        LayoutInflater.from(context).inflate(R.layout.view_custom_loading_button, this, true)
        button = findViewById(R.id.btnSubmit)
        progressBar = findViewById(R.id.progressBar)

        attrs?.let {
            context.withStyledAttributes(it, R.styleable.LoadingButton) {
                originalText = getString(R.styleable.LoadingButton_buttonText) ?: "Submit"
            }
        }

        button.text = originalText
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            button.text = ""
            button.isEnabled = false
            progressBar.visibility = VISIBLE
        } else {
            button.text = originalText
            button.isEnabled = true
            progressBar.visibility = GONE
        }
    }

    fun setOnClickAction(action: () -> Unit) {
        button.setOnClickListener { action() }
    }

    fun setButtonText(text: String) {
        originalText = text
        button.text = text
    }

    fun getButton(): Button = button

}