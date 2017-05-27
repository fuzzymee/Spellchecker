/**
*   @Project: Spell Checker
*   @Author: Ryan Mee
*   @Class: mainHash
*
*   The mainHash class holds the main function for
*   the Spell Checker program and handles the input,
*   output, and function calls needed for exection.
*   It will first create a buffer holding each string
*   from the input file in the order which they appeared,
*   then it will make an entry for each word in a hash table
*   which it creates. Next it will read in a text file to be checked
*   and spell check each word as its read into the program. The
*   misspelled words are printed out and the number of words and probes
*   are stored.
*
*   Usage:
*   eos$ mainHash.java Key.java Alterword.java
*   eos$ java mainHash < dict.txt [input file name] > [output file name]
*/

import java.io.*;
import java.util.*;

public class mainHash {
    //used to create a hash table 1.3x the size of n
    //where n is the number of words in the dictionary file
	private static final double SIZE_MULT = 1.3;
    //the golden ration used for the hash function
	private static final double GOLDEN = Math.pow(((1 + Math.sqrt(5)) / 2), -1);
    //zero value
    private static final int ZERO = 0;
    //one value
    private static final int ONE = 1;
    //two value
    private static final int TWO = 2;
    //three value
    private static final int THREE = 3;
    //four value
    private static final int FOUR = 4;
    //five value
    private static final int FIVE = 5;
    //Radix used in hash calculation
	private static final int RADIX = 16;
    
    //size of our hash table
	public static int tableSize;
    //number of entries in the dictionary file
	public static int count = ZERO;
    //number of words checked in the input file
	public static int wordsChecked = ZERO;
    //number of misspelled words
    public static int misspelled = ZERO;
    //number of match operations called
    public static int lookups = ZERO;
    //number of probes done during spell check
	public static int probes = ZERO;
    //buffer for storing dictionary file
	public static String buf[] = new String[30000];
    //hash table of buckets
	public static Key hashTable[];
	//instantiation of AlterWord class
	public static AlterWord alter = new AlterWord();
	//scanner for reading files
	public static Scanner in;
	//scanner for reading files
    public static Scanner scanner;
    
    /**
        The hashCode method is used to generate the hash value of
        a given string and return in. The hash code uses the golden
        ratio method.
        
        @param String string - word we're getting a hash value for
        
        @return int - hash value
    */
	public static int hashCode(String string) {
		double f = ZERO;
		char[] str = string.toCharArray();
		
		//Summation of character values using (p-1)E(i=0): c(i)r^i
		for (int i = ZERO; i < string.length(); i++) {
			double val = (int)str[i] * Math.pow(RADIX, i);
			f += val;
		}
		//complete f(k) calculation
		f = (f%tableSize) * GOLDEN;
		
		//split f(k) into whole and fractional
		int fInt = (int)f;
		double fFract = f - fInt;
		
		//Calculate and return h(k)
		return (int)Math.floor(tableSize * fFract);
	}
	
    /**
        This function will check to see if two strings are identical.
        
        @param String string - the word being checked against hash table entries
        
        @return boolean found - true if the words match, false if not
    */
	public static boolean match(String string) {
        lookups++;
		int hash = hashCode(string);
		boolean found = false;
		
        //check the given word against the equivalent hash table bucket until match found
		if (hashTable[hash] != null) {
			while (hashTable[hash].current != null) {
                probes++;
				if (string.equals(hashTable[hash].getValue())) {
					found = true;
					break;
				} else {
					hashTable[hash].current = hashTable[hash].getNext();
				}
			}
			hashTable[hash].current = hashTable[hash].head;
		}
		
		return found;
	}
    
