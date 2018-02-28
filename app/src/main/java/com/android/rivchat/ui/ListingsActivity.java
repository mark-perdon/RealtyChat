package com.android.rivchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.android.rivchat.data.StaticConfig;
import com.android.rivchat.model.Message;
import com.google.firebase.database.FirebaseDatabase;
import com.myrealtymlplite.androiddatabase.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mark on 2/20/2018.
 */

public class ListingsActivity extends Activity implements View.OnClickListener{
    private String roomId;
    private static final String TAG = "ListingsActivity";
    private EditText editText_title, et_location, et_desc, et_email, et_phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_listings);
        Button btnSend = (Button) findViewById(R.id.btnSendListing);
        editText_title = (EditText) findViewById(R.id.editText_title);
        et_location = (EditText) findViewById(R.id.et_location);
        et_desc = (EditText) findViewById(R.id.et_desc);
        et_email = (EditText) findViewById(R.id.et_email);
        et_phone = (EditText) findViewById(R.id.et_phone);

        Intent intentData = getIntent();
        btnSend.setOnClickListener(this);
        Log.e(TAG, "initialize ListingsAtivity");
        roomId = intentData.getStringExtra(StaticConfig.INTENT_KEY_CHAT_ROOM_ID);
    }
    public void onRButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        CheckBox chForRent, chForSale, chJointVenture, chWantRent, chWantBuy, chWantJointVenture;
        LinearLayout linearLayoutAvail, linearLayoutWanted;
        Animation fadein;
        Animation fadeout;
        RadioButton rbBrandNew, rbPreOwned, rbForeClosed;
        chForRent = (CheckBox) findViewById(R.id.chForRent);
        chForSale = (CheckBox) findViewById(R.id.chForSale);
        chJointVenture = (CheckBox) findViewById(R.id.chJointVenture);
        chWantRent = (CheckBox) findViewById(R.id.chWantRent);
        chWantBuy = (CheckBox) findViewById(R.id.chWantBuy);
        chWantJointVenture = (CheckBox) findViewById(R.id.chWantJointVenture);
        linearLayoutAvail = (LinearLayout) findViewById(R.id.linearLayoutAvail);
        linearLayoutWanted = (LinearLayout) findViewById(R.id.linearLayoutWanted);
        fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        fadeout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeout);
        rbBrandNew = (RadioButton) findViewById(R.id.rbBrandNew);
        rbPreOwned = (RadioButton) findViewById(R.id.rbPreOwned);
        rbForeClosed = (RadioButton) findViewById(R.id.rbForeClosed);

        switch (view.getId()) {
            case R.id.rbAvailable:
                if (checked) {
                    chWantRent.setChecked(false);
                    chWantBuy.setChecked(false);
                    chWantJointVenture.setChecked(false);
                    chForRent.setChecked(true);
                    chForSale.setChecked(false);
                    chJointVenture.setChecked(false);
                    linearLayoutAvail.startAnimation(fadein);
                    linearLayoutAvail.setVisibility(View.VISIBLE);
                    linearLayoutWanted.startAnimation(fadeout);
                    linearLayoutWanted.setVisibility(View.GONE);
                }
                break;
            case R.id.rbWanted:
                if (checked) {
                    chForRent.setChecked(false);
                    chForSale.setChecked(false);
                    chJointVenture.setChecked(false);
                    chWantRent.setChecked(true);
                    chWantBuy.setChecked(false);
                    chWantJointVenture.setChecked(false);
                    linearLayoutWanted.startAnimation(fadein);
                    linearLayoutWanted.setVisibility(View.VISIBLE);
                    linearLayoutAvail.startAnimation(fadeout);
                    linearLayoutAvail.setVisibility(View.GONE);
                }
                break;
            case R.id.rbBrandNew:
                if(checked){
//                    selected_condition_type = rbBrandNew.getText().toString();
                }
                break;
            case R.id.rbPreOwned:
                if(checked){
//                    selected_condition_type = rbPreOwned.getText().toString();
                }
                break;
            case R.id.rbForeClosed:
                if(checked){
//                    selected_condition_type = rbForeClosed.getText().toString();
                }
                break;

        }
    }

    @Override
    public void onClick(View view) {
        Log.e(TAG, "SEND");
        JSONObject obj = new JSONObject();
        try {
            obj.put("title", editText_title.getText());
            obj.put("location", et_location.getText());
            obj.put("desc", et_desc.getText());
            obj.put("email", et_email.getText());
            obj.put("phone", et_phone.getText());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(view.getId() == R.id.btnSendListing){
            String content = obj.toString();
            Log.e(TAG, content);

            if (content.length() > 0) {
                Message newMessage = new Message();
                newMessage.text = content;
                newMessage.idSender = StaticConfig.UID;
                newMessage.idReceiver = roomId;
                newMessage.timestamp = System.currentTimeMillis();
                FirebaseDatabase.getInstance().getReference().child("message/" + roomId).push().setValue(newMessage);
                finish();
            }
        }
    }
}

