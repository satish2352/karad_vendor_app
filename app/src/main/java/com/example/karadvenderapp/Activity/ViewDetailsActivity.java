package com.example.karadvenderapp.Activity;


import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.karadvenderapp.Adapter.BusinessTimeAdapter;
import com.example.karadvenderapp.Model.BusinessTime;
import com.example.karadvenderapp.MyLib.Camera;
import com.example.karadvenderapp.MyLib.Constants;
import com.example.karadvenderapp.MyLib.Shared_Preferences;
import com.example.karadvenderapp.MyLib.UtilityRuntimePermission;
import com.example.karadvenderapp.NetworkController.APIInterface;
import com.example.karadvenderapp.NetworkController.MyConfig;
import com.example.karadvenderapp.NetworkController.SimpleArcDialog;
import com.example.karadvenderapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDetailsActivity extends UtilityRuntimePermission implements SwipeRefreshLayout.OnRefreshListener, Camera.AsyncResponse, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, BusinessTimeAdapter.OnItemClickListener {
    List<String> homeSliderModelList = new ArrayList<String>();
    private ImageButton back;
    CircleImageView fab, fab1, fab2, fab3, iv_busi_profile;
    LinearLayout fabLayout1, fabLayout2, fabLayout3;
    FrameLayout linLayHeader;
    private ArrayList<BusinessTime> businessTimes = new ArrayList<>();
    private BusinessTimeAdapter businessTimeAdapter;

    String str_listEndDate = "";
    String str_inpStartDate = "";

    boolean isFABOpen = false;
    private Dialog dialog;
    private Camera camera;
    private CharSequence[] options = {"camera", "Gallery", "Cancel"};
    private String profile_image_name = "", profile_image_path = "";
    private TextView text_time, btn_varient, tv_address, tv_mobile, tv_email_id, tv_desc, tv_facebook, tv_youtube, tv_google, tv_twitter, tv_web_text;
    private SimpleArcDialog mDialog;
    private int i = 0;
    private ImageView iv_facebook, iv_googlemap, iv_webite, iv_twitter, iv_youtube;
    String Business_Name, Business_ID, Business_image;
    private TextView dia_busi_name, edt_busi_description, edt_busi_rate, dia_start_time, edt_end_time;
    private SwipeRefreshLayout SwipeRefresh;
    private RecyclerView recycler_times;
    private RelativeLayout rel_update_business;
    private int pos = 0;
    ArrayList<String> list = new ArrayList<String>();

    String filepath = "";
    File imageFile;
    Uri uri;
    String Date = new SimpleDateFormat("yyyymmdd", Locale.getDefault()).format(new Date());
    String Time = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(new Date());

    int hour, min;
    String endtimecompare;

    LinearLayout request, approve, complete, cancel, reject, dissaprove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        back = findViewById(R.id.back);
        text_time = findViewById(R.id.text_time);
        recycler_times = findViewById(R.id.recycler_times);
        rel_update_business = findViewById(R.id.rel_update_business);
        SwipeRefresh = findViewById(R.id.SwipeRefresh);
        request = findViewById(R.id.requestUser);
        approve = findViewById(R.id.requestApprove);
        complete = findViewById(R.id.requestComplete);
        cancel = findViewById(R.id.requestCancel);
        reject = findViewById(R.id.requestReject);
        dissaprove = findViewById(R.id.requestDisaaprove);


        SwipeRefresh.setOnRefreshListener(this);


        SwipeRefresh.post(new Runnable() {
                              @Override
                              public void run() {
                                  SwipeRefresh.setRefreshing(true);
                                  ViewPhoto(Business_ID);
                                  viewBusinessTime();
                              }
                          }
        );

        tv_address = findViewById(R.id.tv_address);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_email_id = findViewById(R.id.tv_email_id);
        tv_desc = findViewById(R.id.tv_desc);
        iv_busi_profile = findViewById(R.id.iv_busi_profile);
        iv_facebook = findViewById(R.id.iv_facebook);
        iv_webite = findViewById(R.id.iv_webite);
        iv_googlemap = findViewById(R.id.iv_googlemap);
        iv_twitter = findViewById(R.id.iv_twitter);
        iv_youtube = findViewById(R.id.iv_youtube);

        mDialog = new SimpleArcDialog(ViewDetailsActivity.this);
        mDialog.setCancelable(false);

        camera = new Camera(ViewDetailsActivity.this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        String businessinfoid = intent.getStringExtra("getBusiness_info_id");
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getBusiness_info_id", intent.getStringExtra("getBusiness_info_id"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getBusiness_info_name", intent.getStringExtra("getBusiness_info_name"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getOwner_name", intent.getStringExtra("getOwner_name"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getFld_business_id", intent.getStringExtra("getFld_business_id"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getFld_business_name", intent.getStringExtra("getFld_business_name"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getFld_business_category_id", intent.getStringExtra("getFld_business_category_id"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getFld_business_subcategory_id", intent.getStringExtra("getFld_business_subcategory_id"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getFld_business_subsubcategory_id", intent.getStringExtra("getFld_business_subsubcategory_id"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getBusiness_description", intent.getStringExtra("getBusiness_description"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getAddress", intent.getStringExtra("getAddress"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getFld_country_id", intent.getStringExtra("getFld_country_id"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getFld_state_id", intent.getStringExtra("getFld_state_id"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getFld_district_id", intent.getStringExtra("getFld_district_id"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getFld_taluka_id", intent.getStringExtra("getFld_taluka_id"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getFld_city_id", intent.getStringExtra("getFld_city_id"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getFld_area_id", intent.getStringExtra("getFld_area_id"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getFld_landmark_id", intent.getStringExtra("getFld_landmark_id"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getBuilding_name", intent.getStringExtra("getBuilding_name"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getPincode", intent.getStringExtra("getPincode"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getContact_person", intent.getStringExtra("getContact_person"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getDesignation", intent.getStringExtra("getDesignation"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getMobile_no", intent.getStringExtra("getMobile_no"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getTelephone_no", intent.getStringExtra("getTelephone_no"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getBusiness_website", intent.getStringExtra("getBusiness_website"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getBusiness_email", intent.getStringExtra("getBusiness_email"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getFacebook_link", intent.getStringExtra("getFacebook_link"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getTwitter_link", intent.getStringExtra("getTwitter_link"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getYoutube_link", intent.getStringExtra("getYoutube_link"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getGoogle_map_link", intent.getStringExtra("getGoogle_map_link"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getWorking_days", intent.getStringExtra("getWorking_days"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getStart_working_time", intent.getStringExtra("getStart_working_time"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getEnd_working_time", intent.getStringExtra("getEnd_working_time"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getInterval_time", intent.getStringExtra("getInterval_time"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getNo_of_people", intent.getStringExtra("getNo_of_people"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getPayment_mode", intent.getStringExtra("getPayment_mode"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getEstablishment_year", intent.getStringExtra("getEstablishment_year"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getAnnual_turnover", intent.getStringExtra("getAnnual_turnover"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getNumber_of_employees", intent.getStringExtra("getNumber_of_employees"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getCertification", intent.getStringExtra("getCertification"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "getBusiness_image", intent.getStringExtra("getBusiness_image"));
        Shared_Preferences.setPrefs(ViewDetailsActivity.this, "get_booking_time", intent.getStringExtra("get_booking_time"));


        tv_address.setText("" + intent.getStringExtra("getAddress"));
        tv_mobile.setText("" + intent.getStringExtra("getMobile_no"));
        tv_email_id.setText("" + intent.getStringExtra("getBusiness_email"));
        tv_desc.setText("" + intent.getStringExtra("getBusiness_description"));

        if (intent.getStringExtra("getFacebook_link").equals("")) {
            iv_facebook.setVisibility(View.GONE);//gone
        } else {
            iv_facebook.setVisibility(View.VISIBLE);
        }
        if (intent.getStringExtra("getYoutube_link").equals("")) {
            iv_youtube.setVisibility(View.GONE);//gone
        } else {
            iv_youtube.setVisibility(View.VISIBLE);
        }
        if (intent.getStringExtra("getTwitter_link").equals("")) {
            iv_twitter.setVisibility(View.GONE);//gone
        } else {
            iv_twitter.setVisibility(View.VISIBLE);
        }
        if (intent.getStringExtra("getGoogle_map_link").equals("")) {
            iv_googlemap.setVisibility(View.GONE);//gone
        } else {
            iv_googlemap.setVisibility(View.VISIBLE);
        }
        if (intent.getStringExtra("getBusiness_website").equals("")) {
            iv_webite.setVisibility(View.GONE);//gone
        } else {
            iv_webite.setVisibility(View.VISIBLE);
        }
        rel_update_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 *
                 * Update Data Shared_Preferences*/
                Intent intent1 = new Intent(ViewDetailsActivity.this, UpdateVendorBusiness.class);

                startActivity(intent1);

            }
        });

        iv_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fb = intent.getStringExtra("getFacebook_link");
                if (fb.startsWith("http://") || fb.startsWith("https://")) {

                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(fb)));
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://" + fb)));
                }

            }
        });
        iv_googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String map = intent.getStringExtra("getGoogle_map_link");
                if (map.startsWith("http://") || map.startsWith("https://")) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(map)));
                    } catch (Exception e) {

                    }

                } else {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://" + map)));
                    } catch (Exception e) {

                    }
                }
            }
        });
        iv_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String twitter = intent.getStringExtra("getTwitter_link");
                if (twitter.startsWith("http://") || twitter.startsWith("https://")) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(twitter)));
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://" + twitter)));
                }
            }
        });
        iv_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String youtube = intent.getStringExtra("getYoutube_link");
                if (youtube.startsWith("http://") || youtube.startsWith("https://")) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(youtube)));
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://" + youtube)));
                }

            }
        });
        iv_webite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String web = intent.getStringExtra("getBusiness_website");
                if (web.startsWith("http://") || web.startsWith("https://")) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(web)));
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://" + web)));
                }
            }
        });

        Business_Name = intent.getStringExtra("getBusiness_info_name");
        Business_ID = intent.getStringExtra("getBusiness_info_id");
        Business_image = intent.getStringExtra("getBusiness_image");
        Picasso.with(ViewDetailsActivity.this)
                .load(Business_image)
                .error(R.drawable.app_logo)
                .placeholder(R.drawable.app_logo)
                .into(iv_busi_profile);
        linLayHeader = (FrameLayout) findViewById(R.id.linLayHeader);
        fabLayout1 = (LinearLayout) findViewById(R.id.fabLayout1);
        fabLayout2 = (LinearLayout) findViewById(R.id.fabLayout2);
        fabLayout3 = (LinearLayout) findViewById(R.id.fabLayout3);
        fab = (CircleImageView) findViewById(R.id.fab);
        fab1 = (CircleImageView) findViewById(R.id.fab1);
        fab2 = (CircleImageView) findViewById(R.id.fab2);
        fab3 = (CircleImageView) findViewById(R.id.fab3);
        btn_varient = (TextView) findViewById(R.id.btn_varient);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    linLayHeader.setBackgroundColor(getResources().getColor(R.color.trans_grey_color));
                    showFABMenu();
                } else {
                    linLayHeader.setBackgroundColor(getResources().getColor(R.color.gray_separatorwhite));
                    closeFABMenu();
                }
            }
        });


        fabLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ViewDetailsActivity.this, AddBusinessPhoto.class);
