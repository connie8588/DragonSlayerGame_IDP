public class Sword {


    //Instance variables
    private int attack;
    private int dodge;

    //Constructor
    public Sword() {
        attack = 10;
        dodge = 20;
    }

    //Getter and setter methods
    public int getAttack(){
        return attack;
    }

    public int getDodge(){
        return dodge;
    }

    public void setAttack(int newAttack){
        attack = newAttack;
    }

    public void setDodge(int newDodge){
        dodge = newDodge;
    }

}