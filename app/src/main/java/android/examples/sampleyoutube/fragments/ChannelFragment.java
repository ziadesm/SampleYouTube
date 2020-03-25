package android.examples.sampleyoutube.fragments;

import android.content.Intent;
import android.examples.sampleyoutube.VideoPlayerActivity;
import android.examples.sampleyoutube.adapter.VideoPostAdapter;
import android.examples.sampleyoutube.interfaces.OnItemClickListener;
import android.examples.sampleyoutube.model.YouTubeDataModel;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.examples.sampleyoutube.R;
import android.widget.AdapterView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelFragment extends Fragment {
    private static String API_KEY = "AIzaSyArVRpq8XfgbKDUjlN0KpmrzMt9sE3Ar0s";
    private static String CHANNEL_ID = "UCoMdktPbSTixAyNGwb-UYkQ";
    private static String CHANNEL_GET_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&channelId="
            + CHANNEL_ID + "&maxResults=20&key=" + API_KEY + "";

    private RecyclerView mRecyclerView;
    private VideoPostAdapter postAdapter;
    private ArrayList<YouTubeDataModel> mListData = new ArrayList<>();


    public ChannelFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_channel, container, false);
        mRecyclerView = v.findViewById(R.id.recycler_list_videos);
        installDataToRecyclerView(mListData);
        new RequestYoutubeAPI().execute();
        return v;
    }

    private void installDataToRecyclerView(ArrayList<YouTubeDataModel> mListData) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        postAdapter = new VideoPostAdapter(getActivity(), mListData, new OnItemClickListener() {
            @Override
            public void onItemClick(YouTubeDataModel item) {
                YouTubeDataModel model = item;
                Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
                intent.putExtra(YouTubeDataModel.class.toString(), model);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(postAdapter);
    }

    private class RequestYoutubeAPI extends AsyncTask<Void, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    mListData = parseVideoListFromResponse(jsonObject);
                    installDataToRecyclerView(mListData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(CHANNEL_GET_URL);
            try {
                HttpResponse response = client.execute(httpGet);
                HttpEntity entity = response.getEntity();
                String json = EntityUtils.toString(entity);
                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private ArrayList<YouTubeDataModel> parseVideoListFromResponse(JSONObject jsonObject) {
        ArrayList<YouTubeDataModel> mList = new ArrayList<>();
        if (jsonObject.has("items")) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    if (json.has("id")) {
                        JSONObject id = json.getJSONObject("id");
                        String idString = "";
                        if (id.has("videoId")) {
                            idString = id.getString("videoId");
                        }
                        if (id.has("kind")) {
                            if (id.getString("kind").equals("youtube#video")) {
                                JSONObject jsonSnippet = json.getJSONObject("snippet");
                                String title = jsonSnippet.getString("title");
                                String description = jsonSnippet.getString("description");
                                String thumbnail = jsonSnippet.getJSONObject("thumbnails").getJSONObject("high").getString("url");

                                YouTubeDataModel model = new YouTubeDataModel();
                                model.setTitle(title);
                                model.setDescription(description);
                                model.setThumbanail(thumbnail);
                                model.setVideoId(idString);
                                mList.add(model);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mList;
    }
}
