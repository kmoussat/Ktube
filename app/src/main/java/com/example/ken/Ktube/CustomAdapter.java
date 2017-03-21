package com.example.ken.Ktube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.ken.Ktube.R.id.videoid;

/**
 * Created by Ken on 20/03/2017.
 */

public class CustomAdapter extends BaseAdapter {

    private List<Response.ItemsBean> mVideoitem;
    private Context mContext;
    private LayoutInflater inflater;

public CustomAdapter(Context mContext, List<Response.ItemsBean> mVideoitem)
{
    this.mContext = mContext;
    this.mVideoitem = mVideoitem;
}

    @Override
    public int getCount() {
        return mVideoitem.size();
    }

    @Override
    public Object getItem(int position) {
        return mVideoitem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }




    View.OnClickListener myButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            View parentRow = (View) view.getParent();
            ListView listview = (ListView) parentRow.getParent();
            final int position = listview.getPositionForView(parentRow);

        }
    };

    View.OnClickListener voiceButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (Integer) view.getTag();
        }
    };

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_templates, parent, false);




        Response.ItemsBean item = (Response.ItemsBean) getItem(position);
       ImageView thumbnail = (ImageView) rowView.findViewById(R.id.videoImage);
        TextView title = (TextView) rowView.findViewById(R.id.videotitle);
        TextView description = (TextView) rowView.findViewById(R.id.videodesc);
        TextView channel = (TextView) rowView.findViewById(R.id.videochannel);
        ImageButton button = (ImageButton) rowView.findViewById(R.id.imageButton1);
        TextView id = (TextView) rowView.findViewById(videoid);

        title.setText(item.getSnippet().getTitle());
        id.setText(item.getId().getVideoId());
        description.setText(item.getSnippet().getDescription());
        channel.setText(item.getSnippet().getChannelTitle());
        String imageUrl = item.getSnippet().getThumbnails().getHigh().getUrl();
        Picasso.with(mContext).load(imageUrl).into(thumbnail);

        button.setTag(position);
        button.setOnClickListener(myButtonClickListener);




        return rowView;



    }



}
