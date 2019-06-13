package fairy.easy.pageviewlifecycle.view;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import android.os.Bundle;


import fairy.easy.pageviewlifecycle.BasePageViewListener;
import fairy.easy.pageviewlifecycle.type.PageViewType;

/**
 * 对页面生命周期进行规整
 *
 * @author 谷闹年
 * @date 2019/6/11
 */
public class LifeCallBacks extends BaseLifeCallBacks implements Application.ActivityLifecycleCallbacks {

    public LifeCallBacks(@PageViewType int i, Context context, BasePageViewListener pageViewListener) {
        super(i,context, pageViewListener);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(final Activity activity) {
        start(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
        end();
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


