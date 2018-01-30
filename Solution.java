package com.example.lib;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static java.lang.Math.min;

public class Solution {

    //Hackerrank Matching Tokens Problem Solution
    //
    //We define the following:
    //
    //There are friends_nodes friends numbered from 1 to friends_nodes.
    //There are friends_edges pairs of friends, where each (xi, yi) pair of friends is connected by a shared integer token described by friends_weighti.
    //Any two friends, xi and yi, can be connected by zero or more tokens because if friends xi and yi share token ti and friends yi and zi also share token ti, then xi and zi are also said to share token ti.
    //
    //Find the maximal product of xi and yi for any directly or indirectly connected (xi, yi) pair of friends such that xi and yi share the maximal number of tokens with each other.
    //
    //Complete the maxTokens function in the editor. It has four parameters:
    //
    //Name	Type	Description
    //friends_nodes	integer	The number of friends.
    //friends_from	integer array	Each friends_from[i] (where 0 ≤ i < friends_edges) denotes the first friend in pair (friends_from[i], friends_to[i]).
    //friends_to	integer array	Each friends_to[i] (where 0 ≤ i < friends_edges) denotes the second friend in pair (friends_from[i], friends_to[i]).
    //friends_weight	integer array	Each friends_weight[i] (where 0 ≤ i < friends_edges) denotes the ID number of a token shared by both friends_from[i] and friends_to[i].
    //Note: friends_edges is the number of pairs of friends that directly share a token.
    //
    //The function must return an integer denoting the maximal product of xi and yi such that xi and yi are a pair of friends that share the maximal number of tokens with each other.
    //
    //Input Format
    //The first line contains two space-separated integers describing the respective values of friends_nodes and friends_edges.
    //Each line i of the friends_edges subsequent lines (where 0 ≤ i < friends_edges) contains three space-separated integers describing the respective values of friends_fromi, friends_toi, and friends_weighti.
    //
    //Constraints
    //
    //2 ≤ friends_nodes ≤ 100
    //1 ≤ friends_edges ≤ min(200, (friends_nodes × (friends_nodes − 1)) / 2)
    //1 ≤ friends_weighti ≤ 100
    //1 ≤ friends_fromi, friends_toi ≤ friends_nodes
    //1≤ friends_weighti ≤ friends_edges
    //friends_fromi ≠ friends_toi
    //Each pair of friends can be connected by zero or more types of tokens.
    //
    //Output Format
    //Return an integer denoting the maximal product of xi and yi such that xi and yi are a pair of friends that share the maximal number of tokens with each other.
    //
    //Sample Input 0
    //4 5
    //1 2 1
    //1 2 2
    //2 3 1
    //2 3 3
    //2 4 3
    //
    //Sample Output 0
    //6
    //
    //Explanation 0
    //Each pair of n = 4 friends is connected by the following tokens:
    //
    //Pair (1, 2) shares 2 tokens (i.e., tokens 1 and 2)
    //Pair (1, 3) shares 1 token (i.e., token 1)
    //Pair (1, 4) shares 0 tokens
    //Pair (2, 3) shares 2 tokens (i.e., tokens 1 and 3)
    //Pair (2, 4) shares 1 token (i.e., token 3)
    //Pair (3, 4) shares 1 token (i.e., token 3)
    //
    //The pairs connected by the maximal number of tokens are (1, 2) and (2, 3). Their respective products are 1 × 2 = 2 and 2 × 3 = 6. We then return the largest of these values as our answer, which is 6.
    //

    public static void main(String[] args) throws IOException {
        int friends_nodes = 4;
        int[] friends_from = {1, 1, 2, 2, 2};
        int[] friends_to = {2, 2, 3, 3, 4};
        int[] friends_weight = {1, 2, 1, 3, 3};
        maxTokens(friends_nodes, friends_from, friends_to, friends_weight);
    }

