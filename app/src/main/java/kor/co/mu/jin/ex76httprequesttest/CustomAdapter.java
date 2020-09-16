package kor.co.mu.jin.ex76httprequesttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<BoardItem> items;

    public CustomAdapter(Context context, ArrayList<BoardItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.boardlist_item, parent, false);
        VH holder = new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH)holder;

        BoardItem item = items.get(position);
        vh.tvno.setText(item.no);
        vh.tvname.setText(item.name);
        vh.tvmessage.setText(item.message);
        vh.tvdate.setText(item.date);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tvno, tvname, tvmessage, tvdate;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvno = itemView.findViewById(R.id.tv_no);
            tvname = itemView.findViewById(R.id.tv_name);
            tvmessage = itemView.findViewById(R.id.tv_message);
            tvdate = itemView.findViewById(R.id.tv_date);
        }
    }
}
