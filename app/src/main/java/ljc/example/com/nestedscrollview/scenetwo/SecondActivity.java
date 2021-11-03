package ljc.example.com.nestedscrollview.scenetwo;

import android.content.Context;
import android.os.Bundle;

import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ljc.example.com.nestedscrollview.R;
import ljc.example.com.nestedscrollview.scenetwo.adapter.SecondOuterRecyclerViewAdapter;
import ljc.example.com.nestedscrollview.scenetwo.view.SecondOuterRecyclerView;
import ljc.example.com.nestedscrollview.scenetwo.view.SecondParentView;

public class SecondActivity extends AppCompatActivity {

    private SecondParentView mSecondParentView;
    private SmartRefreshLayout mRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        SecondOuterRecyclerView recyclerView = findViewById(R.id.recycle);

        mRefreshLayout = findViewById(R.id.mRefreshLayout);
        mSecondParentView = findViewById(R.id.mSecondParentView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        final SecondOuterRecyclerViewAdapter adapter = new SecondOuterRecyclerViewAdapter(this, 20);
        recyclerView.setAdapter(adapter);
        mSecondParentView.setOuterRecyclerView(recyclerView);

        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setDragRate(1.0f);
        mRefreshLayout.setEnableOverScrollBounce(false);
        mRefreshLayout.setEnableOverScrollDrag(false);
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
