package fairy.easy.pageviewlifecycle;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import fairy.easy.pageviewlifecycle.view.LifeCallBacks;

/**
 * 通过对页面的生命周期进行注册从而获取到页面所有控件
 * 管理类
 *
 * @author 谷闹年
 * @date 2019/6/11
 */
public class PageViewHelper {
    /**
     * 当前对象
     */
    private volatile static PageViewHelper mInstance;

    private Context mContext;

    private LifeCallBacks mLifeCallBacks;

    /**
     * 初始化
     *
     * @param context 上下文 用于将注册整个页面的生命周期
     * @return 当前的对象
     */
    public static PageViewHelper with(Context context) {
        if (mInstance == null) {
            synchronized (PageViewHelper.class) {
                if (mInstance == null) {
                    mInstance = new PageViewHelper(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 空构造方法
     *
     * @param context 上下文 用于将注册整个页面的生命周期
     */
    private PageViewHelper(Context context) {
        this.mContext = context.getApplicationContext();
    }


    /**
     * 添加全局监听器
     *
     * @param pageViewListener 监听器
     */
    public void addPageViewListener(BasePageViewListener pageViewListener) {
        Application app = (Application) mContext;
        mLifeCallBacks = new LifeCallBacks(mContext, pageViewListener);
        app.registerActivityLifecycleCallbacks(mLifeCallBacks);
    }

    /**
     * 去除全局监听器
     */
    public void removePageViewListener() {
        if (mLifeCallBacks != null) {
            Application app = (Application) mContext;
            app.unregisterActivityLifecycleCallbacks(mLifeCallBacks);
        }
    }


    /**
     * 去掉长按的监听策略
     * 因为没办法与用户的实际使用情况来判断
     * 返回值会干预业务事件
     */
    public interface PageViewInterface {

        /**
         * 获取view的点击事件
         *
         * @param view 控件
         */
        void onClick(View view);

        /**
         * 自定义点击事件 防止事件被二次使用
         *
         * @param parent   父控件
         * @param view     当前控件
         * @param position 点击的位置
         * @param id       id
         */
        void onItemClick(AdapterView<?> parent, View view, int position, long id);

        /**
         * 自定义选择事件 防止事件被二次使用
         *
         * @param adapterView 父控件
         * @param view        当前控件
         * @param i           点击的位置
         * @param l           id
         */
        void onItemSelected(AdapterView<?> adapterView, View view, int i, long l);

        /**
         * 自定义未选择事件
         *
         * @param adapterView 父控件
         */
        void onNothingSelected(AdapterView<?> adapterView);

    }
}
