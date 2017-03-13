package app.udacity.android.cn.popularmovies;

import android.annotation.SuppressLint;
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import org.androidannotations.annotations.EApplication;

/**
 * Created by Chaitanya on 3/11/2017.
 * Application class defined for Popular Movies App.
 * <p>
 * Added code for using LeakCanary to detect memory leaks in the app.
 */

@SuppressLint("Registered")
@EApplication
public class PopularMoviesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}
