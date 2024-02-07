package net.mpoisv.unicode;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public final class VersionChecker {
    private static String versionCode;
    public static String getVersionCode() {
        return versionCode;
    }

    public static boolean isLatestVersion(String currentVersion) {
        return versionCode == null || versionCode.equals(currentVersion);
    }

    public static void getLatestVersionFromServer() {
        versionCode = null;
        try {
            var connection = (HttpURLConnection) URI.create("https://raw.githubusercontent.com/skyneton/minecraft-unicode-prevent/main/build.gradle.kts").toURL().openConnection();
            connection.setReadTimeout(2000);
            connection.setUseCaches(false);

            try(var reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while((line = reader.readLine()) != null) {
                    if(!(line.startsWith("version =") || line.startsWith("version="))) continue;
                    var versionPart = line.split("=")[1].trim();
                    versionCode = versionPart.substring(1, versionPart.length() - 1);
                    break;
                }
            }
        }catch(Exception e) {
            Main.getInstance().getLogger().warning(e.toString());
        }
    }
}
