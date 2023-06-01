public class Dragon {


    //Instance Variables
    private int health;
    private String dragonName;
    private int level;
    private int attack;
    private int dragonScales;

    //Constructor
    public Dragon(String dragonName, int level){
        health = 100;
        this.dragonName = dragonName;
        this.level = level;
        attack = 30;

        int randomScales = 0;
        if (level > 2){
            randomScales = (int)(Math.random() * 41) + 20;
        } else {
            randomScales = (int)(Math.random() * 11) + 10;
        }
        dragonScales = randomScales;

    }



    //Getter and setter methods
    public int getDragonHealth() {
        return health;
    }

    public int getDragonLevel(){
        return level;
    }

    public String getDragonName(){
        return dragonName;
    }

    public void subtractDragonHealth(int damage){
        health -= damage;
    }

    public int getDragonScales(){
        return dragonScales;
    }

    /**
     * Method for calculating the attack amount of the dragon
     * @return
     */
    public int getDragonAttack(){
        int dragonAttackAmt = level;

        int dragonBuffer = (int)(Math.random() * 9) + 2;
        dragonAttackAmt *= dragonBuffer;

        return dragonAttackAmt + attack;
    }

    /**
     * boolean that checks if a dragon is dead or not
     */
    public boolean dragonIsDead() {
        if (health > 0){
            return false;
        } else {
            return true;
        }
    }






}