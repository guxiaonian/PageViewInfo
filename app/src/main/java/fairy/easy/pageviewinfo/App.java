package fairy.easy.pageviewinfo;

import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.squareup.leakcanary.LeakCanary;

import fairy.easy.pageviewaspectj.PageViewAspectjHelper;
import fairy.easy.pageviewlifecycle.BasePageViewListener;
import fairy.easy.pageviewlifecycle.PageViewLifecycleHelper;
import fairy.easy.pageviewlifecycle.type.PageViewType;

/**
 * @author 谷闹年
 * @date 2019/6/11
 */
public class App extends Application {
    public static final String TAG = PageViewLifecycleHelper.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        initLifecycle();
//        initAspectj();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private void initAspectj() {
        PageViewAspectjHelper.with().setBasePageViewListener(new fairy.easy.pageviewaspectj.BasePageViewListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "点击了:" + view.getClass().getSimpleName());
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "Adapter点击了:" + parent.getClass().getSimpleName() + "内的" + view.getClass().getSimpleName() + "位置为:" + position + "id为:" + id);
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "Adapter选择了:" + parent.getClass().getSimpleName() + "内的" + view.getClass().getSimpleName() + "位置为:" + position + "id为:" + id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(TAG, "Adapter没有选择:" + parent.getClass().getSimpleName());
            }
        });
    }

    private void initLifecycle() {
        PageViewLifecycleHelper.with(this).addPageViewListener(PageViewType.hookView, new BasePageViewListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "点击了:" + view.getClass().getSimpleName());
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "Adapter点击了:" + parent.getClass().getSimpleName() + "内的" + view.getClass().getSimpleName() + "位置为:" + position + "id为:" + id);
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "Adapter选择了:" + parent.getClass().getSimpleName() + "内的" + view.getClass().getSimpleName() + "位置为:" + position + "id为:" + id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(TAG, "Adapter没有选择:" + parent.getClass().getSimpleName());
            }

            @Override
            public void onItemClick(RecyclerView recyclerView, View view, int position) {
                Log.i(TAG, "RecyclerView点击了:" + recyclerView.getClass().getSimpleName() + "内的" + view.getClass().getSimpleName() + "位置为:" + position);
            }
        });
    }


}
