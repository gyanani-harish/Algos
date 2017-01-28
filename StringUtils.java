public static int[] countChars(String str) {
		int freq[] = new int[26];
		char ch;
		for (int i = 0; i < str.length(); i++) {
			ch = Character.toUpperCase(str.charAt(i));
			if (Character.isLetter(ch)) {
				++freq[(int) ch - (int) 'A'];
			}
		}
		return freq;
	}

	public static int totalRepeatedCharsCount(String str) {
		int freq[] = new int[26];
		char ch;
		int index;
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			ch = Character.toUpperCase(str.charAt(i));
			if (Character.isLetter(ch)) {
				index = (int) ch - (int) 'A';
				if (freq[index] == 0)
					++freq[index];
				else
					count++;
			}
		}
		return count;
	}
