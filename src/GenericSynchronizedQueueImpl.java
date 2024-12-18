import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class GenericSynchronizedQueueImpl<T> implements GenericSynchronizedQueue<T> {

    Set<T> visited;
    Queue<T> q;

    Semaphore qLock;
    AtomicInteger runningTasks;

    public GenericSynchronizedQueueImpl() {
        this.visited = Collections.synchronizedSet(new HashSet<>());
        this.q = new LinkedList<>();
        qLock = new Semaphore(1);
        runningTasks = new AtomicInteger();
    }


    @Override
    public boolean offer(T obj) {

        try {
            qLock.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        q.offer(obj);
        visited.add(obj);
        qLock.release();

        return true;


    }

    @Override
    public T poll() {

        try {
            qLock.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        T top = q.poll();

        qLock.release();
        return top;

    }

    @Override
    public boolean isEmpty() {
        return q.isEmpty();
    }

    @Override
    public boolean contains(T obj) {
        return visited.contains(obj);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(visited);
    }

    @Override
    public void incrementTotalRunningTasks() {
        runningTasks.incrementAndGet();
    }

    @Override
    public void decrementTotalRunningTasks() {
        runningTasks.decrementAndGet();
    }

    @Override
    public int getTotalRunningTasks() {
        return runningTasks.get();
    }

}
