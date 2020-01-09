package com.zhuo.dean.atmaautomobile.Api;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import com.zhuo.dean.atmaautomobile.R;

public class Helper {
    public static AlertDialog.Builder loadDialog (Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage("Loading... Please wait...");

        return builder;
    }

    public static String BASE_URL = "http://192.168.19.140/9029/api/";
    public static String BASE_URL_IMAGE = "http://192.168.19.140/9029/Pictures/";

    public static Dialog historyDialog (Context context){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_history_data);
        dialog.setCancelable(true);
        return dialog;
    }
}
