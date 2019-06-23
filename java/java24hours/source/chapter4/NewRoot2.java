class NewRoot2 {
    public static void main(String[] args) {
        /* Arguments are stored as strings in an array that's called args in this application.
           The first argument is stored in array element args[0]. Calling the method
           Integer.parseInt() creates an integer value from a string. */
        int number = Integer.parseInt(args[0]);
        System.out.println("The square root of "
            + number
            + " is "
            + Math.sqrt(number)
        );
    }
}