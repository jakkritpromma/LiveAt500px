package rabbidcompany.liveat500px.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;

import rabbidcompany.liveat500px.dao.PhotoItemCollectionDao;
import rabbidcompany.liveat500px.dao.PhotoItemDao;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotoListManager {

    /* Singleton is not used in Lecture 157. #1
    private static PhotoListManager instance;

    public static PhotoListManager getInstance() {
        if (instance == null)
            instance = new PhotoListManager();
        return instance;
    }*/

    private Context mContext;
    private PhotoItemCollectionDao dao;

    /* Singleton is not used in Lecture 157. #2
    private PhotoListManager() {
        mContext = Contextor.getInstance().getContext();

        //Load the dao from the persistent storage here.
        //Code here...
    }*/

    //The non-singleton version is here.
    public PhotoListManager() {
        mContext = Contextor.getInstance().getContext();

        //Load the data from the persistent storage.
        loadCache();
    }

    public PhotoItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;

        //You can save the dao in the persistent storage here.
        //Code here...

        //Dao is changed #1. So, call saveCache();
        saveCache();
    }

    public void insertDaoAtTopPosition(PhotoItemCollectionDao newDao) {
        if (dao == null) {
            dao = new PhotoItemCollectionDao();
        }
        if (dao.getData() == null) {
            dao.setData(new ArrayList<PhotoItemDao>());
        }
        dao.getData().addAll(0, newDao.getData()); //Add data at the top.
        saveCache();
    }

    public void appendDaoAtBottomPosition(PhotoItemCollectionDao newDao) {
        if (dao == null) {
            dao = new PhotoItemCollectionDao();
        }
        if (dao.getData() == null) {
            dao.setData(new ArrayList<PhotoItemDao>());
        }
        dao.getData().addAll(dao.getData().size(), newDao.getData()); //Add data at the top.
        saveCache();
    }

    public int getMaximumId() {
        if (dao == null) {
            return 0;
        }
        if (dao.getData() == null) {
            return 0;
        }
        if (dao.getData().size() == 0) {
            return 0;
        }
        int maxId = dao.getData().get(0).getId();
        for (int i = 1; i < dao.getData().size(); i++) {
            maxId = Math.max(maxId, dao.getData().get(i).getId());
        }
        return maxId;
    }

    public int getMinimumId() {
        if (dao == null) {
            return 0;
        }
        if (dao.getData() == null) {
            return 0;
        }
        if (dao.getData().size() == 0) {
            return 0;
        }
        int minId = dao.getData().get(0).getId();
        for (int i = 1; i < dao.getData().size(); i++) {
            minId = Math.min(minId, dao.getData().get(i).getId());
        }
        return minId;
    }

    public int getCount() {
        if (dao == null) {
            return 0;
        }
        if (dao.getData() == null) {
            return 0;
        }
        return dao.getData().size();
    }

    //The following methods are for simplicity.
    public Bundle onSaveInstanceState(){
        Bundle bundle = new Bundle();
        bundle.putParcelable("dao", dao); //Put a parcel inside a bundle.
        return bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState){
        dao = savedInstanceState.getParcelable("dao");
    }

    private void saveCache(){
        PhotoItemCollectionDao cacheDao = new PhotoItemCollectionDao();
        if(dao != null && dao.getData() != null) {
            cacheDao.setData(dao.getData().subList(0, Math.min(20, dao.getData().size())));
        }
        String json = new Gson().toJson(cacheDao);

        //SharedPreferences can store only primitive type variables.
        SharedPreferences prefs = mContext.getSharedPreferences("photos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        //Add/Edit/Delete things here.
        editor.putString("json", json);

        editor.apply();
    }

    private void loadCache(){
        SharedPreferences prefs = mContext.getSharedPreferences("photos", Context.MODE_PRIVATE);
        String json = prefs.getString("json", null);
        if(json == null){
            return;
        }
        dao = new Gson().fromJson(json, PhotoItemCollectionDao.class);
    }
}
