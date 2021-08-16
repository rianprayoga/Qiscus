package co.rprayoga.samplechat;

import android.app.Application;
import android.util.Log;

import com.qiscus.sdk.chat.core.QiscusCore;

public class App extends Application {
    private static final String TAG = "App";
    private final String APPID = "sdksample";
    private static App app;

    public static App getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        app = this;
        QiscusCore.setup(this, APPID);

    }
}
