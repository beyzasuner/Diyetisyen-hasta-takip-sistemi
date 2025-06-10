package com.diyetapp.backend.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class VertexAIService {

    private static final String API_KEY = "AIzaSyDZShZcC2eAbMeXPMK0aXXO-fULe2ik9Is"; // senin Gemini API key'in buraya tam haliyle
    private static final String ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    public static String getChatResponse(String userInput) {
        try {
            // ✅ Gerekli JSON yapısı
            JSONObject textPart = new JSONObject();
            textPart.put("text", userInput);

            JSONArray parts = new JSONArray();
            parts.put(textPart);

            JSONObject content = new JSONObject();
            content.put("parts", parts);

            JSONArray contents = new JSONArray();
            contents.put(content);

            JSONObject requestBody = new JSONObject();
            requestBody.put("contents", contents);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ENDPOINT + "?key=" + API_KEY))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("🧠 Gelen yanıt:\n" + response.body());

            JSONObject json = new JSONObject(response.body());

            if (json.has("candidates")) {
                JSONArray candidates = json.getJSONArray("candidates");
                JSONObject candidate = candidates.getJSONObject(0);

                JSONObject contentObj = candidate.getJSONObject("content");
                JSONArray partList = contentObj.getJSONArray("parts");
                JSONObject part = partList.getJSONObject(0);

                return part.getString("text");
            } else if (json.has("error")) {
                return "❌ Gemini API Hatası: " + json.getJSONObject("error").getString("message");
            } else {
                return "❌ Beklenmedik yanıt yapısı.";
            }


        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Cevap alınamadı, lütfen tekrar deneyin.";
        }
    }
}
