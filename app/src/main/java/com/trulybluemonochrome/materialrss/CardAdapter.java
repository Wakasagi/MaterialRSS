package com.trulybluemonochrome.materialrss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import java.util.ArrayList;

public class CardAdapter extends ArrayAdapter<RssItem> {

    static class ViewHolder {
        ImageView imageView;
        TextView txtLineOne;
        Button btnGo;
    }

    private final LayoutInflater mLayoutInflater;
    //private final Random mRandom;
    private final ArrayList<Integer> mBackgroundColors;
    //private final RssItem mRssItem;
    //private final ImageLoader mImageLoader;

    //private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();


    public CardAdapter(final Context context, final int textViewResourceId) {
        super(context, textViewResourceId);
        mLayoutInflater = LayoutInflater.from(context);


        //mImageLoader = new ImageLoader(MAINApplication.getRequestQueue(), new LruImageCache());

        //mRssItem = rssItem;

        //mRandom = new Random();
        mBackgroundColors = new ArrayList<Integer>();
        mBackgroundColors.add(R.color.blue2);
        mBackgroundColors.add(R.color.blue6);
        mBackgroundColors.add(R.color.blue3);
        mBackgroundColors.add(R.color.blue7);
        mBackgroundColors.add(R.color.blue4);
        mBackgroundColors.add(R.color.blue8);
        mBackgroundColors.add(R.color.blue5);

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item, parent, false);
            vh = new ViewHolder();
            vh.imageView = (ImageView)convertView.findViewById(R.id.image);
            vh.txtLineOne = (TextView) convertView.findViewById(R.id.txt_line1);
            vh.btnGo = (Button) convertView.findViewById(R.id.btn_go);

            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        //final double positionHeight = getPositionRatio(position);
        final int backgroundIndex = position >= mBackgroundColors.size() ?
                position % mBackgroundColors.size() : position;

        //convertView.setBackgroundColor(mBackgroundColors.get(backgroundIndex));

        //vh.txtLineOne.setHeightRatio(positionHeight);
        vh.txtLineOne.setText(getItem(position).getTitle());//getItem(position) + position);
        vh.txtLineOne.setBackgroundResource(mBackgroundColors.get(backgroundIndex));

        vh.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(getContext(), "Button Clicked Position " +
                        position, Toast.LENGTH_SHORT).show();
            }
        });


        // リクエストのキャンセル処理
        final ImageContainer imageContainer = (ImageContainer)vh.imageView.getTag();
        if (imageContainer != null) {
            imageContainer.cancelRequest();
        }

        final String imageUrl = getItem(position).getImage();
        final ImageListener listener = ImageLoader.getImageListener(vh.imageView,android.R.drawable.spinner_background,android.R.drawable.ic_dialog_alert);
        if (imageUrl != null){
            vh.imageView.setTag(((MainActivity)getContext()).getImageLoader().get(imageUrl, listener,1920,1080));
        }



        return convertView;
    }
/*
    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }*/
}