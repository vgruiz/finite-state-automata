class FSA {
	boolean FINAL[];
	char[] alphabet;
	char[][] transitions;
	int[][] trTable;
	
	public FSA(int numStates, char[] finalStates, char[] sigma, char[][] tr) {
		
		FINAL = new boolean[numStates];			//set all final state possibilities as false until we interpret the next line
		for(int i = 0; i < FINAL.length; i++) {
			FINAL[i] = false;
			
			for(int j = 0; j < finalStates.length; j++) {  //this loop sets the proper FINAL[] indexes as true
				int state;
				
				if(finalStates[j] == '#') {
					state = 0;
				} else {
					state = Character.getNumericValue(finalStates[j]);
				}
				
				if(i == state) {
					FINAL[i] = true;
				}
			}
		}
		
		alphabet = sigma;
		transitions = tr;
		
		trTable = new int[numStates][alphabet.length];
		

		for(int r = 0; r < transitions.length; r++) {
			int state = Character.getNumericValue(transitions[r][0]); //states will always be digits
			char input = transitions[r][1];		//input will not always be digits, but we get an int from their index value in alphabet[]
			int dest = Character.getNumericValue(transitions[r][2]);		//this is also a state, so it will be a digit
			
			int inputNum = getAlphaNum(input);			//getting the number corresponding to the character to use with trTable
			if(inputNum < 0) {						//if getAlphaNum() returns a negative number, the character was not found in the alphabet
				//SOME PROBLEMS
				System.out.println("NOT IN ALPHABET: " +  input);
			}

			try {
				trTable[state][inputNum] = dest;
			}catch (ArrayIndexOutOfBoundsException e) {
				break;
			}			
		}
		
	}
	
	//this returns the index number corresponding to the characters position in the alphabet
	public int getAlphaNum(char c) {
		for(int i = 0; i < alphabet.length; i++) {
			if(c == alphabet[i]) {
				return i;
			}
		}
		
		System.out.println("The input you provided has characters not found in the FSA's alphabet.");
		System.exit(0);
		return 0;
	}
	
	//the primary test function that runs through each character of the input string and determines if it is accepted or rejected
	public String test(String s) {
		char[] inputArr = s.toCharArray();
		int fsaState = 0;
		int dest = 0;
		
		for(int i = 0; i < inputArr.length; i++) {
			int input = getAlphaNum(inputArr[i]);
			
			dest = trTable[fsaState][input];
			fsaState = dest;
		}
		
		if(FINAL[dest] == true) {
			return "ACCEPT";
		} else {
			return "REJECT";
		}
	}
}