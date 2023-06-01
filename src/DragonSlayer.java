import java.util.*;

public class DragonSlayer {

    //Instance variables
    private Scanner scan;
    private Player player;
    private Dragon dragon;
    private Sword sword;
    private Room den;
    private int totalDragons;
    private int score;
    private static int topScore;
    private boolean playerTurn;
    private int numRooms;
    private Set<String> listOfDragons;
    private Set<String> listOfDens;
    private ArrayList<Integer> listOfScores;
    private boolean isValid;


    private boolean wantsToContinue;
    private boolean playAgain;
    private boolean playerWon;



    //Constructor
    public DragonSlayer() {
        scan = new Scanner(System.in);
        listOfDragons = new HashSet<>(Arrays.asList("Toothless", "Mushu", "Maleficent", "Dvalin", "Stormfly", "Morax", "Azhdaha"));
        listOfDens = new HashSet<>(Arrays.asList("Crimson Blood Keep", "Azul Sea of Terror","Emerald Green Jungle","Golden Death Desert", "White Wraith Island","Black Soul Mountains","Violet Delights Archipelago"));
        listOfScores = new ArrayList<>();
        den = getNewRoom();
        dragon = getDragon();
        playerTurn = true;
        numRooms = 0;
        totalDragons = 7;
        isValid = true;
        wantsToContinue = true;
        playAgain = true;
        playerWon = false;
        score = 0;
        topScore = 0;
    }

    //Static methods

    /**
     * method to make the code print slower
     * gives player more time to read everything
     * @param ms
     */
    private static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * clears the terminal
     * @param
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    //Getter and Setter methods

    /**
     * this method gets or prepares a new dragon with a dragon name from a dragon that is still alive.
     * this method is implemented everytime a dragon dies.
     * @return
     */
    private Dragon getDragon() {

        String[] dragonNames = listOfDragons.toArray(new String[listOfDragons.size()]);
        String dragonName = "";
        int randomName = (int)(Math.random() * listOfDragons.size() - 1);
        dragonName = dragonNames[randomName];

        int level = 0;
        int randomLevel = (int) (Math.random() * 4) + 1; //randomly sets a dragon's level
        if (randomLevel == 1) {
            level = 1;
        } else if (randomLevel == 2) {
            level = 2;
        } else if (randomLevel == 3) {
            level = 3;
        } else {
            level = 4;
        }
        return new Dragon(dragonName, level);
    }

    /**
     * Gets a new room and names it a name that has not yet been used
     */
    private Room getNewRoom() {

        String[] denNames = listOfDens.toArray(new String[listOfDens.size()]);
        String denName = "";
        int randomDenName = (int)(Math.random() * listOfDens.size() - 1);
        denName = denNames[randomDenName];

        return new Room(denName);
    }


    /**
     * sets den to the new room created in getNewRoom()
     */
    private void setDen(){
        den = getNewRoom();
    }

    /**
     * sets the current dragon the player is fighting with to another dragon
     * @param newDragon
     */
    private void setDragon(Dragon newDragon) {
        dragon = newDragon;
    }

    /**
     * when the player dies and wants to restart the game, this method is called to "reset" all the game's memories
     */
    private void resetStats(){
        listOfDragons = new HashSet<>(Arrays.asList("Toothless", "Mushu", "Maleficent", "Dvalin", "Stormfly", "Morax", "Azhdaha"));
        listOfDens = new HashSet<>(Arrays.asList("Crimson Blood Keep", "Azul Sea of Terror","Emerald Green Jungle","Golden Death Desert", "White Wraith Island","Black Soul Mountains","Violet Delights Archipelago"));
        den = getNewRoom();
        dragon = getDragon();
        playerTurn = true;
        numRooms = 0;
        totalDragons = 7;
        isValid = true;
        wantsToContinue = true;
        playAgain = true;
        playerWon = false;
        score = 0;
    }



    //Instance Methods that carry out the actual game logic

