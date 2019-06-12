package fairy.easy.pageviewlifecycle.proxy;

import android.view.View;
import android.widget.AdapterView;

/**
 * 点击监听器
 *
 * @author 谷闹年
 * @date 2019/6/12
 */
public class OnItemClickListenerProxy implements AdapterView.OnItemClickListener {

    private final AdapterView.OnItemClickListener object;
    private final OnItemClickListener mOnItemClickListener;

    public OnItemClickListenerProxy(AdapterView.OnItemClickListener object, OnItemClickListener mOnItemClickListener) {
        this.object = object;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (object != null) {
            object.onItemClick(adapterView, view, i, l);
        }

        if (mOnItemClickListener != null) {
            mOnItemClickListener.doItemListener(adapterView, view, i, l);
        }
    }

    public interface OnItemClickListener {

        /**
         * 自定义点击事件 防止事件被二次使用
         *
         * @param adapterView 父控件
         * @param view        当前控件
         * @param i           点击的位置
         * @param l           id
         */
        void doItemListener(AdapterView<?> adapterView, View view, int i, long l);
    }
}
