package com.datatech.halalhubs.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.datatech.halalhubs.model.Restaurant;
import com.datatech.halalhubs.utility.ApplicationData;
import com.datatech.halalhubs.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

public class RestaurentListAdapter extends BaseAdapter {

    private final Context mContext;
    ArrayList<Restaurant> newsItemList;
    DisplayImageOptions options;
    ImageLoader imageLoader = ImageLoader.getInstance();

    // we use universal image loader library

    public RestaurentListAdapter(Context context, ArrayList<Restaurant> list) {

        mContext = context;
        this.newsItemList = list;
        // =============== UIL Initialization =================

        options = new DisplayImageOptions.Builder()

                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .displayer(new RoundedBitmapDisplayer(5)).cacheInMemory(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext).diskCacheExtraOptions(480, 480, null).build();
        imageLoader.init(config);

        // =====================================================
    }

    @Override
    public int getCount() {
        // return 100;
        return ApplicationData.restaurantList.size();

    }

    @Override
    public Object getItem(int position) {
        return ApplicationData.restaurantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.listview_item_a_restaurent, parent, false);

            holder = new ViewHolder();

            holder.res_name = (TextView) convertView
                    .findViewById(R.id.menu_name);

            holder.res_area = (TextView) convertView
                    .findViewById(R.id.textView_restaurent_address);

            holder.catagory_image = (ImageView) convertView
                    .findViewById(R.id.imageView_menu);

            holder.ratingBar_restaurent = (RatingBar) convertView
                    .findViewById(R.id.ratingBar_restaurent);

            holder.ratingBar_restaurent.setStepSize((float) 0.25);
            holder.ratingBar_restaurent.setRating(4);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Restaurant resObj = (Restaurant) ApplicationData.restaurantList
                .get(position);
        holder.res_name.setText(resObj.getRestaurentName());
        holder.res_area.setText(resObj.getAddress());

        // just of

        //		imageLoader.displayImage(ApplicationData.restaurantList.get(position)
        //				.getImage_Url(), holder.catagory_image, options);

        if (holder.catagory_image != null) {

            // mahbub vi code

        }
        return convertView;
    }

    class ViewHolder {

        private int catatory_id;
        private TextView res_name;
        private TextView res_area;
        private ImageView catagory_image;
        RatingBar ratingBar_restaurent;

    }

}
