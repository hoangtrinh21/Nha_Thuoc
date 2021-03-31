import Cortroller.Imports;
import Entities.Lists;

public class Main {
    public static void main(String[] args) {
        Lists lists = new Lists();
        Imports imports = new Imports();
        imports.importProduct(lists);
        imports.show(lists);
    }
}
