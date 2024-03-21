package com.example.discoveryourself.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.discoveryourself.R

object AlertUtils {
    fun showSaveChanges(ct: Context?, onDialogButtonClickListener: DialogButtonClickListener) {
        val li = LayoutInflater.from(ct)
        val promptsView: View = li.inflate(R.layout.save_changes_layout, null)
        val mDialogBuilder = AlertDialog.Builder(
            ct!!
        )
        mDialogBuilder.setView(promptsView)
        val YesTextView = promptsView.findViewById<TextView>(R.id.YesTextView)
        val NoTextView = promptsView.findViewById<TextView>(R.id.NoTextView)
        val CancelTextView = promptsView.findViewById<TextView>(R.id.CancelTextView)
        val alertDialog = mDialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        YesTextView.setOnClickListener { v: View? ->
            onDialogButtonClickListener.onYesButtonClick()
            alertDialog.dismiss()
        }
        NoTextView.setOnClickListener { v: View? ->
            onDialogButtonClickListener.onNoButtonClick()
            alertDialog.dismiss()
        }
        CancelTextView.setOnClickListener { v: View? -> alertDialog.dismiss() }
        alertDialog.show()
    }

    fun showError(ct: Context?, text: String?) {
        val li = LayoutInflater.from(ct)
        val promptsView: View = li.inflate(R.layout.alert_error_layout, null)
        val mDialogBuilder = AlertDialog.Builder(
            ct!!
        )
        mDialogBuilder.setView(promptsView)
        val TextView1 = promptsView.findViewById<TextView>(R.id.TextView1)
        val Image2 = promptsView.findViewById<ImageView>(R.id.Image2)
        TextView1.text = text
        val alertDialog = mDialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        Image2.setOnClickListener { v: View? -> alertDialog.cancel() }
        alertDialog.show()
    }
}