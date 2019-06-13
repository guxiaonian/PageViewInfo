package fairy.easy.pageviewinfo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ToolAdapter extends RecyclerView.Adapter<ToolAdapter.ToolViewHolder> {


    private Context context;
    private List<Student> list;

    @Override
    public ToolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tool, parent, false);
        ToolViewHolder holder = new ToolViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ToolViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            Bundle bundle = (Bundle) payloads.get(0);
            if (bundle.getString("SSSSS") != null) {
                holder.tv1.setText(bundle.getString("SSSSS"));
                holder.tv1.setTextColor(Color.BLUE);
                holder.tv2.setText(bundle.getInt("AAAAA") + "");
                holder.tv2.setTextColor(Color.RED);
            }
        }
    }

    @Override
    public void onBindViewHolder(ToolViewHolder holder, int position) {
        holder.tv1.setText(list.get(position).getName());
        holder.tv2.setText(list.get(position).getId() + "");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public ToolAdapter(Context context, List data) {
        this.list = data;
        this.context = context;
    }

    public void setDatas(List mDatas) {
        this.list = new ArrayList<>(mDatas);
    }

    List<Student> getData() {
        return list;
    }

    public class ToolViewHolder extends RecyclerView.ViewHolder {

        private TextView tv1,tv2;


        public ToolViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tvtool_tv);
            tv2 = itemView.findViewById(R.id.tvtv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClickClickListener(getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnItemClickClickListener(int postion);
    }

    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
