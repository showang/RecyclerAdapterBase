package tw.showang.recycleradapterbase.example.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

import tw.showang.recycleradapterbase.example.api.base.ExampleApiBase;

public class ExampleGlideModule implements GlideModule {
	@Override
	public void applyOptions(Context context, GlideBuilder builder) {
		builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
	}

	@Override
	public void registerComponents(Context context, Glide glide) {
		glide.register(GlideUrl.class, InputStream.class, ExampleApiBase.createUrlLoaderFactory());
	}
}
