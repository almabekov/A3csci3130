package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class DetailViewActivity extends Activity {

    private EditText nameField, businessNumberField, primaryBusinessField, addressField, provinceField;
    private Button updateButton, deleteButton;
    private MyApplicationData appState;
    private Context context;
    Contact receivedPersonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        appState = ((MyApplicationData) getApplicationContext());
        receivedPersonInfo = (Contact)getIntent().getSerializableExtra("Contact");

        updateButton = (Button) findViewById(R.id.updateButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        nameField = (EditText) findViewById(R.id.name);
        businessNumberField= (EditText) findViewById(R.id.businessNumber);
        primaryBusinessField = (EditText) findViewById(R.id.primaryBusiness);
        addressField = (EditText) findViewById(R.id.address);
        provinceField = (EditText) findViewById(R.id.province);

        context = this;
        if(receivedPersonInfo != null){
            nameField.setText(receivedPersonInfo.name);
            businessNumberField.setText(receivedPersonInfo.businessNumber);
            primaryBusinessField.setText(receivedPersonInfo.primaryBusiness);
            addressField.setText(receivedPersonInfo.address);
            provinceField.setText(receivedPersonInfo.province);

        }
    }

    /**
     * Method is called when the "Update contact" button is clicked
     * @param v can be used if we move method definition to the separate class
     */
    public void updateContact(View v){
        String name = nameField.getText().toString();
        String businessNumber = businessNumberField.getText().toString();
        String primaryBusiness = primaryBusinessField.getText().toString();
        String address = addressField.getText().toString();
        String province = provinceField.getText().toString();
        Contact person = new Contact(receivedPersonInfo.uid, businessNumber, name, primaryBusiness, address, province);
        appState.firebaseReference.child(receivedPersonInfo.uid).setValue(person).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Intent intent=new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Method is callsed when the "Erase contact" button is clicked
     * @param v can be used if we move method definition to the separate class
     */
    public void eraseContact(View v)
    {
        appState.firebaseReference.child(receivedPersonInfo.uid).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Intent intent=new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
