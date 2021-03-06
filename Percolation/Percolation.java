/**
 * Created by Xuyu Yi on 2017/5/21.
 * For the assignment 1 of Algorithms
 * A scientific application of the union–find data structure
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF ufTop;
    private WeightedQuickUnionUF uf;
    private int n;
    private int count=0;
    private boolean[ ][ ] grid;


    public Percolation(int n) {
        if(n<=0){
            throw new IllegalArgumentException("argument must be positive");
        }
        this.n=n;
        grid=new boolean[n][n];
        ufTop = new  WeightedQuickUnionUF(n*n+1);
        uf = new  WeightedQuickUnionUF(n*n+2);           //
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                grid[i][j] = false;
            }
        }
    } // create n-by-n grid, with all sites blocked

    public    void open(int row, int col) {
        if((row>n)||(row<1)||(col>n)||(col<1)){
            throw new IndexOutOfBoundsException("argument is outside its prescribed range");
        }
        if(!grid[row-1][col-1]){
            grid[row-1][col-1] = true;
            count++;
            int index1=(row-1)*n+col;
            int index2;
            if(row==1){
                uf.union(0,index1);
                ufTop.union(0,index1);
            }
            if(row==n){
                uf.union(n*n+1,index1);
            }
            if((row-1)>=1){
                if(grid[row-2][col-1]){
                    index2=index1-n;
                    uf.union( index1, index2);
                    ufTop.union( index1, index2);
                    //uf.union(22 ,7);
                }
            }
            if((row+1)<=n){
                if(grid[row][col-1]){
                    index2=index1+n;
                    uf.union(index1 , index2);
                    ufTop.union( index1, index2);
                }
            }
            if((col+1)<=n){
                if(grid[row-1][col]){
                    index2=index1+1;
                    uf.union(index1 , index2);
                    ufTop.union( index1, index2);
                }
            }
            if((col-1)>=1){
                if(grid[row-1][col-2]){
                    index2=index1-1;
                    uf.union(index1 , index2);
                    ufTop.union( index1, index2);
                }
            }
        }
    }// open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        if((row>n)||(row<1)||(col>n)||(col<1)){
            throw new IndexOutOfBoundsException("argument is outside its prescribed range");
        }
        if(grid[row-1][col-1]){
            return true;
        }else return false;
    }// is site (row, col) open?

    public boolean isFull(int row, int col)  {
        if((row>n)||(row<1)||(col>n)||(col<1)){
            throw new IndexOutOfBoundsException("argument is outside its prescribed range");
        }
        int index1=(row-1)*n+col;
        return ufTop.connected(0,index1);
    }// is site (row, col) full?

    public     int numberOfOpenSites(){
        return count;
    }       // number of open sites

    public boolean percolates()  {
        return uf.connected(0,n*n+1);
    }
    // does the system percolate?


    public static void main(String[] args) {

        Percolation p = new Percolation(5);
        p.open(1,1);
        p.open(2,3);
        p.open(3,2);
        p.open(1,3);
        p.open(3,1);
        System.out.println(p.percolates());
        p.open(4,1);
        p.open(5,1);
        System.out.println(p.percolates());
        System.out.println(p.numberOfOpenSites());
        p.open(3,3);
        System.out.println(p.percolates());
    }

}
