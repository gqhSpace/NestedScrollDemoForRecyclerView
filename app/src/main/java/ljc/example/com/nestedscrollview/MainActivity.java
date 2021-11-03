package ljc.example.com.nestedscrollview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import ljc.example.com.nestedscrollview.nestedheader.HeaderActivity;
import ljc.example.com.nestedscrollview.sceneone.FirstActivity;
import ljc.example.com.nestedscrollview.scenetwo.SecondActivity;
import ljc.example.com.nestedscrollview.test.TestActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_coordinate);
        setContentView(R.layout.activity_main_nested);
        findViewById(R.id.one).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(MainActivity.this, FirstActivity.class));
                    }
                }
        );
        findViewById(R.id.two).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, SecondActivity.class));
                    }
                }
        );
        findViewById(R.id.test).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, TestActivity.class));
                    }
                }
        );

        findViewById(R.id.headerView).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, HeaderActivity.class));
                    }
                }
        );


//        NestedStickerHeaderView rootView = findViewById(R.id.root);
//        rootView.setMaxScrollTop(dp2px(this, 100));

//        RecyclerView recyclerView = findViewById(R.id.recycle);
//        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        //添加Android自带的分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        final ParentRecyclerViewAdapter adapter = new ParentRecyclerViewAdapter(this, 20);
//        recyclerView.setAdapter(adapter);
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
