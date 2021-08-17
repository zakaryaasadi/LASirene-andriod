package Activity;

public interface IResponseData<T> {

    void onRefreshResponse(T data);

    void onFirstResponse(T data);

    void onPageResponse(T data);
}
