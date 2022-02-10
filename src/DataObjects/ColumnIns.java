package DataObjects;

public final class ColumnIns {
    private Column column;

    private final static ColumnIns COLUMN_INSTANCE = new ColumnIns();

    private ColumnIns () {}

    public static ColumnIns getInstance() {return COLUMN_INSTANCE;}

    public void setColumn(Column cl) {this.column = cl;}

    public Column getColumn() {return column;}
}
