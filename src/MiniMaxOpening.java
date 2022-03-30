import java.awt.List;
import java.io.*;
import java.util.*;
/*
public class MiniMaxOpening
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
                String str= scan.next();

                //adding the context of the file into a char array
                char[] board = str.toCharArray();

                //starting the game in min max opening
                MiniMaxOpening opening = new MiniMaxOpening();

                //making sure all the files are working properly
                //System.out.println("board1.txt:"+ new String(board));


                //new board from max min
                char[] board3 = opening.MaxMin(board, depth);


                //printing to the board2.txt

                outfile.println("Board Position: " + new String(board3));
                outfile.println("Positions evaluated by static estimation: " + opening.positions_eval);
                outfile.println("MINIMAX estimate: " + opening.minimax_estimate);
                //printing to the console
                System.out.println("Board Position: " + new String(board3));
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

    public char[] MaxMin(char[] board, int depth)
    {

        if(0 < depth) {

           //subtracting depth
            depth--;

            //array for the leafs
            ArrayList<char[]> child =generateAdd(board);


            char[] maxBoard = new char[100];

            //this is supposed to be - infinity
            int v=-100000;
            //get from minmax method
            char[] min_board;
            //for each child y of x:
            for(int i=0; i < child.size(); i++) {

                //positions_evaluated++;
                //v = max(v, MinMax(y))

                //minBoard=y
                min_board = MinMax(child.get(i), depth);

                if(v < static_estimation(min_board)) {
                    v = static_estimation(min_board);
                    minimax_estimate = v;
                    //add the leaf into the new board
                    maxBoard = child.get(i);
                }
            }
            return maxBoard;
        }

        //else increase the position
        else if(depth == 0){
            positions_eval++;
        }

        return board;
    }


    public char[] MinMax(char[] x, int depth) {

        if(depth>0) {
            //subtract depth again
            depth--;
            //array for black leafs
            ArrayList<char[]> black_child = black_generateMoves(x);
            char[] max_board;
            char[] min_board = new char[100];


            //this is supposed to be infinity
            int v=100000000;

            //for each child y of x:
            for(int i = 0; i < black_child.size(); i++)
            {
                max_board = MaxMin(black_child.get(i), depth);
                if(v > static_estimation(max_board)) {
                    v = static_estimation(max_board);
                    min_board = black_child.get(i);
                }
            }
            return min_board;
        }
        else if(depth==0){
            positions_eval++;
        }
        return x;
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



}*/

public class MiniMaxOpening {

    /**
     * @param args
     */

    private static int positions_evaluated=0;
    private static int  minimax_estimate=0;


    public ArrayList generateAdd(char[] b) {
        ArrayList<char[]> gaList = new ArrayList<char[]>();
        //this.board=b.clone();
        char copyBoard[];
        for(int i=0;i<b.length;i++){
            if(b[i]=='x'){
                copyBoard = b.clone();
                copyBoard[i]='W';
                if(closeMill(i,copyBoard)){
                    gaList = generateRemove(copyBoard, gaList);
                }
                else {
                    gaList.add(copyBoard);
                }
            }
        }
        return gaList;
    }

