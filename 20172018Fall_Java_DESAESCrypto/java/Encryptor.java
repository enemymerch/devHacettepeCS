


public class Encryptor {

    public static void encrypt(String inputFile, String outputFile, String enc_alg, String mode, String keyFile, int threadCount) {
        try {
        	Crypto myCyrpter = new Crypto(inputFile, outputFile, keyFile, enc_alg, mode, threadCount);
        	myCyrpter.encrypt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
