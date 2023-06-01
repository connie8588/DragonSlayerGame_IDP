public class Room {

    //Instance variables
    private static String lairName;
    private int numDragons;
    private boolean isAllSlayed;
    private boolean roomSearched;

    //constructor
    public Room(String lairName){
        this.lairName = lairName;
        int randomNumDragons = (int)(Math.random() * 2) + 1;
        numDragons = randomNumDragons;
        isAllSlayed = false;
        roomSearched = false;
    }


    //Getter and setter methods
    public void setRoomSearchedStatus(boolean status){
        roomSearched = status;
    }

    public String getLairName(){
        return lairName;
    }

    public int getNumDragons(){
        return numDragons;
    }

    public void setNumDragons(int newNuMDragons){
        numDragons = newNuMDragons;
    }

    public boolean getIsAllSlayed() {
        if (numDragons == 0){
            isAllSlayed = true;
        } else {
            isAllSlayed = false;
        }
        return isAllSlayed;
    }


    //Instance methods
    /**
     * updates the number of dragon in each room
     */
    public void slayedDragon(){
        numDragons--;
    }

    //Helper methods
    /**
     * returns a boolean whether the room has been searched for a health pot or not
     * @return
     */
    public boolean isRoomSearched() {
        return roomSearched;
    }





}