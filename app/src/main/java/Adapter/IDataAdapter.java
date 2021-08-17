package Adapter;

import java.util.List;

public interface IDataAdapter<T> {
    void addAll(List<T> data);
    void renewData(List<T> data);
}
