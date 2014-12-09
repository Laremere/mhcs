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
                    /* 14 13 12 11 10
                     * 15          9
                     * 16          8
                     * 1           7
                     * 2  3  4  5  6
                     */
                    final int[] xPositions = new int[]{0,0,1,2,3,4,4,4,4,4,3,2,1,0,0,0};
                    final int[] yPositions = new int[]{1,0,0,0,0,0,1,2,3,4,4,4,4,4,3,2};
                    int width = 0;
                    int height = 0;
                    for(int i = 0; i < count; i+=1){
                        if(xPositions[i] > width){
                            width = xPositions[i]; 
                        }
                        if(xPositions[i] > height){
                            height = yPositions[i]; 
                        }
                    }

                    // not enough to make a full box
                    layout = new Layout(width + 1, height + 1);
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
    

}
