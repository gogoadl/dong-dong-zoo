package com.dongdong.zoo.song.service;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SongCreateStory {
    private static final String REST_API_KEY = "eab3bc61043ea334b735a609ce499ca3"; // REST API 키
    private static final String RETURN_TYPE = "url"; // url(기본), base64_string
    private static final String IMAGE_FORMAT = "png"; // webp(기본), jpeg, png

    // 이미지 생성하기 요청
    private static String t2i(String prompt, String negativePrompt) {
        String apiUrl = "https://api.kakaobrain.com/v2/inference/karlo/t2i";
        HttpURLConnection conn = connect(apiUrl);

        try {
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "KakaoAK " + REST_API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create JSON Request
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("version", "v2.1");
            jsonRequest.put("prompt", prompt);
            jsonRequest.put("negative_prompt", negativePrompt);
            jsonRequest.put("height", 1024);
            jsonRequest.put("width", 1024);
            jsonRequest.put("image_format", IMAGE_FORMAT);
            jsonRequest.put("return_type", RETURN_TYPE);

            // Send JSON Request
            OutputStream os = conn.getOutputStream();
            os.write(jsonRequest.toString().getBytes());
            os.flush();

            // Get Response
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(conn.getInputStream());
            } else { // 에러 발생
                return readBody(conn.getErrorStream());
            }

        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            conn.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    private static URL createImgURL(String prompt, String negativePrompt) throws IOException {

        // 이미지 생성하기 REST API 호출
        String response = t2i(prompt, negativePrompt);

        // 응답의 첫 번째 이미지 생성 결과 출력하기
        JSONObject jsonResponse = new JSONObject(response);
        URL url = new URL(jsonResponse.getJSONArray("images").getJSONObject(0).getString("image"));
        // 이미지 url
        System.out.println("image[0] url: "	+ url.toString());
        System.out.println("prompt: " + prompt);

        return url;
    }

    public static String createStory(String prompt, String tag) throws IOException, JSONException {
        prompt = prompt.replace("{tag}", tag);
        String negativePrompt = "nsfw, low resolution, text, signature, white frame, out of frame, empty background, white background, ugly, bad anatomy";

        return createImgURL(prompt, negativePrompt).toString();

    }

}
