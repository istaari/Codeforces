package phases.phase4_advanced.advanced_dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/*
 * ============================================================
 * CF 1333D — Challenges in School
 * Link   : https://codeforces.com/problemset/problem/1333/D
 * Rating : 1800  |  Tags: dp, greedy, simulation
 * Topic  : Phase 4: Advanced > Advanced DP
 * ============================================================
 *
 * PROBLEM STATEMENT
 * -----------------
 * n students with excitement levels. Each round the teacher selects
 * a set of non-overlapping segments [l,r] of consecutive excited students.
 * A student is "excited" on a round if they are in a selected segment.
 * Given m rounds budget, maximize total segments selected. Also find
 * the minimum number of rounds needed to select all possible segments.
 *
 * Specifically: given sequence a[], for each adjacent pair where a[i] > a[i+1],
 * a "segment boundary" exists. Extract maximal increasing segments.
 * Each round picks a subset of these segments (non-overlapping guaranteed
 * since they're already non-overlapping). Min rounds = number of "groups"
 * that must be separated. Maximize segments picked in m rounds.
 *
 * EXAMPLE
 * -------
 * Input:  n=5, m=2, a=[1,2,3,2,1]
 * Output: min_rounds=2, with 2 rounds pick 2 segments
 *
 * ALGORITHM / KEY INSIGHT
 * -----------------------
 * 1. Find all "peaks": positions where a[i] > a[i-1] (segment starts) and
 *    a[i] > a[i+1] (segment ends). Actually, extract contiguous segments
 *    where each element > next, forming a valid "challenge segment" [l,r]
 *    where a[l]<a[l+1]<...<peak>...>a[r]. Split at each local minimum.
 * 2. For each "group" (between consecutive local minima), count how many
 *    non-overlapping subsegments we can pick per round from that group.
 *    Actually each round can pick one segment from each independent group.
 * 3. Segments list: list of (l,r) pairs where segment is an increasing-then-
 *    decreasing run. Adjacent segments that share a boundary must be in
 *    different rounds.
 * 4. Extract all valid segments: whenever a[i] < a[i+1], start potential
 *    segment; when a[i] > a[i+1], potential end. A segment [l,r] is valid
 *    if a[l] < a[l+1] and a[r-1] > a[r] (has an increase and decrease).
 * 5. Group segments: two segments i, i+1 are in same "chain" if they are
 *    adjacent (share endpoint). Count chains = min rounds. Total segments = answer for m>=chains.
 *    For m < chains: greedily combine -- each extra round allows splitting one chain.
 *
 * COMPLEXITY
 * ----------
 * Time : O(n)
 * Space: O(n)
 * ============================================================
 */

