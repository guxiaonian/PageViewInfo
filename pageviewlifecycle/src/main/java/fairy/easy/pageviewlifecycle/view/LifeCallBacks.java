package fairy.easy.pageviewlifecycle.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import android.os.Bundle;


import fairy.easy.pageviewlifecycle.BasePageViewListener;

/**
 * 对页面生命周期进行规整
 *
 * @author 谷闹年
 * @date 2019/6/11
 */
public class LifeCallBacks extends BaseLifeCallBacks implements Application.ActivityLifecycleCallbacks {

    public LifeCallBacks(Context context, BasePageViewListener pageViewListener) {
        super(context, pageViewListener);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(final Activity activity) {
        backHandler.post(new Runnable() {
            @Override
            public void run() {
                start(activity);
            }
        });
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (backHandler != null) {
            backHandler.post(new Runnable() {
                @Override
                public void run() {
                    end();
                }
            });
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}


