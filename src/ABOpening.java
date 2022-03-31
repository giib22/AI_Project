import java.awt.List;
import java.io.*;
import java.util.*;
public class ABOpening
{
    private static int positions_eval = 0;
    private static int  minimax_estimate = 0;



    // main
    public static void main(String[] args)
    {

        File InputFile = new File(args[0]);
        File OutputFile = new File(args[1]);
        int depth = Integer.parseInt(args[2]);
        try
        {
            FileInputStream infile = new FileInputStream(InputFile);
            PrintWriter outfile = new PrintWriter(new FileWriter(OutputFile));

            Scanner scan= new Scanner(infile);

            while(scan.hasNextLine()){
                //reading the board file
                String pos= scan.next();

                //adding the context of the file into a char array
                char[] board = pos.toCharArray();

                //starting the game in min max opening
                ABOpening opening = new ABOpening();

                //making sure all the files are working properly
                //System.out.println("board1.txt:"+ new String(board));
                int a= -100000;
                int b = 1000000;



                //new board from max min
                char[] board3 = opening.MaxMin(board, depth, a, b);
                char[] board2 = opening.tempb(board3);

                //printing to the board2.txt

                outfile.println("Board Position: " + new String(board2));
                outfile.println("Positions evaluated by static estimation: " + opening.positions_eval);
                outfile.println("MINIMAX estimate: " + opening.minimax_estimate);
                //printing to the console
                System.out.println("Board Position: " + new String(board2));
                System.out.println("Positions evaluated by static estimation: " + opening.positions_eval);
                System.out.println("MINIMAX estimate: " + opening.minimax_estimate);
            }
            //closing input file
            infile.close();
            //closing output file
            outfile.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    //compute the board tempb by swapping the colors in b. Replace each W by a B, and each B by a W.
    public char[] tempb(char[] board)
    {

        //itterate the board and swapp the things
        for(int i=0; i < board.length; i++)
        {
            //whats white will be black
            if(board[i] =='W')
            {
                board[i] = 'B';
                continue;
            }

            //whats black will be white
            if(board[i] =='B')
            {
                board[i] = 'W';
            }
        }

        return board;
    }

    public char[] MaxMin(char[] board, int depth, int alpha, int beta) {

        if(depth>0) {

            depth--;
            ArrayList<char[]> child = generateAdd(board);
            char[] min_board;
            char[] max_board = new char[100];


            int v=-1000000;

            for(int i = 0; i < child.size(); i++) {

                min_board = MinMax(child.get(i), depth, alpha, beta);
                if( v <static_estimation(min_board)) {
                    v = static_estimation(min_board);
                    //positions_evaluated++;
                    minimax_estimate = v;
                    max_board = child.get(i);
                }
                if(v>=beta) {

                    return max_board;
                }
                else {

                    alpha = Math.max(v, alpha);
                }
            }
            return max_board;
        }
        else if(depth==0) {
            positions_eval++;
        }
        return board;
    }

    public char[] MinMax(char[] board, int depth, int alpha, int beta) {

        if(depth>0) {

            depth--;
            ArrayList<char[]> black_child = black_generateMoves(board);
            char[] maxBoard;
            char[] minBoardchoice = new char[50];


            int v=999999;

            for(int x=0; x < black_child.size(); x++) {

                maxBoard = MaxMin(black_child.get(x), depth, alpha, beta);
                if(v > static_estimation(maxBoard)) {

                    v = static_estimation(maxBoard);
                    minBoardchoice = black_child.get(x);
                }
                if(v<=alpha) {

                    return minBoardchoice;
                }
                else {

                    beta = Math.min(v,beta);
                }
            }
            return minBoardchoice;
        }
        else if(depth==0) {
            positions_eval++;
        }
        return board;
    }










    //input board Position
    //it returns an array list
    //A move generator for White
    public ArrayList generateAdd(char[] board_position) {

        //L = empty List
        ArrayList<char[]> L = new ArrayList<char[]>();

        //copy of the board
        char board_copy[];

        //for each location in board:
        for(int i=0; i<board_position.length; i++)
        {
            //if the location is empty AKA x
            if(board_position[i]=='x'){
                board_copy = board_position.clone();
                board_copy[i]='W';

                //if closeMill(location, b) generateRemove(b, L) else add b to L
                if(closeMill(i, board_copy))
                {
                    L = generateRemove(board_copy, L);
                }
                else
                {
                    L.add(board_copy);
                }
            }
        }
        return L;
    }

    //closeMill to  figure out if they got 1 win
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
                    if((copyBoard[7] == choice && copyBoard[2] == choice)||(copyBoard[16] == choice && copyBoard[17] == choice)||(copyBoard[18] == choice && copyBoard[12] == choice))
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


    //Input: a board position and a list L
    public ArrayList generateRemove(char[] board, ArrayList list)
    {
        //for the length of the board
        for(int i=0;i<board.length;i++)
        {
            if(board[i]=='B')
            {
                if(!(closeMill(i,board))) {
                    char copy_board[] = board.clone();
                    copy_board[i] = 'x';
                    list.add(copy_board);

                }
                else {
                    //If no positions were added (all black pieces are in mills) add b to L.
                    char copy_boad[]=board.clone();
                    list.add(copy_boad);

                }
            }
        }
        //positions are added to L by removing black pieces
        return list;
    }




    public int static_estimation(char[] board) {
        int white_count = 0;
        int black_count = 0;

        for(int i=0;i<board.length;i++) {
            if(board[i]=='W') {
                white_count++;
            }
            else if(board[i]=='B') {
                black_count++;
            }
        }
        return white_count-black_count;
    }







    public ArrayList black_generateMoves(char[] board)
    {


        for(int i=0;i<board.length;i++)
        {
            if(board[i]=='W') {
                board[i] = 'B';
                continue;
            }
            if(board[i]=='B')
            {
                board[i] = 'W';
            }
        }

        ArrayList<char[]> moves = generateAdd(board);
        ArrayList<char[]> swap = new ArrayList<char[]>();


        for(char[] x : moves) {
            char[] board2 = x;
            for(int i = 0; i < board2.length; i++) {
                if(board2[i] == 'W') {
                    board2[i] = 'B';
                    continue;
                }
                if(board2[i] == 'B') {
                    board2[i] = 'W';
                }
            }
            swap.add(x);
        }
        return swap;
    }



}
