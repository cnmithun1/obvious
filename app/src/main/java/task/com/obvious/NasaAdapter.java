package task.com.obvious;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class NasaAdapter extends RecyclerView.Adapter<NasaAdapter.MyViewHolder> {

    private List<NasaModel> dataSet;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;


        public MyViewHolder(View view){
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }

    public NasaAdapter(Context context, List<NasaModel> data){
        this.dataSet = data;
        this.mContext = context;
    }


    @Override
    public NasaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_image_list,viewGroup, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull NasaAdapter.MyViewHolder holder, final int position) {

        final NasaModel nm = dataSet.get(position);

        if(nm.getUrl()!=null){
            Glide.with(this.mContext).load(nm.getUrl()).apply(new RequestOptions().placeholder(R.drawable.image)).into(holder.image);
        }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, SwipeActivity.class);
                i.putExtra("position", position);
                mContext.startActivity(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
