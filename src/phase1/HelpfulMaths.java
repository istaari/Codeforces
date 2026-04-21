package phase1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class HelpfulMaths {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().trim().split("\\+");
        Arrays.sort(str);
        String r = "";
        int len = str.length;
        for (int i = 0; i < len; i++) {
            r += str[i];
            if(i != len - 1) r += "+";
        }
        System.out.println(r);
    }

}
