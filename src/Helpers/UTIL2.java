package Helpers;

import java.util.*;

/**
 * Created by philippdeutsch on 16/05/2018.
 */

public class UTIL2 {


    public boolean b = false;
    public static int index = 0;

    public static int next = 1;
    public int notify = 0;

    public int[] defaultvalues = new int[]{3, 4, 5, 26, 27, 28, 29, 30, 51, 52, 53, 54, 55, 1, 2};
    public int[] grid, shuffled;
    public HashMap<Integer, Integer> map;
    public ArrayList<int[]> arrayList;

    public UTIL2() {
        grid = generateGrid(new int[]{1, 2, 3, 4}, new int[8], 4);
      //  grid = adjustGridOrder(grid);
        prepareData2();
        printData(2, 4);
    }

    public void reset() {
        index++;
        System.out.println(index);
        if (index == 8) {
            index = 0;
            prepareData2();
            System.out.println("new data");
            //prepareData();
            printData(2, 4);
            //if (notify >= 6) notify = 0;

        }
    }

    private void printData(int row, int len) {
        printMap(map, row, len);
        System.out.println();
        print(grid, row, len);
    }

    private void prepareData() {

        arrayList = res_order(new ArrayList<>(), defaultvalues);
        shuffled = fillRandomInteger(new int[65]);
        grid = generateGrid(arrayList.get(next), new int[65], 5);
        grid = getData(grid, next, notify);
        increments();
        map = calculateValues(new HashMap<>(), grid);

    }

    private void prepareData2() {
        shuffled = fillRandomInteger(new int[8]);
        grid = getData2(grid, next);
        map = calculateValues(new HashMap<>(), grid);

    }

    public int[] getData2(int[] result, int index) {
        int g = 8;
        int len = 4;
        int row = 2;
        for (int i = 0; i <= row-1; i++) {
            for (int j = 0; j <= len - 1; j++) {
                int k = i * len + j;
                result[k] = result[k] + (g * (index));
            }
        }
        return result;
    }

    private void increments() {
        notify++;
        next++;
    }

    public HashMap<Integer, Integer> calculateValues(HashMap<Integer, Integer> map, int[] arr) {


        // hashmap Key 1..10 Values zuweisen au den array positions.
        // HashMap<Integer, Integer> processing = reciprocal(map,index);

        for (int i = 0; i <= arr.length - 1; i++) {
            map.put(i, arr[i]);

        }
        return map;
    }

    public int[] getData(int[] result, int index, int notify) {

        final int[] arrayindex = new int[]{0, 2, 4, 6, 8, 10, 12};
        int g = 65;
        final int len = 13;
        int even = arrayindex[notify];
        if (comparsion(even, len)) {
            for (int i = 0; i <= 4; i++) {
                for (int j = 0; j <= len - 1; j++) {
                    int k = i * 13 + j;
                    if (comparsion(j, even)) {
                        result[k] = result[k] + (g * (index - 1));
                    } else {
                        result[k] = result[k] + (g * (index));
                    }
                }
            }
        }
        return result;
    }

    public boolean comparsion(int a, int b) {

        return a < b;
    }

    public int position(int a, int[] arr) {

        int where = 0;
        for (int i = 0; i <= arr.length - 1; i++) {
            if (a == arr[i]) {
                where = i;
            }
        }

        return where;
    }

    public HashMap<Integer, Integer> reciprocal(HashMap<Integer, Integer> processing, int index) {

        int g = 65;
        for (int i = 0; i <= 64; i++) {

            if (index == 0) {
                processing.put(i, i + 1 + (g * index));
            } else {
                int value = (int) (processing.get(i) * Math.pow(index, -1));
                processing.put(i, value);
            }
        }

        return processing;
    }

    public int[] getGrid(ArrayList<int[]> arrayList, int which) {

        int[] arr = arrayList.get(which);
        int[] grid = new int[65];

        //0/5/10/15/20/25/30..

        // nach jedem Fragment ist 5 * ++1 ;
        // nach jeden Block ist 5 * blocksize in length = 13;


        for (int i = 0; i < arr.length - 1; i++) {

        }


        return arr;
    }

    public ArrayList<int[]> res_order(ArrayList<int[]> arraylist, int[] arr) {

        int[] temp = arrange(arr, 13); // split at current position, remember items.
        System.out.println("this is TEMP ORDER " + temp.length);
        int[] temp2 = copy(temp, 13);

        for (int i = 0; i <= 60; i++) {

            arraylist.add(defer(temp2, 13));
            temp = arrange(temp, 13);
            temp2 = copy(temp, 13);

        }

        return arraylist;
    }

