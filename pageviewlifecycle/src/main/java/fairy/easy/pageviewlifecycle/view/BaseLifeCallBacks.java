package fairy.easy.pageviewlifecycle.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import fairy.easy.pageviewlifecycle.BasePageViewListener;
import fairy.easy.pageviewlifecycle.PageViewHelper;
import fairy.easy.pageviewlifecycle.proxy.OnItemClickListenerProxy;
import fairy.easy.pageviewlifecycle.proxy.OnItemSelectedListenerProxy;

/**
 * 实施页面点击控件逻辑
 *
 * @author 谷闹年
 * @date 2019/6/11
 */
class BaseLifeCallBacks {
    private static final String TAG = PageViewHelper.class.getSimpleName();
    private final Context mContext;
    private final BasePageViewListener mPageViewListener;
    private final List<View> mViews = new ArrayList<>();
    Handler backHandler;

    BaseLifeCallBacks(Context context, BasePageViewListener pageViewListener) {
        this.mContext = context;
        this.mPageViewListener = pageViewListener;
        HandlerThread handlerThread = new HandlerThread("PageViewHelper");
        handlerThread.start();
        backHandler = new Handler(handlerThread.getLooper());
    }


    void start(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        iteratorView(decorView);
        if (decorView instanceof ViewGroup) {
            ViewGroup decorViewGroup = (ViewGroup) decorView;
            View fistView = decorViewGroup.getChildAt(0);
            if (!(fistView instanceof CoverFrameLayout)) {
                createCoverFrameLayout(decorViewGroup);
            }
        }
    }


    private void createCoverFrameLayout(ViewGroup decorView) {
        new CoverFrameLayout(mContext, decorView, this);
    }


    private void iteratorView(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int childCount = vg.getChildCount();
            if (childCount == 0) {
                hookViewGroup(vg);
            }
            for (int i = 0; i < childCount; i++) {
                iteratorView(vg.getChildAt(i));
            }
        } else {
            mViews.add(view);
        }
    }

    private void hookViewGroup(ViewGroup vg) {
        if (vg instanceof AdapterView) {
            hookAdapterView(vg);
        }
    }

    private void hookAdapterView(ViewGroup vg) {
        final AdapterView adapterView = (AdapterView) vg;
        AdapterView.OnItemClickListener itemClickListener = adapterView.getOnItemClickListener();
        if (itemClickListener != null && !(itemClickListener instanceof OnItemClickListenerProxy.OnItemClickListener)) {
            adapterView.setOnItemClickListener(new OnItemClickListenerProxy(itemClickListener, new OnItemClickListenerProxy.OnItemClickListener() {
                @Override
                public void doItemListener(AdapterView<?> adapterView, View view, int i, long l) {
                    if (mPageViewListener != null) {
                        mPageViewListener.onItemClick(adapterView, view, i, l);
                    }
                }
            }));
        }

        AdapterView.OnItemSelectedListener itemSelectedListener = adapterView.getOnItemSelectedListener();
        if (itemSelectedListener != null && !(itemSelectedListener instanceof OnItemSelectedListenerProxy.OnItemSelectedListener)) {
            adapterView.setOnItemSelectedListener(new OnItemSelectedListenerProxy(itemSelectedListener, new OnItemSelectedListenerProxy.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (mPageViewListener != null) {
                        mPageViewListener.onItemSelected(adapterView, view, i, l);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    if (mPageViewListener != null) {
                        mPageViewListener.onNothingSelected(adapterView);
                    }
                }
            }));
        }

    }

    void end() {
        mViews.clear();
    }


    void touchAnyView(final MotionEvent motionEvent) {
        final View touchView = findTouchView(motionEvent);
        if (touchView != null && mPageViewListener != null) {
            mPageViewListener.onClick(touchView);
        }
    }

    private View findTouchView(MotionEvent motionEvent) {
        int[] location = new int[2];
        for (View v : mViews) {
            if (v.isShown()) {
                v.getLocationOnScreen(location);
                Rect r = new Rect();
                v.getGlobalVisibleRect(r);
                boolean contains = r.contains((int) motionEvent.getX(), (int) motionEvent.getY());
                if (contains) {
                    return v;
                }
            }
        }
        return null;
    }

}