    /**
     * This method will greet the player and display the rules
     */
    private void greeting() {
        printAsciiArt();

        System.out.print("Welcome to Dragon Slayer Game! Please enter your name: ");
        String playerName = scan.nextLine();

        //creates player object
        player = new Player(playerName);
        sword = player.getSword();
        clearConsole();
        System.out.println("-------------------------------------------------------------------------------------------");
        wait(1000);
        System.out.println("Greetings " + player.getName() + "! We have been awaiting for your arrival. Dragons have plagued our kingdom and we need your help to slay them!");
        System.out.println("With each dragon you slay, you gain a random amount of dragon scales. These will become your score at the end of each game and how you define your top score!\n");
        System.out.println("Kill all dragons within FIVE lairs (AKA Rooms) to save us!");
        System.out.println("." + "\n" + "." + "\n" + ".");
        System.out.println("What's that? You want...a reward? Err...here's some gold, you can get a sword upgrade, increase dodge rate, or regain a fraction of your health by spending them! You could also get more gold by trading in some dragon scales?");
        System.out.println("." + "\n" + "." + "\n" + ".");
        System.out.println("O-oh you accept? *mumbles* What a little sh-" + "\n");
        System.out.print("Silly me! We must keep this school-friendly. Well then, brave warrior, enter *ONLY* 'y' or 'Y' to venture on! ");

    }

