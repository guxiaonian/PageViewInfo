package fairy.easy.pageviewinfo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private RecyclerView recyclerView,recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.rv);
        recyclerView2 = findViewById(R.id.rv2);
        init();
    }

    public void init() {
        List<Student> list = new ArrayList<>();
        list.add(new Student("aa", 1));
        list.add(new Student("aa", 2));
        list.add(new Student("aa", 3));
        list.add(new Student("aa", 4));
        ToolAdapter toolAdapter = new ToolAdapter(getApplicationContext(), list);
        toolAdapter.setDatas(list);
        recyclerView.setAdapter(toolAdapter);
        ToolAdapter toolAdapter2= new ToolAdapter(getApplicationContext(), list);
        toolAdapter2.setDatas(list);
        recyclerView2.setAdapter(toolAdapter2);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        LinearLayoutManager llm2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(llm2);
//        toolAdapter.setOnItemClickListener(new ToolAdapter.OnItemClickListener() {
//            @Override
//            public void OnItemClickClickListener(int position) {
//                Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
//            }
//        });
        toolAdapter2.setOnItemClickListener(new ToolAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickClickListener(int position) {

            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 3) {
                    startActivity(new Intent(getApplicationContext(), Main3Activity.class));
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }
}
