package edu.umn.d.grenoble.mhcs.cfinder;

public abstract class Shape {
    public Shape Line = new Shape(){
        private String[] params = new String[]{};

        @Override
        String[] getParams() {
            return this.params;
        }

        @Override
        Layout getLayout(final int count, final float[] params) {
            Layout layout = new Layout(count, 1);
            for (int i = 0; i < count; i += 1){
                layout.set(i, 1, true);
            }
            return layout;
        }
        
    };
    
    abstract Layout getLayout(int count, float[] params); 
    abstract String[] getParams();
    
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
