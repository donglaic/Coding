class Favorite {
     public static void main(String[] arguments) {
         // set up film information
         String favorite = "piano";
         String guess = "ukulele";
         System.out.println("Is Ada's favorite instrument a "
             + guess + "?");
         System.out.println("Answer: " + favorite.equals(guess));
    }
}