    public boolean closeMill(int loc, char[] copyBoard){
        char c = copyBoard[loc];
        if(c=='W' || c=='B'){
            switch(loc) {
                case 0 : if((copyBoard[6]==c && copyBoard[18]==c)||(copyBoard[2]==c && copyBoard[4]==c))
                    return true;
                else
                    return false;//a0
                case 6: if((copyBoard[7]==c && copyBoard[8]==c)||(copyBoard[0]==c && copyBoard[18]==c))
                    return true;
                else
                    return false;//a3
                case 18: if((copyBoard[0]==c && copyBoard[6]==c)||(copyBoard[19]==c && copyBoard[20]==c))
                    return true;
                else
                    return false;//a6
                case 2: if((copyBoard[0]==c && copyBoard[4]==c)||(copyBoard[7]==c && copyBoard[15]==c))
                    return true;
                else
                    return false;//b1
                case 7: if((copyBoard[6]==c && copyBoard[8]==c)||(copyBoard[2]==c && copyBoard[15]==c))
                    return true;
                else
                    return false;//b3
                case 15: if((copyBoard[7]==c && copyBoard[2]==c)||(copyBoard[16]==c && copyBoard[17]==c))
                    return true;
                else
                    return false;//b5
                case 4: if((copyBoard[0]==c && copyBoard[2]==c)||(copyBoard[8]==c && copyBoard[12]==c))
                    return true;
                else
                    return false;//c2
                case 8: if((copyBoard[6]==c && copyBoard[7]==c)||(copyBoard[4]==c && copyBoard[12]==c))
                    return true;
                else
                    return false;//c3
                case 12: if((copyBoard[4]==c && copyBoard[8]==c)||(copyBoard[13]==c && copyBoard[14]==c))
                    return true;
                else
                    return false;//c4
                case 13: if((copyBoard[12]==c && copyBoard[14]==c)||(copyBoard[16]==c && copyBoard[19]==c))
                    return true;
                else
                    return false;//d4
                case 16: if((copyBoard[13]==c && copyBoard[19]==c)||(copyBoard[15]==c && copyBoard[17]==c))
                    return true;
                else
                    return false;//d5
                case 19: if((copyBoard[13]==c && copyBoard[16]==c)||(copyBoard[18]==c && copyBoard[20]==c))
                    return true;
                else
                    return false;//d6
                case 5: if(copyBoard[9]==c && copyBoard[14]==c)
                    return true;
                else
                    return false;//e2
                case 9: if((copyBoard[5]==c && copyBoard[14]==c)||(copyBoard[10]==c && copyBoard[11]==c))
                    return true;
                else
                    return false;//e3
                case 14: if((copyBoard[5]==c && copyBoard[9]==c)||(copyBoard[12]==c && copyBoard[13]==c))
                    return true;
                else
                    return false;//e4
                case 3: if(copyBoard[10]==c && copyBoard[17]==c)
                    return true;
                else
                    return false;//f1
                case 10: if((copyBoard[3]==c && copyBoard[17]==c)||(copyBoard[9]==c && copyBoard[11]==c))
                    return true;
                else
                    return false;//f3
                case 17: if((copyBoard[3]==c && copyBoard[10]==c)||(copyBoard[15]==c && copyBoard[16]==c))
                    return true;
                else
                    return false;//f5
                case 1: if(copyBoard[11]==c && copyBoard[20]==c)
                    return true;
                else
                    return false;//g0
                case 11: if((copyBoard[1]==c && copyBoard[20]==c)||(copyBoard[9]==c && copyBoard[10]==c))
                    return true;
                else
                    return false;//g3
                case 20: if((copyBoard[1]==c && copyBoard[11]==c)||(copyBoard[18]==c && copyBoard[19]==c))
                    return true;
                else
                    return false;//g6
            }
        }
        return false;
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

    public int staticEstimation(char[] sboard) {
        int wcount = 0;
        int bcount = 0;
        char[] lboard = sboard.clone();
        for(int i=0;i<lboard.length;i++) {
            if(lboard[i]=='W') {
                wcount++;
            }
            else if(lboard[i]=='B') {
                bcount++;
            }
        }
        return wcount-bcount;
    }


    public char[] MaxMin(char[] x, int depth) {

        if(depth>0) {

            System.out.println("current depth at MaxMin is"+depth);
            depth--;
            ArrayList<char[]> children = new ArrayList<char[]>();
            char[] minBoard;
            char[] maxBoardchoice = new char[50];
            children = generateAdd(x);
            for(char[] c : children) {
                System.out.println("the possible moves for white are: "+new String(c));
            }
            int v=-999999;

            for(int i=0;i<children.size();i++) {

                //positions_evaluated++;

                minBoard = MinMax(children.get(i), depth);
                if(v<staticEstimation(minBoard)) {
                    v = staticEstimation(minBoard);
                    minimax_estimate = v;
                    maxBoardchoice = children.get(i);
                }
            }
            return maxBoardchoice;
        }
        else if(depth==0){
            positions_evaluated++;
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
                    minBoardchoice = bchildren.get(i);
                }
            }
            return minBoardchoice;
        }
        else if(depth==0){
            positions_evaluated++;
        }
        return x;
    }

    public char[] swapWB(char[] x) {

        char[] lboard = x.clone();

        for(int i=0;i<lboard.length;i++) {
            if(lboard[i]=='W') {
                lboard[i] = 'B';
                continue;
            }
            if(lboard[i]=='B') {
                lboard[i] = 'W';
            }
        }
        return lboard;
    }

    public ArrayList generateBlackMoves(char[] x) {

        char[] lboard = x.clone();
        for(int i=0;i<lboard.length;i++) {
            if(lboard[i]=='W') {
                lboard[i] = 'B';
                continue;
            }
            if(lboard[i]=='B') {
                lboard[i] = 'W';
            }
        }

        ArrayList<char[]> gbm = new ArrayList<char[]>();
        ArrayList<char[]> gbmswap = new ArrayList<char[]>();

        gbm = generateAdd(lboard);
        for(char[] y : gbm) {
            char[] lsboard = y;
            for(int i=0;i<lsboard.length;i++) {
                if(lsboard[i]=='W') {
                    lsboard[i] = 'B';
                    continue;
                }
                if(lsboard[i]=='B') {
                    lsboard[i] = 'W';
                }
            }
            gbmswap.add(y);
        }
        return gbmswap;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        File fInputFile = new File(args[0]);
        File fOutputFile = new File(args[1]);
        int depth = Integer.parseInt(args[2]);
        try {
            FileInputStream fis = new FileInputStream(fInputFile);
            PrintWriter out = new PrintWriter(new FileWriter(fOutputFile));
            //FileOutputStream fos = new FileOutputStream(fOutputFile);
            Scanner s= new Scanner(fis);

            while(s.hasNextLine()){
                String str= s.next();
                char[] b = str.toCharArray();
                //Entry<String,Integer> bm= null;
                MiniMaxOpening m = new MiniMaxOpening();
                System.out.println("given board : "+new String(b));
                char[] e = m.swapWB(b);
                System.out.println(""+new String(e));
                char[] d = m.MaxMin(b, depth);
                System.out.println(""+m.positions_evaluated);
                System.out.println(""+m.minimax_estimate);
                out.println("Board Position : "+new String(d));
                out.println("Positions evaluated by static estimation : "+m.positions_evaluated);
                out.println("MiniMax estimate : " +m.minimax_estimate);
            }
            fis.close();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
