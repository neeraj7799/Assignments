package wumpusworld;

import java.util.Random;

/**
 * Contains starting code for creating your own Wumpus World agent.
 * Currently the agent only make a random decision each turn.
 * 
 * @author Johan Hagelbäck
 */
public class MyAgent implements Agent
{
    private World w;
    private double[][] Q = QlearningTable.setInstance();
    private int currentPosition;

    
    /**
     * Creates a new instance of your solver agent.
     * 
     * @param world Current world state 
     */
    public MyAgent(World world)
    {
        w = world;   
        currentPosition = 11;
    }
   
    public void trainAgent() 
    {       
        int oldPosition = currentPosition;
        int action = selectAction();
        trainAction(action);
        int newPosition = currentPosition;
        double reward = Q[oldPosition][0];
        double maxQValueOfNextState = QlearningTable.getMaxQValue(newPosition);
        Q[oldPosition][action] = 
                Q[oldPosition][action] + QlearningConfigure.LEARNINGRATE * (reward + (QlearningConfigure.DISCOUNTFACTOR * maxQValueOfNextState) - Q[oldPosition][action]);
    }

            
    /**
     * Asks your solver agent to execute an action.
     */
    public void doAction()
    {
        int action = QlearningTable.getMaxQValueAction(currentPosition);

        trainAction(action);

        int cX = w.getPlayerX();
        int cY = w.getPlayerY();

        ////Grab Gold if we can.
        if (w.hasGlitter(cX, cY)) {
            System.out.println("Glitter");
            w.doAction(World.A_GRAB); 
            return;
        } 
        
        //We are in a pit. Climb up.
        if (w.isInPit())
        {
            w.doAction(World.A_CLIMB);
            return;
        }

        if (w.hasBreeze(cX, cY))
        {
            System.out.println("I am in a Breeze");
        }
        if (w.hasStench(cX, cY))
        {
            System.out.println("I am in a Stench");
        }  
        if (w.hasPit(cX, cY)) {
            System.out.println("I am in a Pit");  
        }    
        if (w.hasWumpus(cX, cY)) {
            System.out.println("Wumpus");
        } 
    }
    
     /**
     * Asks your solver agent to select an action.
     */
    public int selectAction() {
        Random rand = new Random();
        if (Math.random() < QlearningConfigure.PROBABILITY) {
            return rand.nextInt(4) + 1;
        } else {
            return QlearningTable.getMaxQValueAction(currentPosition);
        }
    }
    
    /**
     * Asks your solver agent to train the selected action.
     */
    public void trainAction(int action) {
        checkState();
        
        int cX = w.getPlayerX();
        int cY = w.getPlayerY();
               

        // Right
        if (action == 1) {
            if (w.isValidPosition(cX + 1, cY)) {
                currentPosition = (cX + 1) * 10 + cY;
                switch (w.getDirection()) {
                    case World.DIR_RIGHT:
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_DOWN:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_LEFT:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_UP:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    default:
                        break;
                }
            }
        }
        // Up
        if (action == 2) {
            if (w.isValidPosition(cX, cY - 1)) {
                switch (w.getDirection()) {
                    case World.DIR_RIGHT:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_DOWN:
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_LEFT:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_UP:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    default:
                        break;
                }
                currentPosition = cX * 10 + (cY - 1);
            } 
        }
        // Left
        if (action == 3) {
            if (w.isValidPosition(cX - 1, cY)) {
                switch (w.getDirection()) {
                    case World.DIR_RIGHT:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_DOWN:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_LEFT:
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_UP:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    default:
                        break;
                }
                currentPosition = (cX - 1) * 10 + cY;
            } 
        }
        // Down
        if (action == 4) {
            if (w.isValidPosition(cX, cY + 1)) {
                switch (w.getDirection()) {
                    case World.DIR_RIGHT:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_DOWN:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_LEFT:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_UP:
                        w.doAction(World.A_MOVE);
                        break;
                    default:
                        break;
                }
                currentPosition = cX * 10 + (cY + 1);
            }
        }
    }
    
    // Used to check if the current square has either a wumpus, pit or gold in it.
    public void checkState() 
    {
        int x = w.getPlayerX();
        int y = w.getPlayerY();
        if (w.gameOver()) {
            if (w.hasWumpus(x, y)) {
                // Set the Reward for the currentPos 
                Q[currentPosition][0] = QlearningConfigure.WUMPUSREWARD;
            }
        } else if (w.hasGlitter(x, y)) {
            // Set the Reward for the currentPos 
            Q[currentPosition][0] = QlearningConfigure.GOLDREWARD;
            w.doAction(World.A_GRAB);
        } else if (w.hasPit(x, y)) {
            // Set the Reward for the currentPos 
            Q[currentPosition][0] = QlearningConfigure.PITREWARD;
            w.doAction(World.A_CLIMB);
        }
        
    }
}

