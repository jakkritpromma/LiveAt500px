package rabbidcompany.liveat500px.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import rabbidcompany.liveat500px.dao.PhotoItemCollectionDao;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotoListManager {

    private static PhotoListManager instance;

    public static PhotoListManager getInstance() {
        if (instance == null)
            instance = new PhotoListManager();
        return instance;
    }

    private Context mContext;
    private PhotoItemCollectionDao dao;

    private PhotoListManager() {
        mContext = Contextor.getInstance().getContext();

        //Load the dao from the persistent storage here.
        //Code here...
    }

    public PhotoItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;

        //You can save the dao in the persistent storage here.
        //Code here...
    }
}
