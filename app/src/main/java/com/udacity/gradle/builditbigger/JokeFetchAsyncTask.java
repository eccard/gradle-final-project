package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.example.migration.endpoints.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
//import com.udacity.gradle.builditbigger.backend.jokerApi.JokerApi;

import java.io.IOException;


public class JokeFetchAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

//    JokerEndPoint jokerEndPoint;

    private static final String HOST_IP = "http://10.0.2.2:8080";
    private static final String SERVER_URL = HOST_IP + "/_ah/api/";
    private static MyApi myApiService = null;

    private Context context;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {


        if ( myApiService == null) {

            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null);

            builder.setRootUrl(SERVER_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();

        }

        context = params[0].first;
        String name = params[0].second;

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
