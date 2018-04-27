import java.util.Random;
public class FindingMaxElement {
	public int N = 2500;
	private int[] a = new int[N];
	
	public FindingMaxElement(){
		Random Generator = new Random();
		for(int i=0;i<N;i++){
			a[i] = Generator.nextInt(10000);
			
		}
	}
	public void FindMax(){
		int Max = a[0];
		for(int i=0;i<N;i++){
			if(Max<a[i])
				Max = a[i];
		}
	}
}
