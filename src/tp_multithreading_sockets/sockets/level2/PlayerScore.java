package tp_multithreading_sockets.sockets.level2;


public class PlayerScore implements Comparable<PlayerScore> {
    private String playerName;
    private int attempts;
    private String ipAddress;

    public PlayerScore(String playerName, int attempts, String ipAddress) {
        this.playerName = playerName;
        this.attempts = attempts;
        this.ipAddress = ipAddress;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getAttempts() {
        return attempts;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public int compareTo(PlayerScore other) {
        // Sort by attempts (ascending), then by name
        int attemptsComparison = Integer.compare(this.attempts, other.attempts);
        if (attemptsComparison != 0) {
            return attemptsComparison;
        }
        return this.playerName.compareToIgnoreCase(other.playerName);
    }

    @Override
    public String toString() {
        return String.format("%-20s %-6d %s", playerName, attempts, ipAddress);
    }
}

