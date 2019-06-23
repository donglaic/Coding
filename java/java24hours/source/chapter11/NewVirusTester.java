class NewVirusTester {
    public static void main(String arguments[]) {
        NewVirus smash = new NewVirus();
        NewVirus crash = new NewVirus();
        NewVirus crumble = new NewVirus();
        System.out.println("There are " + NewVirus.getVirusCount() + " viruses.");
        System.out.println("The smash NewVirus object has a newSeconds value of " +
            smash.getNewSeconds());
        System.out.println("Attempting to change the value of newSeconds to 20.");
        if (smash.setNewSeconds(30) == true) {
            System.out.println("Attempt succeeded.");
        } else {
            System.out.println("Attempt failed.");
        }
        System.out.println("The smash NewVirus object has a newSeconds value of " +
            smash.getNewSeconds());
        System.out.println("Attempting to change the value of newSeconds to 90.");
        if (smash.setNewSeconds(90) == true) {
            System.out.println("Attempt succeeded.");
        } else {
            System.out.println("Attempt failed.");
        }
        System.out.println("The smash NewVirus object has a newSeconds value of " +
            smash.getNewSeconds());
        }
}