    public int[] defer(int[] arr, int length) {

        int[] temp3 = new int[length];


        for (int i = 0; i <= temp3.length - 1; i++) {
            temp3[i] = arr[i];
        }

        return temp3;

    }

    public int[] copy(int[] arr, int length) {

        int[] copy = new int[length];
        for (int i = 0; i <= copy.length - 1; i++) {
            copy[i] = arr[i];
            //arr[i] = copy[i];
        }

        return copy;
    }

    public int[] getArr(int[] arr, int index) {

        for (int i = 0; i <= arr.length - 1; i++) {

            arr[i] = (i + 50) * index;

        }
        return arr;
    }

    public HashMap<Integer, Integer> fillHashmap(HashMap<Integer, Integer> map, int index) {

        // Zuordnung der Keys auf die korrespondierende Hashmap.

        for (int i = 0; i <= 24; i++) {
            map.put(i, (i + 50) * index);
        }

        return map;
    }

    public int getKey() {

        int[] b = removeLast(shuffled, index);

        return b[b.length - 1];
    }

    public int[] removeLast(int[] a, int index) {

        // System.out.println("a.length: " + a.length);
        final int len = a.length - index;
        //System.out.println("length of array: " + len);
        int[] b = new int[len];
        int f;
        for (int j = len; j > 0; j--) {
            f = len - j;
            b[f] = a[a.length - 1 - f];
        }
        /**  for (int i = 0; i <= b.length - 1; i++) {
         System.out.print(b[i] + " ");
         }**/
        return b;

    }

    private void lol(int[] a, int gap, int[] b, int len) {
        int f;
        for (int j = len; j > gap; j--) {
            f = len - j;
            b[f] = a[f + gap + 1]; //   1+10,2+10,3+10,...  // " + 1 " korrespondiert mit j>gap.
            System.out.print(b[f] + " ");
        }
    }

    public int[] arrange(int[] a, int num) {

        int gap, f;
        int[] b;
        final int len = a.length - 1;
        final int which = num;
        int[] c = new int[a.length];

        for (int i = 0; i <= len; i++) {

            boolean bool = i == which - 1;
            gap = i;
            b = new int[getlength(len, gap)];

            if (bool) {

                for (int j = len; j > gap; j--) {
                    f = len - j;
                    b[f] = a[f + gap + 1]; //   1+10,2+10,3+10,...  // " + 1 " korrespondiert mit j>gap.

                }
                for (int k = len; k >= 0; k--) {
                    f = len - k;
                    if (k > gap) {
                        c[f] = b[f];
                        // System.out.print(c[f] + " ");
                    } else {
                        c[f] = a[f - getlength(len, gap)];
                        // System.out.print(c[f] + " ");

                    }
                }
                bool = false;
            }
        }
        return c;

    }

    public int[] removeValue(int[] a, int num) {

        int gap;
        final int len = a.length - 1;
        int[] c = new int[len];
        for (int i = 0; i <= len; i++) {
            boolean bool = a[i] == num;
            gap = i;
            if (bool) {
                int[] b = new int[getlength(len, gap)];
                int f;
                for (int j = len; j > gap; j--) {
                    f = len - j;

                    b[f] = a[f + gap + 1];
                    // " + 1 " korrespondiert mit j>gap. es soll nur die value kopiert werden die grösser gap ist.
                    // " + 1 " ist garantiert.
                }
                for (int k = 0; k <= c.length - 1; k++) {
                    if (k < gap) {
                        c[k] = a[k];
                        System.out.print(c[k] + " ");
                    } else {
                        c[k] = b[k - gap];
                        System.out.print(c[k] + " ");
                    }
                }
                System.out.println();
                System.out.println("length of array" + c.length);
                bool = false;
            }
        }

        return c;
    }

    public int getlength(int length, int gap) {
        return length - gap;
    }

    public static int[] fillRandomInteger(int[] arr) {
        for (int i = 0; i <= arr.length - 1; i++) {
            arr[i] = i;


        }
        return shuffled(arr);
    }

    public static void shuffle(int[] arr) {
        Random rand = new Random();

        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, i, rand.nextInt(i + 1));
            // System.out.println( arr[i]);

        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static int[] shuffled(int[] arr) {
        int[] copy = Arrays.copyOf(arr, arr.length);
        shuffle(copy);
        return copy;
    }

    public void supply(int[] arr, int index) {

        int value = 50 * index;

        for (int i = 0; i <= arr.length - 1; i++) {
            arr[i] = i + value;
            System.out.print(arr[i] + " ");

        }

    }

