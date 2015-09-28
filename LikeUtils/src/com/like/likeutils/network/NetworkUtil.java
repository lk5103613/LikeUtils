package com.like.likeutils.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

@SuppressLint("NewApi")
public class NetworkUtil {

	private RequestQueue mQueue;
	private ImageLoader mLoader;
	private static Context mContext;
	private static NetworkUtil mInstance;

	private NetworkUtil(Context context) {
		mContext = context.getApplicationContext();
		mQueue = getRequestQueue();

		mLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
			private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(
					20 * 1024 * 1024);

			@Override
			public Bitmap getBitmap(String url) {
				return cache.get(url);
			}

			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				cache.put(url, bitmap);
			}
		});
	}

	public RequestQueue getRequestQueue() {
		if (mQueue == null) {
			mQueue = Volley.newRequestQueue(mContext);
		}
		return mQueue;
	}

	public <T> void addToRequstQueue(Request<T> req) {
		getRequestQueue().add(req);
	}

	public ImageLoader getImageLoader() {
		return mLoader;
	}

	public static synchronized NetworkUtil getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new NetworkUtil(context.getApplicationContext());
		}
		return mInstance;
	}

}
