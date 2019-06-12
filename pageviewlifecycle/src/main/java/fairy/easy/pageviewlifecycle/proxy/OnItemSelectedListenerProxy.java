package fairy.easy.pageviewlifecycle.proxy;

import android.view.View;
import android.widget.AdapterView;

/**
 * 选择监听器
 *
 * @author 谷闹年
 * @date 2019/6/12
 */
public class OnItemSelectedListenerProxy implements AdapterView.OnItemSelectedListener {

    private final AdapterView.OnItemSelectedListener object;
    private final OnItemSelectedListener mOnItemSelectedListener;

    public OnItemSelectedListenerProxy(AdapterView.OnItemSelectedListener object, OnItemSelectedListener mOnItemSelectedListener) {
        this.object = object;
        this.mOnItemSelectedListener = mOnItemSelectedListener;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (object != null) {
            object.onItemSelected(adapterView, view, i, l);
        }
        if (mOnItemSelectedListener != null) {
            mOnItemSelectedListener.onItemSelected(adapterView, view, i, l);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        if (object != null) {
            object.onNothingSelected(adapterView);
        }
        if (mOnItemSelectedListener != null) {
            mOnItemSelectedListener.onNothingSelected(adapterView);
        }
    }

    public interface OnItemSelectedListener {

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
