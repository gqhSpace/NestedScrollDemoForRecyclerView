package ljc.example.com.nestedscrollview.sceneone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ljc.example.com.nestedscrollview.R;

public class ChildRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private int mSize;
    private String itemType;


    public ChildRecyclerViewAdapter(Context context, int size, String itemType) {
        mContext = context;
        mSize = size;
        this.itemType = itemType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, viewGroup, false);

        return new RecyclerView.ViewHolder(view) {};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        View root = viewHolder.itemView;
        ((TextView) root.findViewById(R.id.text)).setText(itemType+",第" + position + "个view");
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
}
