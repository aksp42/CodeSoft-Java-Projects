package com.ds.fx;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import com.google.gson.*;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter base currency (e.g. USD): ");
        String base = sc.nextLine().trim().toUpperCase();

        System.out.print("Enter target currency (e.g. INR): ");
        String target = sc.nextLine().trim().toUpperCase();

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();

        try {
            String urlStr = "https://open.er-api.com/v6/latest/" + base;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            reader.close();

            JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();

            if ("success".equalsIgnoreCase(json.get("result").getAsString())) {
                JsonObject rates = json.getAsJsonObject("rates");
                if (rates.has(target)) {
                    double rate = rates.get(target).getAsDouble();
                    double converted = amount * rate;

                    System.out.printf("\n--- Conversion Result ---%n");
                    System.out.printf("%.2f %s = %.2f %s%n", amount, base, converted, target);
                    System.out.printf("Exchange Rate: 1 %s = %.4f %s%n", base, rate, target);
                } else {
                    System.out.println(" Error: target currency not found in response rates.");
                }
            } else {
                System.out.println("Error: API did not return a success result.");
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
