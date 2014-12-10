package edu.umn.d.grenoble.mhcs.cfinder;

import com.google.gwt.user.client.Window;

public abstract class Shape {
    public static Shape[] shapes = new Shape[]{
        new Shape(){
            @Override
            public Layout getLayout(final int count, final float ratio) {
                Layout layout = new Layout(count, 1);
                for (int i = 0; i < count; i += 1){
                    layout.set(i, 1, true);
                }
                return layout;
            }
            
            @Override
            public String getName(){
                return "Line";
            }
        },
        new Shape(){
            @Override
            public Layout getLayout(final int count, final float ratio) {
                Layout layout;
                final int MinBoxSideLen = 4;
                //Includes one corner
                final int MinBoxNeeded = 4 * MinBoxSideLen;
                
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
                        if(yPositions[i] > height){
                            height = yPositions[i]; 
                        }
                    }

                    // not enough to make a full box
                    layout = new Layout(width + 1, height + 1);
                    for(int i = 0; i < count; i+=1){
                        layout.set(xPositions[i], yPositions[i], true);
                    }
                } else {
                    float remaining = count - MinBoxNeeded;
                    int extraForHeight = (int) Math.round(Math.floor(remaining * ratio));
                    int height = MinBoxSideLen + extraForHeight / 2;
                    int width = (count - 2 * height) / 2;
                    
                    layout = new Layout(width + 1,height + 1);
                    for (int i = 0; i <= width; i += 1){
                        layout.set(i, 0, true);
                        layout.set(i, height, true);
                    }
                    for (int i = 0; i <= height; i += 1){
                        layout.set(0, i, true);
                        layout.set(width, i, true);
                    }
                }
                return layout;
            }
            
            @Override
            public String getName(){
                return "Box";
            }
        },
    };
    
    private Shape(){};
    abstract public Layout getLayout(final int count, final float ratio);
    abstract public String getName();
    

}
