package tw.showang.recycleradapterbase.example;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tw.showang.recycleradapterbase.example.api.GitHubUserRepoApi;
import tw.showang.recycleradapterbase.example.api.GitHubUserRepoApi.GetRepoResult;
import tw.showang.recycleradapterbase.example.api.base.ExampleApiBase.ApiErrorListener;
import tw.showang.recycleradapterbase.example.api.base.ExampleApiBase.ApiSuccessListener;

public class MainActivity extends AppCompatActivity {

	private SwipeRefreshLayout mSwipeRefreshLayout;
	private RecyclerView mRecyclerView;
	private ExampleRepoAdapter mAdapter;
	private GitHubUserRepoApi mApi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initToolbar();
		initRecyclerView();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mAdapter.onConfigurationChanged(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		checkToRequestData();
	}

	private void checkToRequestData() {
		if (mAdapter.getDataSize() == 0) {
			if (mApi == null) {
				mApi = new GitHubUserRepoApi()
						.initUser("showang")
						.success(new ApiSuccessListener<List<GetRepoResult>>() {
							@Override
							public void onSuccess(List<GetRepoResult> response) {
								onRequestRepoInfoSuccess(response);
								hideLoading();
							}
						})
						.fail(new ApiErrorListener() {
							@Override
							public void onFail(int errorType, String message) {
								hideLoading();
								Toast.makeText(MainActivity.this, "Load repo fail.", Toast.LENGTH_LONG).show();
							}
						});
			}
			mApi.start();
			showLoading();
		}
	}

	private void onRequestRepoInfoSuccess(List<GetRepoResult> response) {
		if (mAdapter.getDataSize() == 0) {
			mAdapter.setHeaderView(initHeaderView(mRecyclerView));
		}
		mAdapter.addItem(response);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		final MenuItem menuSearchItem = menu.findItem(R.id.my_search);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView = (SearchView) menuSearchItem.getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(true);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				Toast.makeText(MainActivity.this, "SearchOnQueryTextSubmit: " + query, Toast.LENGTH_LONG).show();
				if (!searchView.isIconified()) {
					searchView.setIconified(true);
				}
				menuSearchItem.collapseActionView();
				Intent intent = new Intent(MainActivity.this, SearchActivity.class);
				intent.putExtra(SearchActivity.INPUT_STRING_QUERY, query);
				startActivity(intent);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String query) {
				return false;
			}
		});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
//		if (id == R.id.action_settings) {
//			return true;
//		}

		return super.onOptionsItemSelected(item);
	}

	private void showLoading() {
		mSwipeRefreshLayout.setEnabled(true);
	}

	private void hideLoading() {
		mSwipeRefreshLayout.setEnabled(false);
	}

	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	private void initRecyclerView() {
		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mainContent_swipeRefreshLayout);
		mRecyclerView = (RecyclerView) findViewById(R.id.mainContent_recyclerView);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		if (mAdapter == null) {
			mAdapter = new ExampleRepoAdapter(new ArrayList<GetRepoResult>());
			mAdapter.setFooterView(initFooterView(mRecyclerView));
		}
		mRecyclerView.setAdapter(mAdapter);
	}

	private View initHeaderView(ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View headerView = inflater.inflate(R.layout.item_header, parent, false);
		headerView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(view.getContext(), "Click HeaderView.", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(view.getContext(), "Click FooterView.", Toast.LENGTH_SHORT).show();
			}
		});
		return footerView;
	}

}
