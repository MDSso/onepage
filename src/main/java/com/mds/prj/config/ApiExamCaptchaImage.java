package com.mds.prj.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

// ���̹� ĸ�� API ���� - ĸ�� �̹��� ����
@Component
public class ApiExamCaptchaImage {
	
	private String clientId="CpoFpGM6kK65u7WcIzih";
	private String clientSecret="SptUhwfiMV";
	private String key; 
	
    public String getimg() {

        // https://openapi.naver.com/v1/captcha/nkey ȣ��� ���� Ű��
        String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);
        
        return responseBody;
    }

    private String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // ���� ȣ��
                return getImage(con.getInputStream());
            } else { // ���� �߻�
                return error(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API ��û�� ���� ����", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL�� �߸��Ǿ����ϴ�. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("������ �����߽��ϴ�. : " + apiUrl, e);
        }
    }

    @SuppressWarnings("unchecked")
	private String getImage(InputStream is){
        int read;
        byte[] bytes = new byte[1024];
        // ������ �̸�����  ���� ����
        String filename = Long.valueOf(new Date().getTime()).toString() + ".jpg";
        String path = "C:\\boardimg\\" ;
        String result = null;
        File f = new File(path + filename);
        try(OutputStream outputStream = new FileOutputStream(f)){
        	f.createNewFile();
            while ((read = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            
            
            JSONObject captchaImgName = new JSONObject();
            captchaImgName.put("imgname", filename);
            captchaImgName.put("key", key);
            result = captchaImgName.toJSONString();
            
            
            return result;  
        } catch (IOException e) {
            throw new RuntimeException("�̹��� ĸ�� ���� ������ ���� �߽��ϴ�.",e);
        }
    }

    private String error(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API ������ �дµ� �����߽��ϴ�.", e);
        }
    }
    
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
