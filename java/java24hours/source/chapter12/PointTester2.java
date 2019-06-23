import java.awt.*;

class PointTester2 {
    public static void main(String[] arguments) {
        Point4D object1 = new Point4D(10,22,71,26);

        System.out.println("The 4D point is located at ("
            + object1.x + ", "
            + object1.y + ", "
            + object1.z + ", "
            + object1.getTime() + ")");
        System.out.println("\tIt's moving to (10, 22, 71, 27)");
        object1.move(10, 22, 71, 27);
        System.out.println("The 4D point is now at ("
            + object1.x + ", "
            + object1.y + ", "
            + object1.z + ", "
            + object1.getTime() + ")");
        System.out.println("\tIt's moving 20 units on the t axes.");
        object1.translate(0, 0, 0, 20);
        System.out.println("The 4D point ends up at ("
            + object1.x + ", "
            + object1.y + ", "
            + object1.z + ", "
            + object1.getTime() + ")");
    }
}
