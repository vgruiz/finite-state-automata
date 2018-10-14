/*
 * Victor Ruiz
 * CS 311 - Formal Language and Automata
 * Professor Daisy Sang
 * 
 * Project 1
 */

import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		//relevant to getting the input data
		Scanner input = new Scanner(System.in);
		Scanner reader = null;
		String fileName;
		File f;
		
		//relevant to capturing and organizing the input data
		int numStates;
		int nTransitions;
		int nTestStrings;
		char[] finalStatesChar;
		char[] alphabet;
		char[][] transitions;
		String temp;
		String[] finalStates;
		String[] tempArr;
		String[] testStrings;
		
		//getting input file and initializing a scanner to read from it	
		System.out.print("Please provide the file name of the FSA: ");
		fileName = input.next();
		f = new File(fileName);
		
		try {
			reader = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int z = 0; z < 5; z++) {		//to run through the 5 FSAs in the input file
			System.out.println("FSA #" + (z+1));
			
			//reading data from the file and printing the information to the console
			nTransitions = 0;
			nTestStrings = 10;
			testStrings = new String[nTestStrings];

			
			//start reading in the FSA data
			//reader.nextLine(); //to skip a line
			temp = reader.nextLine();
			numStates = Integer.parseInt(temp);				//the first value is the total number of states in the machine
			System.out.println("(1) number of states: " + numStates);
			
			finalStates = reader.nextLine().split(" "); 	//the set of final states
			finalStatesChar = new char[finalStates.length];
			System.out.print("(2) final states: ");
			for(int i = 0; i < finalStates.length; i++) {
				char c = finalStates[i].charAt(0);
				finalStatesChar[i] = c;
				System.out.print(finalStates[i] + ", ");
			}
			System.out.println("");
			
			temp = reader.nextLine();		//the set of characters that makes up the alphabet
			tempArr = temp.split(" ");
			alphabet = new char[tempArr.length + 1];
			System.out.print("(3) alphabet: ");
			for(int i = 0; i < tempArr.length; i++) {
				alphabet[i] = tempArr[i].charAt(0);
				System.out.print(alphabet[i] + ", ");
			}
			
			nTransitions = numStates * alphabet.length - numStates; //determine the number of transitions primarily for output formatting purposes		
			
			transitions = new char[nTransitions][3];	//each transition has 3 chars as defined in the project description
			int r = 0, c; //row, column
			int n = 0;	//for testStrings array
			
			while(reader.hasNext()) {			//reading through the transitions
				String tr = reader.nextLine();
				char[] trArr = tr.toCharArray();
				
				if(tr.charAt(0) == '(') {		//to ensure this is a transition string
					
					c = 0; //reset the column back to 0
					for(int i = 0; i < trArr.length; i++) {			
						
						char cur = trArr[i];
						
						if(cur == '(' || cur == ')' || cur == ' ') {
							
							continue;
						} else {
							transitions[r][c] = cur;
							c++;
						}
					}
					r++;
				}else if (tr.charAt(0) == ' ') {
					break;
				} else {
					testStrings[n] = tr;
					n++;
				}
				
			}
			
			//printing the transitions to the console
			System.out.println("\n(4) transitions: ");
			for(int i = 0; i < transitions.length; i++)	{
				System.out.print("          ");
				for(int j = 0; j < transitions[i].length; j++) {
					System.out.print(transitions[i][j] + " ");
				}
				System.out.println("");
			}
			
			//declaring and initializing an FSA
			FSA fsa = new FSA(numStates, finalStatesChar, alphabet, transitions);
			
			System.out.println("(5) strings: ");
			
			//testing each string and printing the results to the screen
			for(int i = 0; i < testStrings.length; i++)	{
				System.out.print("          " + testStrings[i] + "     ");
				System.out.println(fsa.test(testStrings[i]));
			}
			
			System.out.println("\n\n");		//formatting
			
		}
		
		input.close();
		reader.close();
}
	
}