    /*
     * Complete the function below.
     */
    /*
        For the weighted graph:
    	1. The number of nodes is <name>_nodes.
    	2. The number of edges is <name>_edges.
    	3. An edge exists between <name>_from[i] to <name>_to[i] and the weight of the edge is <name>_weight[i].
    */
    static int maxTokens(int friends_nodes, int[] friends_from, int[] friends_to, int[] friends_weight) throws IOException {

        System.out.println("Return an integer denoting the maximal product of xi and yi such that xi and yi are a pair of friends that share the maximal number of tokens with each other.");
        System.out.println();

        //2 ≤ friends_nodes ≤ 100
        if (friends_nodes < 2 || friends_nodes > 100) {
            throw new IOException("Invalid input");
        }

        //1 ≤ friends_edges ≤ min(200, (friends_nodes × (friends_nodes − 1)) / 2)
        if (friends_from.length < 1 || friends_from.length > min(200, (friends_nodes * (friends_nodes - 1)) / 2)) {
            throw new IOException("Invalid input");
        }

        //1 ≤ friends_weighti ≤ 100
        //1 ≤ friends_fromi, friends_toi ≤ friends_nodes
        //1≤ friends_weighti ≤ friends_edges
        //friends_fromi ≠ friends_toi
        for (int i = 0; i < friends_weight.length; i++) {
            if (friends_weight[i] < 1 || friends_weight[i] > 100 || friends_from[i] < 1 || friends_from[i] > friends_nodes || friends_to[i] < 1 || friends_to[i] > friends_nodes || friends_weight[i] < 1 || friends_weight[i] > friends_weight.length || friends_from[i] == friends_to[i])
                throw new IOException("Invalid input");
        }

        HashMap<Integer, HashSet<Integer>> friends_tokens = new HashMap();
        HashMap<Integer, HashSet<Integer>> tokens_friends = new HashMap();

        HashMap<Map.Entry<Integer, Integer>, Integer> pairs_tokencount = new HashMap();


        for (int i = 0; i < friends_from.length; i++) {
            HashSet<Integer> from_friends = friends_tokens.get(friends_from[i]);
            if (from_friends == null)
                from_friends = new HashSet<Integer>();
            from_friends.add(friends_weight[i]);
            HashSet<Integer> to_friends = friends_tokens.get(friends_to[i]);
            if (to_friends == null)
                to_friends = new HashSet<Integer>();
            to_friends.add(friends_weight[i]);

            friends_tokens.put(friends_from[i], from_friends);
            friends_tokens.put(friends_to[i], to_friends);

            HashSet<Integer> tokens = tokens_friends.get(friends_weight[i]);
            if (tokens == null)
                tokens = new HashSet<Integer>();
            tokens.add(friends_from[i]);
            tokens.add(friends_to[i]);
            tokens_friends.put(friends_weight[i], tokens);
        }

        System.out.println("friends_tokens:" + friends_tokens.toString());
        System.out.println("tokens_friends:" + tokens_friends.toString());

        for (int i = 1; i <= friends_nodes; i++) {
            for (int j = 1; j <= friends_nodes; j++) {
                if (i == j)
                    continue;
                int numberOfTokensForThisPair = 0;
                if (friends_tokens.containsKey(i)) {
                    for (Integer token : friends_tokens.get(i)) {
                        if (tokens_friends.containsKey(token)) {
                            if (tokens_friends.get(token).contains(j))
                                numberOfTokensForThisPair++;
                        }
                    }
                    pairs_tokencount.put(new AbstractMap.SimpleEntry(i, j), numberOfTokensForThisPair);
                }
            }
        }

        int maxTokens = 0;
        int maxProduct = 0;

        for (Map.Entry<Map.Entry<Integer, Integer>, Integer> entry : pairs_tokencount.entrySet()) {
            if (entry.getValue() >= maxTokens) {
                maxTokens = entry.getValue();
                Map.Entry<Integer, Integer> pair = entry.getKey();
                int product = pair.getKey() * pair.getValue();
                if (product >= maxProduct) {
                    System.out.println("maxProduct " + maxProduct + ". Pair (" + pair.getKey() + ", " + pair.getValue() + ") shares " + entry.getValue() + " tokens. Product " + product);
                    maxProduct = product;
                }
            }
        }

        System.out.println("return maxProduct: " + maxProduct);
        return maxProduct;
    }

}
