package tw.showang.recycleradapterbase.example;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import tw.showang.recycleradapterbase.example.api.GitHubUserRepoApi.GetRepoResult;
import tw.showang.recycleradapterbase.RecyclerAdapterBase;

public class ExampleRepoAdapter extends RecyclerAdapterBase {

	private List<GetRepoResult> repoResultList;

	public ExampleRepoAdapter(List<GetRepoResult> dataList) {
		super(dataList);
		repoResultList = dataList;
	}

	@Override
	protected ViewHolder onCreateItemViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
		return new RepoItemViewHolder(inflater.inflate(R.layout.item_repo_info, parent, false));
	}

	@Override
	protected void onBindItemViewHolder(ViewHolder viewHolder, int position) {
		RepoItemViewHolder vh = (RepoItemViewHolder) viewHolder;
		Context context = vh.itemView.getContext();
		GetRepoResult repoInfo = repoResultList.get(position);
		vh.titleText.setText(repoInfo.name);
		vh.ownerNameText.setText(repoInfo.ownerInfo.name);
		vh.starTextView.setText(String.valueOf(repoInfo.startedCount));
		Glide.with(context).load(repoInfo.ownerInfo.avatarUrl).bitmapTransform(new CropCircleTransformation(context)).into(vh.avatarImage);
	}

	@Override
	protected ViewHolder onCreateLoadMoreViewHolder(LayoutInflater inflater, ViewGroup parent) {
		return new LoadMoreViewHolder(inflater.inflate(R.layout.item_loadmore, parent, false));
	}

	@Override
	protected void onBindLoadMoreViewHolder(ViewHolder viewHolder, boolean isLoadMoreFailed) {
		super.onBindLoadMoreViewHolder(viewHolder, isLoadMoreFailed);
		LoadMoreViewHolder vh = (LoadMoreViewHolder) viewHolder;
		vh.progressBar.setVisibility(isLoadMoreFailed ? View.GONE : View.VISIBLE);
		vh.failText.setVisibility(isLoadMoreFailed ? View.VISIBLE : View.GONE);
	}

	public void addItem(List<GetRepoResult> response) {
		repoResultList.addAll(response);
	}

	public void onConfigurationChanged(Context context) {
		notifyDataSetChanged();
	}

	private class RepoItemViewHolder extends RecyclerView.ViewHolder {
		TextView titleText;
		TextView ownerNameText;
		TextView starTextView;
		ImageView avatarImage;

		RepoItemViewHolder(View itemView) {
			super(itemView);
			titleText = (TextView) itemView.findViewById(R.id.repoItem_titleTextView);
			ownerNameText = (TextView) itemView.findViewById(R.id.repoItem_ownerNameTextView);
			avatarImage = (ImageView) itemView.findViewById(R.id.repoItem_avatarImageView);
			starTextView = (TextView) itemView.findViewById(R.id.repoItem_startTextView);
		}
	}

	private class LoadMoreViewHolder extends RecyclerView.ViewHolder {
		View progressBar;
		View failText;

		LoadMoreViewHolder(View itemView) {
			super(itemView);
			progressBar = itemView.findViewById(R.id.loadmore_progress);
			failText = itemView.findViewById(R.id.loadmore_failText);
		}
	}
}
