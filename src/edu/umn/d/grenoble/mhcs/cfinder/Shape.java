package edu.umn.d.grenoble.mhcs.cfinder;

public abstract class Shape {
    public Shape[] shapes = new Shape[]{
        new Shape(){
            @Override
            Layout getLayout(final int count, final float ratio) {
                Layout layout = new Layout(count, 1);
                for (int i = 0; i < count; i += 1){
                    layout.set(i, 1, true);
                }
                return layout;
            }
            
            @Override
            String getName(){
                return "Line";
            }
        },
        new Shape(){
            @Override
            Layout getLayout(final int count, final float ratio) {
                Layout layout;
                final int MinBoxNeeded = 16;
                final int MinBoxSideLen = 5;
                if (count < MinBoxNeeded) { 
                    // not enough to make a full box
                    layout = new Layout(MinBoxSideLen,MinBoxSideLen);
                    /* 14 13 12 11 10
                     * 15          9
                     * 16          8
                     * 1           7
                     * 2  3  4  5  6
                     */
                    final int[] xPositions = new int[]{0,0,1,2,3,4,4,4,4,4,3,2,1,0,0,0};
                    final int[] yPositions = new int[]{1,0,0,0,0,0,1,2,3,4,4,4,4,4,3,2};
                    for(int i = 0; i < count; i+=1){
                        layout.set(xPositions[i], yPositions[i], true);
                    }
                } else {
                    layout = new Layout(MinBoxSideLen,MinBoxSideLen);
                    final int[] xPositions = new int[]{0,0,1,2,3,4,4,4,4,4,3,2,1,0,0,0};
                    final int[] yPositions = new int[]{1,0,0,0,0,0,1,2,3,4,4,4,4,4,3,2};
                    for(int i = 0; i < MinBoxNeeded; i+=1){
                        layout.set(xPositions[i], yPositions[i], true);
                    }
                }
                return layout;
            }
            
            @Override
            String getName(){
                return "Box";
            }
        },
    };
    
    abstract Layout getLayout(final int count, final float ratio);
    abstract String getName();
    
    class Layout{
        private int width;
        private int height;
        private boolean[] arr;
        Layout(final int width_, final int height_){
            this.width = width_;
            this.height = height_;
            this.arr = new boolean[this.width * this.height];
        }
        
        boolean get(final int x, final int y){
            if (x >= this.width || y >= this.height || x < 0 || y < 0){
                return false;
            }
            return this.arr[y * this.width + x];
        }
        
        void set(final int x, final int y, final boolean value){
            this.arr[y * this.width + x] = value;
        }
        
        public int SpotCount(){
            int count = 0;
            for (int i = -1; i <= this.width; i += 1){
                for (int j = -1; j <= this.height; j += 1){
                    if(this.get(i + 1, j) || this.get(i - 1, j) ||
                            this.get(i, j + 1) || this.get(i, j - 1)){
                        count += 1;
                    }
                }            
            }
            
            return count;
        }
    }
}
