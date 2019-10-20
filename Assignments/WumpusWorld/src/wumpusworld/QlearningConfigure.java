/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wumpusworld;

/**
 *
 * @author Paul
 */
public class QlearningConfigure {    
    // Set the probability for exploration
    static final double PROBABILITY = 0.2;
    
    // Set the Q-learning params
    static final double LEARNINGRATE = 0.5;
    static final double DISCOUNTFACTOR = 0.8;
    
    // Set the Rewards for different states
    static final int PITREWARD = -1;
    static final int WUMPUSREWARD = -1;
    static final int GOLDREWARD = 1;
    
    // Set the Number of learning iterations
    static final int ITERATIONS = 100000;   
}
