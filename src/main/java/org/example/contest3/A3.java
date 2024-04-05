package org.example.contest3;

import java.io.*;
import java.util.*;

public class A3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int people = Integer.parseInt(br.readLine());
        Set<String> songs = new TreeSet<>();
        Set<String> temp = new HashSet<>();
        inputSongs(br, songs);
        for (int i = 1; i < people; i++) {
            inputSongs(br, temp);
            songs.retainAll(temp);
            temp.clear();
        }

        System.out.print(songs.size() + "\n");
        System.out.print(String.join(" ", songs));
    }

    public static void inputSongs(BufferedReader br, Set<String> songs) throws IOException {
        int count = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int j = 0; j < count; j++) {
            String song = st.nextToken();
            songs.add(song);
        }
    }
}

