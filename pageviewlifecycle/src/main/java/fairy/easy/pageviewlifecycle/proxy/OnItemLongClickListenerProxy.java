package fairy.easy.pageviewlifecycle.proxy;

import android.view.View;
import android.widget.AdapterView;

/**
 * 长按监听器
 * 已经取消
 *
 * @author 谷闹年
 * @date 2019/6/12
 */
@Deprecated
public class OnItemLongClickListenerProxy implements AdapterView.OnItemLongClickListener {

    private final AdapterView.OnItemLongClickListener object;
    private final OnItemLongClickListener mOnItemLongClickListener;

    public OnItemLongClickListenerProxy(AdapterView.OnItemLongClickListener object, OnItemLongClickListener mOnItemLongClickListener) {
        this.object = object;
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (object != null) {
            object.onItemLongClick(adapterView, view, i, l);
        }
        if (mOnItemLongClickListener != null) {
            mOnItemLongClickListener.doItemLongClickListener(adapterView, view, i, l);
        }
        return false;
    }

    public interface OnItemLongClickListener {

        /**
         * 自定义长按事件 防止事件被二次使用
         *
         * @param adapterView 父控件
         * @param view        当前控件
         * @param i           点击的位置
         * @param l           id
         */
        void doItemLongClickListener(AdapterView<?> adapterView, View view, int i, long l);
    }
}
