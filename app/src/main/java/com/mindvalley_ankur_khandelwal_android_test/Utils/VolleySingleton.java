package com.mindvalley_ankur_khandelwal_android_test.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.cache.BitmapImageCache;
import com.android.volley.toolbox.ImageCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * A singleton class to use in
 */
public class VolleySingleton {
    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;

    VolleySingleton(){}

    public static void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
//        mImageLoader = new ImageLoader(mRequestQueue, BitmapImageCache.getInstance(null));

        mImageLoader=new ImageLoader(mRequestQueue, new ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }

            @Override
            public void invalidateBitmap(String url) {

            }

            @Override
            public void clear() {

            }
        });

    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    public static ImageLoader getImageLoader() {
        if (mImageLoader != null) {
            return mImageLoader;
        } else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }


}
