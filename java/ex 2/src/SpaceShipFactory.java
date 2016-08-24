/**
 * This class is in charge of building the array of the ships who play in the game. the game who calls the class
 * single method.
 */
public class SpaceShipFactory {

    /** A class static data member of an array of the players of the current game who called it. */
    public static SpaceShip[] shipsArray;

    /**
     * This method creates an array of ships from a given string, who dictates the types of the ships
     * that will play in the game.
     * @param args the arguments string line of the program parameters
     * @return a SpaceShip array with the new ships objects
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        //creating a new array to contain the ships.
        int listLength = args.length;
        SpaceShip[] shipsOfTheGame=new SpaceShip[listLength];
        int currentShip=0;
        //going through all the inputs and creating new objects accordingly.
        while (currentShip<=listLength-1){
            for(String arg : args){
            if (arg.equals("s")){shipsOfTheGame[currentShip]=new Spacial(SpaceWars.SPECIAL_SHIP);}
            else if (arg.equals("a")){shipsOfTheGame[currentShip]=new Aggressive(SpaceWars.AGGRESSIVE_SHIP);}
            else if (arg.equals("h")) {shipsOfTheGame[currentShip]=new Human(SpaceWars.HUMAN_CONTROLLED_SHIP);}
            else if (arg.equals("r")){shipsOfTheGame[currentShip]=new Runner(SpaceWars.RUNNER_SHIP);}
            else if (arg.equals("b")){shipsOfTheGame[currentShip]=new Basher(SpaceWars.BASHER_SHIP);}
            else if (arg.equals("d")){shipsOfTheGame[currentShip]=new Drunkard(SpaceWars.FLOATING_SHIP);}
            currentShip+=1;
            }
        }
        //updates class static data member and taking care of the Spacial class request before returning.
        shipsArray = shipsOfTheGame;
        chooseShipForSpacialShipType();
        return shipsOfTheGame;
    }

    /**
     * This method is called from the main public method of this class.
     * it will go through all the new ship type from the list she has build,
     * and will activate the Spacial class function in order to choose another ship
     * for the Spacial ships in the game to chase, as part of the implementation.
     * noted that it will happen only once in a game, hence the implementaion in the current class(and not per round).
     */
    private static void chooseShipForSpacialShipType(){
        //checking if only Spacial ships are in the game
        //continue is being used for intention clarification
        boolean sign = true;
        for(SpaceShip Ship : shipsArray){
            if(Ship.shipType == SpaceWars.SPECIAL_SHIP){continue;}
            else{
                sign = false;
                break;
            }
        }
        //if not, calling the choosing method in the Spacial class, for each of the Spacial ships.
        for(SpaceShip aShip: shipsArray){
            if(aShip.shipType == SpaceWars.SPECIAL_SHIP){
                Spacial spacialShip = (Spacial) aShip;
                Spacial.choosingSpaceShip(spacialShip,sign);

            }
        }
    }
}
