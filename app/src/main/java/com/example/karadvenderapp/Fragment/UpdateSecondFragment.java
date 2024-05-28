package com.example.karadvenderapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.karadvenderapp.Model.AreaList;
import com.example.karadvenderapp.Model.CountryList;
import com.example.karadvenderapp.Model.DistrictList;
import com.example.karadvenderapp.Model.LandmarkList;
import com.example.karadvenderapp.Model.StateList;
import com.example.karadvenderapp.Model.TalukaList;
import com.example.karadvenderapp.Model.VillageList;
import com.example.karadvenderapp.MyLib.MyValidator;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateSecondFragment extends Fragment implements BlockingStep {

    private View rateview;
    private EditText edt_website,
            edt_facebook, edt_twitter, edt_youtube, edt_google, edt_pincode, edt_edt_address, edt_building_name;
    private Spinner country_spinner, state_spinner, district_spinner, taluka_spinner, landmark_spinner, village_spinner, area_spinner;
    private String district = "1", taluka = "1", village = "1", country_id = "", state_id = "", area_id = "1", landmark_id = "1";
    private List<CountryList> country = new ArrayList<>();
    private List<StateList> stateList = new ArrayList<>();
    private List<DistrictList> districtLists = new ArrayList<>();
    private List<TalukaList> talukaLists = new ArrayList<>();
    private List<VillageList> villageLists = new ArrayList<>();
    private List<AreaList> areaLists = new ArrayList<>();
    private List<LandmarkList> landmarkLists = new ArrayList<>();
    private SimpleArcDialog mDialog;

    public UpdateSecondFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rateview = inflater.inflate(R.layout.fragment_second_level, container, false);
        FindByID();
        return rateview;
    }

    private void FindByID() {
        edt_website = rateview.findViewById(R.id.edt_website);
        edt_facebook = rateview.findViewById(R.id.edt_facebook);
        edt_twitter = rateview.findViewById(R.id.edt_twitter);
        edt_youtube = rateview.findViewById(R.id.edt_youtube);
        edt_google = rateview.findViewById(R.id.edt_google);
        district_spinner = rateview.findViewById(R.id.district_spinner);
        country_spinner = rateview.findViewById(R.id.country_spinner);
        state_spinner = rateview.findViewById(R.id.state_spinner);
        taluka_spinner = rateview.findViewById(R.id.taluka_spinner);
        village_spinner = rateview.findViewById(R.id.village_spinner);
        landmark_spinner = rateview.findViewById(R.id.landmark_spinner);
        area_spinner = rateview.findViewById(R.id.area_spinner);
        edt_pincode = rateview.findViewById(R.id.edt_pincode);
        edt_edt_address = rateview.findViewById(R.id.edt_edt_address);
        edt_building_name = rateview.findViewById(R.id.edt_building_name);

        edt_website.setText(Shared_Preferences.getPrefs(getContext(), "getBusiness_website"));
        edt_facebook.setText(Shared_Preferences.getPrefs(getContext(), "getFacebook_link"));
        edt_twitter.setText(Shared_Preferences.getPrefs(getContext(), "getTwitter_link"));
        edt_youtube.setText(Shared_Preferences.getPrefs(getContext(), "getYoutube_link"));
        edt_google.setText(Shared_Preferences.getPrefs(getContext(), "getGoogle_map_link"));
        edt_pincode.setText(Shared_Preferences.getPrefs(getContext(), "getPincode"));
        edt_edt_address.setText(Shared_Preferences.getPrefs(getContext(), "getAddress"));
        edt_building_name.setText(Shared_Preferences.getPrefs(getContext(), "getBuilding_name"));
        mDialog = new SimpleArcDialog(getContext());
        mDialog.setCancelable(false);
        getCountryData();
    }


    private boolean validateFields() {
        List<Boolean> listValidation = new ArrayList<>();
        listValidation.add(MyValidator.isValidSpinner(country_spinner));
        listValidation.add(MyValidator.isValidSpinner(state_spinner));
        listValidation.add(MyValidator.isValidSpinner(district_spinner));
        listValidation.add(MyValidator.isValidSpinner(taluka_spinner));
        listValidation.add(MyValidator.isValidSpinner(village_spinner));
        listValidation.add(MyValidator.isValidSpinner(area_spinner));
        listValidation.add(MyValidator.isValidField(edt_edt_address));
        return !listValidation.contains(false);
    }


    private void getCountryData() {
        country.clear();
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getcount();
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("org", "district: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("ResponseCode").equals("1")) {
//                        Toast.makeText(EditUserProfile.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        country.add(new CountryList("--- Select Country ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                country.add(new CountryList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<CountryList> dataAdapter = new ArrayAdapter<CountryList>(getContext(), android.R.layout.simple_spinner_item, country);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        country_spinner.setAdapter(dataAdapter);
                        String stat = Shared_Preferences.getPrefs(getContext(), "getFld_country_id");
                        for (int i = 1; i < country.size(); i++) {
                            if (country.get(i).getCountry_Id().equals(stat)) {
                                country_spinner.setSelection(i);
                                break;
                            }
                        }

                        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                country_id = country.get(i).getCountry_Id();
                                getstateData(country_id);

                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }
                    mDialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDialog.dismiss();

            }
        });
    }

    private void getstateData(String st_ID) {
        stateList.clear();
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getstate(st_ID);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                 mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("org", "state: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("ResponseCode").equals("1")) {

//                        Toast.makeText(EditUserProfile.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        stateList.add(new StateList("--- Select State ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                stateList.add(new StateList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<StateList> dataAdapter = new ArrayAdapter<StateList>(getContext(), android.R.layout.simple_spinner_item, stateList);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        state_spinner.setAdapter(dataAdapter);
                        String stat = Shared_Preferences.getPrefs(getContext(), "getFld_state_id");
                        for (int i = 1; i < stateList.size(); i++) {
                            if (stateList.get(i).getState_Id().equals(stat)) {
                                state_spinner.setSelection(i);
                                break;
                            }
                        }
                        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                state_id = stateList.get(i).getState_Id();
                                getDistData(state_id);
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }
                     mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                 mDialog.dismiss();
            }
        });
    }

    private void getDistData(String state_id) {
        districtLists.clear();
        //mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getDistrict(country_id, state_id);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                // mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("org", "district: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("ResponseCode").equals("1")) {
//                        Toast.makeText(EditUserProfile.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        districtLists.add(new DistrictList("--- Select District ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                districtLists.add(new DistrictList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<DistrictList> dataAdapter = new ArrayAdapter<DistrictList>(getContext(), android.R.layout.simple_spinner_item, districtLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        district_spinner.setAdapter(dataAdapter);
                        String stat = Shared_Preferences.getPrefs(getContext(), "getFld_district_id");
                        for (int i = 1; i < districtLists.size(); i++) {
                            if (districtLists.get(i).getDistrict_id().equals(stat)) {
                                district_spinner.setSelection(i);
                                break;
                            }
                        }
                        district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                district = districtLists.get(i).getDistrict_id();
                                gettalukaData(district);
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }

                    // mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // mDialog.dismiss();
            }
        });
    }

    private void gettalukaData(String district) {
        talukaLists.clear();
        //mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.gettaluka(district);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                // mDialog.dismiss();

                try {
                    output = response.body().string();
                    Log.d("org", "taluka: " + output);
                    JSONObject jsonObject = new JSONObject(output);


                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        talukaLists.add(new TalukaList("--- Select Taluka ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                talukaLists.add(new TalukaList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<TalukaList> dataAdapter = new ArrayAdapter<TalukaList>(getContext(), android.R.layout.simple_spinner_item, talukaLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        taluka_spinner.setAdapter(dataAdapter);
                        String stat = Shared_Preferences.getPrefs(getContext(), "getFld_taluka_id");
                        for (int i = 1; i < talukaLists.size(); i++) {
                            if (talukaLists.get(i).getTaluka_id().equals(stat)) {
                                taluka_spinner.setSelection(i);
                                break;
                            }
                        }
                        taluka_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                taluka = talukaLists.get(i).getTaluka_id();
                                getvillage();
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }
                    // mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // mDialog.dismiss();
            }
        });
    }

    private void getvillage() {//city list
        villageLists.clear();
        //mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getvillage(
                country_id,
                state_id,
                district,
                taluka);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                // mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("org", "village: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("ResponseCode").equals("1")) {
