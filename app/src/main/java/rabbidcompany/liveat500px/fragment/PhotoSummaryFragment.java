package rabbidcompany.liveat500px.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import rabbidcompany.liveat500px.R;
import rabbidcompany.liveat500px.dao.PhotoItemDao;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotoSummaryFragment extends Fragment {

    PhotoItemDao dao;
    ImageView imageViewSummary;
    TextView textViewNameSum, textViewDescription;

    public PhotoSummaryFragment() {
        super();
    }

    public static PhotoSummaryFragment newInstance(PhotoItemDao dao) {
        PhotoSummaryFragment fragment = new PhotoSummaryFragment();
        Bundle args = new Bundle();
        args.putParcelable("dao", dao);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        dao = getArguments().getParcelable("dao");

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo_summary, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState
        imageViewSummary = (ImageView) rootView.findViewById(R.id.ImageViewSumID01);
        textViewNameSum = (TextView) rootView.findViewById(R.id.TextViewNameSumID01);
        textViewDescription = (TextView) rootView.findViewById(R.id.TextViewDescriptionSumID01);

        textViewNameSum.setText(dao.getCaption());
        textViewDescription.setText(dao.getUserName() + "\n" + dao.getCamera());
        Glide.with(PhotoSummaryFragment.this)
                .load(dao.getImageUrl())
                .placeholder(R.drawable.loading)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageViewSummary);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }

}
