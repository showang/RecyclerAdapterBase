package tw.showang.recycleradapterbase.example;

import android.app.Application;

import tw.showang.apiabstrationframework.logger.Logger;
import tw.showang.apiabstrationframework.logger.internal.AndroidLogger;
import tw.showang.recycleradapterbase.example.api.base.ExampleApiBase;
import tw.showang.recycleradapterbase.example.glide.GlideVolleyRequestExecutor;

public class ExampleApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Logger logger = new AndroidLogger("Recycler Base Example");
		ExampleApiBase.init(getString(R.string.github_token), new GlideVolleyRequestExecutor(this, logger), logger);
	}
}
