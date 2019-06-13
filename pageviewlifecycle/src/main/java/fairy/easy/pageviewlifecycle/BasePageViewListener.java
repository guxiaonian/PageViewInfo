package fairy.easy.pageviewlifecycle;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

/**
 * @author 谷闹年
 * @date 2019/6/12
 */
public abstract class BasePageViewListener implements PageViewLifecycleHelper.PageViewInterface {
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {

    }
}
