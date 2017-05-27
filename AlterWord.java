/**
*   @Project: Spell Checker
*   @Author: Ryan Mee
*   @Class: AlterWord
*
*   The AlterWord class is used to organize all the functions
*   needed to adjust the words being spell checked.
*/
public class AlterWord {
    //zero value
    private static final int ZERO = 0;
    //one value
    private static final int ONE = 1;
    //two value
    private static final int TWO = 2;
    //three value
    private static final int THREE = 3;
    
    /**
        Replaces a leading capital letter with a lower case.
        
        @param String string - word being changed
        @retun String - altered word
    */
	public String cap(String string) {
		char[] str = string.toCharArray();
		str[ZERO] = Character.toLowerCase(str[ZERO]);
		return String.valueOf(str);
	}
    
    /**
        Removes any unnecessary apostrophes from the word and
        splits it into that amount of different words.
        
        @param String string - word being changed
        @retun String - space delimited string of words
    */
    public String apostrophe(String string) {
		String words = "";
		char str[] = string.toCharArray();
		
		if (!string.contains("\'")) {
			words = string;
		} else {
			int i = ZERO;
			while (str[i] != '\'') {
				i++;
			}
			if (i == str.length - TWO) {
				words = string;
			} else if (i == str.length - THREE) {
				if ((str[i+ONE] == 'l' && str[i+TWO] == 'l') || (str[i+ONE] == 'v' && str[i+TWO] == 'e') || (str[i+ONE] == 'r' && str[i+TWO] == 'e')) {
					words = string;
				} else {
					words += string.substring(ZERO, i) + " " + apostrophe(string.substring(i+ONE)) + " ";
				}
			} else {
				words += string.substring(ZERO, i) + " " + apostrophe(string.substring(i+ONE)) + " ";
			}
		}
		
		return words;
	}
	
    /**
        Removes an ending "'s"
        
        @param String string - word being changed
        @retun String - altered word
    */
	public String endApostS(String string) {
		return string.substring(ZERO, string.length() - TWO);
	}
	
    /**
        Removes an ending "s"
        
        @param String string - word being changed
        @retun String - altered word
    */
	public String endS(String string) {
		return string.substring(ZERO, string.length() - ONE);
	}
	
    /**
        Removes an ending "es"
        
        @param String string - word being changed
        @retun String - altered word
    */
	public String endES(String string) {
		return string.substring(ZERO, string.length() - TWO);
	}
	
    /**
        Removes an ending "ed"
        
        @param String string - word being changed
        @retun String - altered word
    */
	public String endED(String string) {
		return string.substring(ZERO, string.length() - TWO);
	}
	
    /**
        Removes an ending "d"
        
        @param String string - word being changed
        @retun String - altered word
    */
	public String endD(String string) {
		return string.substring(ZERO, string.length() - ONE);
	}
	
    /**
        Removes an ending "er"
        
        @param String string - word being changed
        @retun String - altered word
    */
	public String endER(String string) {
		return string.substring(ZERO, string.length() - TWO);
	}
	
    /**
        Removes an ending "r"
        
        @param String string - word being changed
        @retun String - altered word
    */
	public String endR(String string) {
		return string.substring(ZERO, string.length() - ONE);
	}
	
    /**
        Removes an ending "ing"
        
        @param String string - word being changed
        @retun String - altered word
    */
	public String endING(String string) {
		return string.substring(ZERO, string.length() - THREE);
	}
	
    /**
        Removes an ending "ly"
        
        @param String string - word being changed
        @retun String - altered word
    */
	public String endLY(String string) {
		return string.substring(ZERO, string.length() - TWO);
	}
	
    /**
        Adds an "e" to the end of the word
        
        @param String string - word being changed
        @retun String - altered word
    */
	public String addE(String string) {
		return string.concat("e");
	}
	
    /**
        Removes an ending punctuation
        
        @param String string - word being changed
        @retun String - altered word
    */
	public String endPunct(String string) {
		char[] str = string.toCharArray();
		if (!Character.isLetter(str[str.length - ONE])) {
			return string.substring(ZERO, string.length() - ONE);
		} else {
			return string;
		}
	}
}