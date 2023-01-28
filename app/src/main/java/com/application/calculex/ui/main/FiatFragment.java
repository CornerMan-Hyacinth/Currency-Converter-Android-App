package com.application.calculex.ui.main;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.application.calculex.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FiatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FiatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Spinner spin1, spin2;
    EditText base;
    TextView quote;
    Button convert;

    public FiatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FiatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FiatFragment newInstance(String param1, String param2) {
        FiatFragment fragment = new FiatFragment();
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
        View viewer = inflater.inflate(R.layout.fragment_crypto, container, false);

        spin1 = viewer.findViewById(R.id.spinner1);
        spin2 = viewer.findViewById(R.id.spinner2);
        base = viewer.findViewById(R.id.base);
        quote = viewer.findViewById(R.id.quote);
        convert = viewer.findViewById(R.id.convert);

        base.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);

        ArrayHolder arrayHolder = new ArrayHolder();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayHolder.currencyCodes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter);
        spin2.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            convert.setOnClickListener(view -> {
                doCall();
            });
        } else {
            Toast.makeText(getContext(), "Device Version is Outdated", Toast.LENGTH_LONG).show();
        }

        return viewer;
    }

    public void doCall(){
        String spin1Str = spin1.getSelectedItem().toString();
        String spin2Str = spin2.getSelectedItem().toString();
        String baseStr = base.getText().toString();

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        /* The APIKEY is provided by apilayer.com */
        Request request = new Request.Builder()
                .url("https://api.apilayer.com/exchangerates_data/convert?to=" +
                        spin2Str + "&from=" + spin1Str + "&amount=" + baseStr)
                .addHeader("apikey", "CkD00oWo5zu30ffwz1nlszQce4CvSBdD").method("GET", null).build();
//                  Response response = null;


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        Double result = jsonObject.getDouble("result");

                        requireActivity().runOnUiThread(() -> quote.setText(String.format("%.4f", result)));

                    } catch (JSONException e) {
                        requireActivity().runOnUiThread(() ->
                                Toast.makeText(getContext(), "Failed Calling the API", Toast.LENGTH_LONG).show());
                    }
                    // Do something with the response
                } else {
                    // Handle error
                    requireActivity().runOnUiThread(() -> quote.setText("Failed to retrieve RESPONSE"));

                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Failed Calling the API", Toast.LENGTH_LONG).show());
            }
        });

    }
}