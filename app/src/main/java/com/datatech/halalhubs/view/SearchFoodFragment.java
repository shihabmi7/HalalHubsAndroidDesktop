package com.datatech.halalhubs.view;

import android.content.Intent;
import android.net.Uri;
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

import com.datatech.halalhubs.R;
import com.datatech.halalhubs.activity.RestaurentListActivity;
import com.datatech.halalhubs.utility.ApplicationUtility;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFoodFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private EditText editTextPostCode;

    View.OnClickListener try_to_search = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            startGettingRestaurent();
        }
    };

    private void startGettingRestaurent() {
        if (ApplicationUtility.isNetworkAvailable(getActivity())) {

            ApplicationUtility.hide_Keyboard(getActivity());
//                String identifier = identifier_input.getText().toString();
//                String password = password_input.getText().toString();

            // Toast.makeText(getActivity(), " Search Food Clicked", Toast.LENGTH_LONG).show();
//                new LoginUser(LoginView.newInstance(), identifier, password, getActivity())
//                        .execute();


            Intent intent = new Intent(getActivity(),
                    RestaurentListActivity.class);
            intent.putExtra("postCode", "" + editTextPostCode.getText());
            startActivity(intent);
        }else {

            ApplicationUtility.hide_Keyboard(getActivity());
        }
    }

    // TODO: Rename and change types of parameters


    public SearchFoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFoodFragment newInstance(String param1, String param2) {
        SearchFoodFragment fragment = new SearchFoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_food, container, false);

        editTextPostCode = (EditText) v.findViewById(R.id.edittext_postcode);


        editTextPostCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    startGettingRestaurent();

                    return true;
                }
                return false;
            }
        });

        EditText editTextCuisines = (EditText) v.findViewById(R.id.edittext_cuisine);
        EditText editTextQuickFilter = (EditText) v.findViewById(R.id.edittext_quick_filter);


        Button searchButton = (Button) v.findViewById(R.id.button_search_food);
        searchButton.setOnClickListener(try_to_search);

        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

  /*  @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

   /* @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
