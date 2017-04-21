package com.lethalskillzz.imgurllery.imgurllery.presentation.gallery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lethalskillzz.imgurllery.R;
import com.lethalskillzz.imgurllery.imgurllery.data.model.Image;
import com.lethalskillzz.imgurllery.imgurllery.presentation.detail.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.BASE_IMG_URL;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.CLICK_IMAGE;
import static com.lethalskillzz.imgurllery.imgurllery.manager.AppConfig.MEDIUM_IMG_SUFFIX;

/**
 * Created by ibrahimabdulkadir on 18/04/2017.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>  {

    private static final String TAG = "GalleryAdapter";
    private Context mContext;
    private List<Image> images;


    public GalleryAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Image image = images.get(position);
        holder.title.setText(image.getTitle());
        if(!image.getIsAlbum())
            Picasso.with(mContext).load(BASE_IMG_URL+image.getId()+MEDIUM_IMG_SUFFIX)
                    .placeholder(R.drawable.large_placeholder).into(holder.image);
            //Glide.with(mContext).load(BASE_IMG_URL+image.getId()+MEDIUM_IMG_SUFFIX).into(holder.image);
        else
            Picasso.with(mContext).load(BASE_IMG_URL+image.getCover()+MEDIUM_IMG_SUFFIX)
                    .placeholder(R.drawable.large_placeholder).into(holder.image);
            //Glide.with(mContext).load(BASE_IMG_URL+image.getCover()+MEDIUM_IMG_SUFFIX).into(holder.image);

    }

    @Override
    public int getItemCount() {
        if (images == null) {
            return 0;
        }
        return images.size();
    }

    public void setResults(List<Image> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView image;
        private final TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.item_image_image);
            title = (TextView) itemView.findViewById(R.id.item_image_title);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailsActivity.class);
            intent.putExtra(CLICK_IMAGE, images.get(getAdapterPosition()));
            view.getContext().startActivity(intent);
        }
    }
}
