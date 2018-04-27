import java.util.*;
public class Main {
	public static void main(String argv[]){
		int N = 2500;
		System.out.println("Let's Begin !\n\n");
		
		// Matrix Multiplication
		MatrixMultiplication M1 = new MatrixMultiplication();
		long ntime1 = System.nanoTime();
		M1.Multiplication();
		long ntime2 = System.nanoTime();
		System.out.println("Matrix Multiplication"+
					"\nTime Taken : "+(ntime2-ntime1)+"nanos");
		
		
		// Finding Maximum Element 
		FindingMaxElement M2 = new FindingMaxElement();
		ntime1 = System.nanoTime();
		M2.FindMax();
		ntime2 = System.nanoTime();

		System.out.println("\nFinding Maximum Element"+
					"\nTime Taken : "+(ntime2-ntime1)+"nanos");
		
		//BubbleSort
		BubbleSort M3 = new BubbleSort();
		ntime1 = System.nanoTime();
		M3.bSort();
		ntime2 = System.nanoTime();
		System.out.println("\nBubble Sort "+ 
					"\nTime Taken : "+ (ntime2-ntime1)+"nanos");
		
		//MergeSort
	     int[] a;
	     a = getRandomArray(N);
	     ntime1 = System.nanoTime();
	     mergeSort(a);
	     ntime2 = System.nanoTime();
	 	 System.out.println("\nMerge Sort "+ 
	 				"\nTime Taken : "+ (ntime2-ntime1)+"nanos");
	 	
		ntime1 = System.nanoTime();
		int index = BinarySearch(a, 5100, 0, N-1);
		ntime2 = System.nanoTime();
	 	 System.out.println("\nBinarySearch "+ 
					"\nTime Taken : "+ (ntime2-ntime1)+"nanos");
	}
	public static int BinarySearch(int[] a, int value, int left, int right){
		int mid;
		while(left <= right){
			mid = ((right-left)/2)+left;
			if(a[mid] == value)
				return mid;
			if(value < a[mid])
				right = mid-1;
			else
				left = mid+1;
		}
		return -1;
	}
	public static int[] getRandomArray(int N){
		int[] a = new int[N];
		Random Generator = new Random();
		for(int i=0;i<N;i++){
			a[i] = Generator.nextInt(10000);
		}
		return a;
	}
    public static void mergeSort(int[] array) {
        if (array.length > 1) {

            int[] left = leftHalf(array);
            int[] right = rightHalf(array);
            

            mergeSort(left);
            mergeSort(right);

            merge(array, left, right);
        }
    }
    
    public static int[] leftHalf(int[] array) {
        int size1 = array.length / 2;
        int[] left = new int[size1];
        for (int i = 0; i < size1; i++) {
            left[i] = array[i];
        }
        return left;
    }
    
    public static int[] rightHalf(int[] array) {
        int size1 = array.length / 2;
        int size2 = array.length - size1;
        int[] right = new int[size2];
        for (int i = 0; i < size2; i++) {
            right[i] = array[i + size1];
        }
        return right;
    }
    
    public static void merge(int[] result, 
                             int[] left, int[] right) {
        int i1 = 0;   // index into left array
        int i2 = 0;   // index into right array
        
        for (int i = 0; i < result.length; i++) {
            if (i2 >= right.length || (i1 < left.length && 
                    left[i1] <= right[i2])) {
                result[i] = left[i1];    // take from left
                i1++;
            } else {
                result[i] = right[i2];   // take from right
                i2++;
            }
        }
    }
}