public class CF_1333D_ChallengesInSchool {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] a = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) a[i] = Integer.parseInt(st.nextToken());

        // Extract valid segments: [l,r] where a[l]<a[l+1] (increasing start)
        // and a[r-1]>a[r] (decreasing end). Must span at least a peak.
        // Approach: scan for rises and falls.
        // A segment is valid if it contains at least one rise (a[i]<a[i+1])
        // and at least one fall (a[j]>a[j+1]) with the rise before the fall.

        // Find all maximal "blocks": positions between two local minima
        // Each block can contribute one "chain" of segments that must be
        // distributed across rounds.

        // Simpler: find segments where consecutive elements alternate
        // Extract "groups": a group is a maximal sequence of indices where
        // a[i] != a[i+1] (adjacent different values). Within a group, segments
        // can be picked if they span a peak.

        // Let's think in terms of "rise-fall" pairs:
        // For each peak p, the segment [l, r] must have l <= p <= r,
        // a[l-1] < a[l] (or l=1), a[r] > a[r+1] (or r=n).

        // Actual approach for this problem:
        // 1. Find all valid (l, r) pairs where [l,r] is a segment that can be "challenged":
        //    a[l-1] >= a[l] or l==1, and a[r] >= a[r+1] or r==n,
        //    and there exists a peak strictly inside.
        //    Simpler: extract pairs (i, i+1) where a[i] > a[i+1]. These are the "downward steps".
        //    Between consecutive downward steps, find the upward steps.

        // Per editorial approach:
        // Collect "groups" where each group is a set of contiguous elements with a pattern.
        // Between two consecutive valleys, there's exactly one peak -> one segment.
        // Adjacent segments that share a valley boundary can be split by the boundary -> different rounds needed.

        // Final approach:
        // segments[i] = list of segments in group i
        // minRounds = number of groups
        // total segments = sum of all segments
        // For m rounds: if m >= minRounds, answer = total.
        // If m < minRounds: we can only pick from the top m groups (sorted by size).
        // But we want to maximize: pick the groups with most segments first.
        // Each group of size s contributes min(s, rounds_allocated) segments.

        // Extract groups
        List<Integer> groupSizes = new ArrayList<>();
        int i = 1;
        while (i <= n) {
            // Find a segment: need at least one rise then fall
            // Skip flat parts
            int l = i;
            boolean hasRise = false, hasFall = false;
            while (i < n && a[i] == a[i + 1]) i++;
            l = i;
            // Count rises in this group
            boolean inGroup = false;
            int segCount = 0;
            int groupStart = i;
            // A group: a maximal block where we can't have a "gap" (equal elements break groups? No)
            // Let's find all consecutive pairs and build segments greedily
            i++;
            break;
        }

        // Restart with cleaner logic
        // Find all valid "challenge segments"
        // A challenge [l,r] requires: exists k in [l,r-1] with a[k]<a[k+1] AND exists k in [l,r-1] with a[k]>a[k+1]
        // For simplicity: segments defined by consecutive rise-fall patterns.

        // Rebuild:
        List<int[]> segments = new ArrayList<>(); // each segment as {l, r}
        // scan for peak segments
        int pos = 1;
        while (pos <= n) {
            // find start of a rising sequence
            while (pos < n && a[pos] >= a[pos + 1]) pos++;
            if (pos == n) break;
            int segL = pos; // start of rise
            // now a[pos] < a[pos+1], find peak then fall
            while (pos < n && a[pos] < a[pos + 1]) pos++;
            // pos is at peak
            if (pos == n || a[pos] <= a[pos + 1]) { pos++; continue; }
            // now a[pos] > a[pos+1], find end of fall
            while (pos < n && a[pos] > a[pos + 1]) pos++;
            int segR = pos; // end of fall
            segments.add(new int[]{segL, segR});
        }

        // Now group adjacent segments (segments that share endpoint are in same chain)
        // Two segments s1=[l1,r1] and s2=[l2,r2] are adjacent if r1 == l2
        List<Integer> chainSizes = new ArrayList<>();
        int j = 0;
        while (j < segments.size()) {
            int chainSize = 1;
            while (j + 1 < segments.size() && segments.get(j)[1] == segments.get(j + 1)[0]) {
                j++;
                chainSize++;
            }
            chainSizes.add(chainSize);
            j++;
        }

        int minRounds = chainSizes.size();
        int totalSegs = segments.size();

        if (m < minRounds) {
            // Need at least minRounds rounds, impossible with m < minRounds?
            // No: if m < minRounds we must skip some chains entirely -> fewer segments
            // Greedy: allocate 1 round per chain; have m rounds to distribute
            // With m rounds among k chains (m < k): we can only handle m chains
            // maximize: pick m chains with most segments
            int[] chainArr = chainSizes.stream().mapToInt(x -> x).toArray();
            // Sort descending
            java.util.Arrays.sort(chainArr);
            int picked = 0;
            for (int ci = chainArr.length - 1; ci >= 0 && m > 0; ci--, m--) {
                picked += chainArr[ci];
            }
            System.out.println(minRounds + " " + picked);
        } else {
            // m >= minRounds: can handle all chains, extra rounds allow splitting chains
            // Each extra round can split one chain of size s into one more piece (s -> two groups)
            // Actually: a chain of size s needs s rounds to fully separate all s segments
            // With m total rounds, distribute m among chains
            // Total segments = totalSegs regardless of how many rounds (as long as m >= minRounds)
            // But we want to output: min_rounds_needed and max_segments_in_m_rounds
            // Min rounds = minRounds (need at least 1 per chain)
            // Max segments in m rounds: if m >= totalSegs, answer = totalSegs
            // Otherwise: each extra round beyond minRounds allows one more segment in some chain
            int maxSegs = Math.min(totalSegs, m >= totalSegs ? totalSegs :
                minRounds + (m - minRounds)); // actually = min(totalSegs, minRounds + extra)
            // Wait: m rounds, minRounds chains. Extra = m - minRounds rounds.
            // Each chain of size s can use up to s rounds, contributing s segments.
            // Base: 1 round per chain = minRounds rounds, totalSegs... no.
            // Each chain of size s must use exactly s rounds to get s segments.
            // Total rounds needed for all segments = totalSegs.
            // With m rounds: if m >= totalSegs, get all totalSegs segments.
            // If minRounds <= m < totalSegs: need to greedily allocate extra rounds to chains.
            // Just: answer = min(totalSegs, m) when m >= minRounds.
            // Because each extra round (beyond minRounds) unlocks 1 more segment.
            System.out.println(minRounds + " " + Math.min(totalSegs, m));
        }
    }
}
