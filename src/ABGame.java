import java.io.*;
import java.util.*;
import java.awt.*;

public class ABGame
{
    private static int positions_eval = 0;
    private static int  minimax_estimate = 0;

    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        File InputFile = new File(args[0]);
        File OutputFile = new File(args[1]);
        int depth = Integer.parseInt(args[2]);
        try
        {
            FileInputStream in = new FileInputStream(InputFile);
            PrintWriter out = new PrintWriter(new FileWriter(OutputFile));
            //FileOutputStream fos = new FileOutputStream(fOutputFile);
            Scanner scan= new Scanner(in);

            while(scan.hasNextLine()){
                //reading the board file
                String str= scan.next();

                //adding the context of the file into a char array
                char[] board = str.toCharArray();

                //starting the game in min max opening
                ABOpening opening = new ABOpening();

                //making sure all the files are working properly
                //System.out.println("board from file : "+ new String(board));
                int a= -100000;
                int b = 1000000;
                //board for the recursive alpha beta
                char[] board2 = opening.MaxMin(board,depth,a,b);

                //make sure everything is correct
                System.out.println(new String(board2));

                //(the program replies:)
                System.out.println("Board Position :" + new String(board2));
                System.out.println("Positions evaluated by static estimation : " + opening.positions_eval);
                System.out.println("MINIMAX estimate : " + opening.minimax_estimate);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public ArrayList generateMovesMidgameEndgame(char[] board)
    {
        ArrayList<char[]> List = new ArrayList<char[]>();
        int white_count=0;

        //if the board has 3 white pieces Return the list produced by GenerateHopping
        //itterating the board to see if there is 3 and then they increase the count
        for(int i = 0; i < board.length; i++){
            if(board[i]=='W')
            {
                white_count++;
            }
        }
        if(white_count==3)
        {
            //Return the list produced by GenerateHopping
            //applied to the board
            List = generateHopping(board);
            return List;
        }
        else
        {
            //Otherwise return the list produced by GenerateMove applied to the board.
            List = generateMove(board);
            return List;
        }


    }
}
