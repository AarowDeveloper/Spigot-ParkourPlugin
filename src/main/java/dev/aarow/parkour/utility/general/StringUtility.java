package dev.aarow.parkour.utility.general;

public class StringUtility {

    public static String formatDuration(long durationInSeconds) {
        long hours = durationInSeconds / 3600;
        long minutes = (durationInSeconds % 3600) / 60;
        long seconds = durationInSeconds % 60;

        StringBuilder formattedDuration = new StringBuilder();

        if (hours > 0) {
            formattedDuration.append(hours).append("h");
        }
        if (minutes > 0) {
            if (formattedDuration.length() > 0) {
                formattedDuration.append(", ");
            }
            formattedDuration.append(minutes).append("m");
        }
        if (seconds > 0 || formattedDuration.length() == 0) {
            if (formattedDuration.length() > 0) {
                formattedDuration.append(", ");
            }
            formattedDuration.append(seconds).append("s");
        }

        return formattedDuration.toString();
    }
}
