public class Player {

    //Instance variables
    private static String name;

    private int health;
    private boolean healthStatus;
    private int gold;
    private boolean healthPotStatus;
    private Sword sword;
    private int dragonScalesBalance;
    //Constructor
    public Player(String name){
        this.name = name;
        health = 100;
        healthStatus = true;
        gold = 50;
        healthPotStatus = false;
        sword = new Sword();
        dragonScalesBalance = 0;
    }

    //Getter and setter methods
    public String getName(){
        return name;
    }

    public int getHealth(){
        return health;
    }

    public int getGold(){
        return gold;
    }

    public boolean getHealthPotStatus(){
        return healthPotStatus;
    }


    public void setHealthPotStatus(boolean foundHealthPot){
        healthPotStatus = foundHealthPot;
    }

    public Sword getSword(){
        return sword;
    }

    public void addHealth(int moreHealth){
        health += moreHealth;
    }

    public void subtractHealth(int damage){
        health -= damage;
    }

    public void addGold(int newGold){
        gold += newGold;
    }

    public void subtractGold(int newGold){
        gold -= newGold;
    }

    public void subtractDragonScales(int scales){
        dragonScalesBalance -= scales;
    }

    public int getDragonScalesBalance(){
        return dragonScalesBalance;
    }

    public void addDragonScales(int scales){
        dragonScalesBalance += scales;
    }


    //Instance methods
    /**
     * This method determines the attack amount for the Player
     * Displays the corresponding text for the attack amount
     */
    public int getPlayerAttack(){
        int attackAmt = sword.getAttack();
        int buffer = (int)(Math.random() * 10) + 1;

        if (generateSpellSuccess()){
            attackAmt *= buffer;
        } else {
            attackAmt += 5;
        }

        return attackAmt;
    }


    /**
     * this method "resets" all the necessary attributes of the player object
     */
    public void resetEverything(){
        health = 100;
        healthStatus = true;
        gold = 50;
        healthPotStatus = false;
        sword.setAttack(10);
        dragonScalesBalance = 0;
    }


    /**
     * method that returns a boolean of whether the player is dead or not
     * @return
     */
    public boolean playerIsDead(){
        if (health > 0) {
            healthStatus = false;
        } else {
            healthStatus = true;
        }
        return healthStatus;
    }

    //Helper methods
    /**
     * Generates a boolean of whether the player's attack will be successful or not
     */
    private boolean generateSpellSuccess(){

        int successRate = (int)(Math.random() * 2) + 1;
        if (successRate == 1){
            return false;
        } else {
            return true;
        }


    }
}