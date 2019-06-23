public class NewVirus {
    static int virusCount = 0;
    private int newSeconds = 0;

    public NewVirus() {
        virusCount++;
    }
 
    static int getVirusCount() {
        return virusCount;
    }

    int getNewSeconds() {
        return newSeconds;
    }

    boolean setNewSeconds(int newValue) {
        if ((newValue > 59) & (newValue < 101)) {
            newSeconds = newValue;
            return true;
        } else {
            return false;
        }
    }
}
