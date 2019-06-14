package fairy.easy.pageviewaspectj;

import android.view.View;
import android.widget.AdapterView;


/**
 * AOP切面
 * 管理类
 *
 * @author 谷闹年
 * @date 2019/6/11
 */
public class PageViewAspectjHelper {
    /**
     * 当前对象
     */
    private volatile static PageViewAspectjHelper mInstance;

    private BasePageViewListener basePageViewListener;


    /**
     * 初始化
     *
     * @return 当前的对象
     */
    public static PageViewAspectjHelper with() {
        if (mInstance == null) {
            synchronized (PageViewAspectjHelper.class) {
                if (mInstance == null) {
                    mInstance = new PageViewAspectjHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 空构造方法
     */
    private PageViewAspectjHelper() {

    }


    /**
     * 添加全局监听器
     *
     * @param pageViewListener 监听器
     */
    public void setBasePageViewListener(BasePageViewListener pageViewListener) {
        this.basePageViewListener = pageViewListener;
    }

    public BasePageViewListener getBasePageViewListener() {
        return basePageViewListener;
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
         * @param parent   父控件
         * @param view     当前控件
         * @param position 点击的位置
         * @param id       id
         */
        void onItemSelected(AdapterView<?> parent, View view, int position, long id);

        /**
         * 自定义未选择事件
         *
         * @param parent 父控件
         */
        void onNothingSelected(AdapterView<?> parent);

    }
}
