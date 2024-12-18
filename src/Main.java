import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        System.out.println(" HELLO");


        String firstUrl = "http://vamsi.com/home/0";

        HtmlParser htmlParser = new HtmlParserImpl();
        System.out.println(" START CRAWLING ");


        List<String> crawledUrls = crawl(firstUrl, htmlParser);
        System.out.println(crawledUrls);


        System.out.println(" DONE CRAWLING : total size of urls crawled " + crawledUrls.size());


    }

    private static List<String> crawl(String start, HtmlParser htmlParser) {

        GenericSynchronizedQueue<String> q = new GenericSynchronizedQueueImpl<>();

        q.incrementTotalRunningTasks();
        q.offer(start);

        List<SingleThreadCrawlerTask> tasks = new ArrayList<>();


        while (q.getTotalRunningTasks() > 0) {
            String url = q.poll();

            SingleThreadCrawlerTask childThread = new SingleThreadCrawlerTask(url, htmlParser, q);
            childThread.t.start();

            tasks.add(childThread);

        }

//        System.out.println(" WAITING FOR CHILD THREADS ");
//        for (SingleThreadCrawlerTask task : tasks) {
//            task.t.join();
//        }


        return q.getAll();
    }
}