
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTestMain {

    public static void main(String[] arg0) throws Exception{
        RunnableThread runnableThread = new RunnableThread();
//        runnableThread.runnableThread();

        ThreadThread threadThread = new ThreadThread();
//        threadThread.runnableThread();

        CallBackThread callBackThread = new CallBackThread();
//        String call = callBackThread.call();
//        System.out.println(call);

        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(runnableThread);
        es.submit(threadThread);
        es.submit(callBackThread);
        es.shutdown();

    }

    public static class ThreadThread extends Thread {
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("extends Thread " + i);
            }
        }
    }

    public static class RunnableThread implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("implements Runnable " + i);
            }
        }
    }

    public static class CallBackThread implements Callable {

        @Override
        public String call() throws Exception {
            for (int i = 0; i < 10; i++) {
                System.out.println("implements Callable " + i);
            }
            return "This is Callback String!";
        }
    }
}
