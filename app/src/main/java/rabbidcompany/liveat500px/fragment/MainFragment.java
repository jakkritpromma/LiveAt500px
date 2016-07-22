package rabbidcompany.liveat500px.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.io.IOException;

import rabbidcompany.liveat500px.R;
import rabbidcompany.liveat500px.activity.MainActivity;
import rabbidcompany.liveat500px.adapter.PhotoListAdapter;
import rabbidcompany.liveat500px.dao.PhotoItemCollectionDao;
import rabbidcompany.liveat500px.datatype.MutableInteger;
import rabbidcompany.liveat500px.manager.PhotoListManager;
import rabbidcompany.liveat500px.manager.http.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {

    /*************************************************************
     * Variables
     ***********************************************************/
    ListView listView01; //This is similar to ScrollView, but it saves memory.
    //GridView gridView01;
    PhotoListAdapter listAdapter01; //This is an adapter.
    Button buttonNewPhotos01;
    SwipeRefreshLayout swipeRefreshLayout01;
    PhotoListManager photoListManager;
    boolean isLoadingMore = false;
    MutableInteger lastPositionInteger;

    /*************************************************************
     * Functions
     ***********************************************************/
    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //This contains variable at Fragment level.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
    }

    //This contains variables at View level without any state.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        photoListManager = new PhotoListManager();
        lastPositionInteger = new MutableInteger(-1);
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        buttonNewPhotos01 = (Button) rootView.findViewById(R.id.ButtonNewPhotosID01);
        buttonNewPhotos01.setOnClickListener(buttonClickListener);

        // Init 'View' instance(s) with rootView.findViewById here
        listView01 = (ListView) rootView.findViewById(R.id.ListViewID01);
        listAdapter01 = new PhotoListAdapter(lastPositionInteger);
        listAdapter01.setDao(photoListManager.getDao()); //Set dao for this listAdapter01
        listView01.setAdapter(listAdapter01); //Bind the view and adapter together.
         /*
        gridView01 = (GridView) rootView.findViewById(R.id.GridViewID01);
        listAdapter01 = new PhotoListAdapter();
        gridView01.setAdapter(listAdapter01);*/

        swipeRefreshLayout01 = (SwipeRefreshLayout) rootView.findViewById(R.id.SwipeRefreshLayoutID01);

        //Handle the pull to refresh.
        swipeRefreshLayout01.setOnRefreshListener(pullToRefreshListener);

        listView01.setOnScrollListener(listViewScrollListener);

        //This is the case of firstly opening the application.
        if (savedInstanceState == null) {
            refreshData();
        }
    }

    private void refreshData() {
        if (photoListManager.getCount() == 0) {
            reloadData();
        } else {
            reloadDataNewer();
        }
    }

    private void reloadDataNewer() {
        int maxId = photoListManager.getMaximumId();
        Call<PhotoItemCollectionDao> call = HttpManager
                .getInstance()
                .getService()
                .loadPhotoListAfterId(maxId);
        call.enqueue(new PhotoListLoadCallBack(PhotoListLoadCallBack.MODE_RELOAD_NEWER));
    }

    private void reloadData() {
        //Call a service singleton object
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getService().loadPhotoList();

        //This synchronous method is not allowed. It uses the main thread.
        //It waits or blocks further executions. It will throw to NetworkonMainThrowException.
        //call.execute()

        //Use an asynchronous one instead. This won't block anything.
        //However, it must not be used with an Activity.
        call.enqueue(new PhotoListLoadCallBack(PhotoListLoadCallBack.MODE_RELOAD));
    }

    private void loadMoreData() {
        if (isLoadingMore) {
            return; //Do nothing
        }
        isLoadingMore = true; //Prevent reloading data more.
        int minId = photoListManager.getMinimumId();
        Call<PhotoItemCollectionDao> call = HttpManager
                .getInstance()
                .getService()
                .loadPhotoListBeforeId(minId);
        call.enqueue(new PhotoListLoadCallBack(PhotoListLoadCallBack.MODE_LOAD_MORE));
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
        outState.putBundle(
                "photoListManager",
                photoListManager.onSaveInstanceState());
        outState.putBundle(
                "lastPositionInteger",
                lastPositionInteger.onSaveInstanceState());
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        photoListManager.onRestoreInstanceState(
                savedInstanceState.getBundle("photoListManager"));
        lastPositionInteger.onRestoreInstanceState(
                savedInstanceState.getBundle("lastPositionInteger"));
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

    private void showButtonNewPhotos() {
        buttonNewPhotos01.setVisibility(View.VISIBLE);
        Animation anim = AnimationUtils.loadAnimation(
                Contextor.getInstance().getContext(), R.anim.zoom_fade_in);
        buttonNewPhotos01.startAnimation(anim);
    }

    private void hideButtonNewPhotos() {
        buttonNewPhotos01.setVisibility(View.GONE);
        Animation anim = AnimationUtils.loadAnimation(
                Contextor.getInstance().getContext(), R.anim.zoom_fade_out);
        buttonNewPhotos01.startAnimation(anim);
    }

    private void showToast(String text) {
        Toast.makeText(Contextor.getInstance().getContext(), text, Toast.LENGTH_LONG).show();
    }

    /**************************************************
     * Listeners
     **************************************************/
    final View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == buttonNewPhotos01) {
                hideButtonNewPhotos();
                listView01.smoothScrollToPosition(0);
            }
        }
    };

    final SwipeRefreshLayout.OnRefreshListener pullToRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshData();
        }
    };

    //While scrolling the view
    final AbsListView.OnScrollListener listViewScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view,
                             int firstVisibleItem,
                             int visibleItemCount,
                             int totalItemCount) {
            if (view == listView01) {
                //Swipe only at Position 0.
                swipeRefreshLayout01.setEnabled(firstVisibleItem == 0);

                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    if (photoListManager.getCount() > 0) { //Prevent the case of no item.
                        //Load more
                        //Log.d("LISTVIEW", "Load more triggered");
                        loadMoreData();
                    }
                }
            }
        }
    };

    /**************************************************
     * Inner Classes
     **************************************************/
    class PhotoListLoadCallBack implements Callback<PhotoItemCollectionDao> {

        public static final int MODE_RELOAD = 1;
        public static final int MODE_RELOAD_NEWER = 2;
        public static final int MODE_LOAD_MORE = 3;
        int mode;

        public PhotoListLoadCallBack(int mode) {
            this.mode = mode;
        }

        //Stop the pull to refresh.
        private void stopPullToRefresh() {
            swipeRefreshLayout01.setRefreshing(false);
        }

        @Override
        public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {
            stopPullToRefresh();

            if (response.isSuccessful()) {
                PhotoItemCollectionDao dao = response.body();

                int firstVisiblePosition = listView01.getFirstVisiblePosition();
                View c = listView01.getChildAt(0); //Get a view at the position 0.
                int top = c == null ? 0 : c.getTop();

                if (mode == MODE_RELOAD_NEWER) {
                    //photoListManager.setDao(dao);
                    photoListManager.insertDaoAtTopPosition(dao);
                } else if (mode == MODE_RELOAD) {
                    photoListManager.setDao(dao);
                } else if (mode == MODE_LOAD_MORE) {
                    photoListManager.appendDaoAtBottomPosition(dao);
                }
                clearLoadingMoreFlageIfCapable(mode); //Loading more data is done.
                //listAdapter01.setDao(dao);
                listAdapter01.setDao(photoListManager.getDao()); //Only the updated data
                listAdapter01.notifyDataSetChanged();

                if (mode == MODE_RELOAD_NEWER) {
                    //Maintain the scroll position.
                    int additionalSize = (dao != null && dao.getData() != null) ? dao.getData().size() : 0;
                    listAdapter01.increaseLastPosition(additionalSize);
                    listView01.setSelectionFromTop(firstVisiblePosition + additionalSize, top);

                    if (additionalSize > 0) {
                        showButtonNewPhotos();
                    }
                } else {

                }

                //Use Load Complete for the case of null
                showToast("Load Completed");
            } else {//onResponse is fail
                clearLoadingMoreFlageIfCapable(mode);
                try {
                    showToast(response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoItemCollectionDao> call, Throwable t) {
            clearLoadingMoreFlageIfCapable(mode);
            stopPullToRefresh();
            showToast(t.toString());
        }

        private void clearLoadingMoreFlageIfCapable(int mode) {
            if (mode == MODE_LOAD_MORE) {
                isLoadingMore = false;
            }
        }
    }
}
