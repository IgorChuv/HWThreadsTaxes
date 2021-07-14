import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

public class Main {

    public static final int workingDayHours = 8;
    public static final int maxIncomePerHour = 20000;

    public static int[] incomePerDay() {
        Random random = new Random();
        int[] incomePerHour = new int[workingDayHours];
        for (int i = 0; i < incomePerHour.length; i++) {
            incomePerHour[i] = random.nextInt(maxIncomePerHour);
        }
        return incomePerHour;
    }

    public static void main(String[] args) throws InterruptedException {

        LongAdder taxReportSum = new LongAdder();

        int[] reportOfStore1 = incomePerDay();
        int[] reportOfStore2 = incomePerDay();
        int[] reportOfStore3 = incomePerDay();

        Thread thread1 = new Thread(() -> Arrays.stream(reportOfStore1).forEach(taxReportSum::add), "Обработчик доходов магазина 1");
        Thread thread2 = new Thread(() -> Arrays.stream(reportOfStore2).forEach(taxReportSum::add), "Обработчик доходов магазина 2");
        Thread thread3 = new Thread(() -> Arrays.stream(reportOfStore3).forEach(taxReportSum::add), "Обработчик доходов магазина 3");

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println(taxReportSum.sum());
    }

}