    /**
     * this method executes the game and contains the enterRoom() method
     * checks if the player can still enter into the next room or is done with the game when all dragons have been slayed within 5 rooms
     * this method also ensures that all 7 dragons are distributed amongst the 5 rooms
     */
    public void play() {
        greeting();
        String answer = scan.nextLine();

        if (answer.equals("y") || answer.equals("Y")) {
            clearConsole();
            System.out.println("-------------------------------------------------------------------------------------------");
            wait(1000);
            setDragon(dragon);

            while (numRooms <= 5 && wantsToContinue && !player.playerIsDead() && playAgain && listOfDragons.size() > 0) {
                boolean isAnswerValid = true;
                numRooms++;
                System.out.println("You are now in room: " + numRooms);

                if (numRooms == 1){
                    System.out.println("You have ventured into your first room..." + "\n");
                } else {
                    System.out.println("You have ventured into a new room..." + "\n");

                    setDen();
                    setDragon(getDragon());

                    if (numRooms == 3 && listOfDragons.size() == 3) {
                        den.setNumDragons(1);
                    } else if (numRooms == 4 && listOfDragons.size() <= 2){
                        den.setNumDragons(1);
                    } else if (numRooms == 5) {
                        den.setNumDragons(listOfDragons.size());
                    } else {
                        den.setNumDragons(den.getNumDragons());
                    }

                }

                System.out.println("Welcome to " + den.getLairName() + "!\n");
                displayStats();
                searchHealthPot();

                if (den.getNumDragons() > 1) {
                    System.out.println("Beware...there are " + den.getNumDragons() + " dragons in here...");
                } else {
                    System.out.println("Beware...there is 1 dragon in here...");
                }
                System.out.println("Here comes a dragon! " + dragon.getDragonName() + " is a level " + dragon.getDragonLevel());
                System.out.println("--------------------------------------------------------------------------------------");
                wait(1000);
                enterRoom();

                if (player.playerIsDead()){
                    System.out.println("""
                            ░▒█▀▀█░█▀▀▄░▒█▀▄▀█░▒█▀▀▀░░░▒█▀▀▀█░▒█░░▒█░▒█▀▀▀░▒█▀▀▄░█░█░█
                            ░▒█░▄▄▒█▄▄█░▒█▒█▒█░▒█▀▀▀░░░▒█░░▒█░░▒█▒█░░▒█▀▀▀░▒█▄▄▀░▀░▀░▀
                            ░▒█▄▄▀▒█░▒█░▒█░░▒█░▒█▄▄▄░░░▒█▄▄▄█░░░▀▄▀░░▒█▄▄▄░▒█░▒█░▄░▄░▄                             
                            """);
                    System.out.println("--------------------------------------------------------------------------------------");
                    System.out.println("SCORE BOARD:");
                    scoreBoard();

                    while (isAnswerValid) {
                        System.out.print("Do you want to play again? (y/n) ");
                        String playerPlaysAgain = scan.nextLine();

                        if (playerPlaysAgain.equals("n") || playerPlaysAgain.equals("N")){
                            wantsToContinue = false;
                            playAgain = false;
                            isAnswerValid = false;
                            System.out.println("Shame! We hope to see you back again :D");
                        } else if (playerPlaysAgain.equals("y") || playerPlaysAgain.equals("Y")){
                            clearConsole();
                            wantsToContinue = true;
                            playAgain = true;
                            player.resetEverything();
                            resetStats();
                            isAnswerValid = false;
                        }
                        else {
                            System.out.print("Bruh you're testing for bugs aren't you? Just answer the question! ");
                        }
                    }

                    System.out.println("--------------------------------------------------------------------------------------");
                } else {
                    if (checkWinnings()) {
                        System.out.println("--------------------------------------------------------------------------------------");
                        System.out.println("""
                                ░▒█▀▀▄░▒█▀▀▀█░▒█▄░▒█░▒█▀▀█░▒█▀▀▄░█▀▀▄░▀▀█▀▀░▒█░▒█░▒█░░░░█▀▀▄░▀▀█▀▀░▀█▀░▒█▀▀▀█░▒█▄░▒█░▒█▀▀▀█
                                ░▒█░░░░▒█░░▒█░▒█▒█▒█░▒█░▄▄░▒█▄▄▀▒█▄▄█░░▒█░░░▒█░▒█░▒█░░░▒█▄▄█░░▒█░░░▒█░░▒█░░▒█░▒█▒█▒█░░▀▀▀▄▄
                                ░▒█▄▄▀░▒█▄▄▄█░▒█░░▀█░▒█▄▄▀░▒█░▒█▒█░▒█░░▒█░░░░▀▄▄▀░▒█▄▄█▒█░▒█░░▒█░░░▄█▄░▒█▄▄▄█░▒█░░▀█░▒█▄▄▄█                        
                                """);
                        System.out.println("CONGRATULATIONS!!! You have saved us all! Maybe you weren't a little sh-\nOh my! I almost slipped up again ;)\nWe hope to see you again!");
                        System.out.println("--------------------------------------------------------------------------------------");
                        System.out.println("SCORE BOARD:");
                        scoreBoard();
                    } else {

                        while (isAnswerValid) {
                            System.out.print("Would you like to proceed into the next room? (y/n) ");
                            String proceed = scan.nextLine();
                            if (proceed.equals("y") || proceed.equals("Y")){
                                clearConsole();
                                wantsToContinue = true;
                                listOfDens.remove(den.getLairName());
                                if (player.getHealthPotStatus()){
                                    System.out.println("Because you did not use your health pot, it has been discarded!");
                                }
                                isAnswerValid = false;
                            } else if (proceed.equals("n") || proceed.equals("N")) {
                                clearConsole();
                                wantsToContinue = false;
                                System.out.println("Goodbye!");
                                isAnswerValid = false;
                            }

                            else {
                                System.out.print("Bruh you're testing for bugs aren't you? Just play the game! ");
                            }
                        }

                    }

                    System.out.println("--------------------------------------------------------------------------------------");
                }

            }

        } else {
            System.out.print("The directions said ONLY 'y' or 'Y' >:p. Come back when you learn tor read!");
            isValid = false;
            wantsToContinue = false;
        }


    }

