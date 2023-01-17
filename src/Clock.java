//Run a clock for the timer on the controller
public class Clock extends Main implements Runnable {
    public void run(){
        long time = System.currentTimeMillis(); //the amount of time since the last update
        while (timeOn) {
            if (System.currentTimeMillis() >= time) {
                time = System.currentTimeMillis() + 100;
                if (timeOn && (min != 0 || sec != 0 || tenth != 0)) { //Add logic to have timer count down correctly
                    if (sec == 0 && min != 0 && tenth == 0) {
                        min--;
                        sec = 59;
                        tenth = 10;
                    }
                    if (tenth == 0 && sec != 0) {
                        sec--;
                        tenth = 10;
                    }
                    tenth--;
                    updateFile(csvFilePath); //Send the new clock data to the file to be outputted
                
                }
            }
        }
    }
}
