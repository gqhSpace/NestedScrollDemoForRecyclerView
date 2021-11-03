package ljc.example.com.nestedscrollview.test;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ljc.example.com.nestedscrollview.R;
import ljc.example.com.nestedscrollview.sceneone.adapter.ChildRecyclerViewAdapter;
import ljc.example.com.nestedscrollview.scenetwo.adapter.SecondOuterRecyclerViewAdapter;
import ljc.example.com.nestedscrollview.scenetwo.view.SecondOuterRecyclerView;
import ljc.example.com.nestedscrollview.scenetwo.view.SecondParentView;

public class TestActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NestedScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        recyclerView = findViewById(R.id.recyclerView);
        scrollView = findViewById(R.id.scrollView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        final ChildRecyclerViewAdapter adapter = new ChildRecyclerViewAdapter(this,10,"child");
        recyclerView.setAdapter(adapter);
//        findViewById(R.id.toast).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(TestActivity.this,"ceshi toast",Toast.LENGTH_LONG).show();
//            }
//        });
    }

}