    /**
     * "enters" a room/Dragon's den
     * contains the code and game logic in each room (player and dragon attacks)
     * will keep the player in the same room if there are more than one dragon to defeat
     */
    private void enterRoom() {

        while (isValid && !player.playerIsDead() && !den.getIsAllSlayed()) {
            boolean isAnswerValid2 = true;
            int playerAttack = player.getPlayerAttack();
            int dragonAttack = dragon.getDragonAttack();

            if (playerTurn) {
                System.out.println("Your current sword attack stat is " + sword.getAttack() + ".");

                while (isAnswerValid2) {
                    System.out.print("Do you want to cast a spell for a higher sword attack? It might turn the tables! (y/n) ");
                    String spellChoice = scan.nextLine();

                    if (spellChoice.equals("y") || spellChoice.equals("Y")){
                        if (playerAttack <= sword.getAttack() + 5){
                            System.out.println("Not the best upgrade. Did you do something to anger the Gods? Oh well! Your new sword attack is now " + playerAttack + "!");
                        } else {
                            System.out.println("The Gods smile upon you! Your new sword attack is now " + playerAttack + "!");
                        }
                        System.out.println("You attack " + dragon.getDragonName());
                        dragon.subtractDragonHealth(playerAttack);
                        isAnswerValid2 = false;
                    } else if (spellChoice.equals("n") || spellChoice.equals("N")){
                        System.out.println("You attack " + dragon.getDragonName());
                        playerAttack = sword.getAttack();
                        dragon.subtractDragonHealth(playerAttack);
                        isAnswerValid2 = false;
                    } else {
                        System.out.print("Bruh you're testing for bugs aren't you? Just play the game! ");
                    }
                }


                if (dragon.dragonIsDead()) {

                    System.out.println("You defeated " + dragon.getDragonName() + "!");
                    den.slayedDragon();
                    listOfDragons.remove(dragon.getDragonName());
                    totalDragons -= 1;
                    System.out.println("Dragons Left: " + totalDragons);

                    if (totalDragons > 0){
                        purchaseMenu();
                    }

                    if (den.getNumDragons() > 0){
                        System.out.println("Don't celebrate too soon! There is still " + den.getNumDragons() + " more dragons...");
                        setDragon(getDragon());
                        System.out.println("In flies " + dragon.getDragonName() + "! It's a level " + dragon.getDragonLevel());
                    } else {
                        break;
                    }
                } else {
                    System.out.printf("The dragon takes %s damage!\n", playerAttack);
                    System.out.printf("The dragon has %s health left%n", dragon.getDragonHealth());
                }
                playerTurn = false;

                promptEnterKey();
                System.out.println("--------------------------------------------------------------------------------------");

            } else {
                System.out.printf("%s has begun to attack...", dragon.getDragonName());
                System.out.printf("It attacks with %s attack points!\n", dragonAttack);

                int randomDodge = (int)(Math.random() * sword.getDodge()) + 1;
                if (randomDodge <= sword.getDodge()/2){
                    System.out.println("The dragon swipes and you dodge!");
                } else {
                    player.subtractHealth(dragonAttack);
                    System.out.println("Ouch! The dragon swipes and you get hit!");

                    if (player.getHealth() > 0){
                        System.out.printf("You now have %s health left\n", player.getHealth());
                    } else {
                        System.out.println(dragon.getDragonName() + " has defeated you! You are DEAD.");
                    }
                }

                if (den.isRoomSearched() && !player.playerIsDead() && player.getHealthPotStatus()){

                    while (isAnswerValid2) {
                        System.out.print("Would you like to use your health pot now? (y/n): ");
                        String healthPot = scan.nextLine();
                        if (healthPot.equals("y") || healthPot.equals("Y")){
                            player.addHealth(Math.abs(player.getHealth() - 100)/2);
                            System.out.println("You have used your health pot! Your health has been restored to " + player.getHealth());
                            player.setHealthPotStatus(false);
                            isAnswerValid2 = false;
                        } else if (healthPot.equals("n") || healthPot.equals("N")){
                            System.out.println("Alright then, if you say so!");
                            isAnswerValid2 = false;
                        } else {
                            System.out.print("Bruh you're testing for bugs aren't you? Just play the game! ");
                        }
                    }

                } else if (den.isRoomSearched() && !player.playerIsDead() && !player.getHealthPotStatus()) {
                    System.out.println("You've already used your health pot!");
                } else {
                    System.out.println(":p no health pot");
                }

                promptEnterKey();
                playerTurn = true;
                System.out.println("--------------------------------------------------------------------------------------");
            }
        }
    }

