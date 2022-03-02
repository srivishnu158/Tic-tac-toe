// Unbetable tictactoe

import java.util.Scanner;
import java.util.Random;
import java.lang.Math;
public class Tictactoe_game
{
    //------------------------------------------ board printer ---------------------------    
    void currentpos(int[][] mat,char piece,boolean iswin){
        System.out.println("\n\t\tBoard \t\t Reference");
        System.out.println(" \t _____ _____ _____  ");
        System.out.println("\t|     |     |     |");
        for(int i = 0;i < 3;i++){
            System.out.printf("\t");
            for(int j = 0;j < 3; j++){
                if(mat[i][j] == 0){
                    System.out.printf("|  -  ");
                }
                else if(mat[i][j] == -1){
                    if(piece == 'x' || piece == 'X' || piece == '1')
                    System.out.printf("|  X  ");
                    else
                      System.out.printf("|  O  ");
                }
                else{
                    if(piece == 'x' || piece == 'X' || piece == '1')
                        System.out.printf("|  O  ");
                    else
                        System.out.printf("|  X  ");
                }
            }
            System.out.printf("|\t %d0  %d1 %d2",i,i,i);
            System.out.printf("\n");
                if(i != 2){
                    System.out.println("\t|     |     |     |");
                    System.out.println("\t|-----------------|");
                    System.out.println("\t|     |     |     |");
                }
                else{
                    System.out.println("\t|_____|_____|_____| ");
                }
        }
        if(iswin){
            return;
        }
        if(piece == '1')
            System.out.println("\tYou - X");
        else if(piece == '2')
            System.out.println("\tYou - O");
        else
            System.out.println("\tYou - " + piece);
        if(piece == 'x' || piece == 'X' || piece == '1')
        System.out.println("\tCPU - o");
        else
        System.out.println("\tCPU - x");
        System.out.println("\tTo quit - q/Q");
        System.out.print("Your move: ");
        
    }
    
    //==================================================================================================
    //------------------------------checking for array range------------------------
    boolean check_validity(int value){
        if(value == 0 || value == 1 || value == 2 || value == 10 ||
            value ==  11 || value == 12 || value == 20 || value == 21 || value == 22 )
            return true;
        return false;
    }
    //===============================================================================
    //--------------------------------------------human steps scanner-------------------
    void humanStep(int[][] mat,char piece,int[] human_owns, int h_index){ //*** Exception handling needed ****
        Scanner scan = new Scanner(System.in);
            this.currentpos(mat,piece,false);
            while(true){     //*** exit condition required ***
            int pos = -1;
             try{
                String temp = scan.nextLine();
                if(temp.equals("q") || temp.equals("Q"))
                    System.exit(1);
                pos = Integer.parseInt(temp);
                boolean in_range = check_validity(pos);
                if(in_range == false){
                    Exception e = new Exception();
                    throw(e);
                }
                
            }
            catch(Exception e){
                System.out.println("wrong input, try again");
                continue;
                }
                int i = pos / 10;
                int j = pos % 10;
                if(mat[i][j] == 0){
                    mat[i][j] = -1;
                    human_owns[h_index] = this.encode(i,j); // adding to human_owns array
                    return;
                }
                
                    System.out.printf("Not available, try another ");
            }

        }   
    //==================================================================================

    //-------------------------------check result--------------------------------
    boolean checkResult(int[] human_owns, int h_index){
        int rec = human_owns[h_index];
        int pre = human_owns[h_index - 1];
        for(int i = 0; i < h_index-1; i++){
            if(human_owns[i] + rec + pre == 15)
            return true;
        }
        return false;
    }
    //===========================================================================

    //------------------------------------Encode and decode blocks------------------------
    int encode(int a,int b){
        if(a == 0 && b == 0)
        return 6;
        else if(a == 0 && b == 1)
        return 7;
        else if(a == 0 && b == 2)
        return 2;
        else if(a == 1 && b == 0)
        return 1;
        else if(a == 1 && b == 1)
        return 5;
        else if(a == 1 && b == 2)
        return 9;
        else if(a == 2 && b == 0)
        return 8;
        else if(a == 2 && b == 1)
        return 3;
        else if(a == 2 && b == 2)
        return 4;
        else 
        return -1;
        
    }

    int decode(int value){
        switch(value){
            case 1:
                return 10;
            case 2:
                return 02;
            case 3:
                return 21;
            case 4:
                return 22;
            case 5:
                return 11;
               
            case 6:
                return 00;
              
            case 7:
                return 01;
              
            case 8:
                return 20;
               
            case 9:
                return 12;
            default:
                return -1;
        }
    }

    //==================================================================================

    //------------------------------------position where cpu can win----------------------------

    Integer get_Cwin_pos(int[]cpu_owns,int c_index,int[][] mat){
        //int rec = cpu_owns[c_index];
        for(int i = 0; i <= c_index; i++){
            for(int j = i+1;j <= c_index;j++){
                int pos = 15 - (cpu_owns[i] + cpu_owns[j]);
                //System.out.println(pos);
                if(pos > 0 && pos <= 9){
                    int temp_pos = this.decode(pos);
                    int x = temp_pos/10;
                    int y = temp_pos%10;
                    if(mat[x][y] == 0){
                        // System.out.println(temp_pos);
                        return temp_pos;
                    }
                } 
            }
        }
        return null;
    }
    //===============================================================================================

