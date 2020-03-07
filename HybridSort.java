import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class HybridSort{

	public static void main(String args[])
	{

		double[] arr = {1.00, 2.00, 2.50, 3.00, 8.00, 5.00, 1.50, 30.00, 90.00, 45.00, 42.23, 73.00, 72.00, 200.00, 750.00, 260.00, 90.00, 5.00, 51.00};
		System.out.println("ThIs Is ThE uNsOrTeD aRrAy: " + Arrays.toString(arr));
		hybridsort(arr, 0, arr.length - 1);
		System.out.println("ThIs Is ThE sOrTeD aRrAy: " + Arrays.toString(arr));
	}


	/*QUICK SORT*/
	public static int quicksort(double [] arr, int left, int right){
	
            int pIndex = partition(arr, left, right);
            hybridsort(arr, left, pIndex - 1);
            hybridsort(arr, pIndex + 1, right);

            return pIndex;
    
	}

	public static int partition(double[] arr, int left, int right)
    {
        int pivot = pivot(left, right); //sets the pivot
        double value = arr[pivot];

        int l = left;
        int r = right;

        while(l < r)
        {
        	while(l <= right && arr[l] <= value){
        		l++;
        	}
        	while(r >= l && (r == pivot || arr[r] > value)){
        		r--;
        	}
        	if(l < r && l <= right){
        		swap(arr, l, r);
        	}
        }

        if(l < pivot){
        	swap(arr, l, pivot);
        	return l;
        }

        if(pivot < r){
        	swap(arr, pivot, r);
        	return r;
        }

        return pivot;

    }


	public static int pivot(int left, int right){ 
		int pivot = 0;

		Random random = new Random();
		pivot = random.ints(left, right+1).findFirst().getAsInt();

		return pivot;
	}

    

    public static void swap(double[] arr, int i, int j)
    {
        double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
	/*END OF QUICK SORT*/


	/*START OF QUADRATIC SORT , INSERTION SORT*/ //FIX THIS
	public static void quadraticsort(double [] arr, int left, int right){

		for(int i = left + 1; i <= right; i++){ 
			double temp = arr[i];
			int j = i - 1;

			while(j >= left && temp < arr[j])
			{
				arr[j + 1] = arr[j];
				--j;
			}

			arr[j+1] = temp;
		}

	}
	/*END OF QUADRATIC SORT, INSERTION SORT*/


	/*START OF HYBRID SORT*/
	public static void hybridsort (double [] arr, int left, int right){
		int subSize = 5;

		if(right - left < subSize){
			quadraticsort(arr, left, right);
		}

		else{
			quicksort(arr, left, right);
		}
	}
	/*END OF HYBRID SORT*/
	
}