    /**
     * the menu that displays for the player to decide what they want to upgrade or buy after a dragon dies
     * it is also the method where the player's score (AKA dragon scales) is calculated
     *      - if the player chooses not to buy or upgrade anything, dragon scales are added to their score
     *      - else, no dragon scales are added
     */

    private void purchaseMenu(){
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println(dragon.getDragonName() + " has dropped " + dragon.getDragonScales() + " scales!");
        displayStats();

        if (player.getGold() < 10){
            System.out.println("Unfortunately, you don't have enough money to even browse the menu! Don't worry, dragon scales will now be automatically added to your score.");
            player.addDragonScales(dragon.getDragonScales());
        } else {
            System.out.println("\nHere are you options:\n1. 10 Gold to double your current health.\n2. 20 Gold to double your base sword attack.\n3. 30 Gold to double your dodge rate.\n4. Trade in half of your dragon scales for 50 more pieces of gold.\n5. Buy nothing and add your dragon scales!");
            System.out.print("\nType in the corresponding answer number (i.e. 1, 2, 3...) ");
            String purchase = scan.nextLine();

            if (purchase.equals("1")){

                player.subtractGold(10);
                player.addHealth(player.getHealth());
                System.out.println("Your current health is now: " + player.getHealth());

            } else if (purchase.equals("2")){

                if (player.getGold() < 20) {
                    System.out.println("You don't have enough gold! Dragon scales are automatically added to your score.");
                } else {
                    player.subtractGold(20);
                    sword.setAttack(sword.getAttack() * 2);
                    System.out.println("Your current base sword attack is now: " + sword.getAttack());
                }

            } else if (purchase.equals("3")){

                if (player.getGold() < 30) {
                    System.out.println("You don't have enough gold! Dragon scales are automatically added to your score.");
                } else {
                    player.subtractGold(30);
                    sword.setDodge(sword.getDodge() * 2);
                    System.out.println("Your current dodge rate is now: " + sword.getDodge());
                }

            } else if (purchase.equals("4")){

                player.subtractDragonScales(player.getDragonScalesBalance()/2);
                player.addGold(50);

            } else if (purchase.equals("5")) {
                player.addDragonScales(dragon.getDragonScales());
                System.out.println("Ok then! You get to keep your dragon scales. They are now added to your total score!");
            } else {
                System.out.println("Oops! That's not an option. YOU GET NOTHING!!");
            }
        }

        System.out.println("Your current gold balance is now: " + player.getGold());
        System.out.println("You currently have " + player.getDragonScalesBalance() + " dragon scales!");
        System.out.println("--------------------------------------------------------------------------------------");
        wait(1000);

    }

    /**
     * this method asks the player if they want to search the room for a health pot
     * randomly decides if the player finds one in the room
     */
    private void searchHealthPot() {
        boolean isAnswerValid3 = true;

        while (isAnswerValid3) {
            System.out.print("Would you like to search the room for a health pot? You can save and use it to heal yourself during battle! (y/n) ");
            String answer = scan.nextLine();
            if (answer.equals("n") || answer.equals("N")) {
                clearConsole();
                den.setRoomSearchedStatus(false);
                isAnswerValid3 = false;
            } else if (answer.equals("y") || answer.equals("Y")) {
                clearConsole();
                int healthPotRandom = (int) (Math.random() * 2) + 1;
                if (healthPotRandom == 1) {
                    den.setRoomSearchedStatus(true);
                    System.out.println("You've successfully found a health pot! You may now choose to use it as you see fit during battle.");
                    player.setHealthPotStatus(true);
                } else {
                    den.setRoomSearchedStatus(false);
                    System.out.println("Unfortunately, there is no health pot in this room.");
                    player.setHealthPotStatus(false);
                }
                isAnswerValid3 = false;
            }  else {
                System.out.print("Bruh you're testing for bugs aren't you? Just play the game! ");

            }
        }

    }


    //Helper methods

    /**
     * checks if the player has won
     */
    private boolean checkWinnings(){
        if (!player.playerIsDead() && numRooms <= 5 && listOfDragons.size() == 0){
            playerWon = true;
        }
        return playerWon;
    }


