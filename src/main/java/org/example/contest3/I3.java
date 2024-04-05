package org.example.contest3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class I3 {
    static Map<String, Team> teams = new HashMap<>();
    static Map<String, Player> players = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        Pattern gamePattern = Pattern.compile("(\"[^\"]+\") - (\"[^\"]+\") (\\d+):(\\d+)");
        Pattern goalPattern = Pattern.compile("(.+?) (\\d+)'");
        Map<String, Integer> games = new LinkedHashMap<>();
        Map<Integer, String> goals = new LinkedHashMap<>();

        while ((line = br.readLine()) != null) {
            Matcher gameMatcher = gamePattern.matcher(line);
            Matcher goalMatcher = goalPattern.matcher(line);
            if (gameMatcher.find()) {
                if (!games.isEmpty())
                    countingStatistics(games, goals, teams, players);
                games.put(gameMatcher.group(1), Integer.parseInt(gameMatcher.group(3)));
                games.put(gameMatcher.group(2), Integer.parseInt(gameMatcher.group(4)));
            } else if (goalMatcher.find()) {
                goals.put(Integer.parseInt(goalMatcher.group(2)), goalMatcher.group(1));
            } else {
                if (!games.isEmpty())
                    countingStatistics(games, goals, teams, players);
                executeRequest(line);
            }
        }
        teams.clear();
        players.clear();
    }

    private static void countingStatistics(Map<String, Integer> games, Map<Integer, String> goals, Map<String, Team> teams, Map<String, Player> players) {
        Set<Player> playersSet = new HashSet<>();
        Team teamOne = new Team();
        Team teamTwo = new Team();
        int goalsFirstTeam = 0;
        int teamOneOpen = 91;
        int teamTwoOpen = 91;
        int count = 0;
        for (Map.Entry<String, Integer> game : games.entrySet()) {
            String teamName = game.getKey();
            Integer teamGoals = game.getValue();
            Team team = teams.getOrDefault(teamName, new Team(teamName));
            team.setCounts(1, teamGoals);
            teams.put(teamName, team);
            for (Player player : team.getPlayers()) {
                player.setGames(team.getGamesCount());
            }
            playersSet.addAll(team.getPlayers());
            if (count == 0) {
                goalsFirstTeam = teamGoals;
                teamOne = team;
            } else {
                teamTwo = team;
            }
            count++;
        }
        count = 0;
        for (Map.Entry<Integer, String> goal : goals.entrySet()) {
            Integer minute = goal.getKey();
            String playerName = goal.getValue();
            Player player = players.getOrDefault(playerName, new Player(playerName));
            player.setGoalsCount(1);
            player.getMinutes().add(minute);
            players.put(playerName, player);

            if (count < goalsFirstTeam) {
                teamOne.getPlayers().add(player);
                if (!playersSet.contains(player)) {
                    player.setGames(teamOne.getGamesCount());
                }
            } else {
                teamTwo.getPlayers().add(player);
                if (!playersSet.contains(player)) {
                    player.setGames(teamTwo.getGamesCount());
                }
            }

            if (goalsFirstTeam != 0 && count == 0) {
                teamOneOpen = minute;
            } else if (count == goalsFirstTeam) {
                teamTwoOpen = minute;
            }
            playersSet.add(player);
            count++;
        }

        if (teamOneOpen < teamTwoOpen) {
            teamOne.setScoreOpens(1);
        } else if (teamOneOpen > teamTwoOpen) {
            teamTwo.setScoreOpens(1);
        }

        int open = Math.min(teamOneOpen, teamTwoOpen);
        for (Map.Entry<Integer, String> goal : goals.entrySet()) {
            if (open == goal.getKey()) {
                String playerName = goal.getValue();
                Player player = players.get(playerName);
                player.setScoreOpens(1);
                break;
            }
        }
        games.clear();
        goals.clear();
    }

    public static void executeRequest(String request) {
        Pattern patternQuoted = Pattern.compile("^(Total goals for|Mean goals per game for|Total goals by|Mean goals per game by|Score opens by) (\"?[^\"]+\"?)");
        Pattern pattern2 = Pattern.compile("^(Goals on minute|Goals on first|Goals on last) (\\d+)( minutes)? by ([\\w\\s]+)$");
        Matcher matcherQuoted = patternQuoted.matcher(request);
        Matcher matcher2 = pattern2.matcher(request);

        if (matcherQuoted.find()) {
            switch (matcherQuoted.group(1)) {
                case "Total goals for" -> getTotalGoalsFor(matcherQuoted.group(2));
                case "Mean goals per game for" -> getMeanGoalsPerGameFor(matcherQuoted.group(2));
                case "Total goals by" -> getTotalGoalsBy(matcherQuoted.group(2));
                case "Mean goals per game by" -> getMeanGoalsPerGameBy(matcherQuoted.group(2));
                default -> getScoreOpensBy(matcherQuoted.group(2));
            }
        } else if (matcher2.find()) {
            switch (matcher2.group(1)) {
                case "Goals on minute" -> getGoalsOnMinuteBy(matcher2.group(4), Integer.parseInt(matcher2.group(2)));
                case "Goals on first" -> getGoalsOnFirstMinutesBy(matcher2.group(4), Integer.parseInt(matcher2.group(2)));
                default -> getGoalsOnLastMinutesBy(matcher2.group(4), Integer.parseInt(matcher2.group(2)));
            }
        }
    }

    public static void getTotalGoalsFor(String name) {
        Team team = teams.get(name);
        if (checkNull(team)) return;
        System.out.print(team.getGoalsCount() + "\n");
    }

    public static void getMeanGoalsPerGameFor(String name) {
        Team team = teams.get(name);
        System.out.print( (double) team.getGoalsCount() / team.getGamesCount() + "\n");
    }

    public static void getTotalGoalsBy(String name) {
        Player player = players.get(name);
        if (checkNull(player)) return;
        System.out.print(player.getGoalsCount() + "\n");
    }

    public static void getMeanGoalsPerGameBy(String name) {
        Player player = players.get(name);
        System.out.print((double) player.getGoalsCount() / player.getGamesCount() + "\n");
    }

    public static void getGoalsOnMinuteBy(String name, int minute) {
        int result = countGoalsByCondition(name, m -> m == minute);
        System.out.print(result + "\n");
    }

    public static void getGoalsOnFirstMinutesBy(String name, int minute) {
        int result = countGoalsByCondition(name, m -> m > 0 && m <= minute);
        System.out.print(result + "\n");
    }

    public static void getGoalsOnLastMinutesBy(String name, int minute) {
        int result = countGoalsByCondition(name, m -> m >= 91 - minute && m <= 90);
        System.out.print(result + "\n");
    }

    private static int countGoalsByCondition(String name, Predicate<Integer> condition) {
        Player player = players.get(name);
        if (player == null) return 0;
        return (int) player.getMinutes().stream().filter(condition).count();
    }

    public static void getScoreOpensBy(String name) {
        if (name.matches("\"([^\"]+)\"")) {
            Team team = teams.get(name);
            if (checkNull(team)) return;
            System.out.print(team.getScoreOpens() + "\n");
        } else {
            Player player = players.get(name);
            if (checkNull(player)) return;
            System.out.print(player.getScoreOpens() + "\n");
        }
    }

    public static boolean checkNull(Object object) {
        if (object == null) System.out.print(0 + "\n");
        return object == null;
    }
}

