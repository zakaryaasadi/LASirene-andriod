package Adapter;

public interface IProgressbarAdapter {
    int VIEW_TYPE_ITEM = 1;
    int VIEW_TYPE_LOADING = 2;
    void showLoadingMore();
    void hideLoadingMore();
}
