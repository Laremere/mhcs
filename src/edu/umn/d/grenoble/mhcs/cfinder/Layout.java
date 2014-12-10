package edu.umn.d.grenoble.mhcs.cfinder;

public class Layout{
    private int width;
    private int height;
    private boolean[] arr;
    Layout(final int width_, final int height_){
        this.width = width_;
        this.height = height_;
        this.arr = new boolean[this.width * this.height];
    }
    
    public boolean get(final int x, final int y){
        if (x >= this.width || y >= this.height || x < 0 || y < 0){
            return false;
        }
        return this.arr[y * this.width + x];
    }
    
    public boolean isSpot(final int x, final int y){
        return (!this.get(x, y)) && (
            this.get(x + 1, y) || this.get(x - 1, y) ||
            this.get(x, y + 1) || this.get(x, y - 1)
            );
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
    
    void set(final int x, final int y, final boolean value){
        this.arr[y * this.width + x] = value;
    }
    
    public int SpotCount(){
        int count = 0;
        for (int i = -1; i <= this.width; i += 1){
            for (int j = -1; j <= this.height; j += 1){
                if(this.isSpot(i,j)){
                    count += 1;
                }
            }            
        }
        
        return count;
    }
}