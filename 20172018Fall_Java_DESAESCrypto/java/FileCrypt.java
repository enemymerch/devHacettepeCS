

public class FileCrypt {

    public static void main(String[] args) {

        InputHandler IH = InputHandler.getInstance();

        try {
            IH.getInput(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

	}

}
