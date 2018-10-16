/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolffilm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jwolf
 */
public class Wolffilm {

    private static void readMatrix() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("chart.csv"));
            String file="";
            String line = br.readLine();
            for (int i = 0; line != null; i++) {
                file+=line+"\n";
                line = br.readLine();
            }
            String[] lines = file.split("\n");
            matrix = new String[lines.length][];
            for (int i = 0; i < lines.length; i++) {
                matrix[i] = lines[i].split(";");
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Wolffilm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Wolffilm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String[][] matrix;

    public static void main(String[] args) {
        readMatrix();
        printMatrix();
        input();
    }

    private static void printMatrix() {
        String top_bottom = "_ _ _ _ _ _ _ _ _ _ "
                + "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ "
                + "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ "
                + "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ "
                + "_ _ _ _ _ _ _ _ _ _ _ _ _ _";
        System.out.println("You can check results via this table:");
        System.out.println(top_bottom);
        System.out.println("|\tTemp\tTime\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t|");
        System.out.println(top_bottom);
        for (String[] arr : matrix) {
            System.out.print("|");
            for (String string : arr) {
                System.out.print("\t" + string + "");
            }
            System.out.println("\t|");
        }
        System.out.println(top_bottom);
    }

    private static void input() {
        Scanner scan = new Scanner(System.in);
        System.out.println("");
        System.out.println("Enter the recommended temperature:");
        String rec_temp = scan.next();
        System.out.println("Enter the recommended time:");
        String rec_time = scan.next();
        System.out.println("Enter the actual temperature of the developer:");
        String new_temp = scan.next();
        System.out.println("The new time to develop for is:");
        System.out.println(calculateTime(rec_temp, rec_time, new_temp));
    }

    private static String calculateTime(String t1, String t2, String t3) {
        double rec_temp = Double.parseDouble(t1);
        double rec_time = Double.parseDouble(t2);
        double new_temp = Double.parseDouble(t3);
        double new_time = 0;
        double delta = 0;
        int index=0;

        for (String[] matrix1 : matrix) {
            if (Double.parseDouble(matrix1[0]) == rec_temp) {
                for (int i = 1; i < matrix1.length; i++) {
                    if (Double.parseDouble(matrix1[i]) == rec_time) {
                        index = i;
                        break;
                    } else if (Double.parseDouble(matrix1[i]) >= rec_time) {
                        delta = rec_time-Double.parseDouble(matrix1[i-1]);
                        delta = delta/(Double.parseDouble(matrix1[i])-Double.parseDouble(matrix1[i-1]));
                        System.out.println("delta "+delta);
                        index = i-1;
                        break;
                    }
                }
            }
        }
        
        for (int i = 0; i<matrix.length; i++){
            if (Double.parseDouble(matrix[i][0]) == new_temp) {
                if (delta!=0) {
                    new_time = Double.parseDouble(matrix[i][index]) + delta * (Double.parseDouble(matrix[i][index+1])-Double.parseDouble(matrix[i][index]));
                }
                else
                {
                    new_time = Double.parseDouble(matrix[i][index]);
                }
                int minutes = (int) Math.floor(new_time);
                int seconds = (int) ((new_time-minutes)*60);
                return minutes + "m"+seconds + "s";
            }
            else if (Double.parseDouble(matrix[i][0]) <= new_temp)
            {
                double delta_temp = new_temp-Double.parseDouble(matrix[i][0]);
                System.out.println("delta temp "+delta_temp);
                double base_time = Double.parseDouble(matrix[i][index]);
                System.out.println("base "+base_time);
                double next_time = Double.parseDouble(matrix[i+1][index]);
                System.out.println("next "+next_time);
                new_time = base_time + (delta)*((1-delta_temp)*(next_time-base_time));
                
                int minutes = (int) Math.floor(new_time);
                int seconds = (int) ((new_time-minutes)*60);
                return minutes + "m"+seconds + "s";
            }
        }

        return new_time + "";
    }

}
