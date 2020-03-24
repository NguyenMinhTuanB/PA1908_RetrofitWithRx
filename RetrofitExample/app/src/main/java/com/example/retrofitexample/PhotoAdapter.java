package com.example.retrofitexample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private Context mContext;
    private List<Photo> mPhotos;

    public PhotoAdapter(Context context) {
        mContext = context;
    }

    public void setPhotos(List<Photo> photos) {
        mPhotos = photos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mPhotos.get(position));
    }

    @Override
    public int getItemCount() {
        return mPhotos == null ? 0 : mPhotos.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private TextView mTextTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image_avatar);
            mTextTitle = itemView.findViewById(R.id.text_title);
        }

        public void bindData(Photo photo) {
            Log.d("TAG", "bindData: "+photo.getThumbnailUrl());
            Glide.with(mContext).load(photo.getThumbnailUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(mImage);
            mTextTitle.setText(photo.getTitle());
        }
    }
}