//                startActivity(intent);
                ViewDialogue();
                if (!isFABOpen) {
                } else {
                    closeFABMenu();
                }
            }
        });
        fabLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_Business_varient();
                if (!isFABOpen) {
                } else {
                    closeFABMenu();
                }
            }
        });
        fabLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_Business_Time();
                if (!isFABOpen) {
                } else {
                    closeFABMenu();
                }
            }
        });
        btn_varient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ViewDetailsActivity.this, MyBusinessActivity.class);
                intent1.putExtra("my_id", Business_ID);
                intent1.putExtra("my_name", Business_Name);
                startActivity(intent1);
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewDetailsActivity.this, RequestUserActivity.class);
                startActivity(i);
            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewDetailsActivity.this, RequestApprovedActivity.class);
                startActivity(i);
            }
        });
        dissaprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewDetailsActivity.this, RequestDisapproveActivity.class);
                startActivity(i);
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewDetailsActivity.this, RequestCompleteActivity.class);
                startActivity(i);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewDetailsActivity.this, RequestCancelActivity.class);
                startActivity(i);
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewDetailsActivity.this, RequestRejectActivity.class);
                i.putExtra("hide", "hide");
                startActivity(i);
            }
        });

        //   getLastScheduledEndDate();

    }

