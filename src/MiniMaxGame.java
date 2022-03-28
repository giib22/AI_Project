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

                //print out position and minmax estimate after the minmax opening
                System.out.println(game.positions_eval);
                System.out.println(game.minimax_est);


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
    public char[] MaxMin(char[] x, int depth)
    {

        if(depth>0) {
            //remove 1 from depth
            depth--;

            //new leaf
            ArrayList<char[]> child = new ArrayList<char[]>();
            char[] minBoard;
            char[] maxBoardchoice = new char[50];
            child = generateMovesMidgameEndgame(x);
            for(char[] c : child) {
                System.out.println("the possible moves for white are: "+new String(c));
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
        return x;
    }

    public char[] MinMax(char[] x, int depth) {

        if(depth>0) {
            System.out.println("current depth at MinMax is"+depth);
            depth--;
            ArrayList<char[]> bchildren = new ArrayList<char[]>();
            char[] maxBoard;
            char[] minBoardchoice = new char[50];
            bchildren = generateBlackMoves(x);
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
    public ArrayList generateBlackMoves(char[] board)
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
                    if((copyBoard[9] == choice && copyBoard[14] == choice)||(copyBoard[3] == choice && copyBoard[2] == choice))
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
                    if((copyBoard[4] == choice && copyBoard[8] == choice)||(copyBoard[13] == choice && copyBoard[14] == choice)|| (copyBoard[13] == choice && copyBoard[14] == choice))
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
                    if((copyBoard[7] == choice && copyBoard[2] == choice)||(copyBoard[16] == choice && copyBoard[17] == choice)||(copyBoard[18] == choice && copyBoard[18] == choice))
                        return true;
                    else
                        return false;

                case 16://d5
                    if((copyBoard[13] == choice && copyBoard[19] == choice)||(copyBoard[15] == choice && copyBoard[17] == choice))

                        return true;
                    else
                        return false;

                case 17://f5
                    if((copyBoard[10] == choice && copyBoard[3] == choice)||(copyBoard[15] == choice && copyBoard[16] == choice))
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
                    if((copyBoard[1] == choice && copyBoard[11] == choice)||(copyBoard[18] == choice && copyBoard[19] == choice))
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
        ArrayList<char[]> nbmList = new ArrayList<char[]>();
        nbmList = generateBlackMoves(board);
        int bmovecount = nbmList.size();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'W') {
                white_count++;
            } else if (board[i] == 'B') {
                black_count++;
            }
        }
        if (black_count <= 2) {
            return 10000;
        } else if (white_count <= 2) {
            return -10000;
        } else if (bmovecount == 0) {
            return 10000;
        } else {
            return ((1000 * (white_count - black_count)) - bmovecount);
        }
    }
    public ArrayList generateHopping(char[] x) {
        ArrayList<char[]> ghList = new ArrayList<char[]>();
        //this.board=b.clone();
        char copyBoard[];
        for(int i=0;i<x.length;i++) {
            if(x[i]=='W') {
                for(int j=0;j<x.length;j++) {
                    if(x[j]=='x') {
                        copyBoard = x.clone();
                        copyBoard[i]='x';
                        copyBoard[j]='W';
                        if(closeMill(j,copyBoard)){
                            generateRemove(copyBoard, ghList);
                        }
                        else {
                            ghList.add(copyBoard);
                        }
                    }
                }
            }
        }
        return ghList;
    }



    public ArrayList generateRemove(char[] b, ArrayList list) {
        //ArrayList li = new ArrayList();
        //li=(ArrayList) list.clone();
        ArrayList grList = (ArrayList) list.clone();
        //char bo[] = b.clone();
        for(int i=0;i<b.length;i++) {
            if(b[i]=='B') {
                if(!(closeMill(i,b))) {
                    char cbo[]=b.clone();
                    cbo[i] = 'x';
                    grList.add(cbo);

                }
                else {
                    char cbo[]=b.clone();
                    grList.add(cbo);
                }
            }
        }
        return grList;
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

    public ArrayList generateMove(char[] x) {
        ArrayList<char[]> gmList = new ArrayList<char[]>();
        //this.board=b.clone();
        char copyBoard[];
        int[] nlist;
        for(int i=0;i<x.length;i++){
            if(x[i]=='W'){
                nlist=neighbours(i);
                for(int j: nlist) {
                    if(x[j]=='x') {
                        copyBoard = x.clone();
                        copyBoard[i]='x';
                        copyBoard[j]='W';
                        if(closeMill(j,copyBoard)){
                            generateRemove(copyBoard, gmList);
                        }
                        else {
                            gmList.add(copyBoard);
                        }
                    }
                }
            }
        }
        return gmList;
    }

}
