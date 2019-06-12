package fairy.easy.pageviewlifecycle.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 将页面上放置一层帧布局
 * 对此布局进行点击操作
 *
 * @author 谷闹年
 */
@SuppressLint("ViewConstructor")
public class CoverFrameLayout extends FrameLayout {

    private final BaseLifeCallBacks mLifeCallBacks;

    public CoverFrameLayout(Context context, ViewGroup decorView, BaseLifeCallBacks lifeCallBacks) {
        super(context);
        this.mLifeCallBacks = lifeCallBacks;
        View firstView = decorView.getChildAt(0);
        View focusView = firstView.findFocus();
        decorView.removeViewAt(0);
        addView(firstView);
        ViewGroup parent = (ViewGroup) this.getParent();
        if (parent != null) {
            parent.removeView(this);
        }
        decorView.addView(this, 0);
        if (focusView != null) {
            focusView.requestFocus();
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            mLifeCallBacks.touchAnyView(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }
}
