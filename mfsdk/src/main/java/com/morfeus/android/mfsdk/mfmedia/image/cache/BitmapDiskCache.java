package com.morfeus.android.mfsdk.mfmedia.image.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.morfeus.android.mfsdk.log.LogManager;
import com.morfeus.android.mfsdk.mfmedia.image.MfImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Cache bitmaps in internal disk storage.
 */
public class BitmapDiskCache implements MfImageLoader.ImageCache {
    private static final String TAG = BitmapDiskCache.class.getSimpleName();

    private Context mContext;

    public BitmapDiskCache(Context context) {
        mContext = context;
    }

    @Override
    public Bitmap getBitmap(String url, String botId) {
        String imgDir = getImageDirPath(botId);
        return BitmapFactory.decodeFile(imgDir + "/" + url);
    }

    @Override
    public void putBitmap(@NonNull String url, @NonNull Bitmap bitmap, String botId) {
        File botDir = createFile(url, botId);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(botDir);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            LogManager.e(TAG, "Error: fail to cache bitmap", e);
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                LogManager.e(TAG, "Error: fail to close FileOutputStream", e);
            }
        }
    }

    /**
     * Always create file in specific bot directory  with given name.
     *
     * @param name file name to create
     * @param dir  bot directory in which to create file
     * @return created file inside given bot directory
     */
    @NonNull
    private File createFile(@NonNull String name, String dir) {
        String imgPath = getImageDirPath(dir);
        File botDir = new File(imgPath);
        if (botDir.mkdirs() || botDir.isDirectory()) {
            // bot dir always be exist
            return new File(botDir, name);
        }
        return null;
    }

    /**
     * Returns image directory for given bot
     */
    @NonNull
    private String getImageDirPath(String botId) {
        File rootDir = mContext.getFilesDir();
        return rootDir.getAbsolutePath() + "/image/" + botId;
    }

    /**
     * Clear all the cached images for given bot
     *
     * @param botId bot for which to clear image cache.
     */
    public void clearAll(String botId) {
        File imgDir = new File(getImageDirPath(botId));
        for (File file : imgDir.listFiles()) {
            if (!file.isDirectory())
                file.delete();
        }
    }

    /**
     * Clear all the cached images for all bots.
     */
    public void clearAll() {
        File rootDir = mContext.getFilesDir();
        for (File botDir : rootDir.listFiles()) {
            if (botDir.isDirectory()) {
                clearAll(botDir.getName());
            }
        }
    }

}
