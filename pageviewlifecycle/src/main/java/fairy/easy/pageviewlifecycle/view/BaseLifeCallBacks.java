package fairy.easy.pageviewlifecycle.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import fairy.easy.pageviewlifecycle.BasePageViewListener;
import fairy.easy.pageviewlifecycle.proxy.OnClickListenerProxy;
import fairy.easy.pageviewlifecycle.proxy.OnItemClickListenerProxy;
import fairy.easy.pageviewlifecycle.proxy.OnItemSelectedListenerProxy;
import fairy.easy.pageviewlifecycle.proxy.OnRecyclerViewItemClickListenerProxy;
import fairy.easy.pageviewlifecycle.type.PageViewType;

/**
 * 实施页面点击控件逻辑
 *
 * @author 谷闹年
 * @date 2019/6/11
 */
class BaseLifeCallBacks {
    private final Context mContext;
    private final BasePageViewListener mPageViewListener;
    private final int i;
    private final List<View> mViews = new ArrayList<>();
    private WeakReference activityRefer = null;

    BaseLifeCallBacks(@PageViewType int i, Context context, BasePageViewListener pageViewListener) {
        this.i = i;
        this.mContext = context;
        this.mPageViewListener = pageViewListener;
    }

    private OnClickListenerProxy onClickListenerProxy;
    private OnItemClickListenerProxy onItemClickListenerProxy;
    private OnItemSelectedListenerProxy onItemSelectedListenerProxy;

    void start(Activity activity) {
        this.activityRefer = new WeakReference<>(activity);
        View decorView = activity.getWindow().getDecorView();
        iteratorView(decorView);
        if (i == PageViewType.frameLayout) {
            startFrameLayout(decorView);
        } else {
            startHook();
        }

    }

    private void startFrameLayout(View decorView) {
        if (decorView instanceof ViewGroup) {
            ViewGroup decorViewGroup = (ViewGroup) decorView;
            View fistView = decorViewGroup.getChildAt(0);
            if (!(fistView instanceof CoverFrameLayout)) {
                createCoverFrameLayout(decorViewGroup);
            }
        }
    }

    private void startHook() {
        for (View v : mViews) {
            hookSingleView(v);
        }
    }

    private void hookSingleView(View view) {
        Class mClassView;
        try {
            mClassView = Class.forName("android.view.View");
            Method method = mClassView.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);
            Object listenerInfoObject = method.invoke(view);
            Class mClassListenerInfo = Class.forName("android.view.View$ListenerInfo");
            Field fieldOnClickListener = mClassListenerInfo.getDeclaredField("mOnClickListener");
            fieldOnClickListener.setAccessible(true);
            View.OnClickListener mOnClickListenerObject = (View.OnClickListener) fieldOnClickListener.get(listenerInfoObject);
            if (!(mOnClickListenerObject instanceof OnClickListenerProxy)) {
                onClickListenerProxy = new OnClickListenerProxy(mOnClickListenerObject, new OnClickListenerProxy.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mPageViewListener != null) {
                            mPageViewListener.onClick(view);
                        }
                    }
                });
                fieldOnClickListener.set(listenerInfoObject, onClickListenerProxy);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void createCoverFrameLayout(ViewGroup decorView) {
        if (activityRefer.get() == null) {
            return;
        }
        new CoverFrameLayout((Activity) activityRefer.get(), decorView, this);
    }


    private void iteratorView(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            if (hookViewGroup(vg)) {
                int childCount = vg.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    iteratorView(vg.getChildAt(i));
                }
            }
        } else {
            mViews.add(view);
        }
    }

    private boolean hookViewGroup(ViewGroup vg) {
        if (vg instanceof AdapterView) {
            hookAdapterView(vg);
            return false;
        } else if (vg instanceof RecyclerView) {
            hookRecyclerView(vg);
            return false;
        }
        return true;
    }

    private OnRecyclerViewItemClickListenerProxy mOnRecyclerViewItemClickListenerProxy;
    private final List<RecyclerView> listRecyclerView = new ArrayList<>();

    /**
     * 针对RecycleView 控件没有方法得到历史绑定事件信息
     * 所以使用list来绑定
     * 根据生命周期来实现绑定与解绑
     *
     * @param vg 外部布局
     */
    private void hookRecyclerView(ViewGroup vg) {
        RecyclerView recyclerView = (RecyclerView) vg;
        listRecyclerView.add(recyclerView);
        if (mOnRecyclerViewItemClickListenerProxy == null) {
            mOnRecyclerViewItemClickListenerProxy = new OnRecyclerViewItemClickListenerProxy(mContext, new OnRecyclerViewItemClickListenerProxy.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView recyclerView, View view, int position) {
                    if (mPageViewListener != null) {
                        mPageViewListener.onItemClick(recyclerView, view, position);
                    }
                }
            });
        }
        recyclerView.addOnItemTouchListener(mOnRecyclerViewItemClickListenerProxy);

    }

    private void hookAdapterView(ViewGroup vg) {
        AdapterView adapterView = (AdapterView) vg;
        AdapterView.OnItemClickListener itemClickListener = adapterView.getOnItemClickListener();
        if (itemClickListener != null && !(itemClickListener instanceof OnItemClickListenerProxy)) {
            onItemClickListenerProxy = new OnItemClickListenerProxy(itemClickListener, new OnItemClickListenerProxy.OnItemClickListener() {
                @Override
                public void doItemListener(AdapterView<?> adapterView, View view, int i, long l) {
                    if (mPageViewListener != null) {
                        mPageViewListener.onItemClick(adapterView, view, i, l);
                    }
                }
            });

            adapterView.setOnItemClickListener(onItemClickListenerProxy);
        }


        AdapterView.OnItemSelectedListener itemSelectedListener = adapterView.getOnItemSelectedListener();
        if (itemSelectedListener != null && !(itemSelectedListener instanceof OnItemSelectedListenerProxy)) {
            onItemSelectedListenerProxy=new OnItemSelectedListenerProxy(itemSelectedListener, new OnItemSelectedListenerProxy.OnItemSelectedListener() {
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
            });

            adapterView.setOnItemSelectedListener(onItemSelectedListenerProxy);
        }

    }

    void end() {
        for (RecyclerView recyclerView : listRecyclerView) {
            if (recyclerView != null && mOnRecyclerViewItemClickListenerProxy != null) {
                recyclerView.removeOnItemTouchListener(mOnRecyclerViewItemClickListenerProxy);
            }
        }
        listRecyclerView.clear();
        mOnRecyclerViewItemClickListenerProxy=null;
        mViews.clear();
        onClickListenerProxy = null;
        onItemClickListenerProxy=null;
        onItemSelectedListenerProxy=null;
    }


    void touchAnyView(final MotionEvent motionEvent) {
        if (activityRefer.get() == null) {
            return;
        }
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


