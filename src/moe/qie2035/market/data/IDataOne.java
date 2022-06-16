package moe.qie2035.market.data;

public interface IDataOne<T, I> {
    T findOne(I in);

    void delOne(I in);
}
