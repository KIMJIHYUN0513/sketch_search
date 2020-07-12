package com.google.sample.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class TranslationActivity extends AppCompatActivity {

    Trans translation = new Trans();

    public static Context mContext;
    public static String koreaString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);

        translation.execute();

        mContext = this;

        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
    }


    public class Trans extends AsyncTask<Void, Void, Void> {

        private String detectionResult = ((CloudVisionActivity) CloudVisionActivity.mContext).detectionResult;
        private String[] array = detectionResult.split(":");
        private String englishString = array[4].substring(0,array[4].length()-6);

        private final static String URL = "https://www.googleapis.com/language/translate/v2?key=";
        //https://translation.googleapis.com/language/translate/v2
        private final static String KEY = "AIzaSyDS7-BsfGJqI0GcZEwBku0Zl0knoxAuN7s";
        private final static String TARGET = "&target=ko";
        private final static String SOURCE = "&source=en";
        private final static String query = "&q=";

        @Override
        protected Void doInBackground(Void... params) {
            StringBuilder result = new StringBuilder();

            try {
                String encodedText = URLEncoder.encode(englishString, "UTF-8");
                java.net.URL url = new URL(URL + KEY + SOURCE + TARGET + query + encodedText);

                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                InputStream stream;
                if (conn.getResponseCode() == 200) {
                    stream = conn.getInputStream();
                } else {
                    stream = conn.getErrorStream();
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                //result는 json 데이터
                JsonParser parser = new JsonParser();

                JsonElement element = parser.parse(result.toString());

                if (element.isJsonObject()) {
                    JsonObject obj = element.getAsJsonObject();
                    if (obj.get("error") == null) {
                        //Json data를 파싱하여 "translations" 하위 데이터 삽입
                        koreaString = obj.get("data").getAsJsonObject().get("translations").
                                getAsJsonArray().get(0).getAsJsonObject().get("translatedText").
                                getAsString();
                    }
                }

                if (conn.getResponseCode() != 200) {
                    Log.e("GoogleTranslatorTask", result.toString());
                }

            } catch (IOException | JsonSyntaxException ex) {
                Log.e("GoogleTranslatorTask", ex.getMessage());
            }

            return null;
        }

    }

}

