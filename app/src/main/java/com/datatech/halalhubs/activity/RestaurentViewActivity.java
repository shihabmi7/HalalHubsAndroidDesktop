package com.datatech.halalhubs.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.adpater.RestaurentListAdapter;
import com.datatech.halalhubs.model.Restaurant;
import com.datatech.halalhubs.utility.ApplicationData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

public class RestaurentViewActivity extends CustomWindow {

	RestaurentViewActivity activity = this;
	// https://github.com/nostra13/Android-Universal-Image-Loader/wiki

	ListView list_view;
	private RestaurentListAdapter resAdapter;
	ArrayList<Restaurant> resList = new ArrayList<Restaurant>();

	TextView restaurent_name, textView_restaurent_address, restaurent_open,
			restaurent_close;
	ImageView imageView_restaurent_pic;
	// ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	Context context = this;;
	LinearLayout linear_restaurent_info;

	private View relative_restaurent_info;
	Button button_show_menu;

	private RatingBar ratingBar_restaurent_view;

	@Override
	protected void onResume() {

		super.onResume();
		doIncrease();

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurent_view);

		// restaurent_name = (TextView) findViewById(R.id.restaurent_name);

		try {
			int position = getIntent().getIntExtra("position", 0);

			options = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(R.drawable.ic_launcher)
					.showImageOnFail(R.drawable.ic_launcher)
					.displayer(new RoundedBitmapDisplayer(5))
					.cacheInMemory(true).build();

			// ImageLoaderConfiguration config = new
			// ImageLoaderConfiguration.Builder(
			// context).diskCacheExtraOptions(480, 480, null).build();
			// imageLoader.init(config);

			imageView_restaurent_pic = (ImageView) findViewById(R.id.imageView_menu);
			restaurent_name = (TextView) findViewById(R.id.menu_name);
			textView_restaurent_address = (TextView) findViewById(R.id.restaurent_address);
			restaurent_open = (TextView) findViewById(R.id.restaurent_open);
			restaurent_close = (TextView) findViewById(R.id.restaurent_close);
			button_show_menu = (Button) findViewById(R.id.button_show_menu);
			// relative_restaurent_info = (LinearLayout)
			// findViewById(R.id.relative_restaurent_info);

			ratingBar_restaurent_view = (RatingBar) findViewById(R.id.ratingBar_restaurent_view);
			ratingBar_restaurent_view.setStepSize((float) 0.25);
			ratingBar_restaurent_view.setRating(4);

			button_show_menu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					startActivity(new Intent(activity,
							DemoRestaurentFoodListActivity.class));

				}
			});

			Restaurant aRestaurant = ApplicationData.restaurantList
					.get(position);

			restaurent_name.setText(aRestaurant.getRestaurentName());
			textView_restaurent_address.setSelected(true);
			textView_restaurent_address.setText(aRestaurant.getAddress());
			restaurent_open
					.setText(getString(R.string.restaurant_delivery_open)
							+ aRestaurant.getOpening_Time());
			restaurent_close
					.setText(getString(R.string.restaurent_delivery_close)
							+ aRestaurant.getClosing_Time());

			ApplicationData.ImageLoaderGetInstance().displayImage(
					aRestaurant.getImage_Url(), imageView_restaurent_pic,
					options);

			ApplicationData.ImageLoaderGetInstance().loadImage(
					aRestaurant.getImage_Url(),
					new SimpleImageLoadingListener() {
						@SuppressWarnings("deprecation")
						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {

							// Do whatever you want with Bitmap
							Log.e("loadImage", "onLoadingComplete ");

							// TODO UPDATE CODE HERE SHIHAB
							// relative_restaurent_info
							// .setBackground(new BitmapDrawable(
							// loadedImage));
						}
					});

			imageView_restaurent_pic.bringToFront();

			// imageLoader

			// imageLoader
			// .displayImage(ApplicationData.restaurantList.get(position)
			// .getImage_Url(),
			// ((LinearLayout)findViewById(R.id.linear_restaurent_info)),
			// options);

		} catch (RuntimeException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
