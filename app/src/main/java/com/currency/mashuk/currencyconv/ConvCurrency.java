package com.currency.mashuk.currencyconv;

import android.app.Activity;
import android.app.IntentService;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.sql.Connection;
import java.text.DecimalFormat;


public class ConvCurrency extends AppCompatActivity {

    WebView mywWebView = null;
    private EditText USDeditText  = null;
    private EditText BDTeditText  = null;

    double usd = 0, bdt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("clicked","on create called");
        setContentView(R.layout.activity_conv_currency);

        mywWebView = (WebView)findViewById(R.id.webView);

        //force running network operation on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //this.USDeditText.clearFocus();
        //this.BDTeditText.clearFocus();


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Clicked", "on resume called");
        this.USDeditText = (EditText) this.findViewById((R.id.USDeditText));
        this.BDTeditText = (EditText)this.findViewById(R.id.BDTeditText);
        //Log.d("fsdfsd",s);
        Log.d("Clicked","called2.5");

        ExtractCurrentValue();



    }

    public void ExtractCurrentValue(){


        try {
            mywWebView.loadUrl("http://hrhafiz.com/converter/");
            URL url = new URL("http://hrhafiz.com/converter/");

            // read text returned by server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            Log.d("ami","called9.7");

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            //Log.d("hr",sb.toString());

            String USDtoBDT = sb.toString().substring(0, sb.toString().indexOf(','));
            USDtoBDT = USDtoBDT.replaceAll("[^\\d.]", "");
            Log.d("hr",USDtoBDT);

            usd = Double.parseDouble(USDtoBDT);
            bdt = 1/usd;

            bdt = Double.valueOf(new DecimalFormat("#.###").format(bdt));

            Log.d("usd",String.valueOf(usd));
            Log.d("bdt",String.valueOf(bdt));

            //BDTeditText.setText("0");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void conversion(View v) {
        if(USDeditText.hasFocus()) {

            if(USDeditText.length() == 0){
                BDTeditText.setText(String.valueOf(""));

            }
            else {
                BDTeditText.setText(String.valueOf(Double.parseDouble(USDeditText.getText().toString()) * usd));

            }
        }

        else if(BDTeditText.hasFocus()) {
            if(BDTeditText.length() == 0){
                USDeditText.setText(String.valueOf(""));

            }
            else{
                USDeditText.setText(String.valueOf(Double.parseDouble(BDTeditText.getText().toString()) * bdt));

            }


        }
    }

    public void Clear(){
        USDeditText.setText(String.valueOf(""));
        BDTeditText.setText(String.valueOf(""));
    }

    public void CurrentData(View v) {
        ExtractCurrentValue();
        Clear();
    }
}
