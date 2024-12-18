import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleThreadCrawlerTask implements Runnable {

    HtmlParser htmlParser;
    String url;
    Thread t;


    GenericSynchronizedQueue<String> q;

    public SingleThreadCrawlerTask(String url, HtmlParser htmlParser, GenericSynchronizedQueue<String> q) {
        this.url = url;
        this.htmlParser = htmlParser;
        this.q = q;
        t = new Thread(this, "individual_crawler");
    }

    @Override
    public void run() {
        List<String> urls = htmlParser.getUrls(url);
        for (String url : urls) {
            if (!q.contains(url)) {
                q.incrementTotalRunningTasks();
                q.offer(url);
            }
        }
        q.decrementTotalRunningTasks();
    }
}
