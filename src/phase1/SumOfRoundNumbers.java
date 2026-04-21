package phase1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SumOfRoundNumbers {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
    
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(br.readLine().trim());
            int count = 0;
            int place = 1;
            int[] round = new int[5];
            while (num > 0) {
                int digit = num % 10;
                if (digit != 0) {
                    round[count++] = digit * place;
                }

                place = place * 10;
                num = num / 10;
            }

            System.out.println(count);

            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < count; j++) {
                if (j > 0)
                    sb.append(" ");
                sb.append(round[j]);
            }

            System.out.println(sb.toString());
        }
    }
}
