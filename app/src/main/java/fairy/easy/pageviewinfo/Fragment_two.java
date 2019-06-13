package fairy.easy.pageviewinfo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 谷闹年 on 2019/6/12.
 */
public class Fragment_two extends Fragment {

    public static Fragment_two newInstance() {

        Bundle args = new Bundle();

        Fragment_two fragment = new Fragment_two();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_two,container,false);
        return view;
    }
}
