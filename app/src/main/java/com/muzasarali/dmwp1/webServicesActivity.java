package com.muzasarali.dmwp1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class webServicesActivity extends AppCompatActivity {

    EditText mClass,mEmail,mLastName,mFirstName,mRequest;
    Button btnSubmit,btnRequest;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_services);
        builder = new AlertDialog.Builder(this);
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url ="https://5f4801ca3f6a.ngrok.io/students";


        mClass = findViewById(R.id.etClass);
        mEmail = findViewById(R.id.etEmail);
        mLastName = findViewById(R.id.etlName);
        mFirstName = findViewById(R.id.etfName);
        mRequest   = findViewById(R.id.etReq);
        btnSubmit   = findViewById(R.id.btnSumit);
        btnRequest  = findViewById(R.id.btnRequest);


        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String req_url = url + "/" + mRequest.getText();
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, req_url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("TAGG", response.toString());
                                try {
                                    JSONArray data = response.getJSONArray("data");
                                    if (data.length() == 0){
                                        Toast.makeText(getApplicationContext(), "ID Doesn't Exist", Toast.LENGTH_SHORT).show();
                                    } else {
                                        JSONObject obj = data.getJSONObject(0);
                                        Toast.makeText(getApplicationContext(), ""+obj.getString("FirstName"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                queue.add(jsonObjReq);
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject postparams = new JSONObject();
                try {
                    postparams.put("FirstName", mFirstName.getText());
                    postparams.put("LastName", mLastName.getText());
                    postparams.put("Email", mEmail.getText());
                    postparams.put("Class", mClass.getText());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, postparams,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                                showAlret(""+response.toString(),"Data Received");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });

                queue.add(jsonObjReq);

            }
        });


    }
    public void showAlret(String message, String title){
        builder.setMessage("") .setTitle(title);

        //Setting message manually and performing action on button click
        builder.setMessage(""+message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("AlertDialogExample");
        alert.show();
    }


}