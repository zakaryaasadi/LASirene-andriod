package Activity;

public interface IFetchService<T> {

    void firstFetchDataFromApi();
    void refreshFetchDataFromApi();
    void fetchDataFromApi(int page);

}
