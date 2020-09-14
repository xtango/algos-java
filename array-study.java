import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Program
{
    static Integer inPlaceRemove( ArrayList<Integer> arr, int idx) {
        return arr.remove(idx);
    }
    
    static void inPlaceMult(int[] arr, int x) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] *= x;
        }
    }
    
    static int[] mult(int[] arr, int x) {
        int[] newArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i] * 3;
        }
        return newArr;
    }
        
    static ArrayList<Integer> mult(ArrayList<Integer> al, int x) {
        return al.stream().map(a -> a * x).collect(Collectors.toCollection(ArrayList::new));
    }
    
    static ArrayList<Integer> onlyOdds(ArrayList<Integer> al) {
        return al.stream()
                .filter(x -> x % 2 == 1)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
	public static void main(String[] args) {
		ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(1,2,5,8));
 		System.out.println(arr);
        System.out.format("only odds: %s", onlyOdds(arr).toString());
        
	}
}
