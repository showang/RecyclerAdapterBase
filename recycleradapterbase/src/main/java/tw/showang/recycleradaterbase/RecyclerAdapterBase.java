package tw.showang.recycleradaterbase;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import tw.showang.recycleradaterbase.RecyclerAdapterHelper.CustomizeSizeDelegate;

@SuppressWarnings("unused")
public abstract class RecyclerAdapterBase extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CustomizeSizeDelegate {

	private final static int DEFAULT_LOAD_MORE_LAYOUT = R.layout.recycler_item_load_more;

	private RecyclerAdapterHelper recyclerAdapterHelper;
	private View headerView;
	private View footerView;

	protected RecyclerAdapterBase(List<?> dataList) {
		recyclerAdapterHelper = new RecyclerAdapterHelper(dataList, null, this);
	}

	@Override
	final public int getItemViewType(int adapterPosition) {
		return recyclerAdapterHelper.isNormalItem(adapterPosition) ?
				getCustomItemViewType(recyclerAdapterHelper.calcDataItemPosition(adapterPosition)) :
				recyclerAdapterHelper.getItemType(adapterPosition);
	}

	protected int getCustomItemViewType(int position) {
		return RecyclerAdapterHelper.VIEW_TYPE_NORMAL;
	}

	@Override
	final public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ViewHolder vh;
		if (recyclerAdapterHelper.isHeaderType(viewType)) {
			vh = onCreateHeaderViewHolder(LayoutInflater.from(parent.getContext()), parent);
			vh.setIsRecyclable(false);
		} else if (recyclerAdapterHelper.isLoadMoreType(viewType)) {
			vh = onCreateLoadMoreViewHolder(LayoutInflater.from(parent.getContext()), parent);
		} else if (recyclerAdapterHelper.isFooterType(viewType)) {
			vh = onCreateFooterViewHolder(LayoutInflater.from(parent.getContext()), parent);
		} else {
			vh = onCreateItemViewHolder(LayoutInflater.from(parent.getContext()), parent, viewType);
		}
		return vh;
	}

	@Override
	final public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		if (recyclerAdapterHelper.isNormalItem(position)) {
			onBindItemViewHolder(viewHolder, position - (recyclerAdapterHelper.hasHeaderView() ? 1 : 0));
		} else if (recyclerAdapterHelper.checkToBindLoadMore(position)) {
			onBindLoadMoreViewHolder(viewHolder, recyclerAdapterHelper.isLoadMoreFailed());
		} else if (recyclerAdapterHelper.isHeaderItem(position)) {
			onBindHeaderViewHolder(viewHolder);
		} else if (recyclerAdapterHelper.isFooterItem(position)) {
			onBindFooterViewHolder(viewHolder);
		}
	}

	@Override
	final public int getItemCount() {
		return recyclerAdapterHelper.getAdapterItemCount();
	}

	@Override
	public int getDataSize() {
		return recyclerAdapterHelper.getDataSize();
	}

	final public void setLoadMoreEnable(boolean isEnable) {
		recyclerAdapterHelper.setLoadMoreEnable(isEnable);
	}

	final public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
		recyclerAdapterHelper.setLoadMoreListener(loadMoreListener);
	}

	@Override
	public void onViewDetachedFromWindow(ViewHolder holder) {
		super.onViewDetachedFromWindow(holder);
		if (recyclerAdapterHelper.isLoadMoreItem(holder.getAdapterPosition())) {
			recyclerAdapterHelper.setLoadMoreFailed(false);
		}
	}

	public void clearItemData() {
		recyclerAdapterHelper.clearData();
	}

	final public void setHeaderView(View headerView) {
		this.headerView = headerView;
		recyclerAdapterHelper.setHasHeaderView(headerView != null);
	}

	final public void setFooterView(View footerView) {
		this.footerView = footerView;
		recyclerAdapterHelper.setHasFooterView(footerView != null);
	}

	@SuppressWarnings("UnusedParameters")
	private RecyclerView.ViewHolder onCreateHeaderViewHolder(LayoutInflater inflater, ViewGroup parent) {
		return new ViewHolder(headerView) {
		};
	}

	@SuppressWarnings("UnusedParameters")
	private RecyclerView.ViewHolder onCreateFooterViewHolder(LayoutInflater inflater, ViewGroup parent) {
		return new ViewHolder(footerView) {
		};
	}

	@SuppressWarnings("UnusedParameters")
	protected void onBindLoadMoreViewHolder(RecyclerView.ViewHolder viewHolder, boolean isLoadMoreFailed) {
	}

	@SuppressWarnings("UnusedParameters")
	private void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder) {
		if (headerView.getParent() != null) {
			((ViewGroup) headerView.getParent()).removeView(headerView);
		}
	}

	@SuppressWarnings("UnusedParameters")
	private void onBindFooterViewHolder(RecyclerView.ViewHolder viewHolder) {
		if (footerView.getParent() != null) {
			((ViewGroup) footerView.getParent()).removeView(footerView);
		}
	}

	public boolean hasHeaderView() {
		return recyclerAdapterHelper.hasHeaderView();
	}

	public boolean hasFooterView() {
		return recyclerAdapterHelper.hasFooterView();
	}

	public boolean isLoadMoreEnable() {
		return recyclerAdapterHelper.isLoadMoreEnable();
	}

	protected RecyclerView.ViewHolder onCreateLoadMoreViewHolder(LayoutInflater from, ViewGroup parent) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(DEFAULT_LOAD_MORE_LAYOUT, parent, false)) {
		};
	}

	protected abstract RecyclerView.ViewHolder onCreateItemViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

	protected abstract void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position);

	public View getHeaderView() {
		return headerView;
	}

	protected int getItemPosition(ViewHolder viewHolder) {
		return viewHolder.getLayoutPosition() - (hasHeaderView() ? 1 : 0);
	}

	public void onLoadMoreFailed(boolean isLoadMoreFailed) {
		recyclerAdapterHelper.setLoadMoreFailed(isLoadMoreFailed);
	}
}
