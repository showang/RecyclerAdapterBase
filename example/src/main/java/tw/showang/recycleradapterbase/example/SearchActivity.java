package tw.showang.recycleradapterbase.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import tw.showang.recycleradapterbase.example.api.GitHubSearchRepoApi;
import tw.showang.recycleradapterbase.example.api.GitHubSearchRepoApi.SearchResult;
import tw.showang.recycleradapterbase.example.api.GitHubUserRepoApi.GetRepoResult;
import tw.showang.recycleradapterbase.example.api.base.ExampleApiBase.ApiErrorListener;
import tw.showang.recycleradapterbase.example.api.base.ExampleApiBase.ApiSuccessListener;
import tw.showang.recycleradapterbase.LoadMoreListener;

public class SearchActivity extends AppCompatActivity {

	public static final String INPUT_STRING_QUERY = "0";

	private String mQueryString;
	private Toolbar mToolbar;
	private RecyclerView mRecyclerView;
	private ExampleRepoAdapter mAdapter;
	private GitHubSearchRepoApi mApi;
	private int mCurrentPage = -1;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		initAttributes();
		initToolbar();
		initRecyclerView();
		initSearchApi();
	}

	@Override
	protected void onResume() {
		super.onResume();
		checkToRequestData();
	}

	private void initSearchApi() {
		if (mApi == null) {
			mApi = new GitHubSearchRepoApi()
					.success(new ApiSuccessListener<SearchResult>() {
						@Override
						public void onSuccess(SearchResult response) {
							onRequestSuccess(response);
						}
					})
					.fail(new ApiErrorListener() {
						@Override
						public void onFail(int errorType, String message) {
							mAdapter.onLoadMoreFailed(true);
							mAdapter.notifyDataSetChanged();
						}
					});
		}
	}

	private void onRequestSuccess(SearchResult response) {
		mCurrentPage++;
		if (mAdapter.getDataSize() == 0) {
			mAdapter.setHeaderView(initHeaderView(mRecyclerView));
		}
		mAdapter.addItem(response.repoList);
		mAdapter.setLoadMoreEnable(response.totalCount > mAdapter.getDataSize());
		mAdapter.notifyDataSetChanged();
	}

	private void checkToRequestData() {
		if (mAdapter.getDataSize() == 0) {
			requestSearchRepoData();
		}
	}

	private void requestSearchRepoData() {
		mApi.keyword(mQueryString, mCurrentPage + 1).start();
	}

	private void initAttributes() {
		mQueryString = getIntent().getStringExtra(INPUT_STRING_QUERY);
	}

	private void initToolbar() {
		mToolbar = (Toolbar) findViewById(R.id.searchActivity_toolbar);
		mToolbar.setTitle(mQueryString);
		mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
		mToolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		mToolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
	}

	private void initRecyclerView() {
		mRecyclerView = (RecyclerView) findViewById(R.id.searchActivity_recycler);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		if (mAdapter == null) {
			mAdapter = new ExampleRepoAdapter(new ArrayList<GetRepoResult>());
			mAdapter.setFooterView(initFooterView(mRecyclerView));
			mAdapter.setLoadMoreListener(new LoadMoreListener() {
				@Override
				public void onLoadMore() {
					requestSearchRepoData();
				}
			});
		}
		mRecyclerView.setAdapter(mAdapter);
	}

	private View initHeaderView(ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View headerView = inflater.inflate(R.layout.item_header, parent, false);
		headerView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(view.getContext(), "Click Search HeaderView.", Toast.LENGTH_SHORT).show();
			}
		});
		return headerView;
	}

	private View initFooterView(ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View footerView = inflater.inflate(R.layout.item_footer, parent, false);
		footerView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(view.getContext(), "Click Search FooterView.", Toast.LENGTH_SHORT).show();
			}
		});
		return footerView;
	}
}
