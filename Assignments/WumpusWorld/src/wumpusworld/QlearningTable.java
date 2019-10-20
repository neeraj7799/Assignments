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
public class QlearningTable 
{
    private static double[][] Q = null;
 
    public static double[][] setInstance()
    {
        if(Q == null){
            Q = new double[45][5];
            
            for(int x = 1; x < 5; x++){
                
                for(int y = 1;y < 5; y++){
                    
                    for(int r = 0; r < 5; r++){
                        
                        int p = x * 10 + y;
                        
                        Q[p][r] = 0;
                        
                    }
                } 
            }
        }
        
        return Q;

    }
    
    public static int getMaxQValueAction(int position)
    {
        
        int bestAction = 1;
        for(int i = 1; i < 5; i++){
            if(Q[position][bestAction] < Q[position][i]){
                bestAction = i;
            }
        }
        return bestAction;
    }
    
    public static double getMaxQValue(int position)
    {
        
        double bestValue = Q[position][1];
        for(int i = 1; i < 5; i++){
            if(bestValue < Q[position][i]){
                bestValue = Q[position][i];
            }
        }
        return bestValue;
    }
    
    public static void printQTable()
    {
        for(int x = 1; x < 5; x++){ 
                for(int y = 1;y < 5; y++){   
                    
                    System.out.println(x * 10 + y +  " \nAction: ");
                    for(int r = 0; r < 5; r++){                     
                        int pos = x * 10 + y;
                        
                        if(r == 0){
                            System.out.println("Reward: " + Q[pos][r]);
                        }
                        System.out.println(Q[pos][r]);                      
                    }                  
                }               
        }
    }
}
