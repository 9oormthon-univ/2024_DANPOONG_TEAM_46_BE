package com.goormthon.bookduchilseong.domain.ocr.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OCRService {
	@Value("${spring.naver.ocr.client-secret}")
	private String SECRET;

	@Value("${spring.naver.ocr.api-url}")
	private String API_URL;

	public String execute(MultipartFile file) {
		try {
			// 파일을 Base64로 인코딩
			String base64Image = encodeImageToBase64(file);

			// API 요청 URL
			URL url = new URL(API_URL);
			HttpURLConnection connection = createRequestHeader(url);
			createRequestBody(connection, base64Image);

			// 응답 데이터 받기
			StringBuilder response = getResponseData(connection);

			// 응답 데이터 파싱
			return parseResponseData(response);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return null;
	}

	private static String encodeImageToBase64(MultipartFile file) throws IOException {
		byte[] bytes = file.getBytes();
		return Base64.encodeBase64String(bytes);
	}

	private HttpURLConnection createRequestHeader(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setReadTimeout(5000);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json;");
		connection.setRequestProperty("X-OCR-SECRET", SECRET); // 인스턴스 변수 사용
		return connection;
	}

	private static void createRequestBody(HttpURLConnection connection, String base64Image) throws IOException {
		JSONObject image = new JSONObject();
		image.put("format", "PNG");
		image.put("name", "requestImage");
		image.put("data", base64Image);  // Base64 인코딩된 이미지 데이터

		JSONArray images = new JSONArray();
		images.put(image);

		JSONObject requestObject = new JSONObject();
		requestObject.put("version", "V2");
		requestObject.put("requestId", UUID.randomUUID().toString());
		requestObject.put("timestamp", System.currentTimeMillis());
		requestObject.put("lang", "ko");
		requestObject.put("resultType", "string");
		requestObject.put("images", images);

		connection.connect();
		DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
		outputStream.write(requestObject.toString().getBytes(StandardCharsets.UTF_8));
		outputStream.flush();
		outputStream.close();
	}

	private static BufferedReader checkResponse(HttpURLConnection connection) throws IOException {
		int responseCode = connection.getResponseCode();
		BufferedReader reader;

		if (responseCode == 200) {
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} else {
			reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
		}
		return reader;
	}

	private static StringBuilder getResponseData(HttpURLConnection connection) throws IOException {
		BufferedReader reader = checkResponse(connection);
		String line;
		StringBuilder response = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			response.append(line);
		}
		reader.close();
		return response;
	}

	private static String parseResponseData(StringBuilder response) {
		try {
			JSONObject parsedResponse = new JSONObject(response.toString());
			JSONArray parsedImages = parsedResponse.getJSONArray("images");
			StringBuilder result = new StringBuilder();

			if (parsedImages.length() > 0) {
				JSONObject parsedImage = parsedImages.getJSONObject(0);
				JSONArray parsedTexts = parsedImage.getJSONArray("fields");

				for (int i = 0; i < parsedTexts.length(); i++) {
					JSONObject current = parsedTexts.getJSONObject(i);
					result.append(current.getString("inferText")).append(" ");
				}
			}

			return result.toString();  // Converting StringBuilder to String
		} catch (Exception e) {
			e.printStackTrace();
			return "Error parsing response data.";
		}
	}
}