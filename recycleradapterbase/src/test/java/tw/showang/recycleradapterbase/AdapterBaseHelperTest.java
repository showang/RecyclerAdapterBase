package tw.showang.recycleradapterbase;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class AdapterBaseHelperTest {

    private List<Object> dataList;
    private RecyclerAdapterHelper helper;

    @Mock
    LoadMoreListener loadMoreListener;

    @Before
    public void setup(){
        dataList = new ArrayList<>();
        helper = new RecyclerAdapterHelper(dataList, loadMoreListener, new RecyclerAdapterHelper.CustomizeSizeDelegate() {
            @Override
            public int getDataSize() {
                return dataList.size();
            }
        });
    }

    @Test
    public void testItemCount_Header(){
        int mockDataSize = 10;
        addMockData(mockDataSize);
        Assert.assertThat("data size 10", dataList.size(), CoreMatchers.is(mockDataSize));
        helper.setHasHeaderView(true);
        Assert.assertThat("header + 1", helper.getAdapterItemCount(), CoreMatchers.is(mockDataSize + 1));
        helper.setHasHeaderView(false);
        Assert.assertThat("no header", helper.getAdapterItemCount(), CoreMatchers.is(mockDataSize));
    }

    @Test
    public void testItemCount_Footer(){
        int mockDataSize = 10;
        addMockData(mockDataSize);
        Assert.assertThat("data size 10", dataList.size(), CoreMatchers.is(mockDataSize));
        helper.setHasFooterView(true);
        Assert.assertThat("footer + 1", helper.getAdapterItemCount(), CoreMatchers.is(mockDataSize + 1));
        helper.setHasFooterView(false);
        Assert.assertThat("no footer", helper.getAdapterItemCount(), CoreMatchers.is(mockDataSize));
    }

    @Test
    public void testItemCount_Header_Footer(){
        int mockDataSize = 10;
        addMockData(mockDataSize);
        Assert.assertThat("data size 10", dataList.size(), CoreMatchers.is(mockDataSize));
        helper.setHasFooterView(true);
        Assert.assertThat("footer + 1", helper.getAdapterItemCount(), CoreMatchers.is(mockDataSize + 1));
        helper.setHasHeaderView(true);
        Assert.assertThat("header and footer + 2", helper.getAdapterItemCount(), CoreMatchers.is(mockDataSize + 2));
        helper.setHasFooterView(false);
        Assert.assertThat("header + 1", helper.getAdapterItemCount(), CoreMatchers.is(mockDataSize + 1));
        helper.setHasHeaderView(false);
        Assert.assertThat("no header and footer", helper.getAdapterItemCount(), CoreMatchers.is(mockDataSize));
    }

    @Test
    public void testItemCount_Footer_LoadMore(){
        int mockDataSize = 10;
        addMockData(mockDataSize);
        Assert.assertThat("data size 10", dataList.size(), CoreMatchers.is(mockDataSize));
        helper.setHasFooterView(true);
        Assert.assertThat("footer + 1", helper.getAdapterItemCount(), CoreMatchers.is(mockDataSize + 1));
        helper.setLoadMoreEnable(true);
        Assert.assertThat("footer exclusive load more + 1", helper.getAdapterItemCount(), CoreMatchers.is(mockDataSize + 1));
        helper.setHasFooterView(false);
        Assert.assertThat("load more + 1", helper.getAdapterItemCount(), CoreMatchers.is(mockDataSize + 1));
        helper.setLoadMoreEnable(false);
        Assert.assertThat("no footer and load more", helper.getAdapterItemCount(), CoreMatchers.is(mockDataSize));
    }

    @Test
    public void testBindLoadMore(){
        int mockDataSize = 10;
        int loadMoreItemPosition = 10;
        addMockData(mockDataSize);
        helper.setLoadMoreEnable(true);
        Assert.assertThat("load more + 1", helper.getAdapterItemCount(), CoreMatchers.is(mockDataSize + 1));
        helper.checkToBindLoadMore(loadMoreItemPosition);
        Mockito.verify(loadMoreListener, times(1)).onLoadMore();
    }

    @Test
    public void testBindLoadMore_loadMoreFail(){
        int mockDataSize = 10;
        int loadMoreItemPosition = 10;
        addMockData(mockDataSize);
        helper.setLoadMoreEnable(true);
        helper.checkToBindLoadMore(loadMoreItemPosition);
        Mockito.verify(loadMoreListener, times(1)).onLoadMore();
        helper.setLoadMoreFailed(true);
        helper.checkToBindLoadMore(loadMoreItemPosition);
        Mockito.verify(loadMoreListener, times(1)).onLoadMore();
    }

    private void addMockData(int size){
        for(int i = 0; i < size; i ++){
            dataList.add(new Object());
        }
    }

}
