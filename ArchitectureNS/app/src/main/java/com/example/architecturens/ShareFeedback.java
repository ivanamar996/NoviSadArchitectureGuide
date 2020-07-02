package com.example.architecturens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.NetworkConnection.DbConnection;
import com.example.service.GetDataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareFeedback extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText editText;
    private Button saveButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShareFeedback() {
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
    public static ShareFeedback newInstance(String param1, String param2) {
        ShareFeedback fragment = new ShareFeedback();
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
        View v = inflater.inflate(R.layout.fragment_sharefeedback, container, false);

        editText = v.findViewById(R.id.simpleEditText);
        saveButton = v.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (PlaceInfoActivity.wifiConnected) {
                    if(editText.getText().toString().equals(""))
                        Toast.makeText(getActivity(), "Enter message before saving.", Toast.LENGTH_SHORT).show();
                    else
                        postCommentToBackend();
                } else {
                    Toast.makeText(getActivity(), "Please, connect to wifi to share feedback message.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    private void postCommentToBackend(){
        final GetDataService service = DbConnection.getRetrofitInstance().create(GetDataService.class);

        Call<String> call = service.commentApp(editText.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getActivity(),"Thanks for sharing feedback message.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
