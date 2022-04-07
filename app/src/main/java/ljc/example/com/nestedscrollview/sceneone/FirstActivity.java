package ljc.example.com.nestedscrollview.sceneone;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ljc.example.com.nestedscrollview.R;
import ljc.example.com.nestedscrollview.sceneone.adapter.ParentRecyclerViewAdapter;
import ljc.example.com.nestedscrollview.sceneone.view.ParentRecyclerView;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_coordinate);
        setContentView(R.layout.activity_first);



//        NestedStickerHeaderView rootView = findViewById(R.id.root);
//        rootView.setMaxScrollTop(dp2px(this, 100));

        ParentRecyclerView recyclerView = findViewById(R.id.recycle);
        recyclerView.initManager(this);
//        final LinearLayoutManager √ = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        final ParentRecyclerViewAdapter adapter = new ParentRecyclerViewAdapter(this, 50);
        recyclerView.setAdapter(adapter);
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
