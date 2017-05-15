package com.example.developers.firebasetryout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText name, passwod;
    Button btn;
    String USERNAME, PASSWORD, DEVICEID;
    String url = "http://www.terapanthmmbg.org/json_login.php";
    String id;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        name = (EditText) findViewById(R.id.name);
        passwod = (EditText) findViewById(R.id.passwd);
        btn = (Button) findViewById(R.id.submit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                USERNAME = name.getText().toString();
                PASSWORD = passwod.getText().toString();

                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "Token: " + token);
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                //  makeJsonLoginRequest();
            }
        });

    }

    private void makeJsonLoginRequest() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Creating JsonObject from response String
                            JSONObject BaseObject = new JSONObject(response);
                            JSONArray abc = BaseObject.getJSONArray("PersonalProfile");


                            for (int i = 0; i < abc.length(); i++) {
                                JSONObject c = abc.getJSONObject(i);


                                Intent j = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(j);
                                Toast.makeText(getApplicationContext(), "Login Successful..Welcome", Toast.LENGTH_SHORT).show();
                                finish();

                            }


                        } catch (JSONException e) {

                        }
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("UserName", USERNAME);
                params.put("Pass", PASSWORD);
                params.put("gcmKey", DEVICEID);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


        Log.d("response", stringRequest.toString());
    }


}
