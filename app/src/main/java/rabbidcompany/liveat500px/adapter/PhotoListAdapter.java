package rabbidcompany.liveat500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import rabbidcompany.liveat500px.PhotoListItem;
import rabbidcompany.liveat500px.R;
import rabbidcompany.liveat500px.dao.PhotoItemCollectionDao;
import rabbidcompany.liveat500px.dao.PhotoItemDao;
import rabbidcompany.liveat500px.manager.PhotoListManager;

/**
 * Created by noneemotion on 13/7/2559.
 */

//An adapter (extended from BaseAdapter) cooperating with the listView
public class PhotoListAdapter extends BaseAdapter {

    PhotoItemCollectionDao dao; //Do not use a singleton dao anymore.
    int lastPosition = -1;

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        //return 0;
        //return 10000; //This is for fun.

        //These cases use singleton daos.
        /*
        if (PhotoListManager.getInstance().getDao() == null) {
            return 0;
        }
        else if(PhotoListManager.getInstance().getDao().getData() == null){
            return 0;
        }
        else {
            return PhotoListManager.getInstance().getDao().getData().size();
        }*/


        if (dao == null) {
            return 0;
        } else if (dao.getData() == null) {
            return 0;
        } else {
            return dao.getData().size();
        }
    }

    @Override
    public Object getItem(int position) {
        //return null;
        return dao.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        //return super.getViewTypeCount();
        return 2; //Return the maximum number of View types.
    }
/*
    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        return position % 2 == 0 ? 0 : 1; //Return the control number of each View type.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == 0) {
            PhotoListItem item;
            if (convertView != null) {//Reuse a PhotoListItem. Otherwise, create a new one.
                item = (PhotoListItem) convertView;
            } else {
                item = new PhotoListItem(parent.getContext());
            }
            return item;
        } else {
            TextView item;
            if (convertView != null) {
                item = (TextView) convertView;
            } else {
                item = new TextView(parent.getContext());
            }
            item.setText("Position: " + position);
            return item;
        }

        //The first case
        //return null;

        //The second case
        //return new PhotoListItem(parent.getContext()); //Return a new PhotoListItem.

        //return item;
    }
    */

    //getView feeds views to the ListView in the fragment.
    //convertView is a created and unused convertView(s).
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoListItem item;

        if (convertView != null) {
            item = (PhotoListItem) convertView;
        } else {
            item = new PhotoListItem(parent.getContext());
        }

        //A data access object referred to the given position of the item, not the collection
        PhotoItemDao dao = (PhotoItemDao) getItem(position);
        item.setNameText(dao.getCaption());
        item.setDescriptionText(dao.getUserName() + "\n" + dao.getCamera());
        item.setImageUrl(dao.getImageUrl());

        //Translate the view up or down.
        if (position > lastPosition) {
            Animation anim = AnimationUtils.loadAnimation(parent.getContext(), R.anim.up_from_button);
            item.startAnimation(anim);
        } else {
            Animation anim_down = AnimationUtils.loadAnimation(parent.getContext(), R.anim.down_from_above);
            item.startAnimation(anim_down);
        }
        lastPosition = position;

        //return null;
        return item;
    }

}
