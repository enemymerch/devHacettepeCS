import java.util.Random;

public class BubbleSort {
	public int N = 2500;
	private int[] a = new int[N];
	
	public BubbleSort(){
		Random Generator = new Random();
		for(int i=0;i<N;i++){
			a[i] = Generator.nextInt(10000);
			
		}
	}
	public void bSort(){
		int temp;
		for(int i=0;i<N-1;i++){
			for(int j=0;j<N-i-1;j++){
				if(a[j]>a[j+1]){
					temp = a[j];
					a[j] = a[j+1];
					a[j+1] = temp;
				}
			}
		}
	}
	public void DisplayArray(){
		for(int i=0;i<N;i++){
			System.out.print(a[i]+ " ");
		}
	}
}
