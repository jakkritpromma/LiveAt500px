package rabbidcompany.liveat500px.configuration;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by noneemotion on 16/7/2559.
 */
//This class is used for configuring Glide.
public class GlideConfiguation implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //Use this option because the default image decode format is low quality RGB565.
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
