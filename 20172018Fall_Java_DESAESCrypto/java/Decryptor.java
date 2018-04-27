
public class Decryptor {

    public static void decrypt(String inputFile, String outputFile, String enc_alg, String mode, String keyFile, int threadCount) {
        try {
        	Crypto myCyrpter = new Crypto(inputFile, outputFile, keyFile, enc_alg, mode, threadCount);
        	myCyrpter.decrypt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
