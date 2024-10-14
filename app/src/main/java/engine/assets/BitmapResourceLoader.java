package engine.assets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.DrawableRes;

public class BitmapResourceLoader implements ResourceLoader<Bitmap> {
    @DrawableRes int resid;
    ResourceLoader.Callback callback;

    public BitmapResourceLoader(@DrawableRes int resid) {
        this.resid = resid;
    }

    @Override
    public void loadResource(Context context) {
        callback.onLoad(BitmapFactory.decodeResource(context.getResources(), resid));
    }

    @Override
    public void registerCallback(Callback<Bitmap> callback) {
        this.callback = callback;
    }
}
