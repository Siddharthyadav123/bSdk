/**
 * Copyright (C) 2013 The Android Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.morfeus.android.mfsdk.ui.widget.bubble;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.morfeus.android.mfsdk.mfmedia.image.MfImageLoader;
import com.morfeus.android.mfsdk.volley.VolleyError;

/**
 * Handles fetching an image from a URL as well as the life-cycle of the
 * associated request.
 */
public class MfNetworkImageView extends ImageView implements MfMediaStatusView.MfMediaStatusCallback {
    /**
     * If this will be used to enable/disable media download UI
     */
    public static boolean sEnabledMediaDownloadStatus = false;
    /**
     * If this will be enable image downloads automatically
     */
    public static boolean sImageAutoDownloadingEnabled = true;
    /**
     * The botId for which to fetch image
     */
    private String mBotId;

    /**
     * The URL of the network image to load
     */
    private String mUrl;

    /**
     * The local image name inside project
     */
    private String mLocalImage;

    /**
     * Resource ID of the image to be used as a placeholder until the network image is loaded.
     */
    private int mDefaultImageId;

    /**
     * Resource ID of the image to be used if the network response fails.
     */
    private int mErrorImageId;

    /**
     * Local copy of the ImageLoader.
     */
    private MfImageLoader mImageLoader;

    /**
     * Media loading UI View
     */
    private MfMediaStatusView mMfMediaStatusView;

    /**
     * Current ImageContainer. (either in-flight or finished)
     */
    private MfImageLoader.ImageContainer mImageContainer;

    public MfNetworkImageView(Context context) {
        this(context, null);
    }

