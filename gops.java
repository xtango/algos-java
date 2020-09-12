import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Gops
{   
    int[] bounty = {5, 6, 7, 8};
    
    int[][] players = {Arrays.copyOf(bounty, bounty.length), Arrays.copyOf(bounty, bounty.length)};
   // List<List<Integer>> players = new ArrayList<List<int>>();

    
 //   List <List <Integer>> players = Arrays.asList(bounty.clone(), bounty.clone());
    
    public int getRandomNumber (int len) {
        return (int) (Math.random() * len);
    }
    
    public int[] cloneRemove (int[] arr, int idx) {
        int[] newArray = new int[arr.length - 1];
        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i != idx) {
                newArray[j] = arr[i];
                j++;
            }
        }
        return newArray;
    }
    
    
    void play() {
        
        int p0score = 0;
        int p1score = 0;
        
        for (int i = 0; i < bounty.length; i++) {
            
            int p0Index = getRandomNumber(players[0].length);
            int p0 = players[0][p0Index];
            
            players[0] = cloneRemove(players[0], p0Index);
            
            int p1 = bounty[i];
            int p1Index = Arrays.asList(players[1]).indexOf(p1);
            
            if (p0 > p1) {
                p0score += bounty[i];
            } else if (p1 > p0) {
                p1score += bounty[i];
            }
            
            //System.out.println(p0);
            //System.out.println(p1);
            //System.out.println(p1Index);
            System.out.format("\nbounty: %d, p0: %d p0score: %d, p1: %d p1score: %d\n",bounty[i], p0, p0score,
            p1, p1score);
        }
        
	}

    void testRemove() {
         int[] case1 = {1, 2, 3};
          if (Arrays.toString(cloneRemove(case1, 2)).equals("[1, 2]")) {
              
                System.out.format("remove: case 1 Passed"); 
            } else {
                System.out.format("remove: case 1 fail"); 
            }
    }        
    
	public static void main(String[] args) {
	    Gops game = new Gops();
	    
	    game.testRemove();
	    
	    game.play();
	    
	   // System.out.println(Arrays.toString(game.bounty));
    //     System.out.println(Arrays.toString(game.players[0]));
    //     System.out.println(Arrays.toString(game.players[1]));

	}
}
