package rabbidcompany.liveat500px.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.inthecheesefactory.thecheeselibrary.view.SlidingTabLayout;

import rabbidcompany.liveat500px.R;
import rabbidcompany.liveat500px.dao.PhotoItemDao;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MoreInfoFragment extends Fragment {

    /*************************************************************
     * Variables
     ***********************************************************/
    ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    PhotoItemDao dao;
    /*************************************************************
     * Functions
     ***********************************************************/
    public MoreInfoFragment() {
        super();
    }

    public static MoreInfoFragment newInstance(PhotoItemDao dao) {
        MoreInfoFragment fragment = new MoreInfoFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_more_info, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        setHasOptionsMenu(true);
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState

        viewPager = (ViewPager) rootView.findViewById(R.id.viewPagerID01);

        //Slide views
        /*viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                //return 0;
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                //return false;
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Button btn = new Button(container.getContext());
                btn.setText("Position: " + position);
                container.addView(btn);
                //return super.instantiateItem(container, position);
                return btn;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                //super.destroyItem(container, position, object);
                container.removeView((View) object);
            }
        });*/

        //Slide fragments.
        //Note: FragmentPagerAdapter does not save any state, but it requires fewer resources.
        //FragmentStatePagerAdapter automatically save fragments' states.
        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                //return null;
                switch (position){
                    case 0:
                        return PhotoSummaryFragment.newInstance(dao);
                    case 1:
                        return PhotoInfoFragment.newInstance(dao);
                    case 2:
                        return PhotoTagsFragment.newInstance(dao);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Summary";
                    case 1:
                        return "Info";
                    case 2:
                        return "Tags";
                    default:
                        return "";
                }
                //return super.getPageTitle(position);
            }
        });
        slidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.slidingTabLayoutID01);
        slidingTabLayout.setViewPager(viewPager); //Bind slidingTabLayout to ViewPager.

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_more_info, menu);

        MenuItem menuItem = (MenuItem) menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        shareActionProvider.setShareIntent(getShareIntent());
    }

    private Intent getShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT, "Extra Text");
        return intent;
    }
}
