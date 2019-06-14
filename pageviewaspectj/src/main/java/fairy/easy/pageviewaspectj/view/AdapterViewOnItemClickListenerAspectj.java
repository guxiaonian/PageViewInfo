package fairy.easy.pageviewaspectj.view;

import android.view.View;
import android.widget.AdapterView;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

import fairy.easy.pageviewaspectj.PageViewAspectjHelper;


/**
 * AdapterView的控件选择事件
 *
 * @author 谷闹年
 * @date 2019/6/14
 */
@Aspect
public class AdapterViewOnItemClickListenerAspectj extends BaseAspectj {

    /**
     * android.widget.AdapterView.OnItemClickListener.onItemClick(android.widget.AdapterView,android.view.View,int,long)
     *
     * @param joinPoint 切面
     */
    @After("execution(* android.widget.AdapterView.OnItemClickListener.onItemClick(android.widget.AdapterView,android.view.View,int,long))")
    public void onItemClickAOP(JoinPoint joinPoint) {
        baseClick(joinPoint);
    }

    @Override
    void onItemClick(JoinPoint joinPoint) {
        try {
            if (joinPoint == null || joinPoint.getArgs() == null || joinPoint.getArgs().length != 4) {
                return;
            }
            Object object = joinPoint.getArgs()[0];
            if (object == null) {
                return;
            }
            View view = (View) joinPoint.getArgs()[1];
            if (view == null) {
                return;
            }
            PageViewAspectjHelper.with().getBasePageViewListener().onItemClick((AdapterView) object, view, (int) joinPoint.getArgs()[2], (long) joinPoint.getArgs()[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
