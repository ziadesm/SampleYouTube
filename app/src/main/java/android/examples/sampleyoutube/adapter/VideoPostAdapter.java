package android.examples.sampleyoutube.adapter;

import android.content.Context;
import android.examples.sampleyoutube.R;
import android.examples.sampleyoutube.interfaces.OnItemClickListener;
import android.examples.sampleyoutube.model.YouTubeDataModel;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoPostAdapter extends RecyclerView.Adapter<VideoPostAdapter.YouTubePostHolder> {
    private ArrayList<YouTubeDataModel> dataSet;
    private Context mContext;
    private OnItemClickListener listener;

    public VideoPostAdapter(Context mContext, ArrayList<YouTubeDataModel> dataSet, OnItemClickListener listener) {
        this.dataSet = dataSet;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public YouTubePostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_post_layout, parent, false);
        YouTubePostHolder postHolder = new YouTubePostHolder(v);
        return postHolder;
    }

    @Override
    public void onBindViewHolder(YouTubePostHolder holder, int position) {
        YouTubeDataModel model = dataSet.get(position);
        Picasso.with(mContext).load(model.getThumbanail()).into(holder.mImageView);
        holder.bind(model, listener);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class YouTubePostHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mDes;
        ImageView mImageView;
        public YouTubePostHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textViewTitle);
            mDes = itemView.findViewById(R.id.textViewDes);
            mImageView = itemView.findViewById(R.id.imageThumb);
        }
        public void bind(final YouTubeDataModel model, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(model);
                }
            });
            mTitle.setText(model.getTitle());
            mDes.setText(model.getDescription());
        }
    }
}
