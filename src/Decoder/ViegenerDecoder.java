package Decoder;

import FileReader.IReaderService;
import Utils.Utils;
import java.util.ArrayList;

public class ViegenerDecoder implements IDecoder<String> {

    IReaderService<String> readerService;
    public ViegenerDecoder(IReaderService<String> service){
        readerService = service;
    }
    @Override
    public String decodeText(String filename) throws Exception {

        String originalText = readerService.getContent(filename);
        String textWithoutSpaces = originalText.replace(" ","");

        int numOfFragments = this.analyzeText(textWithoutSpaces);

        int[] counts = new int[numOfFragments];
        char[][] fragments = this.parseTextIntoFragments(textWithoutSpaces,numOfFragments,counts);
        for (int i = 0; i < fragments.length; i++) {
            for (int j = 0; j < fragments[j].length; j++) {
                if (fragments[i][j] != 0) System.out.print(fragments[i][j] + " ");
            }
            System.out.println();
        }
        int[][] columsResult = this.analyzeColumns(fragments);

        int[] passwordEN = this.findPassword(columsResult,counts, Utils.en);
        int[] passwordSK = this.findPassword(columsResult,counts, Utils.sk);

        String skText = this.printDecriptedByPassword(passwordSK,textWithoutSpaces,originalText);
        String enText = this.printDecriptedByPassword(passwordEN,textWithoutSpaces,originalText);

        return skText;
    }

    @Override
    public String getInputText(String filename) throws Exception {
        return readerService.getContent(filename);
    }

    private int analyzeText(String text){
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i < text.length()-5; i++) {
            for (int j = i+3; j < text.length()-2; j++) {
                if (text.substring(i, i + 3).equals(text.substring(j, j + 3))){
                    System.out.println(text.substring(i, i + 3) + " " + (j - i));
                    ints.add(j-i);
                }
            }
        }
        int[] passwordLen = new int[11];
        for (int i = 0; i < ints.size(); i++) {
            for (int j = 0; j < passwordLen.length; j++) {
                if (ints.get(i) % (j + 15) == 0) {
                    passwordLen[j] += 1;
                }
            }
        }
        int maxCount = 0;
        int retVal = 0;

        for (int i = 0; i < passwordLen.length; i++) {
            if (maxCount < passwordLen[i]){
                maxCount = passwordLen[i];
                retVal = i + 15;
            }
        }
        return retVal;
    }

    private char[][] parseTextIntoFragments(String text, int numberOfFragments, int[] counts){
        char[][] fragments;
        fragments = new char[(text.length()/numberOfFragments)+1][numberOfFragments];
        for (int i = 0; i < text.length(); i++) {
            fragments[i/numberOfFragments][i%numberOfFragments] = text.charAt(i);
            counts[i%numberOfFragments] += 1;
        }

        return fragments;
    }

    private int[][] analyzeColumns(char [][] field){
        int[][] result = new int[26][field[1].length];
        for (int i = 0; i < field[1].length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[j][i] != 0) {
                    int val = field[j][i] - 65;
                    result[val][i] = result[val][i] + 1;
                }
            }
        }

        return result;
    }

    private int[] findPassword(int [][] field, int[] counts, double[] compare){
        int[] password = new int[field[1].length];
        for (int i = 0; i < field[1].length; i++) {

            double bestSum = 999999999;
            for (int k = 0; k < 26; k++) {
                double sum = 0;
                for (int j = 0; j < field.length; j++) {
                    // compare[j] je fixne takze vlastne hladam ze aky je posun aby som nasiel dane pismeno "j"
                    //sum += Math.pow(((double)field[(j+k)%26][i] / counts[i]) - compare[j],2);
                    //----------------------------------------------------------------------------------------
                    // asi lepsie takto
                    sum += Math.pow(((double)field[(j)][i] / counts[i]) - compare[(j-k+26)%26],2);

                }
                if (sum < bestSum){
                    bestSum = sum;
                    password[i] = k;
                }
            }

        }
        return password;

    }

    private String printDecriptedByPassword(int[] password, String textToDecrypt, String originalText){
        String decryptedText = "";
        for (int i = 0; i < textToDecrypt.length(); i++) {
            decryptedText = decryptedText + (char)(((textToDecrypt.charAt(i) - 65) + 26 - password[i%(password.length)])%26 + 65);
        }
        System.out.println();
        decryptedText = fillEmptySpaces(decryptedText, originalText);
        System.out.println(decryptedText);
        return decryptedText;
    }

    private static String fillEmptySpaces(String s, String originalText){
        int counter = 0;
        String newS = "";
        for (int i = 0; i < originalText.length(); i++) {
            if (originalText.charAt(i) == ' '){
                newS = newS + " ";
            } else {
                newS = newS + s.charAt(counter);
                counter++;
            }

        }
        return newS;
    }

}