//                        Toast.makeText(EditUserProfile.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        villageLists.add(new VillageList("--- Select City ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                villageLists.add(new VillageList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<VillageList> dataAdapter = new ArrayAdapter<VillageList>(getContext(), android.R.layout.simple_spinner_item, villageLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        village_spinner.setAdapter(dataAdapter);
                        String stat = Shared_Preferences.getPrefs(getContext(), "getFld_city_id");
                        for (int i = 1; i < villageLists.size(); i++) {
                            if (villageLists.get(i).getVillage_id().equals(stat)) {
                                village_spinner.setSelection(i);
                                break;
                            }
                        }
                        village_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                village = villageLists.get(i).getVillage_id();
                                getArea();
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }
                    // mDialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // mDialog.dismiss();
            }
        });
    }

    private void getArea() {
        areaLists.clear();
        //mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getArea(village);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                // mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("org", "village: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("ResponseCode").equals("1")) {
//                        Toast.makeText(EditUserProfile.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        areaLists.add(new AreaList("--- Select Area ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                areaLists.add(new AreaList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<AreaList> dataAdapter = new ArrayAdapter<AreaList>(getContext(), android.R.layout.simple_spinner_item, areaLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        area_spinner.setAdapter(dataAdapter);
                        String stat = Shared_Preferences.getPrefs(getContext(), "getFld_area_id");
                        for (int i = 1; i < areaLists.size(); i++) {
                            if (areaLists.get(i).getFld_area_id().equals(stat)) {
                                area_spinner.setSelection(i);
                                break;
                            }
                        }
                        area_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                area_id = areaLists.get(i).getFld_area_id();
                                getLandMark();
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }
                    // mDialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // mDialog.dismiss();
            }
        });
    }

    private void getLandMark() {
        landmarkLists.clear();
        //mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        final Call<ResponseBody> result = (Call<ResponseBody>) apiInterface.getLandmark(village, area_id);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                // mDialog.dismiss();
                try {
                    output = response.body().string();
                    Log.d("org", "landmark: " + output);
                    JSONObject jsonObject = new JSONObject(output);

                    if (jsonObject.getString("ResponseCode").equals("1")) {
//                        Toast.makeText(EditUserProfile.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        // Parsing json
                        landmarkLists.add(new LandmarkList("--- Select LandMark ---"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                landmarkLists.add(new LandmarkList(obj));
                            } catch (JSONException e) {
                                //Log.d("Progress Dialog","Progress Dialog");
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<LandmarkList> dataAdapter = new ArrayAdapter<LandmarkList>(getContext(), android.R.layout.simple_spinner_item, landmarkLists);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        landmark_spinner.setAdapter(dataAdapter);
                        String stat = Shared_Preferences.getPrefs(getContext(), "getFld_landmark_id");
                        for (int i = 1; i < landmarkLists.size(); i++) {
                            if (landmarkLists.get(i).getFld_landmark_id().equals(stat)) {
                                landmark_spinner.setSelection(i);
                                break;
                            }
                        }
                        landmark_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                //  On selecting a spinner item
                                String item = adapterView.getItemAtPosition(i).toString();
                                landmark_id = landmarkLists.get(i).getFld_landmark_id();
                            }

                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                    }
                    // mDialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // mDialog.dismiss();
            }
        });
    }


    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        if (validateFields()) {
            Shared_Preferences.setPrefs(getContext(), "website", edt_website.getText().toString().trim());
            Shared_Preferences.setPrefs(getContext(), "facebook", edt_facebook.getText().toString().trim());
            Shared_Preferences.setPrefs(getContext(), "twitter", edt_twitter.getText().toString().trim());
            Shared_Preferences.setPrefs(getContext(), "youtube", edt_youtube.getText().toString().trim());
            Shared_Preferences.setPrefs(getContext(), "google", edt_google.getText().toString().trim());
            Shared_Preferences.setPrefs(getContext(), "district", district);
            Shared_Preferences.setPrefs(getContext(), "taluka", taluka);
            Shared_Preferences.setPrefs(getContext(), "village", village);
            Shared_Preferences.setPrefs(getContext(), "country", country_id);
            Shared_Preferences.setPrefs(getContext(), "state", state_id);
            Shared_Preferences.setPrefs(getContext(), "landmark", landmark_id);
            Shared_Preferences.setPrefs(getContext(), "area", area_id);
            Shared_Preferences.setPrefs(getContext(), "pincode", edt_pincode.getText().toString().trim());
            Shared_Preferences.setPrefs(getContext(), "address", edt_edt_address.getText().toString().trim());
            Shared_Preferences.setPrefs(getContext(), "building_name", edt_building_name.getText().toString().trim());


            callback.goToNextStep();
        } else {
            Toast.makeText(getContext(), "Fill The Complete Data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
