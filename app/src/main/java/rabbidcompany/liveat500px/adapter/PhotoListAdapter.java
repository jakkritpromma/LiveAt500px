package rabbidcompany.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import rabbidcompany.liveat500px.PhotoListItem;

/**
 * Created by noneemotion on 13/7/2559.
 */
public class PhotoListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        //return 0;
        return 10000; //This is for fun.
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return null;
        return new PhotoListItem(parent.getContext()); //Return a new PhotoListItem.
    }
}
