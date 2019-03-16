package Dictionary;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Dictionary {
	
	static String[] words = new String[5000000]; // 단어에서 특수문자 지운 거
	static String[] tmp = new String[5000000]; // 단어
	static String[] type = new String[5000000];	// 품사
	static String[] info = new String[5000000]; // 설명
	static int n = 0; // 단어 개수
	
	public static void main(String []args) {
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("$ ");
			String s = sc.next();
			if(s.equals("read")) {
				String filename = sc.next();
				read(filename);
			}
			else if(s.compareToIgnoreCase("size") == 0) {
				System.out.println(n);
			}
			else if(s.compareToIgnoreCase("find") == 0) {
				s = sc.nextLine();
				s = s.trim();
				s = replace(s);
				
				int index = find_index(0, n, s);
				find_words(index, s);
			}
			else if(s.compareToIgnoreCase("exit") == 0)
				break;
		}
		sc.close();
	}
	
	static String replace(String s) {
		String []spe = {"-", "'", " "};
		for(int i=0;i<spe.length;i++) 
			s = s.replaceAll(spe[i], "");
		
		return s;
	}
	
	static void read(String f) {
		
		try { 
			BufferedReader reader = new BufferedReader(new FileReader(f)); 
			String data = ""; 
			
			while ((data = reader.readLine()) != null) { 
				if(data.length() == 0) 
					data = reader.readLine();
				
				int idx1 =data.indexOf("(");
				int idx2 = data.indexOf(")");
				
				String str1 = data.substring(0, idx1 - 1);
				String str2 = data.substring(idx1, idx2 + 1);
				String str3 = data.substring(idx2 + 1); // 설명이 없는 단어도 있어서 +1로 처리
				
				words[n] = replace(str1);
				tmp[n] = str1;
				type[n] = str2;
				info[n] = str3;
				
				n++;
			} 
			reader.close(); 
		} catch (FileNotFoundException e) { 
			System.out.println("파일없음");
		} catch (IOException e) {
			System.out.println("예외");
		} 
	}
	
	
	
	static int find_index(int begin, int end, String s) {
		
		if(words[0].compareToIgnoreCase(s) == 0) {
			return 0;
		}
		else if(words[n - 1].compareToIgnoreCase(s) == 0) {
			return n - 1;
		}
		else if (begin > end) {
			if(end >= 0)	return end;
			else			return -1;
		}
		else {
			int middle = (begin + end) / 2;
			int compResult = s.compareToIgnoreCase(words[middle]);
			if (compResult == 0)
				return middle;
			else if (compResult < 0)
				return find_index(begin, middle - 1, s);
			else
				return find_index(middle + 1, end, s);
		}
	}
	
	static void find_words(int index, String s) {
		
		if (index < 0) {
			System.out.println("Not found.");
			System.out.println("- - -");
			System.out.println(tmp[index + 1] + " " + type[index + 1]);
		}
		else if (s.compareToIgnoreCase(words[index]) != 0){
			System.out.println("Not found.");
			System.out.println(tmp[index] + " " + type[index]);
			System.out.println("- - -");
			System.out.println(tmp[index + 1] + " " + type[index + 1]);
		}
		else {
			int[] arr = new int[100];
			int m = 0;
			int i = index;
			while(true) {
				if(i < 0) break;
				if(words[i].compareToIgnoreCase(s) == 0)
					arr[m++] = i;
				i--;
			}
			i = index + 1;
			while(true) {
				if (i > (n-1)) break;
				if(words[i].compareToIgnoreCase(s) == 0) 
					arr[m++] = i;
				i++;
			}
			System.out.println("Found " + m + " items.");
			for(int j = 0; j < m; j++)
				System.out.println(tmp[arr[j]] + " " + type[arr[j]] + " " + info[arr[j]]);
		}
	}
	

}










