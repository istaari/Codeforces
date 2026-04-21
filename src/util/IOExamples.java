package util;

import java.io.*;
import java.util.*;

/*
 * ======================================
 * Java I/O Reference for Competitive Programming
 * ======================================
 *
 * Two approaches:
 *   1. Scanner   — simple, slower (fine for small input)
 *   2. BufferedReader + StringTokenizer — fast (use for CF)
 *
 * ──────────────────────────────────────
 * SCANNER METHODS
 * ──────────────────────────────────────
 *
 * next()        → reads next token (word), stops at whitespace
 * nextLine()    → reads entire line including spaces
 * nextInt()     → reads next int, leaves newline in buffer
 * nextLong()    → reads next long
 * nextDouble()  → reads next double
 * next().charAt(0) → read single char (no direct method)
 *
 * ⚠ Pitfall: after nextInt()/nextLong()/nextDouble(), the
 *   newline stays in buffer. Call sc.nextLine() to consume it
 *   before reading the next line.
 *
 *   int age = sc.nextInt();
 *   sc.nextLine();              // consume leftover newline
 *   String name = sc.nextLine(); // now reads actual input
 *
 * ──────────────────────────────────────
 * BUFFEREDREADER (preferred for CF)
 * ──────────────────────────────────────
 *
 * BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 * br.readLine()                → reads full line as String
 * Integer.parseInt(br.readLine().trim()) → single int
 * StringTokenizer st = new StringTokenizer(br.readLine());
 * st.nextToken()               → next space-separated token
 * Integer.parseInt(st.nextToken()) → parse token as int
 */
public class IOExamples {

    // ── Approach 1: Scanner (simple, slower) ──────────────
    @SuppressWarnings("all")
    static void scannerExample() {
        Scanner sc = new Scanner(System.in);

        String word = sc.next(); // single word
        sc.nextLine(); // clear buffer
        String line = sc.nextLine(); // full line
        int n = sc.nextInt(); // integer
        long big = sc.nextLong(); // long
        double d = sc.nextDouble(); // double
        char ch = sc.next().charAt(0); // single char

        sc.close();
    }

    // ── Approach 2: BufferedReader (fast, use for CF) ─────
    static void bufferedReaderExample() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Single value
        int n = Integer.parseInt(br.readLine().trim());

        // Multiple values on one line
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());

        // Read N lines
        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            System.out.println(s);
        }

        // Read array
        st = new StringTokenizer(br.readLine());
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
    }
}
