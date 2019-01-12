package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.migration.endpoints.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.util.SimpleIdlingResource;

import java.io.IOException;


public class JokeFetchAsyncTask extends AsyncTask<Void, Void, String> {


    private static final String HOST_IP = "http://10.0.2.2:8080";
    private static final String SERVER_URL = HOST_IP + "/_ah/api/";
    private static MyApi myApiService = null;

    private SimpleIdlingResource mIdlingResource;

    interface JockerListener{
        void onJokeFetching();
        void onJokerFetched(String joke);
    }

    public JokeFetchAsyncTask(JockerListener listener) {
        this.listener = listener;
    }

    private JockerListener listener;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        getIdlingResource().setIdleState(false);
        listener.onJokeFetching();
    }

    @Override
    protected String doInBackground(Void... params) {


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

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        listener.onJokerFetched(s);
        getIdlingResource().setIdleState(true);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        releaseResources();
    }

    private void releaseResources() {
        Log.d(JokeFetchAsyncTask.class.getSimpleName(), "Listener " + listener.getClass().getSimpleName() +" will be freed");
        listener = null;
        Log.d(JokeFetchAsyncTask.class.getSimpleName(), "Listener was freed");
        mIdlingResource.setIdleState(true);
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @NonNull
    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    public SimpleIdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
