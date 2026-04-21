package phase1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WordCapitalization {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] ch = br.readLine().toCharArray();
        ch[0] = Character.toUpperCase(ch[0]);
        System.out.println(new String(ch));
         
        // Using String Builder
        // StringBuilder str = new StringBuilder(br.readLine());
        // System.out.println(str.replace(0, 1,
        // String.valueOf(str.charAt(0)).toUpperCase()));
    }

}
