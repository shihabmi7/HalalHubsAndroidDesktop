package com.datatech.halalhubs.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.activity.TrackMyFoodActivity;

/**
 * @author Shihab Uddin
 *
 */
public class TrackFoodFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private SearchFoodFragment.OnFragmentInteractionListener mListener;
    private EditText edittext_track_number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragmenta_track_food, container, false);

        edittext_track_number = (EditText) v.findViewById(R.id.edittext_track_number);


        edittext_track_number.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    //startGettingRestaurent();

                    Toast.makeText(getActivity(),"Track is working...",Toast.LENGTH_SHORT).show();
                    return true;

                }
                return false;
            }
        });




        Button button_track_food = (Button) v.findViewById(R.id.button_track_food);
        button_track_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(),
							TrackMyFoodActivity.class));

            }
        });

        return v;
    }
    public  TrackFoodFragment(){



    }

    // TODO: Rename and change types and number of parameters
    public static SearchFoodFragment newInstance(String param1, String param2) {
        SearchFoodFragment fragment = new SearchFoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
}