package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            // Execute the command with LANG=C to force English output
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "LANG=C iw dev wlan0 link");
            Process process = processBuilder.start();

            // Read the command output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            String rssiValue = null;

            // Regex pattern to match the RSSI value (e.g., "-45 dBm")
            Pattern pattern = Pattern.compile("-?\\d+ dBm");

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    rssiValue = matcher.group();
                    break;
                }
            }

            // Wait for the process to finish
            process.waitFor();

            // Print the RSSI value
            if (rssiValue != null) {
                System.out.println("RSSI: " + rssiValue);
            } else {
                System.out.println("Could not find RSSI in the output.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}