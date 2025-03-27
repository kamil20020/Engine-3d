package pl.engine;

public class Matrix {

    private int[][] m;

    public Matrix(int[][] m){

        this.m = m;
    }

    public Matrix(int numberOfRows, int numberOfColumns){

        this.m = new int[numberOfRows][numberOfColumns];

        for(int i = 0; i < numberOfRows; i++){

            this.m[i] = new int[numberOfColumns];
        }
    }

    public int get(int row, int col) throws IllegalArgumentException{

        if(row < 0 || row >= getNumberOfRows()){
            throw new IllegalArgumentException("Invalid row number: " + row + " for " + getHeader() + " matrix");
        }

        if(col < 0 || col >= getNumberOfCols()){
            throw new IllegalArgumentException("Invalid col number: " + row + " for " + getHeader() + " matrix");
        }

        return this.m[row][col];
    }

    public int getNumberOfRows(){
        return m.length;
    }

    public int getNumberOfCols(){
        return m[0].length;
    }

    public Matrix multiply(Matrix m1) throws IllegalArgumentException{

        if(this.getNumberOfCols() != m1.getNumberOfRows()){
            throw new IllegalArgumentException("Number of columns of first matrix must be equal to number of rows of second matrix");
        }

        Matrix result = new Matrix(this.getNumberOfRows(), m1.getNumberOfCols());

        for(int r = 0; r < result.getNumberOfRows(); r++){

            for(int i = 0; i < this.getNumberOfCols(); i++){

                int sum = 0;

                for(int c = 0; c < this.getNumberOfCols(); c++){

                    sum += this.m[r][c] * m1.m[c][i];
                }

                result.m[r][i] = sum;
            }
        }

        return result;
    }

    public static Matrix multiply(Matrix m, Matrix m1) throws IllegalArgumentException{

        return m.multiply(m1);
    }

    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        for (int[] row : this.m) {

            for (int c : row) {

                stringBuilder.append(c);
                stringBuilder.append(" ");
            }

            stringBuilder.append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }

    public String getHeader(){

        return getNumberOfRows() + " : " + getNumberOfCols();
    }

    public static void main(String[] args){

        int[][] m = {
            {1, 0, 1},
            {2, 1, 1},
            {0, 1, 1},
            {1, 1, 2}
        };

        int[][] m1 = {
            {1, 2, 1},
            {2, 3, 1},
            {4, 2, 2}
        };

        Matrix matrix = new Matrix(m);
        Matrix matrix1 = new Matrix(m1);

        System.out.println(matrix);
        System.out.println(matrix1);

        Matrix result = matrix.multiply(matrix1);
        Matrix result1 = Matrix.multiply(matrix, matrix1);

        System.out.println(result);
        System.out.println(result1);
    }
}
