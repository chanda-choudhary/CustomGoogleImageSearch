package com.chanda.customgoogleimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chanda.customgoogleimagesearch.R;
import com.chanda.customgoogleimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageResultAdapter extends RecyclerView.Adapter<ImageResultAdapter.MyViewHolder> {

    private List<ImageResult> imageResultList = new ArrayList<>();
    Context context;
    public ImageResultAdapter(Context context,List<ImageResult> imageResultList) {
        this.context=context;
        this.imageResultList = imageResultList;
    }
    public void clear() {
        imageResultList.clear();
    }

    public void addItems(List<ImageResult> newsList) {
        imageResultList.addAll(newsList);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_result, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //holder.ivImage.setImageResource(0);
        holder.tvTitle.setText(Html.fromHtml(imageResultList.get(position).getTitle()));
        Picasso.get().load(imageResultList.get(position).getThumbUrl()).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
            return imageResultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivImage;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            ivImage = view.findViewById(R.id.ivImage);
        }
    }
}