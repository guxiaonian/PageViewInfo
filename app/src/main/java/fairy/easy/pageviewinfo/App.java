package fairy.easy.pageviewinfo;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import fairy.easy.pageviewlifecycle.BasePageViewListener;
import fairy.easy.pageviewlifecycle.PageViewHelper;

/**
 * @author 谷闹年
 * @date 2019/6/11
 */
public class App extends Application {
    public static final String TAG = PageViewHelper.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        PageViewHelper.with(this).addPageViewListener(new BasePageViewListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, view.getClass().getSimpleName());
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, view.getClass().getSimpleName() + "------------" + position);
            }


        });
    }
}
