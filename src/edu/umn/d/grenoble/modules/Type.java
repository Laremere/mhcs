package edu.umn.d.grenoble.modules;

public enum Type {
    Invalid ("INVALID", -1, -1),
    Plain ("Plain", 1, 40),
    Dormitory ("Dormitory", 61, 80),
    Sanitation ("Sanitation", 91, 100),
    FoodWater ("Food & Water", 111, 120),
    Gym ("Gym & Relaxation", 131, 134),
    Canteen ("Canteen", 141, 144),
    Power ("Power", 151, 154),
    Control ("Control", 161, 164),
    Airlock ("Airlock", 171, 174),
    Medical ("Medical", 181, 184);
    
    private final String plainName;
    private final int startId;
    private final int endId;
    
    Type(String plainName, int startId, int endId) {
        this.plainName = plainName;
        this.startId = startId;
        this.endId = endId;
    }
    
    public String getPlainName() {
        return this.plainName;
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