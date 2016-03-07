package com.datatech.halalhubs.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.adpater.DemoFoodListAdapter;
import com.datatech.halalhubs.model.Food;
import com.datatech.halalhubs.utility.ApplicationData;
import com.datatech.halalhubs.utility.InternetConnection;
import com.datatech.halalhubs.view.PinnedSectionListView;

import java.util.ArrayList;

import network.CustomListener;
import network.GetFoodListFromAPI;

public class DemoRestaurentFoodListActivity extends CustomWindow {

	DemoRestaurentFoodListActivity activity = this;

	// TODO GET THE LIST FROM HALAL HUBS: finished
	// TODO SET THE LSIT ITEM LAYOUT & ADAPTER : finished
	// TODO SHOW LIST , implements pin secion list adapter : finish

	PinnedSectionListView list_view_foodList;
	// ListView list_view_foodList;
	private DemoFoodListAdapter foodListAdapter;
	ArrayList<Food> foodList = new ArrayList<Food>();

	private boolean isFastScroll = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_activity_food_list);

		list_view_foodList = (PinnedSectionListView) findViewById(R.id.list_view_foodList);

		// list_view_foodList = (ListView)
		// findViewById(R.id.list_view_foodList);
		// list_view_foodList.setClickable(true);

		try {

			if (InternetConnection.isAvailable(activity)) {

				getFoodListFromAPI();

			} else {

				showAlert(activity);

			}

			list_view_foodList
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long arg3) {

							// TODO GO TO NEXT PAGE
							Food food = foodList.get(position);

							Toast.makeText(getApplicationContext(),
									"Food Item : " + food.getFoodTypeName(),
									Toast.LENGTH_LONG).show();

							if (food.getFoodType() == Food.FOOD_ITEM) {
								// do your want to do...

								Toast.makeText(getApplicationContext(),
										"Food Item : " + food.getFoodName(),
										Toast.LENGTH_LONG).show();

								// startActivity(new Intent(activity,
								// ShowFoodDetailsActivity.class)
								// .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
								// .putExtra("position", position)
								// .putExtra("food", food));

							} else if (food.getFoodType() == Food.FOOD_SECTION) {

								// the Section is on click

								Toast.makeText(getApplicationContext(),
										"" + food.getFoodTypeName(),
										Toast.LENGTH_LONG).show();

							}

						}
					});

		} catch (RuntimeException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {

		super.onResume();
		doIncrease();

	}

	//
	// @Override
	// protected void onListItemClick(ListView l, View v, int position, long id)
	// {
	//
	// Food item = (Food) getListView().getAdapter().getItem(position);
	//
	// if (item != null) {
	//
	// Toast.makeText(this,
	// "Item " + position + ": " + item.getFoodName(),
	// Toast.LENGTH_SHORT).show();
	// } else {
	//
	// Toast.makeText(this, "Item " + position, Toast.LENGTH_SHORT).show();
	//
	// }
	// }

	public void getFoodListFromAPI() {

		GetFoodListFromAPI api = new GetFoodListFromAPI(activity, true);

		api.setListener(new CustomListener() {

			@Override
			public void ModificationMade() {

				foodList = ApplicationData.foodList;
				foodListAdapter = new DemoFoodListAdapter(
						getApplicationContext(), foodList);

				list_view_foodList.setAdapter(foodListAdapter);
				// setListAdapter(foodListAdapter);

			}
		});

		api.execute();
	}

	public void showAlert(final Activity activity) {

		if (InternetConnection.isAvailable(activity)) {

			getFoodListFromAPI();

		} else {

			new AlertDialog.Builder(activity)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("No Internet Connection")
					.setMessage("Please check your connectivity.")
					.setPositiveButton("Exit",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									InternetConnection
											.ExitApplication(activity);

								}

							})
					.setNegativeButton("Retry",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									// Stop the activity
									showAlert(activity);

								}

							}).show();
		}

	}
}
