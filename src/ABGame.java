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
                // -infinity and positive infinity for the abprunning
                int a= -100000;
                int b = 1000000;
                //board for the recursive alpha beta
                char[] board2 = opening.MaxMin(board,depth,a,b);

                //make sure everything is correct
                System.out.println(new String(board2));

                //(the program replies:)
               out.println("Board Position :" + new String(board2));
               out.println("Positions evaluated by static estimation : " + opening.positions_eval);
               out.println("MINIMAX estimate : " + opening.minimax_estimate);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean closeMill(int location, char[] copyBoard){
        char choice = copyBoard[location];
        if(choice =='W' || choice =='B')
        {
            switch(location)
            {
                case 0 ://a0
                    if((copyBoard[6] == choice && copyBoard[18] == choice)||(copyBoard[2] == choice && copyBoard[4]== choice))
                        return true;
                    else
                        return false;

                case 1: //g0
                    if((copyBoard[11] == choice && copyBoard[20] == choice)||(copyBoard[3] == choice && copyBoard[5] == choice))
                        return true;
                    else
                        return false;

                case 2://b1
                    if((copyBoard[0] == choice && copyBoard[4] == choice)||(copyBoard[7] == choice && copyBoard[15] == choice))
                        return true;
                    else
                        return false;

                case 3://f1
                    if((copyBoard[10] == choice && copyBoard[17] == choice)||(copyBoard[5] == choice && copyBoard[1] == choice))
                        return true;
                    else
                        return false;

                case 4://c2
                    if((copyBoard[0] == choice && copyBoard[2] == choice)||(copyBoard[8] == choice && copyBoard[12] == choice))
                        return true;
                    else
                        return false;

                case 5://e2
                    if((copyBoard[9] == choice && copyBoard[14] == choice)||(copyBoard[3] == choice && copyBoard[1] == choice))
                        return true;
                    else
                        return false;

                case 6://a3
                    if((copyBoard[7] == choice && copyBoard[8] == choice)||(copyBoard[0] == choice && copyBoard[18] == choice))
                        return true;
                    else
                        return false;

                case 7://b3
                    if((copyBoard[6] == choice && copyBoard[8] == choice)||(copyBoard[2] == choice && copyBoard[15] == choice))
                        return true;
                    else
                        return false;

                case 8://c3
                    if((copyBoard[6] == choice && copyBoard[7] == choice)||(copyBoard[4] == choice && copyBoard[12] == choice))
                        return true;
                    else
                        return false;

                case 9://e3
                    if((copyBoard[5] == choice && copyBoard[14] == choice)||(copyBoard[10] == choice && copyBoard[11] == choice))
                        return true;
                    else
                        return false;

                case 10://f3
                    if((copyBoard[3] == choice && copyBoard[17] == choice)||(copyBoard[9] == choice && copyBoard[11] == choice))
                        return true;
                    else
                        return false;

                case 11://g3
                    if((copyBoard[1] == choice && copyBoard[20] == choice)||(copyBoard[9] == choice && copyBoard[10] == choice))
                        return true;
                    else
                        return false;

                case 12://c4
                    if((copyBoard[4] == choice && copyBoard[8] == choice)||(copyBoard[13] == choice && copyBoard[14] == choice)|| (copyBoard[15] == choice && copyBoard[18] == choice))
                        return true;
                    else
                        return false;

                case 13://d4
                    if((copyBoard[12] == choice && copyBoard[14] == choice)||(copyBoard[16] == choice && copyBoard[19] == choice))
                        return true;
                    else
                        return false;

                case 14://e4
                    if((copyBoard[5] == choice && copyBoard[9] == choice)||(copyBoard[12] == choice && copyBoard[13] == choice)||(copyBoard[17] == choice && copyBoard[20] == choice))
                        return true;
                    else
                        return false;

                case 15://b5
                    if((copyBoard[7] == choice && copyBoard[2] == choice)||(copyBoard[16] == choice && copyBoard[17] == choice)||(copyBoard[18] == choice && copyBoard[15] == choice))
                        return true;
                    else
                        return false;

                case 16://d5
                    if((copyBoard[13] == choice && copyBoard[19] == choice)||(copyBoard[15] == choice && copyBoard[17] == choice))

                        return true;
                    else
                        return false;

                case 17://f5
                    if((copyBoard[10] == choice && copyBoard[3] == choice)||(copyBoard[15] == choice && copyBoard[16] == choice)||(copyBoard[14] == choice && copyBoard[20] == choice))
                        return true;
                    else
                        return false;

                case 18://a6
                    if((copyBoard[0] == choice && copyBoard[6] == choice)||(copyBoard[19] == choice && copyBoard[20] == choice)||(copyBoard[15] == choice && copyBoard[12] == choice))
                        return true;
                    else
                        return false;

                case 19://d6
                    if((copyBoard[13] == choice && copyBoard[16] == choice)||(copyBoard[18] == choice && copyBoard[20] == choice))
                        return true;
                    else
                        return false;

                case 20://g6
                    if((copyBoard[1] == choice && copyBoard[11] == choice)||(copyBoard[18] == choice && copyBoard[19] == choice)||(copyBoard[14] == choice && copyBoard[17] == choice))
                        return true;
                    else
                        return false;
            }
        }
        return false;
    }

    public int[] neighbors(int j)
    {
        //array to input the neighbors
        int[] arr;

        switch(j)
        {
            case 0 :
                arr = new int[]{1, 2, 6};
                return arr;

            case 1 :
                arr = new int[]{0, 3, 11};
                return arr;

            case 2 :
                arr = new int[]{0, 3, 4, 7};
                return arr;

            case 3 :
                arr = new int[]{1, 2, 5, 10};
                return arr;

            case 4 :
                arr = new int[]{2, 5, 8};
                return arr;

            case 5 :
                arr = new int[]{3, 4, 9};
                return arr;

            case 6 :
                arr = new int[]{0, 7, 18};
                return arr;

            case 7 :
                arr = new int[]{2, 6, 8, 15};
                return arr;

            case 8 :
                arr = new int[]{4, 7, 12};
                return arr;

            case 9 :
                arr=new int[]{5, 10, 14};
                return arr;

            case 10 :
                arr = new int[]{3, 9, 11, 17};
                return arr;

            case 11 :
                arr =new int[]{1, 10, 20};
                return arr;

            case 12 :
                arr=new int[]{8, 13, 15};
                return arr;

            case 13 :
                arr = new int[]{12, 14, 16};
                return arr;

            case 14 : arr = new int[]{9, 13, 17};
                return arr;

            case 15 :
                arr = new int[]{7, 12, 16, 18};
                return arr;

            case 16 :
                arr = new int[]{13, 15, 17, 19};
                return arr
                        ;
            case 17 :
                arr = new int[]{10, 14, 16, 20};
                return arr;

            case 18 :
                arr = new int[]{6, 15, 19};
                return arr;

            case 19 :
                arr = new int[]{16, 18, 20};
                return arr;

            case 20 :
                arr = new int[]{11, 17, 19};
                return arr;

            default:
                arr = new int[]{};
                return arr;

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
    public ArrayList generateMove(char[] board)
    {
        //empty list
        ArrayList<char[]> list = new ArrayList<char[]>();

        //for each location in board
        for(int i = 0; i < board.length; i++){
            //if board loacation =='w'
            if(board[i]=='W')
            {
                //make a list of neighbors location
                //**** CREATE NEIGHBORS METHOD*****
                int[] n_list = neighbors(i);

                //for each j in n

                for(int j: n_list)
                {
                    //if board in location j empty
                    if(board[j]=='x')
                    {
                        //b = copy of board; b[location] = empty; b[j]=W
                        char board_copy[] = board.clone();
                        board_copy[i]='x';
                        board_copy[j]='W';

                        //if closeMill(j, b) GenerateRemove(b, L)
                        if(closeMill(j, board_copy))
                        {
                            generateRemove(board_copy, list);
                        }
                        //else add b to L
                        else
                        {
                            list.add(board);
                        }
                    }
                }
            }
        }
        return list;
    }
    public ArrayList generateHopping(char[] board)
    {
        //L = empty list
        ArrayList<char[]> list = new ArrayList<char[]>();
        //for each location α in board
        for(int a = 0; a < board.length; a++)
        {
            //if board[α] == W
            if(board[a] == 'W')
            {
                //for each location β in board
                for(int b = 0; b < board.length; b++)
                {
                    //if board[β] == empty aka x
                    if(board[b] == 'x')
                    {
                        //b = copy of board; b[α] = empty; b[β] = W
                        char board_copy[] = board.clone();
                        board_copy[a]='x';
                        board_copy[b]='W';


                        //if closeMill(β, b) generateRemove(b, L)
                        if(closeMill(b, board_copy))
                        {
                            generateRemove(board_copy, list);
                        }
                        else
                        {
                            // else add b to L
                            list.add(board_copy);
                        }
                    }
                }
            }
        }
        return list;
    }

    public ArrayList generateRemove(char[] board, ArrayList L)
    {

        //for each location in board:
        for(int i = 0; i < board.length; i++)
        {
            if(board[i] == 'B')
            {
                //if not closeMill(location, board)
                if(!(closeMill(i,board)))
                {
                    char board_copy[]=board.clone();
                    board_copy[i] = 'x';
                    L.add(board_copy);

                }
                else
                {
                    //If no positions were added (all black pieces are in mills) add b to L.
                    char board_copy[]=board.clone();
                    L.add(board_copy);
                }
            }
        }
        return L;
    }
    //generate black moves
    public ArrayList BlackgenerateMoves(char[] board)
    {
        //similar to the tempb method
        for(int i = 0; i < board.length; i++)
        {   //if theyre white make black
            if(board[i]=='W') {
                board[i] = 'B';
                continue;
            }

            //if theyre black make white
            if(board[i]=='B')
            {
                board[i] = 'W';
            }
        }

        //black_board will get its contents from GMME method
        ArrayList<char[]> black_board = generateMovesMidgameEndgame(board);
        ArrayList<char[]> black_swap = new ArrayList<char[]>();

        for(char[] x : black_board)
        {
            char[] board2 = x;
            for(int i = 0; i < board2.length; i++)
            {
                if(board2[i] =='W')
                {
                    board2[i] = 'B';
                    continue;
                }
                if(board2[i]=='B')
                {
                    board2[i] = 'W';
                }
            }
            black_swap.add(x);
        }
        return black_swap;
    }

    //compute the board tempb by swapping the colors in b. Replace each W by a B, and each B by a W.
    public char[] tempb(char[] swapped_board)
    {

        //itterate the board and swapp the things
        for(int i=0; i < swapped_board.length; i++)
        {
            if(swapped_board[i] =='W')
            {
                swapped_board[i] = 'B';
            }
            if(swapped_board[i] =='B')
            {
                swapped_board[i] = 'W';
            }
        }

        return swapped_board;
    }

}
