package tw.showang.recycleradapterbase.example.glide;

import android.content.Context;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;

import tw.showang.apiabstrationframework.logger.Logger;
import tw.showang.apiabstrationframework.support.glide.GlideLoaderFactory;
import tw.showang.apiabstrationframework.support.volley.VolleyRequestExecutor;

public class GlideVolleyRequestExecutor extends VolleyRequestExecutor implements GlideLoaderFactory {
	public GlideVolleyRequestExecutor(Context context, Logger logger) {
		super(context, logger);
	}

	@Override
	public ModelLoaderFactory<GlideUrl, InputStream> createUrlLoaderFactory() {
		return new VolleyUrlLoader.Factory(getRequestQueue());
	}
}
