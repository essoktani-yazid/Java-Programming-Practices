package tp_multithreading_sockets.sockets.level2;

import java.util.*;


public class Leaderboard {
    private final List<PlayerScore> scores;
    private final int maxEntries;

    public Leaderboard(int maxEntries) {
        this.scores = new ArrayList<>();
        this.maxEntries = maxEntries;
    }


    public synchronized void addScore(String playerName, int attempts, String ipAddress) {
        scores.add(new PlayerScore(playerName, attempts, ipAddress));
        Collections.sort(scores);
        
        // Keep only top scores
        if (scores.size() > maxEntries) {
            scores.remove(scores.size() - 1);
        }
    }


    public synchronized List<PlayerScore> getLeaderboard() {
        return new ArrayList<>(scores);
    }


    public synchronized String getLeaderboardString() {
        if (scores.isEmpty()) {
            return "No scores yet!";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n🏆 LEADERBOARD 🏆\n");
        sb.append(String.format("%-5s %-20s %-10s %s%n", "Rank", "Player", "Attempts", "IP Address"));
        sb.append("-".repeat(60)).append("\n");

        for (int i = 0; i < scores.size(); i++) {
            sb.append(String.format("%-5d %s%n", i + 1, scores.get(i)));
        }

        return sb.toString();
    }


    public synchronized int getRank(int attempts) {
        int rank = 1;
        for (PlayerScore score : scores) {
            if (score.getAttempts() < attempts) {
                rank++;
            } else {
                break;
            }
        }
        return rank;
    }
}