class Team {
    private String name;
    private int gamesCount, goalsCount, scoreOpens;
    private Set<Player> players;

    public Team(String name) {
        this.name = name;
        this.gamesCount = 0;
        this.goalsCount = 0;
        this.scoreOpens = 0;
        this.players = new HashSet<>();
    }
    public Team() {
    }

    public String getName() {
        return name;
    }
    public int getGamesCount() {
        return gamesCount;
    }
    public void setCounts(int gamesCount, int goalsCount) {
        this.gamesCount += gamesCount;
        this.goalsCount += goalsCount;
    }
    public int getGoalsCount() {
        return goalsCount;
    }
    public int getScoreOpens() {
        return scoreOpens;
    }
    public void setScoreOpens(int scoreOpens) {
        this.scoreOpens += scoreOpens;
    }
    public Set<Player> getPlayers() {
        return players;
    }
}

class Player {
    private final String name;
    private int gamesCount, goalsCount, scoreOpens;
    private List<Integer> minutes;

    public Player(String name) {
        this.name = name;
        this.gamesCount = 0;
        this.goalsCount = 0;
        this.scoreOpens = 0;
        this.minutes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public int getGamesCount() {
        return gamesCount;
    }
    public void setGames(int games) {
        this.gamesCount = games;
    }
    public int getGoalsCount() {
        return goalsCount;
    }
    public void setGoalsCount(int goalsCount) {
        this.goalsCount += goalsCount;
    }
    public int getScoreOpens() {
        return scoreOpens;
    }
    public void setScoreOpens(int scoreOpens) {
        this.scoreOpens += scoreOpens;
    }
    public List<Integer> getMinutes() {
        return minutes;
    }
}