package engine.assets;

import android.content.Context;

public interface ResourceLoader<T> {
    interface Callback<T> {
        void onLoad(T resource);
    }
    void loadResource(Context context);
    void registerCallback(Callback<T> callback);
}
