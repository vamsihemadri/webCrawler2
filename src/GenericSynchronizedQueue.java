import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface GenericSynchronizedQueue<T> {


    boolean offer(T obj);

    T poll();

    boolean isEmpty();

    boolean contains(T obj);


    List<T> getAll();


    void incrementTotalRunningTasks();

    void decrementTotalRunningTasks();

    int getTotalRunningTasks();
}
