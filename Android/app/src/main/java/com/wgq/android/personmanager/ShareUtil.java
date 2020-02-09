package com.wgq.android.personmanager;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

public class ShareUtil {
    private static final String TAG = "ShareUtil";

    static void dealTextMessage(Intent intent){
        String share = intent.getStringExtra(Intent.EXTRA_TEXT);
        String title = intent.getStringExtra(Intent.EXTRA_TITLE);
        Log.e(TAG, share);
        //Log.e(TAG, title);
    }

    static void dealPicStream(Intent intent){
        Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
    }

    static void dealMultiplePicStream(Intent intent){
        ArrayList<Uri> arrayList = intent.getParcelableArrayListExtra(intent.EXTRA_STREAM);
    }
}
