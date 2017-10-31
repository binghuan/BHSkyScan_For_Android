package com.bh.android.bhskyscan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by binghuan on 31/10/2017.
 */

class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private final boolean DBG = true;
    private final String TAG = "BH_ImageDownloader";


    private final ImageView imageview;

    public ImageDownloader(ImageView view) {
        this.imageview = view;
    }

    protected Bitmap doInBackground(String... urls) {
        String url2Display = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(url2Display).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return mIcon11;
    }

    protected void onPostExecute(Bitmap bitmap) {

        if (bitmap == null) {
            return;
        }
        imageview.setImageBitmap(bitmap);
    }

}
