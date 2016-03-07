package com.datatech.halalhubs.adpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.activity.ShowFoodDetailsActivity;
import com.datatech.halalhubs.model.Food;
import com.datatech.halalhubs.view.PinnedSectionListView.PinnedSectionListAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

public class DemoFoodListAdapter extends BaseAdapter implements
		PinnedSectionListAdapter {

	private final Context mContext;
	ArrayList<Food> foodList;
	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();

	// View Type for Separators
	private static final int ITEM_VIEW_TYPE_SEPARATOR = 0;
	// View Type for Regular rows
	private static final int ITEM_VIEW_TYPE_REGULAR = 1;
	// Types of Views that need to be handled
	// -- Separators and Regular rows --
	private static final int ITEM_VIEW_TYPE_COUNT = 2;

	// we use universal image loader library

	public DemoFoodListAdapter(Context context, ArrayList<Food> list) {

		mContext = context;
		this.foodList = list;
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

	/*
	 * DONE
	 */@Override
	public int getCount() {
		// return 100;
		return foodList.size();

	}

	@Override
	public Food getItem(int position) {
		return foodList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean isEnabled(int position) {

		return getItemViewType(position) != ITEM_VIEW_TYPE_SEPARATOR;
	}

	@Override
	public int getViewTypeCount() {
		return ITEM_VIEW_TYPE_COUNT;
	}

	@Override
	public int getItemViewType(int position) {

		return foodList.get(position).getFoodType();
	}

	/*
	 * 
	 * Responsible for Pinned ListView
	 */
	@Override
	public boolean isItemViewTypePinned(int viewType) {
		// TODO Auto-generated method stub
		return viewType == Food.FOOD_SECTION;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		final Food aFood = foodList.get(position);

		if (convertView == null) {

			holder = new ViewHolder();

			switch (aFood.getFoodType()) {

			case Food.FOOD_SECTION:

				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.item_food_header, parent, false);

				holder.food_section_header = (TextView) convertView
						.findViewById(R.id.header);

				break;

			case Food.FOOD_ITEM:

				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listview_item_a_food, parent, false);

				holder.food_name = (TextView) convertView
						.findViewById(R.id.food_name);

				holder.food_description = (TextView) convertView
						.findViewById(R.id.food_description);

				holder.food_image = (ImageView) convertView
						.findViewById(R.id.imageView_food_pic);

				break;

			}

			// holder.ratingBar_restaurent = (RatingBar) convertView
			// .findViewById(R.id.ratingBar_restaurent);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		convertView.setClickable(true);
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Toast.makeText(mContext, "Food Item : " + aFood.getFoodName(),
						Toast.LENGTH_LONG).show();

				mContext.startActivity(new Intent(mContext,
						ShowFoodDetailsActivity.class).setFlags(
						Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("position",
						aFood.getFoodIDNo()));
			}
		});

		switch (aFood.getFoodType()) {

		case Food.FOOD_SECTION:

			holder.food_section_header.setText(aFood.getFoodTypeName());

			break;

		case Food.FOOD_ITEM:

			// show only food name
			holder.food_name.setText(aFood.getFoodName());

			break;

		}

		// holder.food_description.setText(aFood.getFoodDescription());

		// holder.ratingBar_restaurent.setStepSize((float) 0.25);
		// holder.ratingBar_restaurent.setRating(4);

		// imageLoader.displayImage(aFood.getFoodPictureUrl(),
		// holder.food_image,
		// options);
		//
		// if (holder.food_image != null) {
		//
		// // mahbub vi code
		//
		// }
		return convertView;
	}

	class ViewHolder {

		private int catatory_id;
		private TextView food_name;
		private TextView food_section_header;
		private TextView food_description;
		private ImageView food_image;
		RatingBar ratingBar_restaurent;

	}

}
