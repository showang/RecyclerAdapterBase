package tw.showang.recycleradaterbase;

import java.util.List;

public class RecyclerAdapterHelper {

	public final static int VIEW_TYPE_NORMAL = Integer.MAX_VALUE;
	private final static int VIEW_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;
	private final static int VIEW_TYPE_HEADER = Integer.MAX_VALUE - 4;
	private final static int VIEW_TYPE_FOOTER = Integer.MAX_VALUE - 8;

	private boolean isLoadMoreEnable = false;
	private boolean isLoadMoreFailed = false;
	private boolean hasHeaderView = false;
	private boolean hasFooterView = false;
	private LoadMoreListener loadMoreListener;
	private CustomizeSizeDelegate sizeDelegate;

	private List dataList;

	public RecyclerAdapterHelper(List dataList, LoadMoreListener loadMoreListener, CustomizeSizeDelegate helperListener) {
		this.dataList = dataList;
		this.loadMoreListener = loadMoreListener;
		this.sizeDelegate = helperListener;
	}

	public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
		this.loadMoreListener = loadMoreListener;
	}

	public void setLoadMoreEnable(boolean isEnable) {
		this.isLoadMoreEnable = isEnable;
	}

	public void setLoadMoreFailed(boolean isLoadMoreFailed) {
		this.isLoadMoreFailed = isLoadMoreFailed;
	}

	public void setHasHeaderView(boolean hasHeaderView) {
		this.hasHeaderView = hasHeaderView;
	}

	public void setHasFooterView(boolean hasFooterView) {
		this.hasFooterView = hasFooterView;
	}

	public int getAdapterItemCount() {
		int dataSize = sizeDelegate.getDataSize();
		return dataSize == 0 ? (hasHeaderView ? 1 : 0) : dataSize + (isLoadMoreEnable || hasFooterView ? 1 : 0) + (hasHeaderView ? 1 : 0);
	}

	public int calcDataItemPosition(int adapterPosition) {
		return adapterPosition - (hasHeaderView ? 1 : 0);
	}

	public int getDataSize() {
		return dataList.size();
	}

	public int getItemType(int position) {
		int itemType;
		if (isHeaderItem(position)) {
			itemType = VIEW_TYPE_HEADER;
		} else if (isLoadMoreItem(position)) {
			itemType = VIEW_TYPE_LOAD_MORE;
		} else if (isFooterItem(position)) {
			itemType = VIEW_TYPE_FOOTER;
		} else {
			itemType = VIEW_TYPE_NORMAL;
		}
		return itemType;
	}

	public boolean checkToBindLoadMore(int position) {
		if (isLoadMoreItem(position)) {
			if (!isLoadMoreFailed) {
				loadMoreListener.onLoadMore();
			}
			return true;
		}
		return false;
	}

	public boolean hasHeaderView() {
		return hasHeaderView;
	}

	public boolean hasFooterView() {
		return hasFooterView;
	}

	public boolean isNormalItem(int position) {
		return !isHeaderItem(position) && !isFooterItem(position) && !isLoadMoreItem(position);
	}

	public boolean isHeaderItem(int position) {
		return hasHeaderView && position == 0;
	}

	public boolean isFooterItem(int position) {
		return hasFooterView && position == getAdapterItemCount() - 1;
	}

	public boolean isLoadMoreItem(int position) {
		return isLoadMoreEnable && position == getAdapterItemCount() - 1;
	}

	public boolean isLoadMoreType(int viewType) {
		return viewType == VIEW_TYPE_LOAD_MORE;
	}

	public boolean isHeaderType(int viewType) {
		return viewType == VIEW_TYPE_HEADER;
	}

	public boolean isFooterType(int viewType) {
		return viewType == VIEW_TYPE_FOOTER;
	}

	public boolean isLoadMoreEnable() {
		return isLoadMoreEnable;
	}

	public boolean isLoadMoreFailed() {
		return isLoadMoreFailed;
	}

	public void clearData() {
		dataList.clear();
	}

	public interface CustomizeSizeDelegate {
		int getDataSize();
	}
}
