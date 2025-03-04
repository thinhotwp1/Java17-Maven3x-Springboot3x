package ldt.demo.base_project;

public class StringPerformanceTest {
    /**
     * <p>String là immutable - bất biến, nên khi gán một giá trị sẽ tạo một biến mới -> nhiều rác trong heap.</p>
     * <p>String Builder tiết kiệm hơn String vì thay đổi giá trị ban đầu mà không tạo ra mới</p>
     * <p>String Builder nhanh hơn, String Buffer an toàn trong môi trường đa luồng vì các hàm đều sử dụng synchronize.</p>
     * <p>=> Sử dụng String khi chuỗi không đổi.</p>
     * <p>=> Sử dụng String Builder khi chuỗi thay đổi và sử dụng trong môi trường đơn luồng
     * .</p>
     * <p>=> Sử dụng String Buffer khi chuoi thay doi va su dung trong moi truong da luong (it dung).</p>
     */

    public static void main(String[] args) throws InterruptedException {
        stringAndStringBufferHeap();

        stringBuilderAndStringBufferSpeed();
    }

    private static void stringAndStringBufferHeap() throws InterruptedException {
        System.gc(); // Gọi GC trước khi bắt đầu đo
        long heapBeforeS = getUsedMemory();

        String string = "1";
        for (int i = 0; i < 100000; i++) {
            string = string + i;
        }

        long heapAfterS = getUsedMemory();
        System.out.println("String Memory Used: " + (heapBeforeS - heapAfterS) + " bytes");

        Thread.sleep(500L);

        System.gc(); // Gọi GC trước khi bắt đầu đo
        long heapBeforeSB = getUsedMemory();

        StringBuilder sb = new StringBuilder("1");
        for (int i = 0; i < 100000; i++) {
            sb.append(i);
        }

        long heapAfterSB = getUsedMemory();
        System.out.println("StringBuilder Memory Used: " + (heapBeforeSB - heapAfterSB) + " bytes");

        /** Result:
         * String Memory Used: 87940576 bytes (87,9 MB)
         * StringBuilder Memory Used: 1278672 bytes (12,7 MB)
         */
    }

    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static void stringBuilderAndStringBufferSpeed() {
        long startTime, endTime;
        int iterations = 100000;

        // Test StringBuffer
        StringBuffer sbf = new StringBuffer();
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            sbf.append("a");
        }
        endTime = System.nanoTime();
        System.out.println("StringBuffer time: " + (endTime - startTime) + " ns");

        // Test StringBuilder
        StringBuilder sb = new StringBuilder();
        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            sb.append("a");
        }
        endTime = System.nanoTime();
        System.out.println("StringBuilder time: " + (endTime - startTime) + " ns");

        /** Result:
         * StringBuffer time: 7398800 ns (7.39 ms)
         * StringBuilder time: 2626900 ns (2.62 ms)
         **/
    }
}
