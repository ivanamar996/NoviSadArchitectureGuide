package com.example.architecturens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.NetworkConnection.DbConnection;
import com.example.service.GetDataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RateApp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RateApp extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RatingBar ratingBar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RateApp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RateApp.
     */
    // TODO: Rename and change types and number of parameters
    public static RateApp newInstance(String param1, String param2) {
        RateApp fragment = new RateApp();
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
        View v = inflater.inflate(R.layout.fragment_rate_app, container, false);

        ratingBar = v.findViewById(R.id.ratingApp);

        addListenerOnRatingBar(v);

        return v;
    }

    public void addListenerOnRatingBar(View v) {

        ratingBar = v.findViewById(R.id.ratingApp);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                if (PlaceInfoActivity.wifiConnected) {
                    postGradeToBackend(rating);
                } else {
                    Toast.makeText(getActivity(), "Please, connect to wifi to rate the place.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void postGradeToBackend(double grade){
        final GetDataService service = DbConnection.getRetrofitInstance().create(GetDataService.class);

        Call<String> call = service.rateApp(grade);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getActivity(),"Thanks for rating our app.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
