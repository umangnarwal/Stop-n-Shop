package com.project.umang.googlesignin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;

public class Details extends AppCompatActivity {
    int id;
    String title;
    String description;
    int price;
    String image;
    TextInputLayout quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView imageView = (ImageView)findViewById(R.id.imageView1);
        TextView title = (TextView)findViewById(R.id.title1);
        TextView description = (TextView)findViewById(R.id.description1);
        TextView price= (TextView)findViewById(R.id.price1);

        Picasso.with(this).load(getIntent().getStringExtra("image")).into(imageView);
        title.setText(getIntent().getStringExtra("title"));
        description.setText(getIntent().getStringExtra("description"));
        price.setText(String.valueOf(getIntent().getIntExtra(("price"),0)));

        quantity = (TextInputLayout) findViewById(R.id.quan);

        AppCompatButton cart = (AppCompatButton)findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                final String usermail =  String.valueOf(preferences.getString("email",null));

                final int id = getIntent().getIntExtra("id",0);
                final String qu = quantity.getEditText().getText().toString();

                class AddEmployee extends AsyncTask<Void,Void,String> {

                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(Details.this,"Adding...","Wait...",false,false);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(Details.this, s, Toast.LENGTH_LONG).show();
                    }
                    @Override
                    protected String doInBackground(Void... v) {
                        HashMap<String,String> params = new HashMap<>();
                        //Adding parameters to request
                        params.put("id",String.valueOf(id));
                        params.put("count",qu);
                        params.put("usermail",usermail);
                        RequestHandler rh = new RequestHandler();
                        String res = rh.sendPostRequest(Config.CART, params);
                        return res;

                    }
                }

                AddEmployee ae = new AddEmployee();
                ae.execute();
            }
        });


        AppCompatButton favourites = (AppCompatButton)findViewById(R.id.favourites);
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int id = getIntent().getIntExtra("id",0);

                class AddEmployee extends AsyncTask<Void,Void,String> {

                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(Details.this,"Adding...","Wait...",false,false);
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        loading.dismiss();
                        Toast.makeText(Details.this, s, Toast.LENGTH_LONG).show();
                    }
                    @Override
                    protected String doInBackground(Void... v) {
                        HashMap<String,String> params = new HashMap<>();
                        //Adding parameters to request
                        params.put("id",String.valueOf(id));

                        RequestHandler rh = new RequestHandler();
                        String res = rh.sendPostRequest(Config.FAVOURITES, params);
                        return res;

                    }
                }

                AddEmployee ae = new AddEmployee();
                ae.execute();
            }
        });
    }

    }

