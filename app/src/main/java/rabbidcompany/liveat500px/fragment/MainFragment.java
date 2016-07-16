package rabbidcompany.liveat500px.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import rabbidcompany.liveat500px.R;
import rabbidcompany.liveat500px.activity.MainActivity;
import rabbidcompany.liveat500px.adapter.PhotoListAdapter;
import rabbidcompany.liveat500px.dao.PhotoItemCollectionDao;
import rabbidcompany.liveat500px.manager.PhotoListManager;
import rabbidcompany.liveat500px.manager.http.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {

    ListView listView01; //This is similar to ScrollView, but it saves memory.
    //GridView gridView01;
    PhotoListAdapter listAdapter01; //This is an adapter.

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        listView01 = (ListView) rootView.findViewById(R.id.ListViewID01);
        listAdapter01 = new PhotoListAdapter();
        listView01.setAdapter(listAdapter01); //Bind the view and adapter together.

        /*
        gridView01 = (GridView) rootView.findViewById(R.id.GridViewID01);
        listAdapter01 = new PhotoListAdapter();
        gridView01.setAdapter(listAdapter01);*/

        //Call a service singleton object
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getService().loadPhotoList();

        //This synchronous method is not allowed. It uses the main thread.
        //It waits or blocks further executions. It will throw to NetworkonMainThrowException.
        //call.execute()

        //Use an asynchronous one instead. This won't block anything.
        // However, it must not be used with an Activity.
        call.enqueue(new Callback<PhotoItemCollectionDao>() {
            //Load data here
            @Override
            public void onResponse(Call<PhotoItemCollectionDao> call,
                                   Response<PhotoItemCollectionDao> response) {
                if (response.isSuccessful()) {
                    PhotoItemCollectionDao dao = response.body();

                    //Store data into a singleton PhotoListManager.
                    PhotoListManager.getInstance().setDao(dao);

                    //Refresh the list view.
                    listAdapter01.notifyDataSetChanged();

                    //Toast.makeText(getActivity(),
                    Toast.makeText(Contextor.getInstance().getContext(),
                            dao.getData().get(0).getCaption(),
                            Toast.LENGTH_LONG)
                            .show();
                } else {
                    try {
                        Toast.makeText(Contextor.getInstance().getContext(),
                                response.errorBody().string(),
                                Toast.LENGTH_LONG)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            //This maybe the case of server failure.
            @Override
            public void onFailure(Call<PhotoItemCollectionDao> call,
                                  Throwable t) {
               Toast.makeText(Contextor.getInstance().getContext(),
                        t.toString(),
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
}
