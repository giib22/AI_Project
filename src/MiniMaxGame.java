import java.awt.List;
import java.io.*;
import java.util.*;
public class MiniMaxGame {
    private static int positions_eval = 0;
    private static int minimax_est = 0;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        File InputFile = new File(args[0]);
        File OutputFile = new File(args[1]);
        int depth = Integer.parseInt(args[2]);
        try {
            FileInputStream in = new FileInputStream(InputFile);
            PrintWriter out = new PrintWriter(new FileWriter(OutputFile));

            //FileOutputStream fos = new FileOutputStream(fOutputFile);
            Scanner scan = new Scanner(in);

            while (scan.hasNextLine()) {
                //reading the board file
                String str = scan.next();

                //adding the context of the file into a char array
                char[] board = str.toCharArray();

                //starting the game in min max opening
                MiniMaxGame game = new MiniMaxGame();


                //new board from max min
                char[] board2 = game.MaxMin(board, depth);


                //(the program replies:)
                System.out.println("Board Position :" + new String(board2));
                System.out.println("Positions evaluated by static estimation : " + game.positions_eval);
                System.out.println("MINIMAX estimate : " + game.minimax_est);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




    //Recursive Alpha-Beta pruning
    public char[] MaxMin(char[] board, int depth)
    {

        if(depth>0) {
            //remove 1 from depth
            depth--;

            //new leaf
            ArrayList<char[]> child = new ArrayList<char[]>();
            char[] minBoard;
            char[] maxBoardchoice = new char[50];
            child = generateMovesMidgameEndgame(board);
            for(char[] c : child) {
                System.out.println("the possible moves for white are: " + new String(c));
            }
            //setv=−∞
            int v=-999999;
            for(int i=0;i<child.size();i++) {
                minBoard = MinMax(child.get(i), depth);
                if(v<staticEstimation(minBoard)) {
                    v = staticEstimation(minBoard);
                    minimax_est = v;
                    maxBoardchoice = child.get(i);
                }
            }
            return maxBoardchoice;
        }
        else if(depth==0) {
            positions_eval++;
        }
        return board;
    }

    public char[] MinMax(char[] x, int depth) {

        if(depth>0) {
            System.out.println("current depth at MinMax is"+depth);
            depth--;
            ArrayList<char[]> bchildren = new ArrayList<char[]>();
            char[] maxBoard;
            char[] minBoardchoice = new char[50];
            bchildren = BlackgenerateMoves(x);
            for(char[] bc : bchildren) {
                System.out.println("the possible moves for black are: "+new String(bc));
            }
            int v=999999;
            for(int i=0;i<bchildren.size();i++) {

                maxBoard = MaxMin(bchildren.get(i), depth);
                if(v>staticEstimation(maxBoard)) {
                    v = staticEstimation(maxBoard);
                    //minimax_estimate = v;
                    minBoardchoice = bchildren.get(i);
                }
            }
            return minBoardchoice;
        }
        else if(depth==0) {
            positions_eval++;
        }
        return x;
    }

    //generate black moves
    public ArrayList BlackgenerateMoves(char[] board)
    {


        for(int i=0;i<board.length;i++) {
            if(board[i]=='W') {
                board[i] = 'B';
                continue;
            }
            if(board[i]=='B') {
                board[i] = 'W';
            }
        }

        ArrayList<char[]> black_board = new ArrayList<char[]>();
        ArrayList<char[]> black_swap = new ArrayList<char[]>();

        black_board = generateMovesMidgameEndgame(board);
        for(char[] y : black_board) {
            char[] board2 = y;
            for(int i = 0; i < board2.length; i++) {
                if(board2[i] =='W') {
                    board2[i] = 'B';
                    continue;
                }
                if(board2[i]=='B') {
                    board2[i] = 'W';
                }
            }
            black_swap.add(y);
        }
        return black_swap;
    }

    //compute the board tempb by swapping the colors in b. Replace each W by a B, and each B by a W.
    public char[] tempb(char[] board)
    {
        //make a copy of the board
        char[] swapped_board = board.clone();

        //itterate the board and swapp the things
        for(int i=0; i < swapped_board.length; i++)
        {
            if(swapped_board[i] =='W')
            {
                swapped_board[i] = 'B';
                continue;
            }
            if(swapped_board[i] =='B')
            {
                swapped_board[i] = 'W';
            }
        }

        return swapped_board;
    }

     //copied from minimaxopening
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


    //static estimation
    public int staticEstimation(char[] board) {
        int white_count = 0;
        int black_count = 0;
        ArrayList<char[]> List = new ArrayList<char[]>();
        List = BlackgenerateMoves(board);
        int bmovecount = List.size();


        for (int i = 0; i < board.length; i++)
        {
            if (board[i] == 'W')
            {
                white_count++;
            } else if (board[i] == 'B')
            {
                black_count++;
            }
        }

        //if (numBlackPieces ≤ 2) return(10000)
        if (black_count <= 2)
        {
            return 10000;
        }

        //else if (numWhitePieces ≤ 2) return(-10000)
        else if (white_count <= 2)
        {
            return -10000;
        } //else if (numBlackMoves==0) return(10000)
        else if (bmovecount == 0)
        {
            return 10000;
        } //else return ( 1000(numWhitePieces − numBlackPieces) - numBlackMoves)
        else
        {
            return ((1000 * (white_count - black_count)) - bmovecount);
        }
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

}
