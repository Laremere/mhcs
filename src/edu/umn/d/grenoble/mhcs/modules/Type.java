package edu.umn.d.grenoble.mhcs.modules;

public enum Type {
    Invalid ("INVALID", -1, -1, null),
    Plain ("Plain", 1, 40, "Plain"),
    Dormitory ("Dormitory", 61, 80, "Dormitory"),
    Sanitation ("Sanitation", 91, 100, "Sanitation"),
    FoodWater ("Food & Water", 111, 120, "Food"),
    Gym ("Gym & Relaxation", 131, 134, "Gym"),
    Canteen ("Canteen", 141, 144, "Canteen"),
    Power ("Power", 151, 154, "Power"),
    Control ("Control", 161, 164, "Control"),
    Airlock ("Airlock", 171, 174, "Airlock"),
    Medical ("Medical", 181, 184, "Medical");
    
    private final String plainName;
    private final int startId;
    private final int endId;
    private final String filename;
    
    Type(String plainName, int startId, int endId, String filename) {
        this.plainName = plainName;
        this.startId = startId;
        this.endId = endId;
        this.filename = filename;
    }
    
    public String getPlainName() {
        return this.plainName;
    }
    
    public String getImageUrl() {
        if (filename == null) {
            return null;
        }        return "images/modules/" + filename + ".jpg";
    }
    
    public static Type getFromId(int id) {
        for (Type mt : Type.values()) {
            if (id >= mt.startId && id <= mt.endId) {
                return mt;
            }
        }
        return Invalid;
    }
    
}