    /**
        This function checks all the variations of a word ending
        with the suffix -s
        
        @param String string - the word being checked
        @return found - true if the word was matched, false if not
    */
    public static boolean spellcheckS(String string) {
		char[] str = string.toCharArray();
		boolean found = false;
		
		//drop -'s (example: cook's)
		if (str[str.length - TWO] == '\'') {
			found = match(alter.endApostS(string));
			if (found)
				return found;
			if (str[str.length - FOUR] == 'e' && str[str.length - THREE] == 'r') {
				//drop -er (example: cooked)
				found = match(alter.endER(alter.endApostS(string)));
				if (found)
					return found;
				//drop -r (example: baked)
				found = match(alter.endR(alter.endApostS(string)));
				if (found)
					return found;
			}
		}
		//drop -s (example: cooks)
		found = match(alter.endS(string));
		if (found) {
			return found;
		} else if (str[str.length - TWO] == 'r' && str[str.length - THREE] == 'e') {
			//drop -ers (example: starters)
			found = match(alter.endER(alter.endS(string)));
			if (found)
				return found;
		} else if (str[str.length - TWO] == 'g' && str[str.length - THREE] == 'n' && str[str.length - FOUR] == 'i') {
			//drop -ings (example: endings)
			found = match(alter.endS(alter.endING(string)));
			if (found)
				return found;
		} else if (str[str.length - TWO] == 'e' && str[str.length - THREE] == 'd') {
			//drop -eds (example: betrotheds)
			found = match(alter.endS(alter.endING(string)));
			if (found)
				return found;
		}
		//drop -es (example -dishes)
		if (str[str.length - TWO] == 'e') {
			found = match(alter.endES(string));
			if (found)
				return found;
		}
		
		return found;
	}
	
    /**
        This function checks all the variations of a word ending
        with the suffix -ed
        
        @param String string - the word being checked
        @return found - true if the word was matched, false if not
    */
	public static boolean spellcheckED(String string) {
		char[] str = string.toCharArray();
		boolean found = false;
		
		//drop -ed (example: cooked)
		found = match(alter.endED(string));
		if (found)
			return found;
		if (str[str.length - FOUR] == 'e' && str[str.length - THREE] == 'r') {
			//drop -ered (example: brokered)
			found = match(alter.endER(alter.endED(string)));
			if (found)
				return found;
			//drop -red (example: kindred)
			found = match(alter.endR(alter.endED(string)));
			if (found)
				return found;
		}
		//drop -d (example: baked)
		found = match(alter.endD(string));
		if (found)
			return found;
		
		return found;
	}
	
    /**
        This function checks all the variations of a word ending
        with the suffix -er
        
        @param String string - the word being checked
        @return found - true if the word was matched, false if not
    */
	public static boolean spellcheckER(String string) {
		boolean found = false;
		
		//drop -er (example: cooked)
		found = match(alter.endER(string));
		if (found)
			return found;
		//drop -r (example: baked)
		found = match(alter.endR(string));
		if (found)
			return found;
		
		return found;
	}
	
    
    /**
        This function checks all the variations of a word ending
        with the suffix -ing
        
        @param String string - the word being checked
        @return found - true if the word was matched, false if not
    */
	public static boolean spellcheckING(String string) {
		boolean found = false;
		
		//drop -ing (example: spilling)
		found = match(alter.endING(string));
		if (found)
			return found;
		//drop -ing, add -e (example: baking)
		found = match(alter.addE(alter.endING(string)));
		if (found)
			return found;
		
		return found;
	}
	
    /**
        This function checks all the variations of a word ending
        with the suffix -ly
        
        @param String string - the word being checked
        @return found - true if the word was matched, false if not
    */
	public static boolean spellcheckLY(String string) {
		char[] str = string.toCharArray();
		boolean found = false;
		
		//drop -ly (example: plainly)
		found = match(alter.endLY(string));
		if (found)
			return found;
		if (str[str.length - THREE] == 'g' && str[str.length - FOUR] == 'n' && str[str.length - FIVE] == 'i') {
			//drop -ingly (example: beamingly) 
			found = match(alter.endING(alter.endLY(string)));
			if (found)
				return found;
			//drop -ingly, add -e (example: alternatingly)
			found = match(alter.addE(alter.endING(alter.endLY(string))));
			if (found)
				return found;
		}
		
		return found;
	}
	
