public class Sorting {
    public static void main(String[] args){

        int[][] grid = {{0,1,0},{0,0,1},{1,1,1},{0,0,0}};
        int[] arr = {2,4,5,0,1,3};
        Sorting hl = new Sorting();
        hl.insertionSort(arr);
        System.out.println();
    }


    void insertionSort(int[] arr){
        for(int i = 1 ; i < arr.length ;i++){
            int j = i-1, key = arr[i];
            while(j >=0 && arr[j] > key ){
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = key;
        }
    }
}
