package tw.showang.recycleradapterbase.example.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

import tw.showang.recycleradapterbase.example.api.GitHubSearchRepoApi.SearchResult;
import tw.showang.recycleradapterbase.example.api.GitHubUserRepoApi.GetRepoResult;
import tw.showang.recycleradapterbase.example.api.base.ApiException;
import tw.showang.recycleradapterbase.example.api.base.ExampleApiBase;

public class GitHubSearchRepoApi extends ExampleApiBase<GitHubSearchRepoApi, SearchResult> {

	private int mPage;
	private String mKeyword;

	public GitHubSearchRepoApi keyword(String keyword, int page) {
		mKeyword = keyword;
		mPage = page;
		return this;
	}

	@Override
	public void getParameter(Map<String, String> parameterMap) {
		parameterMap.put("q", mKeyword);
		parameterMap.put("page", String.valueOf(mPage));
	}

	@Override
	protected SearchResult parseResult(Gson gson, String result) throws ApiException {
		Log.e("SearchRepoApi", result);
		return gson.fromJson(result, SearchResult.class);
	}

	@Override
	public int getHttpMethod() {
		return HttpMethod.GET;
	}

	@Override
	public String getUrl() {
		return getBaseUri() + "/search/repositories";
	}

	public class SearchResult {
		@SerializedName("total_count")
		public int totalCount;
		@SerializedName("items")
		public List<GetRepoResult> repoList;

	}
}
