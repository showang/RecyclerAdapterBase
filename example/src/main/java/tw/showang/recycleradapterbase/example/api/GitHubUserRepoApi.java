package tw.showang.recycleradapterbase.example.api;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import tw.showang.recycleradapterbase.example.api.GitHubUserRepoApi.GetRepoResult;
import tw.showang.recycleradapterbase.example.api.base.ApiException;
import tw.showang.recycleradapterbase.example.api.base.ExampleApiBase;

public class GitHubUserRepoApi extends ExampleApiBase<GitHubUserRepoApi, List<GetRepoResult>> {

	private String mUserName;

	public GitHubUserRepoApi initUser(String userName) {
		mUserName = userName;
		return this;
	}

	@Override
	protected List<GetRepoResult> parseResult(Gson gson, String result) throws ApiException {
		Type type = new TypeToken<List<GetRepoResult>>() {
		}.getType();
		return gson.fromJson(result, type);
	}

	@Override
	public int getHttpMethod() {
		return HttpMethod.GET;
	}

	@Override
	public String getUrl() {
		return getBaseUri() + "/users/" + mUserName + "/repos";
	}

	public class GetRepoResult {
		@SerializedName("id")
		public int id;
		@SerializedName("name")
		public String name;
		@SerializedName("full_name")
		public String fullName;
		@SerializedName("description")
		public String description;
		@SerializedName("private")
		public boolean isPrivate;
		@SerializedName("fork")
		public boolean isFork;
		@SerializedName("url")
		public String url;
		@SerializedName("html_url")
		public String html_url;
		@SerializedName("owner")
		public OwnerInfo ownerInfo;
		@SerializedName("stargazers_count")
		public int startedCount;

		public class OwnerInfo {
			@SerializedName("avatar_url")
			public String avatarUrl;
			@SerializedName("login")
			public String name;
			@SerializedName("id")
			public int id;
		}
	}

	@Override
	public GitHubUserRepoApi start(Object tag) {
		if (mUserName == null) {
			throw new RuntimeException("User name must be initialized.");
		}
		return super.start(tag);
	}
}
