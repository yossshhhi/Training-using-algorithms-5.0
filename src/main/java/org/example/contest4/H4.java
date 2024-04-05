package org.example.contest4;

import java.io.*;
import java.util.*;

public class H4 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int n = Integer.parseInt(reader.readLine());
            List<Party> parties = new ArrayList<>();

            String[] partyData;
            for (int i = 0; i < n; i++) {
                partyData = reader.readLine().split(" ");
                parties.add(new Party(i + 1, Integer.parseInt(partyData[0]), Integer.parseInt(partyData[1])));
            }

            Collections.sort(parties);

            Party first = parties.getFirst();
            int allExpenses, numberParty, votesWinParty, needVotes;
            if (first.bribe != -1) {
                allExpenses = first.bribe;
                numberParty = first.number;
                votesWinParty = first.votes;
            } else {
                allExpenses = Integer.MAX_VALUE;
                numberParty = 0;
                votesWinParty = 0;
            }
            needVotes = 0;
            Party last = first;
            long sumGreaterThanAverage = first.votes;
            Queue<Integer> lessThanAverage = new LinkedList<>();

            Party next = n > 1 ? parties.get(1) : null;
            if (first.bribe != -1 && next != null && first.votes == next.votes) {
                allExpenses += 1;
                needVotes += 1;
                votesWinParty += 1;
            }

            for (int i = 1; i < parties.size(); i++) {
                Party party = parties.get(i);
                sumGreaterThanAverage += party.votes;
                if (party.bribe != -1) {
                    long[] votes = calculateVotesToLure(party.votes, i + 1, last, lessThanAverage, sumGreaterThanAverage);
                    int notEnoughVotes = (int) votes[0];
                    sumGreaterThanAverage = votes[1];
                    int partyExpense = party.bribe + notEnoughVotes;
                    if (partyExpense < allExpenses) {
                        allExpenses = partyExpense;
                        numberParty = party.number;
                        votesWinParty = party.votes + notEnoughVotes;
                        needVotes = notEnoughVotes;
                    }
                } else {
                    sumGreaterThanAverage = calculateAverage(i + 1, last, lessThanAverage, sumGreaterThanAverage);
                }
                last = party;
            }

            int remainder = needVotes;

            for (Party party : parties) {
                if (party.number != numberParty) {
                    if (party.votes >= votesWinParty && remainder > 0) {
                        int lure = party.votes - (votesWinParty - 1);
                        party.setVotes(party.votes - lure);
                        remainder -= lure;
                    }
                } else {
                    if (votesWinParty > party.votes) {
                        party.setVotes(votesWinParty);
                    }
                }
            }

            int index = 0;
            while (remainder > 0) {
                Party party = parties.get(index++);
                if (party.number != numberParty) {
                    if (party.votes >= remainder) {
                        party.setVotes(party.votes - remainder);
                        remainder = 0;
                    } else if (party.votes > 0) {
                        party.setVotes(party.votes - 1);
                        remainder--;
                    }
                }
            }

            parties.sort(Comparator.comparingInt(a -> a.number));
            writer.write(allExpenses + "\n");
            writer.write(numberParty + "\n");
            for (Party party : parties) {
                writer.write(party.votes + " ");
            }
            writer.flush();
        }
    }

    public static long[] calculateVotesToLure(int yourPartyVotes, int count, Party last, Queue<Integer> lessThanAverage, long sumGreaterThanAverage) {
        sumGreaterThanAverage = calculateAverage(count, last, lessThanAverage, sumGreaterThanAverage);

        int newCount = count - lessThanAverage.size();
        long remainder = sumGreaterThanAverage % newCount;
        long needVotesForWin = remainder <= 1 ? sumGreaterThanAverage / newCount + 1 : sumGreaterThanAverage / newCount + 2;

        return new long[]{(needVotesForWin - yourPartyVotes), sumGreaterThanAverage};
    }

    public static long calculateAverage(int count, Party last, Queue<Integer> lessThanAverage, long sumGreaterThanAverage) {
        long average = sumGreaterThanAverage / (count - lessThanAverage.size());

        if (average > last.votes) {
            lessThanAverage.add(last.votes);
            sumGreaterThanAverage -= last.votes;
            average = sumGreaterThanAverage / (count - lessThanAverage.size());
        }
        Integer peek = lessThanAverage.peek();
        while (peek != null && peek > average) {
            sumGreaterThanAverage += lessThanAverage.poll();
            average = sumGreaterThanAverage / (count - lessThanAverage.size());
            peek = lessThanAverage.peek();
        }
        return sumGreaterThanAverage;
    }

    public static class Party implements Comparable<Party> {
        int number;
        int votes;
        int bribe;

        public Party(int number, int votes, int bribe) {
            this.number = number;
            this.votes = votes;
            this.bribe = bribe;
        }

        @Override
        public int compareTo(Party other) {
            return other.votes - this.votes;
        }

        public void setVotes(int votes) {
            this.votes = votes;
        }
    }
}
