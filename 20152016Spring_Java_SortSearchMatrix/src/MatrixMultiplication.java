import java.util.Random;
public class MatrixMultiplication {
	public int N = 2500;
	private int[][] c = new int[N][N];
	private int[][] b = new int[N][N];
	private int[][] a = new int[N][N];
	public MatrixMultiplication(){
		Random Generator = new Random();
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				b[i][j] = Generator.nextInt(10000);
				a[i][j] = Generator.nextInt(10000);
				c[i][j] = 0;
			}
		}
	}
	public void Multiplication(){
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				for(int k=0;k<N;k++){
					c[i][j] = c[i][j] + a[i][k]*b[i][j];
				}
			}
		}
	
	}
	
	public void Display(){
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				System.out.print(c[i][j]+"-");
			}
			System.out.println();
		}
		
	}
}
