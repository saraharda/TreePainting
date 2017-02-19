import java.util.Arrays;

/**
 * A BinarySearch class that implements the binary search algorithm. 
 * @author Sara Harda
 **/
public class BinarySearch {
	
    /** 
      * The main method of the BinarySearch class
      * Calls the oddTester method
     **/
	public static void main(String[] args) 
	{
		oddTester();
	}

	/** 
	 * Searches the sorted array for the test int. 
	 * Assumes the array is sorted in increasing order (smallest at index 0).
	 * If test is found, returns its index; otherwise, returns -1.
     * @param sorted the sorted array 
     * @test the test value
	 **/
	public static int binarySearch( int[] sorted, int test )
	{
		// start the recursion between first and last indices
		return binarySearch( sorted, test, 0, sorted.length -1 );
	}

	/** 
	 * Searches the sorted array for the test number between loIndex and hiIndex, inclusive. 
	 * Assumes the array is sorted in increasing order (smallest at index 0).
	 * If test is found, returns its index; otherwise, returns -1.
     * @param sorted the sorted array 
     * @test the test value
     * @param loIndex the lowest index of the array
     * @param hiIndex the highest index of the array
	 **/
	private static int binarySearch( int[] sorted, int test, int loIndex, int hiIndex )
	{
        // Returns -1 if the high index is lower than the low index
		if (hiIndex < loIndex) 
        {
            return -1;
		}
        // Sets the middle index halfway between the high index and the low index of the array
        int midIndex = (hiIndex + loIndex) / 2;
        
        // Returns the middle index if the value in it is equal to the test value, 
        if (sorted[midIndex] == test) {
            return midIndex;
        // Finds the position of the test value within the sorted array if it is not equal the middle index value
        } else if (sorted[midIndex] > test) {
            return binarySearch(sorted, test, loIndex, midIndex - 1);
        } else {
            return binarySearch(sorted, test, midIndex + 1, hiIndex);
        }
    }  

    /**
     * Creates an array of the first 100 odd numbers
     * Creates an array of the following tester values: 26, 78 ,100, 186, 13, 99, 101, 177
     * Loops through the array of tester values and prints out the result of searching
     **/     
    private static void oddTester() {

        // Creates an aray of 200 indexes 
    	int[] oddNum = new int[200];

        // Initializes the value of the first odd number to 1
        int odd = 1;

        // Sets the values of the array to the first 100 odd numbers 
        for(int i=0; i<oddNum.length;i++) 
        {
        		oddNum[i]= odd;
        	    odd = odd+2;
        }
        
        // Creates an array of test values
        int[] tester = {26, 78, 100, 186, 13, 99, 101, 177};

        // Tests the values created in the tester array to check if the binary search code works
        for (int i = 0; i< tester.length; i++ ) 
        {
            System.out.println("searching for " + tester[i] + " : " + binarySearch(oddNum, tester[i]));
        }
    }
}