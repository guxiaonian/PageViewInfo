package fairy.easy.pageviewaspectj.view;

import android.view.View;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

import fairy.easy.pageviewaspectj.PageViewAspectjHelper;


/**
 * 控件点击
 *
 * @author 谷闹年
 * @date 2019/6/14
 */
@Aspect
public class ViewOnClickListenerAspectj extends BaseAspectj {

    /**
     * android.view.View.OnClickListener.onClick(android.view.View)
     *
     * @param joinPoint 切面
     */
    @After("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
    public void onClickAOP(JoinPoint joinPoint) {
        baseClick(joinPoint);
    }

    @Override
    void onClick(JoinPoint joinPoint) {
        try {
            if (joinPoint == null || joinPoint.getArgs() == null || joinPoint.getArgs().length != 1) {
                return;
            }
            View view = (View) joinPoint.getArgs()[0];
            if (view == null) {
                return;
            }
            //这里是所有的View都会返回 可以筛选个别ViewGroup进行单独处理
            PageViewAspectjHelper.with().getBasePageViewListener().onClick(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
