package com.example.mayarfareed.sunshineapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link forecastFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link forecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class forecastFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

//    private ArrayAdapter<String> forecastAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast,
//            R.id.list_item_forecast_textView, new ArrayList<String>());

    ListView listView;
    ProgressBar progressbar;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public forecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        updateWeather();
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.action_refresh){
            updateWeather();
            return true;
        }
        else if (item.getItemId() == R.id.action_settings){
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.action_map){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri= Uri.parse("geo:0,0?z=10&q="+
                    PreferenceManager.getDefaultSharedPreferences(getContext())
                            .getString(getString(R.string.prefrences_location_key),
                                    getString(R.string.prefrences_location_default)));
            intent.setData(uri);
//            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
//            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateWeather() {
        FetchWeatherTask weatherTask = new FetchWeatherTask();
//            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("myPrefFile", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String location = sharedPreferences.getString(getString(R.string.prefrences_location_key), getString(R.string.prefrences_location_default));
        String unit = sharedPreferences.getString(getString(R.string.prefrences_units_key), "no units sharedPreference  "+getString(R.string.prefrences_units_key));
//            Log.d("forecastFragment", "onOptionsItemSelected location value: "+ location);
//        Log.d("forecastFragment", "onOptionsItemSelected units value: "+ unit);
        weatherTask.execute(location, "json", unit);  //metric or imperial
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment forecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static forecastFragment newInstance(String param1, String param2) {
        forecastFragment fragment = new forecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
//

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rooView= inflater.inflate(R.layout.fragment_forecast_list, container, false);
        progressbar = (ProgressBar) rooView.findViewById(R.id.progressBar);
        listView = (ListView) rooView.findViewById(R.id.listView_forecast);
        progressbar.setVisibility(View.GONE);
        return rooView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




    public class FetchWeatherTask extends AsyncTask<String, Void, String[]>{

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected void onPostExecute(String[] strings) {
            ArrayAdapter forecastAdapter;
            List<String> weekForecast = new ArrayList<>(Arrays.asList(forecasts));
            forecastAdapter= new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast,
                    R.id.list_item_forecast_textView, weekForecast);
            listView.setAdapter(forecastAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String data = (String) adapterView.getItemAtPosition(i);
                    Toast.makeText(getContext(), data, Toast.LENGTH_LONG).show();
                    Snackbar.make(view, data, Snackbar.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), DetailsActivity.class);
                    intent.putExtra("data", data);
                    startActivity(intent);
                }
            });

            progressbar.setVisibility(View.GONE);
            super.onPostExecute(strings);
        }

        @Override
        protected void onPreExecute() {
            progressbar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }


        /* The date/time conversion code is going to be moved outside the asynctask later,
         * so for convenience we're breaking it out into its own method now.
         */
        private String getReadableDateString(long time){
            // Because the API returns a unix timestamp (measured in seconds),
            // it must be converted to milliseconds in order to be converted to valid date.
            SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
            return shortenedDateFormat.format(time);
        }

        /**
         * Prepare the weather high/lows for presentation.
         */
        private String formatHighLows(double high, double low) {
            // For presentation, assume the user doesn't care about tenths of a degree.
            long roundedHigh = Math.round(high);
            long roundedLow = Math.round(low);

            String highLowStr = roundedHigh + "/" + roundedLow;
            return highLowStr;
        }


        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_LIST = "list";
            final String OWM_WEATHER = "weather";
            final String OWM_TEMPERATURE = "temp";
            final String OWM_MAX = "max";
            final String OWM_MIN = "min";
            final String OWM_DESCRIPTION = "main";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

            // OWM returns daily forecasts based upon the local time of the city that is being
            // asked for, which means that we need to know the GMT offset to translate this data
            // properly.

            // Since this data is also sent in-order and the first day is always the
            // current day, we're going to take advantage of that to get a nice
            // normalized UTC date for all of our weather.

            Time dayTime = new Time();
            dayTime.setToNow();

            // we start at the day returned by local time. Otherwise this is a mess.
            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

            // now we work exclusively in UTC
            dayTime = new Time();

            String[] resultStrs = new String[numDays];
            for(int i = 0; i < weatherArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String day;
                String description;
                String highAndLow;

                // Get the JSON object representing the day
                JSONObject dayForecast = weatherArray.getJSONObject(i);

                // The date/time is returned as a long.  We need to convert that
                // into something human-readable, since most people won't read "1400356800" as
                // "this saturday".
                long dateTime;
                // Cheating to convert this to UTC time, which is what we want anyhow
                dateTime = dayTime.setJulianDay(julianStartDay+i);
                day = getReadableDateString(dateTime);

                // description is in a child array called "weather", which is 1 element long.
                JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
                description = weatherObject.getString(OWM_DESCRIPTION);

                // Temperatures are in a child object called "temp".  Try not to name variables
                // "temp" when working with temperature.  It confuses everybody.
                JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
                double high = temperatureObject.getDouble(OWM_MAX);
                double low = temperatureObject.getDouble(OWM_MIN);

                highAndLow = formatHighLows(high, low);
                resultStrs[i] = day + " - " + description + " - " + highAndLow;
            }

            return resultStrs;

        }

        private String[] forecasts = new String[0];



        @Override
        protected String[] doInBackground(String... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                final String FORECAST_BASE_URL =
                        "http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String QUERY_PARAM = "q";
                final String FORMAT_PARAM = "mode";
                final String UNITS_PARAM = "units";
                final String DAYS_PARAM = "cnt";
                final String APPID_PARAM = "appid";
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, String.valueOf(params[0]))
                        .appendQueryParameter(FORMAT_PARAM, String.valueOf(params[1]))
                        .appendQueryParameter(UNITS_PARAM, String.valueOf(params[2]))
                        .appendQueryParameter(DAYS_PARAM, Integer.toString(7))
                        .appendQueryParameter(APPID_PARAM, "8f6dc70888d407b66d8e34e5cfd4753b")
                        .build();
                URL url = new URL(builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    Snackbar.make(getView(), "Connect to network", Snackbar.LENGTH_LONG).show();
                    return null;
                }
                forecastJsonStr = buffer.toString();
                forecasts = getWeatherDataFromJson(forecastJsonStr, 7);
            } catch (IOException e) {

                Snackbar.make(getView(), "Connect to network", Snackbar.LENGTH_LONG).show();
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            return forecasts;
        }
    }
}
