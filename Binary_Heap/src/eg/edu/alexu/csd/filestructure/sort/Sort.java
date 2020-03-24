package eg.edu.alexu.csd.filestructure.sort;

import eg.edu.alexu.csd.filestructure.sort.IHeap;
import java.util.ArrayList;

public class Sort<T extends Comparable<T>> implements ISort<T> {

    @Override
    public IHeap<T> heapSort(ArrayList<T> unordered) {
        Heap<T> heap = new Heap<T>();
        if (unordered==null) {
            return heap;
        }
        return heap.sort(unordered);


    }

    @Override
    public void sortSlow(ArrayList<T> unordered) {
        if(unordered == null) {
            return;
        }
        int i, j;
        int n = unordered.size();
        for (i = 0; i < n - 1; i++) {
            for (j = 0; j < n - i - 1; j++) {
                if (unordered.get(j).compareTo(unordered.get(j + 1)) > 0) {
                    T temp = unordered.get(j);
                    unordered.set(j, unordered.get(j + 1));
                    unordered.set(j + 1, temp);
                }
            }
        }
    }

    private void merge(ArrayList<T> arr, int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;

        ArrayList<T> L = new ArrayList<>();
        ArrayList<T> R = new ArrayList<>();

        for (int i = 0; i < n1; ++i)
            L.add(arr.get(l + i));
        for (int j = 0; j < n2; ++j)
            R.add(arr.get(m + 1 + j));


        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2)
        {
            if (L.get(i).compareTo(R.get(j)) <= 0) {
                arr.set(k, L.get(i));
                i++;
            } else {
                arr.set(k, R.get(j));
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr.set(k, L.get(i));
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr.set(k, R.get(j));
            j++;
            k++;
        }
    }


    private void sort(ArrayList<T> arr, int l, int r)
    {
        if (l < r)
        {
            // Find the middle point
            int m = (l+r)/2;

            // Sort first and second halves
            sort(arr, l, m);
            sort(arr , m+1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }
    @Override
    public void sortFast(ArrayList<T> unordered) {
        if(unordered == null) {
            return;
        }
        sort(unordered, 0, unordered.size() - 1);
    }

}