import java.util.*;
import java.io.*;

public class MiniMaxOpeningBlack
{
    private static int positions_eval=0;
    private static int miniMax_est = 0;

    public static void main(String[] args) {

        File InputFile = new File(args[0]);
        File OutputFile = new File(args[1]);
        int depth = Integer.parseInt(args[2]);
        try {
            FileInputStream infile = new FileInputStream(InputFile);
            PrintWriter outfile = new PrintWriter(new FileWriter(OutputFile));

            Scanner scan = new Scanner(infile);

            while(scan.hasNextLine()){

                //read the board file
                String pos= scan.next();

                char[] board = pos.toCharArray();
                //check if all everything was acanned correctly
                //System.out.println(new String(board));

                //constructor
                MiniMaxOpeningBlack opening = new MiniMaxOpeningBlack();


                //switching back w and b
                //char[] board3 = opening.tempb(board);
                //recursive getting the min max to get the correct output
                char[] board2 = opening.MinMax(board, depth);
                //switching back w and b
                //char[] board4 = opening.tempb(board2);



                outfile.println("Board Position : "+ new String(board2));
                //outfile.println("Positions evaluated by static estimation : "+opening.positions_eval);
                //outfile.println("MiniMax estimate : " +opening.miniMax_est);
                //the program outputs to console
                System.out.println("Board Position :" + new String(board2));
                System.out.println("Positions evaluated by static estimation : " +  opening.positions_eval);
                System.out.println("MINIMAX estimate : " + opening.miniMax_est);
            }
            infile.close();
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
            if(board[i] =='W')
            {
                board[i] = 'B';
                continue;
            }
            if(board[i] =='B')
            {
                board[i] = 'W';
            }
        }

        return board;
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
                    char copy_board[]=board.clone();
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
                if(closeMill(i,board_copy))
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

    public int staticEstimation(char[] board) {
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
        return white_count - black_count;
    }
    public char[] MaxMin(char[] board, int depth)
    {

        if(0 < depth) {

            depth--;
            ArrayList<char[]> child = generateAdd(board);
            char[] min_board;
            char[] max_board = new char[100];

            //negative infinity
            int v=-100000;

            for(int i = 0; i < child.size(); i++) {

                //positions_evaluated++;

                min_board = MinMax(child.get(i), depth);
                if (v < staticEstimation(min_board)) {
                    v = staticEstimation(min_board);
                    miniMax_est = v;
                    max_board = child.get(i);
                }
            }
            return max_board;
        }
        //else increase the position
        else if(depth == 0){
            positions_eval++;
        }
        return board;
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

        ArrayList<char[]> moves = new ArrayList<char[]>();
        ArrayList<char[]> swap = new ArrayList<char[]>();

        moves = generateAdd(board);



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

    public char[] MinMax(char[] x, int depth) {

        if(depth>0) {


            depth--;
            ArrayList<char[]> bchild = new ArrayList<char[]>();
            char[] maxBoard;
            char[] minBoard = new char[100];
            bchild = black_generateMoves(x);

            int v=100000000;

            for(int i=0;i<bchild.size();i++)
            {
                maxBoard = MaxMin(bchild.get(i), depth);
                if(v > staticEstimation(maxBoard)) {
                    v = staticEstimation(maxBoard);
                    minBoard = bchild.get(i);
                }
            }
            return minBoard;
        }
        else if(depth==0){
            positions_eval++;
        }
        return x;
    }
}