//    private void getLastScheduledEndDate() {
//
//        //str_lastEndDate = businessTimes.get(businessTimes.size()).getFld_working_close_time();
//
//        Log.e("Response", "size of businessTime List is: " + businessTimes.size());
//        for (int i = 0; i < businessTimes.size(); i++) {
//            str_lastEndDate = businessTimes.get(i).getFld_working_close_time();
//            Log.e("Response", "str_lastEndDate: " + str_lastEndDate);
//
//
//        }
//
//        Log.e("Response", "str_lastEndDate: " + str_lastEndDate);
//
//
//    }

    private void showFABMenu() {
        if (Shared_Preferences.getPrefs(ViewDetailsActivity.this, "getFld_business_name").equals("Appoinments")) {
            isFABOpen = true;
            fabLayout1.setVisibility(View.VISIBLE);
            fabLayout2.setVisibility(View.GONE);
            fabLayout3.setVisibility(View.VISIBLE);
            fab.animate().rotationBy(360);
            fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.fifteen));
            fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        } else {
            isFABOpen = true;
            fabLayout1.setVisibility(View.VISIBLE);
            fabLayout2.setVisibility(View.VISIBLE);
            fabLayout3.setVisibility(View.VISIBLE);
            fab.animate().rotationBy(360);
            fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.fifteen));
            fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
            fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        }

    }

    private void closeFABMenu() {
        linLayHeader.setBackgroundColor(getResources().getColor(R.color.gray_separatorwhite));
        isFABOpen = false;
        fab.animate().rotation(0);
        fabLayout1.animate().translationY(0);
        fabLayout1.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        fabLayout2.animate().translationY(0);
        fabLayout2.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        fabLayout3.animate().translationY(0);
        fabLayout3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    private void ViewDialogue() {
        dialog = new Dialog(ViewDetailsActivity.this, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_upload_image);
        dialog.setCanceledOnTouchOutside(true);


        final CircleImageView imageView = (CircleImageView) dialog.findViewById(R.id.imageView);
        final RelativeLayout rel_image = (RelativeLayout) dialog.findViewById(R.id.rel_image);

        rel_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ViewDetailsActivity.this);
                builder.setTitle("Select Image");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (options[i].equals("camera")) {
                            Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takepic, 0);
                        } else if (options[i].equals("Gallery")) {
                            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(gallery, 1);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });
        dialog.findViewById(R.id.btnCancelId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btnReopenId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getDrawable() != null) {
                    dialog.dismiss();
//                    Log.d("sdfss", "onClick: " + profile_image_name + "  " + profile_image_path);
//                    uploadFile(profile_image_path, profile_image_name);
                    uploadFile();

                } else {
                    Toast.makeText(ViewDetailsActivity.this, "please Select Image", Toast.LENGTH_SHORT).show();
                    Log.d("else", "onClick: " + profile_image_name + "  " + profile_image_path);

                }

            }


        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background));
        dialog.show();
    }

    private void Add_Business_varient() {
        dialog = new Dialog(ViewDetailsActivity.this, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_add_varient);
        dialog.setCanceledOnTouchOutside(true);


        dia_busi_name = dialog.findViewById(R.id.dia_busi_name);
        edt_busi_description = dialog.findViewById(R.id.edt_busi_description);
        edt_busi_rate = dialog.findViewById(R.id.edt_busi_rate);


        dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dia_busi_name.getText().toString().trim().equals("")) {
                    if (!edt_busi_description.getText().toString().trim().equals("")) {
                        addVarient();
                    } else {
                        Toast.makeText(ViewDetailsActivity.this, "Fill Required", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ViewDetailsActivity.this, "Fill Required", Toast.LENGTH_SHORT).show();

                }

            }


        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background));
        dialog.show();
    }

    private void addVarient() {
        Log.d("Business_ID", "addVarient: " + Business_ID);
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.add_varient_businss(Business_ID,
                dia_busi_name.getText().toString().trim(),
                edt_busi_description.getText().toString().trim(),
                edt_busi_rate.getText().toString().trim()

        );
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();

                    Log.d("Response", "varient=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        dialog.dismiss();
                        Toast.makeText(ViewDetailsActivity.this, reason, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ViewDetailsActivity.this, reason, Toast.LENGTH_SHORT).show();
                    }
                    mDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Retrofit Error:",t.getMessage());
                mDialog.dismiss();
            }
        });
    }


    private void Add_Business_Time() {
        dialog = new Dialog(ViewDetailsActivity.this, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_add_time_slot);
        dialog.setCanceledOnTouchOutside(true);


        dia_start_time = dialog.findViewById(R.id.dia_start_time);
        edt_end_time = dialog.findViewById(R.id.edt_end_time);

        dia_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        min = minute;

                        DateData.start_date = hour;


                        dia_start_time.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));
                        // Log.e("Response", "dia_start_time: " + dia_start_time);

                        str_inpStartDate = String.format(Locale.getDefault(), "%02d:%02d", hour, min);

                        Log.e("Replace", "str_inpStartDate: " + str_inpStartDate);


                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(ViewDetailsActivity.this, AlertDialog.THEME_HOLO_LIGHT, onTimeSetListener, hour, min, true);
                timePickerDialog.setTitle("Select Start Time");
                timePickerDialog.show();

            }
        });

        edt_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        min = minute;

                        DateData.end_date = hour;

                        edt_end_time.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));

                    }
                };

                TimePickerDialog timePickerDialog = new TimePickerDialog(ViewDetailsActivity.this, AlertDialog.THEME_HOLO_LIGHT, onTimeSetListener, hour, min, true);
                timePickerDialog.setTitle("Select End Time");
                timePickerDialog.show();
            }
        });


        dialog.findViewById(R.id.btnCanceltime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btnAddtime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dia_start_time.getText().toString().trim().equals("")) {
                    if (!edt_end_time.getText().toString().trim().equals("")) {
                        // CheckValidSlot();

                        try {

                            Log.e("Replace", "num_date: " + Replace(str_listEndDate));

                            if (Replace(str_inpStartDate) <= Replace(str_listEndDate)) {

                                Log.e("Replace", "Invalid");

                                Toast.makeText(getApplicationContext(), "Invalid Date", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("Replace", "Valid");
                                addBTime();
                            }


//                            //  endDate = edt_end_time.getText().toString().trim();
//
//                            Log.e("Response", "endData: " + endDate);
//
//
//                            int numeric_end_date = Integer.parseInt(str_lastEndDate);
//
//                            int int_input_end_date = Integer.parseInt(endDate);
//
//                            Replace(str_lastEndDate);
//
//
//
//                            Log.e("check", "numeric_end_date: " + numeric_end_date);
//                            Log.e("check", "int_input_end_date: " + int_input_end_date);
//
//                            if (int_input_end_date <= numeric_end_date) {
//                                Log.e("check", "You have selected Invalid Date!");
//
//                            } else {
//                                Log.e("check", "You can can add time slot!");
//                            }


                        } catch (Exception e) {

                            e.printStackTrace();

                        }


//                        addBTime();

//                       for(i=0; i<list.size();i++)
//                        {
//
//                            int num= Integer.parseInt(edt_end_time.getText().toString());
//
//                            if(num > Integer.parseInt(list.get(i)))
//                            {
//
//                            }
//                            else
//                            {
//                                Toast.makeText(ViewDetailsActivity.this, "You already Selected this time", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }

                    } else {
                        Toast.makeText(ViewDetailsActivity.this, "Fill Required", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ViewDetailsActivity.this, "Fill Required", Toast.LENGTH_SHORT).show();

                }

            }


        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background));
        dialog.show();
    }


    private void CheckValidSlot() {

        for (int i = DateData.start_date; i < DateData.end_date; i++) {

            DateData.listDate.add(i);
        }


    }


    private void addBTime() {
        Log.d("Business_ID", "addVarient: " + Business_ID);
        mDialog.show();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.add_business_time(Business_ID,
                dia_start_time.getText().toString().trim(),
                edt_end_time.getText().toString().trim()

        );
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDialog.dismiss();
                try {
                    String output = response.body().string();

                    Log.d("Response", "time=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        dialog.dismiss();
                        viewBusinessTime();
                        Toast.makeText(ViewDetailsActivity.this, reason, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ViewDetailsActivity.this, reason, Toast.LENGTH_SHORT).show();
                    }
                    mDialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Retrofit Error:",t.getMessage());
                mDialog.dismiss();
            }
        });
    }

    private void viewBusinessTime() {
        Log.e("Response", "Business_ID: " + Business_ID);
        Log.d("Business_ID", "addVarient: " + Business_ID);
        businessTimes.clear();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.View_business_time(Business_ID);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                businessTimes.clear();
                try {
                    String output = response.body().string();
                    Log.d("Response", "time_view=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            businessTimes.add(new BusinessTime(object));

                        }

                        Log.e("Response", "size of businessTime List is: " + businessTimes.size());
                        for (int i = 0; i < businessTimes.size(); i++) {
                            str_listEndDate = businessTimes.get(i).getFld_working_close_time();
                            Log.e("Response", "onResponse: str_lastEndDate: " + str_listEndDate);


                        }


//                        str_lastEndDate = businessTimes.get(businessTimes.size()).getFld_working_close_time();
//                        Log.e("Response", "str_lastEndDate: " + str_lastEndDate);


                        text_time.setVisibility(View.VISIBLE);
                        businessTimeAdapter = new BusinessTimeAdapter(ViewDetailsActivity.this, businessTimes, ViewDetailsActivity.this);
                        recycler_times.setAdapter(businessTimeAdapter);
                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                        recycler_times.setLayoutManager(layoutManager);

                        //Toast.makeText(ViewDetailsActivity.this, reason, Toast.LENGTH_SHORT).show();
                    } else {
                        text_time.setVisibility(View.GONE);
//                        Toast.makeText(ViewDetailsActivity.this, reason, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Retrofit Error:",t.getMessage());
            }
        });
    }


    private void loadBanner() {
        homeSliderModelList.add(Business_image);

        SliderLayout imgSliderLayout = (SliderLayout) findViewById(R.id.imageSlider);
        DefaultSliderView defaultSliderView = new DefaultSliderView(ViewDetailsActivity.this);

        defaultSliderView
                //.description(name)
                .image(homeSliderModelList.get(i))
                .setScaleType(BaseSliderView.ScaleType.Fit);
        defaultSliderView.bundle(new Bundle());
        defaultSliderView.getBundle().putString("extra", homeSliderModelList.get(i));

        imgSliderLayout.addSlider(defaultSliderView);
        imgSliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        //  imgSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imgSliderLayout.setCustomAnimation(new DescriptionAnimation());
        imgSliderLayout.setDuration(5000);
    }

    @Override
    public void onRefresh() {
        homeSliderModelList.clear();
        viewBusinessTime();
        ViewPhoto(Business_ID);

    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void ViewPhoto(String Id) {
        homeSliderModelList.clear();
        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.getphotos(Id);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                homeSliderModelList.clear();
                try {
                    String output = response.body().string();

                    Log.d("Response", "response=> " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    String reason = jsonObject.getString("ResponseMessage");

                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            homeSliderModelList.add(object.getString("business_photo"));
                            SliderLayout imgSliderLayout = (SliderLayout) findViewById(R.id.imageSlider);
                            DefaultSliderView defaultSliderView = new DefaultSliderView(ViewDetailsActivity.this);

                            defaultSliderView
                                    //.description(name)
                                    .image(homeSliderModelList.get(i))
                                    .setScaleType(BaseSliderView.ScaleType.Fit);
                            defaultSliderView.bundle(new Bundle());
                            defaultSliderView.getBundle().putString("extra", homeSliderModelList.get(i));

                            imgSliderLayout.addSlider(defaultSliderView);
                            imgSliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
                            imgSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                            imgSliderLayout.setCustomAnimation(new DescriptionAnimation());
                            imgSliderLayout.setDuration(5000);

                        }
                        SwipeRefresh.setRefreshing(false);
                    } else {
                        SwipeRefresh.setRefreshing(false);
                        loadBanner();
                        homeSliderModelList.clear();
                        //Toast.makeText(ViewDetailsActivity.this, reason, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Log.d("Retrofit Error:",t.getMessage());
                SwipeRefresh.setRefreshing(false);
            }
        });
    }

    public void processFinish(String result, int img_no) {
        String[] parts = result.split("/");
        String imagename = parts[parts.length - 1];
        // Log.d("Image_path", result + " " + img_no);
        profile_image_name = imagename;
        profile_image_path = result;

    }

    @Override
    public void onPermissionsGranted(boolean result) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap image = (Bitmap) data.getExtras().get("data");
                        filepath = com.example.karadvenderapp.MyLib.FileUtils.getPath(ViewDetailsActivity.this, getImageUri(ViewDetailsActivity.this, image));

                        CircleImageView img = dialog.findViewById(R.id.imageView);
                        img.setImageBitmap(image);

                    }
                    break;

                case 1: {
                    if (resultCode == RESULT_OK && data != null) {
                        uri = data.getData();
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(ViewDetailsActivity.this.getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        filepath = com.example.karadvenderapp.MyLib.FileUtils.getPath(ViewDetailsActivity.this, getImageUri(ViewDetailsActivity.this, bitmap));
                        Picasso.with(ViewDetailsActivity.this).load(uri).into((CircleImageView) dialog.findViewById(R.id.imageView));

                    }
                }
            }

        }

    }

    private void uploadFile() {
        RequestBody data_id = RequestBody.create(MediaType.parse("text/plain"), Business_ID);
        imageFile = new File(filepath);

        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);

        MultipartBody.Part business_photo = MultipartBody.Part.createFormData("business_photo", imageFile.getName(), reqBody);

        APIInterface apiInterface = MyConfig.getRetrofit().create(APIInterface.class);
        Call<ResponseBody> result = apiInterface.upload(data_id, business_photo);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Log.v("Upload", "success");
                String output = "";
                try {
                    output = response.body().string();

                    Log.d("Response", "response23 " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    Log.d("Result_New", output);
                    if (jsonObject.getString("ResponseCode").equals("1")) {
                        dialog.dismiss();
                        Toast.makeText(ViewDetailsActivity.this, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ViewDetailsActivity.this, "" + jsonObject.getString("ResponseMessage"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Upload_error:", "");
            }
        });

    }

    public Uri getImageUri(Context context, Bitmap bitmap) {
        String profile = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Img" + Time + Date, "");

        return Uri.parse(profile);
    }


    @Override
    public void onItemClick(boolean flag) {
        if (flag) {
            viewBusinessTime();
        }

    }


    private double Replace(String oldStr) {
        double afterConvert = 0.0;

        NumberFormat f = NumberFormat.getInstance();
        String newString = oldStr.replace(":", ".");

        Log.e("Replace", "New String is: " + newString);


        try {
//            afterConvert = f.parse(newString).doubleValue();
            afterConvert = Double.parseDouble(newString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return afterConvert;


    }
}
