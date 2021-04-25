package start;



/**
 * Ҫдע��ѽ
 */
public class Start {
    private static final Integer TIMES=300;
    private static final String BASE_PATH="/Users/mac/fuck1/datamin/mining/src/main/java";
    static void single(){
        DataMining_CART.Client.startTime(TIMES,BASE_PATH);
        DataMining_ID3.Client.startTime(TIMES,BASE_PATH);
        DataMining_NaiveBayes.Client.startTime(TIMES,BASE_PATH);
    }
    static void withThread(){
        DataMining_CART.Client.startTime(TIMES,BASE_PATH);
        DataMining_ID3.Client.startTime(TIMES,BASE_PATH);
        DataMining_NaiveBayes.Client.startTime(TIMES,BASE_PATH);
    }
    public static void main(String[] args) throws InterruptedException {
        Start.single();
        Start.withThread();
    }
}
