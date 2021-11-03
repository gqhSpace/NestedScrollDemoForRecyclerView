package ljc.example.com.nestedscrollview.scenetwo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ljc.example.com.nestedscrollview.R;
import ljc.example.com.nestedscrollview.scenetwo.item.SecondLastRecyclerViewItem;
import ljc.example.com.nestedscrollview.scenetwo.view.SecondInnerRecyclerView;

public class SecondOuterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private int mSize;
    private final int COMMON_TYPE = 1;
    private final int VIEWPAGER_TYPE = 2;
    SecondLastRecyclerViewItem lastRecyclerViewItem;

    public SecondOuterRecyclerViewAdapter(Context context, int size) {
        mContext = context;
        mSize = size;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType) {
            case COMMON_TYPE:
                view = LayoutInflater.from(mContext).inflate(R.layout.item, viewGroup, false);
                break;
            case VIEWPAGER_TYPE:
                view = new SecondLastRecyclerViewItem(mContext);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == COMMON_TYPE) {
            View root = viewHolder.itemView;
            ((TextView) root.findViewById(R.id.text)).setText("第" + position + "个view");
        } else {
            lastRecyclerViewItem = (SecondLastRecyclerViewItem) viewHolder.itemView;
            lastRecyclerViewItem.setData(4);
        }


    }

    @Override
    public int getItemViewType(int position) {
        return position == (mSize - 1) ? VIEWPAGER_TYPE : COMMON_TYPE;
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
    }

    public int getSize() {
        return mSize;
    }

    public SecondInnerRecyclerView getChildRecyclerView() {
        return lastRecyclerViewItem != null ? lastRecyclerViewItem.getChildRecyclerView() : null;
    }
}
