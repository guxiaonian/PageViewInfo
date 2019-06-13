package fairy.easy.pageviewlifecycle.type;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 标识当前的类型
 * 0表示添加布局的形式
 * 1表示反射获取的形式
 *
 * @author 谷闹年
 * @date 2019/6/13
 */
@IntDef
@Retention(RetentionPolicy.SOURCE)
public @interface PageViewType {

    int frameLayout = 0;

    int hookView = 1;
}