    // //--------------------------------------position where human can win (to block the position)------------------
    Integer get_Hwin_pos(int[]human_owns,int h_index,int[][]mat){
        int rec = human_owns[h_index];
        for(int i = 0; i < h_index; i++){
            int pos = 15 - (human_owns[i] + rec );
            if(pos > 0 && pos <= 9){
                int temp_pos = this.decode(pos);
                int x = temp_pos/10;
                int y = temp_pos%10;
                if(mat[x][y] == 0){
                    return this.decode(pos);
                }
            }
        }
        return null;
    }

    //=============================================================

    //-------------------------------- start --------------------------------------
 void start(char choice,int[][]mat){
        int[] cpu_owns = new int[5];
        int[] human_owns = new int[5];
        int count = 0,c_index = -1,h_index = -1;
        if(choice == 'X'|| choice == 'x'|| choice == '1'){
           this.humanStep(mat,choice,human_owns,++h_index);
           
            if(mat[1][1] == 0){ //compStep(mat);
                mat[1][1] = 1;
                cpu_owns[++c_index] = this.encode(1,1);
            }
            else{
                mat[0][0] = 1;
                cpu_owns[++c_index] = this.encode(0,0);
            }
            
            this.humanStep(mat,choice,human_owns,++h_index);
            count = 3;
        }
        else {
            mat[1][1] = 1; //compStep(mat);
            cpu_owns[++c_index] = this.encode(1,1);
            
            this.humanStep(mat,choice,human_owns,++h_index);
            
            if(mat[0][0] == 0 && mat[2][2] == 0){ //compStep(mat);
                mat[0][0] = 1;
                cpu_owns[++c_index] = this.encode(0,0);
            }
            else{
                mat[2][0] = 1;
                cpu_owns[++c_index] = this.encode(2,0);
            }
            
            humanStep(mat,choice,human_owns,++h_index);
            count = 4;
        }
        
        while(count < 9){ // checks if game is up 
            if( h_index > 1 && this.checkResult(human_owns,h_index)){  // checks if human has won
                this.currentpos(mat,choice,true);
                System.out.println("You won the game");
                System.exit(1);
            }
            
            Integer nMove = get_Cwin_pos(cpu_owns,c_index,mat); // searches for winning square
            if(nMove != null){
                int i = nMove / 10;
                int j = nMove % 10;
                mat[i][j] = 1;
                cpu_owns[++c_index] = this.encode(i,j);
                this.currentpos(mat,choice,true);
                System.out.println("You lost");
                System.exit(1);
            }
            
            nMove = get_Hwin_pos(human_owns,h_index,mat);
            
            if(nMove != null){
                int i = nMove / 10;
                int j = nMove % 10;
                mat[i][j] = 1;
                cpu_owns[++c_index] = this.encode(i,j);
                count ++;
            }
            else{
                if(count == 3){
                    if(mat[0][0] == 0){
                        mat[0][0] = 1;
                        cpu_owns[++c_index] = this.encode(0,0);
                    }
                    else if(mat[0][2] == 0){
                        mat[0][2] = 1;
                        cpu_owns[++c_index] = this.encode(0,2);
                    }
                     else if(mat[2][0] == 0){
                        mat[2][0] = 1;
                        cpu_owns[++c_index] = this.encode(2,0);
                    }
                    else if(mat[2][2] == 0){
                        mat[2][2] = 1;
                        cpu_owns[++c_index] = this.encode(2,2);
                    }
                }
                
                else{
                    for(int times = 0 ; times <= 100; times++){
                    
                        Random rn = new Random();
                        int answer = rn.nextInt(9) + 1;
                        int temp_pos = this.decode(answer);
                        int i = temp_pos/10;
                        int j = temp_pos%10;
                        if(mat[i][j] == 0){
                            mat[i][j] = 1;
                            cpu_owns[++c_index] = answer;
                            break;
                        }
                    }
                }
                count ++;
            }
            
            if(count < 9){
                this.humanStep(mat,choice,human_owns,++h_index);
                count ++;
            }
            if(count >= 9){
                this.currentpos(mat,choice,true);
                System.out.println("Its a draw !!");
                System.exit(1);
            }
        }
    }
    //============================================================================

    //-------------------------------------- Main method-----------------------------------
    public static void main(String[] args) {
        int[][] mat = new int[3][3];
       Scanner scan = new Scanner(System.in);
        Tictactoe_game obj1 = new Tictactoe_game();
        System.out.println("Unbetable tictactoe (You can't beat it)");
        System.out.println("Choose your piece");
        System.out.printf("X - plays first \nO - plays second\n");
        System.out.println("q/Q - quit");
        System.out.printf("Pick the piece : ");
        char piece = ' ';
        piece = scan.next().charAt(0);
        if(piece == 'q' || piece == 'Q')
            System.exit(1);
        while(piece != 'X'&& piece != 'x' && piece != '1' && piece != 'O' && piece != 'o' && piece != '2')
        {
            System.out.printf("Enter the correct choice : ");
            piece = scan.next().charAt(0);
        }
        System.out.println();
        obj1.start(piece,mat);
    }
}