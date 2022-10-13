public class Sudoko {
    int N = 9;
    int[][] rows = new int[N][N+1];
    int[][] cols = new int[N][N+1];
    int[][] block = new int[N][N+1];

    public static void main(String[] args) {
        Sudoko bc = new Sudoko();
        char[][] b = {{'5','3','.','.','7','.','.','.','.'},{'6','.','.','1','9','5','.','.','.'},{'.','9','8','.','.','.','.','6','.'},{'8','.','.','.','6','.','.','.','3'},{'4','.','.','8','.','3','.','.','1'},{'7','.','.','.','2','.','.','.','6'},{'.','6','.','.','.','.','2','8','.'},{'.','.','.','4','1','9','.','.','5'},{'.','.','.','.','8','.','.','7','9'}};
        bc.solveSudoku(b);

    }

    boolean isSolved ;

    public void solveSudoku(char[][] board) {

        initialiseData(board);
        placeNumber(0,0,board);

    }

    private void placeNumber(int row, int col,char[][] board) {
        if(row == N && col == N) {
            isSolved = true;
            return;
        }
        if(col == N) {
            if(row ==N-1){
                isSolved = true;
                return;
            }
            placeNumber(row+1,0,board);
            return;
        }

        if (board[row][col] != '.') {
            placeNumber(row, col + 1, board);
            return;
        }

        for(int val = 1 ; val <= 9 ; val++) {
            if (board[row][col] == '.') {
                if(canBePlaced(row, col, board, val)){
                    addNumber(row, col, board, val);
                    placeNumber(row, col + 1, board);
                    if(!isSolved){
                        removeNumber(row,col,board,val);
                    }
                }
            }
        }
    }


    private void removeNumber(int row, int col, char[][] board, int val) {
        board[row][col] = '.';
        rows[row][val]--;
        cols[col][val]--;
        int blockNo = (row/3)*3+col/3;
        block[blockNo][val]--;
    }


    private void addNumber(int row, int col, char[][] board, int val) {
        board[row][col] = (char) (val+'0');
        rows[row][val]++;
        cols[col][val]++;
        int blockNo = (row/3)*3+col/3;
        block[blockNo][val]++;
    }

    private boolean canBePlaced(int row, int col, char[][] board,int val) {
        int blockNo = (row/3)*3+col/3;
        return rows[row][val]+cols[col][val]+ block[blockNo][val] == 0;
    }

    private void initialiseData(char[][] board) {
        for(int i  = 0 ; i < board.length ;i++){
            for(int j = 0 ; j< board[0].length ; j++){
                if(board[i][j] != '.'){
                    int val = Character.getNumericValue(board[i][j]);
                    rows[i][val]++;
                    cols[j][val]++;
                    int blockNo = (i/3)*3+j/3;
                    block[blockNo][val]++;
                }
            }
        }
    }
}
