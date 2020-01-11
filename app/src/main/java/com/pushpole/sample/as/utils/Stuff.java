package com.pushpole.sample.as.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.util.Consumer;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Stuff {

    /**
     * Return a list from all inputs
     */
    @SafeVarargs
    public static <T> List<T> listOf(T... data) {
        return Arrays.asList(data);
    }

    /**
     * Shows a dialog having an EditText.
     * @param callback will be called when user clicked on OK. Callback contains the text that was entered in editText.
     * @see Consumer which is a simple interface.
     */
    public static void prompt(Context activityContext, String title, String message, final Consumer<String> callback) {
        final EditText editText = new EditText(activityContext);
        editText.setSingleLine();
        editText.setMaxLines(1);

        new AlertDialog.Builder(activityContext)
                .setIcon(android.R.drawable.ic_input_get)
                .setTitle(title)
                .setMessage(message)
                .setView(editText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String text = editText.getText().toString();
                        callback.accept(text);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Nothing
                    }
                }).create().show();
    }

    /**
     * Same as the other function but can pass default text to editText.
     */
    public static void prompt(Context activityContext, String title, String message, String defaultText, final Consumer<String> callback) {
        final EditText editText = new EditText(activityContext);
        editText.setSingleLine();
        editText.setMaxLines(1);
        editText.setText(defaultText);

        new AlertDialog.Builder(activityContext)
                .setIcon(android.R.drawable.ic_input_get)
                .setTitle(title)
                .setMessage(message)
                .setView(editText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String text = editText.getText().toString();
                        callback.accept(text);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.accept(null);
                    }
                }).create().show();
    }

    /**
     * Show simple information using alertDialog.
     */
    public static void alert(Context activityContext, String title, String message) {
        new AlertDialog.Builder(activityContext)
                .setIcon(android.R.drawable.ic_menu_info_details)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    /**
     * Add text instead of replacing the text in android studio.
     * Takes a textView and a text and adds the text to the textView.
     */
    public static void addText(TextView textView, String text) {
        if (textView == null) return;
        String currentText = (String) textView.getText();
        if (currentText == null) currentText = "";
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        String newText = currentText + "\n-----\n"  + text + "\nTime: " + currentDateTimeString + "\n";


        textView.setText(newText);
    }
}
