package fairy.easy.pageviewlifecycle;

import android.view.View;
import android.widget.AdapterView;

/**
 * @author 谷闹年
 * @date 2019/6/12
 */
public abstract class BasePageViewListener implements PageViewHelper.PageViewInterface {
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
