package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//import org.springframework.util.StreamUtils;

public class HttpUtil {
	public static String CheckToken(String httpURL, String token) throws IOException {
		String sTotalString = null;
		try {
			URL url = new URL(httpURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			/**
			 * Ȼ���������Ϊ���ģʽ��URLConnectionͨ����Ϊ������ʹ�ã���������һ��Webҳ��
			 * ͨ����URLConnection��Ϊ���������԰����������Webҳ���͡��������������
			 */
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("content-type", "text/html");
			connection.setRequestMethod("POST");

			connection.connect();
			/**
			 * ���Ϊ�˵õ�OutputStream�������������Լ����Writer���ҷ���POST��Ϣ�У����磺 ...
			 */
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
			out.write(token); // ��������ʽ��ֵ
			out.flush();

			String sCurrentLine;
			sCurrentLine = "";
			sTotalString = "";
			InputStream l_urlStream;
			l_urlStream = connection.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				sTotalString += sCurrentLine;
			}
			System.out.println(sTotalString); // ��ȡ���ص���
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sTotalString;
	}

}