    public void box2() {

        int[] arr = new int[]{3, 4, 5, 26, 27, 28, 29, 30, 51, 52, 53, 54, 55, 1, 2};
        ArrayList<int[]> arrayList = res_order(new ArrayList<>(), arr);
        System.out.println();

        for (int i = 0; i <= arrayList.size() - 1; i++) {

            int[] arr1 = arrayList.get(i);
            for (int j = 0; j <= arr1.length - 1; j++) {

                System.out.print(arr1[j] + " ");
            }
            System.out.println();
        }
    }

    public void generateNext() {

        int[] arr = new int[]{1, 2, 3, 4, 25, 26, 27, 28, 29, 30, 51, 52, 53, 54, 55};
        int[] temp = copy(arr, 15);
        int sum = 0;
        int f = 5;


        for (int i = 0; i < 5; i++) {
            int g = 5;
            g = g * i;
            for (int j = 0; j <= temp.length - 1; j++) {

                temp[j] = arr[j] + g;

                System.out.print(temp[j] + " ");

            }


            System.out.println();
        }
    }

    public int[] generateGrid(int[] arr, int[] grid, int cellrange) {

        int[] temp = copy(arr, arr.length);
        final int len = temp.length;
        final int row = 1;


        for (int i = 0; i <= row; i++) {
            int g = cellrange;
            g = g * i;
            for (int j = 0; j <= temp.length - 1; j++) {
                temp[j] = arr[j] + g;
                grid[i * 4 + j] = temp[j];
            }
        }
        return grid;
    }

    public void print(int[] arr, int row, int len) {

        int row1 = row - 1;
        int len1 = len - 1;


        for (int i = 0; i <= row1; i++) {


            for (int j = 0; j <= len1; j++) {
                System.out.print(arr[i * len + j] + " ");

            }
            System.out.println();

        }

    }

    public void printMap(HashMap<Integer, Integer> map, int row, int len) {

        int row1 = row - 1;
        int len1 = len - 1;
        for (int i = 0; i <= row1; i++) {
            for (int j = 0; j <= len1; j++) {
                System.out.print(map.get(i * len + j) + " ");
            }
            System.out.println();
        }
    }


    public int[] cellOrder(int[] arr, int cellwidth, int times) {
        int[] result = arr;
        final int cellheigth = 4;

        //swap(arr,cellwidth-1,0);


        //swapLength(arr, cellwidth);


        return result;
    }

    private int[] adjustGridOrder(int[] arr) {
        int[] result = arr;
        int row = 4;
        int len = 4;
        int start = 1;
        int multiplacative = 0;
        int num = 4; //0,1,2,3
        final int[] index = new int[]{0, 1, 2, 3, 0};

        for (int i = 0; i <= row; i++) {
            int f = num * multiplacative;
            calculatePatterns(result, index[i], f, start, len);
            System.out.println();
            multiplacative += 3;
        }
        return result;
    }

    private void swapPatterns(int[] arr, int start, int len, int f) {
        for (int j = start - 1; j <= len - 1; j++) {
            int k = f + j;
            System.out.print("k: " + k + " ");
            int m = k + 4;
            System.out.println("m: " + m + " ");
            int n = k + 8;
            if (check(j, 3)) {
                swap(arr, k, k + 1);
                swap(arr, m, m + 1);
                swap(arr, n, n + 1);
            }
        }
    }

    private boolean check(int a, int b) {
        if (a < b) return true;
        return false;
    }

    private void calculatePatterns(int[] arr, int times, int f, int start, int len) {

        switch (times) {
            case 0:
                break;

            case 1:
                swapPatterns(arr, start, len, f);
                break;
            case 2:
                swapPatterns(arr, start, len, f);
                swapPatterns(arr, start, len, f);
                break;
            case 3:
                swapPatterns(arr, start, len, f);
                swapPatterns(arr, start, len, f);
                swapPatterns(arr, start, len, f);
                swapPatterns(arr, start, len, f);
                swapPatterns(arr, start, len, f);
                break;
            case 4:
                break;
        }
    }

    private void swapLength(int[] arr, int cellwidth, int times) {


        /** int num = cellwidth;
         int k = num - i;

         swap(arr, cellwidth, k);**/
    }

    public int[] makeArr(int len) {
        return new int[len];
    }

    public void fillArr(int[] arr) {
        for (int i = 0; i <= arr.length - 1; i++) {
            arr[i] = i;
        }
    }

    public void printArr(int[] arr, int row, int len) {
        for (int i = 0; i <= row - 1; i++) {
            for (int j = 0; j <= len - 1; j++) {
                int k = arr[i * len + j];
                System.out.print(k + " ");
            }
            System.out.println();
        }


    }


}