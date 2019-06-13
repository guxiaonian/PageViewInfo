package fairy.easy.pageviewlifecycle.proxy;

import android.view.View;


/**
 * 点击监听器
 *
 * @author 谷闹年
 * @date 2019/6/12
 */
public class OnClickListenerProxy implements View.OnClickListener {

    private final View.OnClickListener object;
    private final OnClickListener mListener;

    public OnClickListenerProxy(View.OnClickListener object, OnClickListener basePageViewListener) {
        this.object = object;
        this.mListener = basePageViewListener;
    }


    @Override
    public void onClick(View view) {
        if (object != null) {
            object.onClick(view);
        }

        if (mListener != null) {
            mListener.onClick(view);
        }
    }

    public interface OnClickListener {

        /**
         * 自定义点击事件
         *
         * @param view 当前控件
         */
        void onClick(View view);
    }
}
