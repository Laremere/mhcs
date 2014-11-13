package edu.umn.d.grenoble.modules;

public enum Status {
    Unknown ("Unkown"),
    Good ("Good"),
    NeedsRepair ("Needs Repair"),
    Damaged ("Damaged");
    
    private final String name;
    
    private Status(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
