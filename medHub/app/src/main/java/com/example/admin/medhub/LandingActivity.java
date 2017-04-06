package com.example.admin.medhub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by Admin on 28-03-2017.
 */

public class LandingActivity extends Activity implements View.OnClickListener{

    Button customer, pharmacist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        customer= (Button) findViewById(R.id.btn_customer);
        pharmacist= (Button) findViewById(R.id.btn_pharmacist);

        customer.setOnClickListener(this);
        pharmacist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==customer){
            Intent intent= new Intent(this, LoginActivityUser.class);
            startActivity(intent);
        }
        if(v==pharmacist){
            Intent intent= new Intent(this, LoginActivityPharm.class);
            startActivity(intent);
        }
    }
}