    public MfNetworkImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MfNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets URL of the image that should be loaded into this view. Note that calling this will
     * immediately either set the cached image (if available) or the default image specified by
     * {@link MfNetworkImageView#setDefaultImageResId(int)} on the view.
     * <p/>
     * NOTE: If applicable, {@link MfNetworkImageView#setDefaultImageResId(int)} and
     * {@link MfNetworkImageView#setErrorImageResId(int)} should be called prior to calling
     * this function.
     *
     * @param url         The image drawable and URL that should be loaded into this ImageView.
     * @param imageLoader ImageLoader that will be used to make the request.
     */
    @Deprecated
    public void setImageUrl(String[] url, MfImageLoader imageLoader) {
        mLocalImage = url[0];
        mUrl = url[1];
        mImageLoader = imageLoader;
        // The URL has potentially changed. See if we need to load it.
        loadImageIfNecessary(false, true);
    }

    /**
     * Sets URL of the image that should be loaded into this view. Note that calling this will
     * immediately either set the cached image (if available) or the default image specified by
     * {@link MfNetworkImageView#setDefaultImageResId(int)} on the view.
     * <p/>
     * NOTE: If applicable, {@link MfNetworkImageView#setDefaultImageResId(int)} and
     * {@link MfNetworkImageView#setErrorImageResId(int)} should be called prior to calling
     * this function.
     *
     * @param url         The image drawable and URL that should be loaded into this ImageView.
     * @param imageLoader ImageLoader that will be used to make the request.
     */
    public void setImageUrl(String[] url, MfImageLoader imageLoader, MfMediaStatusView mfMediaStatusView) {
        mMfMediaStatusView = mfMediaStatusView;
        mLocalImage = url[0];
        mUrl = url[1];
        mImageLoader = imageLoader;

        if (sEnabledMediaDownloadStatus) {
            setDefaultImageOrNull();
            showImageBitmapOrDownloadIt();
        } else {
            mMfMediaStatusView.hideView();
            loadImageIfNecessary(false, true);
        }
    }

    private void showImageBitmapOrDownloadIt() {
        Bitmap imageBitmap = loadCachedBitmapIfExist();
        if (imageBitmap != null) {
            if (mMfMediaStatusView != null) {
                mMfMediaStatusView.hideView();
            }
            setImageBitmap(imageBitmap);
        } else {
            if (sImageAutoDownloadingEnabled) {
                loadImageIfNecessary(false, true);
            } else if (mMfMediaStatusView != null) {
                if (mUrl == null || mUrl.length() == 0) {
                    mMfMediaStatusView.hideView();
                } else {
                    mMfMediaStatusView.showRetryState();
                }
            }
        }
    }

    private Bitmap loadCachedBitmapIfExist() {
        int width = getWidth();
        int height = getHeight();
        ScaleType scaleType = getScaleType();

        if (getLayoutParams() != null) {
            width = getLayoutParams().width;
            height = getLayoutParams().height;
        }

        MfImageLoader.ImageContainer newContainer = mImageLoader.getLocalImageIfExist(new String[]{mLocalImage, mUrl},
                mBotId, width, height, scaleType);

        if (newContainer != null) {
            return newContainer.getBitmap();
        } else {
            return null;
        }
    }

    /**
     * Sets the default image resource ID to be used for this view until the attempt to load it
     * completes.
     */
    public void setDefaultImageResId(int defaultImage) {
        mDefaultImageId = defaultImage;
    }

    /**
     * Sets the error image resource ID to be used for this view in the event that the image
     * requested fails to load.
     */
    public void setErrorImageResId(int errorImage) {
        mErrorImageId = errorImage;
    }

    public void setBotId(String botId) {
        mBotId = botId;
    }

    /**
     * Loads the image for the view if it isn't already loaded.
     *
     * @param isInLayoutPass True if this was invoked from a layout pass, false otherwise.
     */
    void loadImageIfNecessary(final boolean isInLayoutPass, final boolean isNeedToCheckOldRequest) {
        int width = getWidth();
        int height = getHeight();
        ScaleType scaleType = getScaleType();

        if (getLayoutParams() != null) {
            width = getLayoutParams().width;
            height = getLayoutParams().height;
        }
        // if the URL to be loaded in this view is empty, cancel any old requests and clear the
        // currently loaded image.
        if (mUrl == null || mUrl.length() == 0) {
            if (mImageContainer != null) {
                mImageContainer.cancelRequest();
                mImageContainer = null;
            }
            setDefaultImageOrNull();
            if (mMfMediaStatusView != null && sEnabledMediaDownloadStatus) {
                mMfMediaStatusView.hideView();
            }
            return;
        }

        if (mMfMediaStatusView != null && sEnabledMediaDownloadStatus) {
            mMfMediaStatusView.showDownloadingState();
        }
        // if there was an old request in this view, check if it needs to be canceled.
        if (isNeedToCheckOldRequest) {
            if (mImageContainer != null && mImageContainer.getRequestUrl() != null) {
                if (mImageContainer.getRequestUrl().equals(mUrl)) {
                    // if the request is from the same URL, return.
                    return;
                } else {
                    // if there is a pre-existing request, cancel it if it's fetching a different URL.
                    mImageContainer.cancelRequest();
                    setDefaultImageOrNull();
                }
            }
        }
        // The pre-existing content of this view didn't match the current URL. Load the new image
        // from the network.
        MfImageLoader.ImageContainer newContainer = mImageLoader.get(
                new String[]{mLocalImage, mUrl},
                mBotId,
                new MfImageLoader.ImageListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mErrorImageId != 0) {
                            setImageResource(mErrorImageId);
                        }
                        if (mMfMediaStatusView != null && sEnabledMediaDownloadStatus) {
                            mMfMediaStatusView.showRetryState();
                        }
                    }

                    @Override
                    public void onResponse(final MfImageLoader.ImageContainer response, boolean isImmediate) {
                        // If this was an immediate response that was delivered inside of a layout
                        // pass do not set the image immediately as it will trigger a requestLayout
                        // inside of a layout. Instead, defer setting the image by posting back to
                        // the main thread.
                        if (isImmediate && isInLayoutPass) {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    onResponse(response, false);
                                }
                            });
                            return;
                        }

                        if (mMfMediaStatusView != null && sEnabledMediaDownloadStatus
                                && response.getBitmap() != null) {
                            postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mMfMediaStatusView.hideView();
                                }
                            }, 500);
                        }

                        if (response.getBitmap() != null) {
                            setImageBitmap(response.getBitmap());
                        } else if (mDefaultImageId != 0) {
                            setImageResource(mDefaultImageId);
                        }

                    }
                }, width, height, scaleType);

        // update the ImageContainer to be the new bitmap container.
        mImageContainer = newContainer;
    }

    private void setDefaultImageOrNull() {
        if (mDefaultImageId != 0) {
            setImageResource(mDefaultImageId);
        } else {
            setImageBitmap(null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!sEnabledMediaDownloadStatus) {
            loadImageIfNecessary(false, true);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
//        if (mImageContainer != null) {
//            // If the view was bound to an image request, cancel it and clear
//            // out the image from the view.
//            mImageContainer.cancelRequest();
//            setImageBitmap(null);
//            // also clear out the container so we can reload the image if necessary.
//            mImageContainer = null;
//        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    @Override
    public void onMediaDownloadBtnClick() {
        loadImageIfNecessary(false, false);
    }

    @Override
    public void onMediaCancelDownloadClick() {
        // if there was an old request in this view, check if it needs to be canceled.
        if (mImageContainer != null && mImageContainer.getRequestUrl() != null) {
            // if there is a pre-existing request, cancel it if it's fetching a different URL.
            mImageContainer.cancelRequest();
        }
    }
}
