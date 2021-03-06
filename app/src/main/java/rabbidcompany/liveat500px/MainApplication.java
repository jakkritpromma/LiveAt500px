package rabbidcompany.liveat500px;

import android.app.Application;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by noneemotion on 11/7/2559.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        //Initialize things here.
        Contextor.getInstance().init(getApplicationContext());

        //You can load the dao in the persistent storage here.
        //Code here...
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        //You can save the dao in the persistent storage here.
        //Code here...
    }
}
