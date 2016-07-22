package rabbidcompany.liveat500px.manager;

import android.content.Context;

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
    }

    public PhotoItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;

        //You can save the dao in the persistent storage here.
        //Code here...
    }

    public void insertDaoAtTopPosition(PhotoItemCollectionDao newDao) {
        if (dao == null) {
            dao = new PhotoItemCollectionDao();
        }
        if (dao.getData() == null) {
            dao.setData(new ArrayList<PhotoItemDao>());
        }
        dao.getData().addAll(0, newDao.getData()); //Add data at the top.
    }

    public void appendDaoAtBottomPosition(PhotoItemCollectionDao newDao) {
        if (dao == null) {
            dao = new PhotoItemCollectionDao();
        }
        if (dao.getData() == null) {
            dao.setData(new ArrayList<PhotoItemDao>());
        }
        dao.getData().addAll(dao.getData().size(), newDao.getData()); //Add data at the top.
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
}
