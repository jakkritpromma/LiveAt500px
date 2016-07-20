package rabbidcompany.liveat500px;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Display;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotoListItem extends BaseCustomViewGroup {

    ImageView imageView01;
    TextView textViewName01;
    TextView textViewDes01;
    //FrameLayout frameLayoutRef01;

    public PhotoListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public PhotoListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public PhotoListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public PhotoListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_item_photo, this);
    }

    private void initInstances() {
        // findViewById here
        imageView01 = (ImageView) findViewById(R.id.ImageViewID01);
        textViewName01 = (TextView) findViewById(R.id.TextViewNameID01);
        textViewDes01 = (TextView) findViewById(R.id.TextViewDescriptionID01);
        //frameLayoutRef01 = (FrameLayout) findViewById(R.id.ContentContainerID01);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);
        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    //widthMeasureSpe and heightMeasureSpec are not normal integers, but they have modes too.
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /* //The following codes (finding the FrameLayout's spec) must be put in somewhere else.
        FrameLayout frameLayoutRef01 = (FrameLayout) findViewById(R.id.ContentContainerID01);
        ViewTreeObserver vto = frameLayoutRef01.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                frameLayoutRef01.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //Toast.makeText(getContext(), "FrameLayHeight: " + frameLayoutRef01.getMeasuredHeight(), Toast.LENGTH_SHORT).show();
            }
        });*/

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 838;

        //Make a new spec.
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        //Apply to the child views.
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);

        //Apply to itself, but the following code seems unnecessary.
        setMeasuredDimension(width, height);
    }

    public void setImageUrl(String url) {
        Glide.with(getContext())  //Glide
                .load(url)
                .placeholder(R.drawable.loading)  //Default image without loading any new image.
                //.error()        //The image for a loading error.
                //.transform()    //Transform an image
                //.bitmapTransform(new BlurTransformation(getContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL) //Use .ALL because more than one image size maybe used.
                .into(imageView01);
    }

    public void setNameText(String text) {
        textViewName01.setText(text);
    }

    public void setDescriptionText(String text) {
        textViewDes01.setText(text);
    }
}
