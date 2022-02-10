package DataObjects;

public final class DefaultProjectIns {
    private Default aDefault;

    private final static DefaultProjectIns PROJECT_INS = new DefaultProjectIns();

    private DefaultProjectIns () {}

    public static DefaultProjectIns getInstance() {return PROJECT_INS;}

    public void setProjectIns(Default aDefault1) {this.aDefault = aDefault1;}

    public Default getProjectIns() {return aDefault;}

}
