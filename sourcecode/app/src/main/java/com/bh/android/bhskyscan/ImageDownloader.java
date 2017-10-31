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

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private final boolean DBG = true;
    private final String TAG = "BH_ImageDownloader";


    final ImageView imageview;
    private Context mContext = null;

    public ImageDownloader(Context context, ImageView view) {
        mContext = context;
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

    public int mImgSizeW = 32;
    public int mImgSizeH = 32;

    protected void onPostExecute(Bitmap bitmap) {

        if (bitmap == null) {
            return;
        }
//        if (DBG) Log.v(TAG, ">>> getOriginal Bitmap Size: W -> " +
//                bitmap.getWidth() + ", H -> " + bitmap.getHeight());
//        if (DBG) Log.v(TAG, ">>> Convert Bitmap Size to : W -> " +
//                mImgSizeW + ", H -> " + mImgSizeH);
//
//        Drawable d = new BitmapDrawable(mContext.getResources(),
//                Bitmap.createScaledBitmap(bitmap, mImgSizeW, mImgSizeH, false));
        imageview.setImageBitmap(bitmap);
    }

}
