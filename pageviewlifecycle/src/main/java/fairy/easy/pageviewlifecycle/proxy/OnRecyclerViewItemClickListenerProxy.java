package fairy.easy.pageviewlifecycle.proxy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * RecyclerView点击监听器
 *
 * @author 谷闹年
 * @date 2019/6/12
 */
public class OnRecyclerViewItemClickListenerProxy implements RecyclerView.OnItemTouchListener {
    private View childView;
    private RecyclerView touchView;

    public OnRecyclerViewItemClickListenerProxy(Context context, final OnItemClickListener mListener) {
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent ev) {
                if (childView != null && mListener != null) {
                    mListener.onItemClick(touchView, childView, touchView.getChildAdapterPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent ev) {
//                if (childView != null && mListener != null) {
//                    mListener.onLongClick(touchView, childView, touchView.getChildAdapterPosition(childView));
//                }
            }
        });
    }

    private GestureDetector mGestureDetector;

    public interface OnItemClickListener {

        /**
         * 点击
         *
         * @param recyclerView 父组件
         * @param view         当前控件
         * @param position     当前位置
         */
        void onItemClick(RecyclerView recyclerView, View view, int position);

//        /**
//         * 长按
//         * 取消
//         * @param recyclerView
//         * @param view
//         * @param position
//         */
//        void onLongClick(RecyclerView recyclerView, View view, int position);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        childView = rv.findChildViewUnder(e.getX(), e.getY());
        touchView = rv;
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