    /**
        The spellcheck function check the given word for a match in the hash table
        and alter the word to account for capitalization, plural, past tense, etc.
        
        @param String string - the word being spell checked
        
        @return boolean found - true if a match is found, false if not
    */
	public static boolean spellcheck(String string) {
		char[] str = string.toCharArray();
		boolean found = match(string);
		
		if (!found) {
			//check without capitalization first
			if (Character.isUpperCase(str[ZERO])) {
				found = spellcheck(alter.cap(string));
			}
			
			if (str.length >= THREE && !found) {
				while (!found) {
					if (str[str.length - ONE] == 's') {
						//check word that ends in 's'
						found = spellcheckS(string);
						if (found)
							break;
					}
					if (str[str.length - TWO] == 'e' && str[str.length - ONE] == 'd') {
						//check word that ends in 'ed'
						found = spellcheckED(string);
						if (found)
							break;
					}
					if (str[str.length - TWO] == 'e' && str[str.length - ONE] == 'r') {
						//check word that ends in 'er'
						found = spellcheckER(string);
						if (found)
							break;
					}
					if (str[str.length - ONE] == 'g' && str[str.length - TWO] == 'n' && str[str.length - THREE] == 'i') {
						//check word that ends in 'ing'
						found = spellcheckING(string);
						if (found)
							break;
					}
					if (str[str.length - ONE] == 'y' && str[str.length - TWO] == 'l') {
						//check word that ends in 'ly'
						found = spellcheckLY(string);
						if (found)
							break;
					}
					break;
				}
			}
		}
		
		return found;
	}
	
    /**
        Main function of the program.
        
        Will handle the input and output files as well
        as creating the needed data structures.
        
        @param String[] args - the command-line arguments
        [0] - input file
        [1] - output file
    */
	public static void main(String[] args)
	{
		in = new Scanner(System.in);
		
		while (in.hasNext())
		{
			buf[count++] = in.next();
		}
		
		in.close();
		
		tableSize = (int)Math.floor(count * SIZE_MULT);
		hashTable = new Key[tableSize];
        
		for (int i = ZERO; i < count; i++) {
			int code = hashCode(buf[i]);
			if (hashTable[code] == null)
				hashTable[code] = new Key(buf[i], null);
			else {
				hashTable[code].insert(buf[i],  null);
			}
		}
		
		try {
			scanner = new Scanner(new File(args[ZERO]));
			in = scanner.useDelimiter("[^a-zA-Z0-9']");
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			e.printStackTrace();
		}
		
        System.out.println("-----------------");
        System.out.println("Misspelled Words:");
        System.out.println("-----------------");
        
		while(in.hasNext()) {
			String string = in.next();
			//remove any apostrophes that need removing
			if (string.contains("\'")) {
				string = alter.apostrophe(string);
			}
			
			//split words by space if needed and check
			String words[] = string.split(" ");
			for (int i = ZERO; i < words.length; i++) {
				if (string.length() > ZERO) {
					if (!spellcheck(words[i])) {
                        misspelled++;
						System.out.println(words[i]);
					}
					wordsChecked++;
				}
			}
		}
		in.close();
        scanner.close();
		
        System.out.println();
        System.out.println("--------------------");
        System.out.println("Project Information:");
        System.out.println("--------------------");
        System.out.println("Dictionary Entries: " + count);
        System.out.println("Total Words Checked: " + wordsChecked);
        System.out.println("Total Words Misspelled: " + misspelled);
        System.out.println("Total Probes: " + probes);
        System.out.println("Average Probes per Word: " + (double)probes/(double)wordsChecked);
        System.out.println("Average Probes per lookUp: " + (double)probes/(double)lookups);
    }
}