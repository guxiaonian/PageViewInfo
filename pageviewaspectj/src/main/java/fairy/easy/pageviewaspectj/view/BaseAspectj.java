package fairy.easy.pageviewaspectj.view;


import org.aspectj.lang.JoinPoint;

import fairy.easy.pageviewaspectj.BasePageViewListener;
import fairy.easy.pageviewaspectj.PageViewAspectjHelper;
import fairy.easy.pageviewaspectj.utils.ThreadPoolUtils;

/**
 * Aspectj基础类
 *
 * @author 谷闹年
 * @date 2019/6/14
 */
abstract class BaseAspectj {
    void baseClick(final JoinPoint joinPoint) {
        BasePageViewListener mBasePageViewListener = PageViewAspectjHelper.with().getBasePageViewListener();
        if (mBasePageViewListener == null) {
            return;
        }
        ThreadPoolUtils.getInstance().execute(new Runnable() {

            @Override
            public void run() {
                onClick(joinPoint);
                onItemClick(joinPoint);
                onItemSelected(joinPoint);
                onNothingSelected(joinPoint);
            }
        });
    }


    void onClick(JoinPoint joinPoint) {

    }

    void onItemClick(JoinPoint joinPoint) {

    }

    void onItemSelected(JoinPoint joinPoint) {

    }

    void onNothingSelected(JoinPoint joinPoint) {

    }
}
