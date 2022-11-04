package profITsoft.Task1;

import java.util.Arrays;
import java.util.Collections;

public class SortingArrayTask {

    // used Java 8 stream API
    public static int[] sortMassWithStream(int[] mass){
        return Arrays.stream(mass)
                .boxed()
                .filter(m -> m>=0)
                .sorted(Collections.reverseOrder())
                .mapToInt(x -> x)
                .toArray();
    }

    // I used quicksort here
    public static int[] vanillaSorting(int[] mass){

        // if using streams
        /*int[] filterMass = Arrays.stream(mass)
                .filter(x -> x>=0)
                .toArray();*/
        // or

        int n=0;
        int[] temporaryArray = new int[mass.length];
        for (int i = 0; i < mass.length; i++) {
            if(mass[i]>=0){
                temporaryArray[n] = mass[i];
                n++;
            }
        }
        int[] filterMass = Arrays.copyOf(temporaryArray,n);

        return quickSort(filterMass,0, filterMass.length-1);
    }

    // quicksort
    public static int[] quickSort(int[] mass, int minElement, int maxElement){
        if(mass.length==0){
            return mass;
        }
        if(minElement>=maxElement){
            return mass;
        }

        int middlePosition = minElement + (maxElement-minElement)/2;
        int middleValue = mass[middlePosition];

        int start = minElement;
        int finish = maxElement;

        while (start<=finish){
            while (middleValue<mass[start]){
                start++;
            }
            while (middleValue>mass[finish]){
                finish--;
            }
            if(start<=finish){
                int temp = mass[start];
                mass[start] = mass[finish];
                mass[finish] = temp;
                start++;
                finish--;
            }
        }

        if (minElement<finish){
            quickSort(mass,minElement,finish);
        }

        if(maxElement>start){
            quickSort(mass,start,maxElement);
        }

        return mass;
    }
}