    /**
     * Keeps track of the player's current score and top score
     */
    private void scoreBoard(){
        score = player.getDragonScalesBalance();
        listOfScores.add(score);
        System.out.println("You had " + score + " dragon scales. Your score is " + score + "!");

        if (score > topScore){
            topScore = score;
        }
        System.out.println("Here are all your scores: " + listOfScores);

        System.out.println("Your top score is " + topScore);
    }

    /**
     * displays the current stats of the player (health, attack, dodge, currency, and dragon scales)
     */
    private void displayStats(){
        System.out.println("Here are your stats:");
        System.out.println("- Your health is: " + player.getHealth() + "\n- Your current sword attack is: " + sword.getAttack() + " (you can increase it when you clear a room)!\n- Your dodge rate is: " + sword.getDodge() + "%\n- You currently have: " + player.getGold() + " gold.\n- You currently have: " + player.getDragonScalesBalance() + " dragon scales.");
        System.out.println("** During battle, you can choose to cast a spell to temporarily increase your sword attack **");
        System.out.println("** Each Dragon starts off with a base health of 100 and base attack of 30 (which they can increase during battle) **\n");
    }

    private void printAsciiArt() {

        System.out.println(
                """
                ██████╗░██████╗░░█████╗░░██████╗░░█████╗░███╗░░██╗  ░██████╗██╗░░░░░░█████╗░██╗░░░██╗███████╗██████╗░
                ██╔══██╗██╔══██╗██╔══██╗██╔════╝░██╔══██╗████╗░██║  ██╔════╝██║░░░░░██╔══██╗╚██╗░██╔╝██╔════╝██╔══██╗
                ██║░░██║██████╔╝███████║██║░░██╗░██║░░██║██╔██╗██║  ╚█████╗░██║░░░░░███████║░╚████╔╝░█████╗░░██████╔╝
                ██║░░██║██╔══██╗██╔══██║██║░░╚██╗██║░░██║██║╚████║  ░╚═══██╗██║░░░░░██╔══██║░░╚██╔╝░░██╔══╝░░██╔══██╗
                ██████╔╝██║░░██║██║░░██║╚██████╔╝╚█████╔╝██║░╚███║  ██████╔╝███████╗██║░░██║░░░██║░░░███████╗██║░░██║
                ╚═════╝░╚═╝░░╚═╝╚═╝░░╚═╝░╚═════╝░░╚════╝░╚═╝░░╚══╝  ╚═════╝░╚══════╝╚═╝░░╚═╝░░░╚═╝░░░╚══════╝╚═╝░░╚═╝
                """
        );

        System.out.println();

        System.out.println(
                """
                                        
                ██████╗░██╗░░░██╗  ░█████╗░░█████╗░███╗░░██╗███╗░░██╗██╗███████╗  ██╗░░░░░██╗██╗░░░██╗░░░
                ██╔══██╗╚██╗░██╔╝  ██╔══██╗██╔══██╗████╗░██║████╗░██║██║██╔════╝  ██║░░░░░██║██║░░░██║░░░
                ██████╦╝░╚████╔╝░  ██║░░╚═╝██║░░██║██╔██╗██║██╔██╗██║██║█████╗░░  ██║░░░░░██║██║░░░██║░░░
                ██╔══██╗░░╚██╔╝░░  ██║░░██╗██║░░██║██║╚████║██║╚████║██║██╔══╝░░  ██║░░░░░██║██║░░░██║░░░
                ██████╦╝░░░██║░░░  ╚█████╔╝╚█████╔╝██║░╚███║██║░╚███║██║███████╗  ███████╗██║╚██████╔╝██╗
                ╚═════╝░░░░╚═╝░░░  ░╚════╝░░╚════╝░╚═╝░░╚══╝╚═╝░░╚══╝╚═╝╚══════╝  ╚══════╝╚═╝░╚═════╝░╚═╝
                """
        );


    }



    /**
     * method that prompts the player to hit enter to continue
     */
    private void promptEnterKey(){
        System.out.print("Press \"ENTER\" to continue...");
        scan.nextLine();
        clearConsole();
    }
}