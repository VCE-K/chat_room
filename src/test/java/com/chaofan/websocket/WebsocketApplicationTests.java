package com.chaofan.websocket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebsocketApplicationTests {

	@Test
	public void sendGet2() throws Exception {
		String apiEndpoint = "https://api.openai.com/v1/engines/davinci-codex/completions";
		String apiKey = "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\n"; // 替换成你的 API Key

		String requestBody = "{\n" +
				"  \"prompt\": \"What is the meaning of life?\",\n" +
				"  \"max_tokens\": 50,\n" +
				"  \"temperature\": 0.5\n" +
				"}";

		try {
			URL url = new URL(apiEndpoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Bearer " + apiKey);
			conn.setDoOutput(true);

			OutputStream os = conn.getOutputStream();
			os.write(requestBody.getBytes());
			os.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test(){
		Scanner in = new Scanner(System.in);
		String str = in.nextLine().toLowerCase();
		char c = in.nextLine().charAt(0);
		int num = 0;
		for(int i=0;i<str.length() - 1 ;i++){
			if(str.charAt(i) == c){
				num++;
			}
		}
		System.out.println(num);
	}


	private final String USER_AGENT = "Mozilla/5.0";
	// HTTP GET请求
	@Test
	public void sendGet() throws Exception {

		String url = "https://api.wrdan.com/hitokoto";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//默认值我GET
		con.setRequestMethod("GET");

		//添加请求头
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//打印结果
		System.out.println(response.toString());

	}

	@Test
	public void HJ3test(){
		Scanner in = new Scanner(System.in);
		int num = in.nextInt();
		List<Integer> dataList = new ArrayList();
		Set set = new HashSet<Integer>();
		for(int i=0;i<num;i++){
			int n = in.nextInt();
			if(set.contains(n)){

			}else{
				set.add(n);
				dataList.add(n);
			}
		}
		Iterator it = set.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		Object[] data = dataList.toArray();
		Arrays.sort(data);
		for(int i=0;i<data.length;i++){
			System.out.println(data[i]);
		}
	}

	@Test
	public void HJ4test() {
		Scanner in = new Scanner(System.in);
		String str = in.nextLine();
		String[] strs = str.split(" ");
		List<String> list = new ArrayList();
		for(int i=0;i<strs.length;i++){
			String s = strs[i];
			if(s.length()>8){
				list.add(s.substring(0, 8));
			}else{
				int num = 8 - s.length();
			}
		}
	}

	private final int BASE = 16;
	private final Map<Character, Integer> map = new HashMap() {
		{
			put('0', 0);
			put('1', 1);
			put('2', 2);
			put('3', 3);
			put('4', 4);
			put('5', 5);
			put('6', 6);
			put('7', 7);
			put('8', 8);
			put('9', 9);
			put('A', 10);
			put('B', 11);
			put('C', 12);
			put('D', 13);
			put('E', 14);
			put('F', 15);
		}
	};

	@Test
	public void HJ5test() {
		Scanner in = new Scanner(System.in);
		// 注意 hasNext 和 hasNextLine 的区别
		while (in.hasNext()) { // 注意 while 处理多个 case
			String str = in.nextLine().toUpperCase();
			int num = 0;
			for (int i = 2; i < str.length(); i++) {
				char c = str.charAt(i);
				//10*16+10*1
				int t = 0;
				if(c>='0' && c<='9'){
					t = c - '0';
				}else if(c>='A' && c<='F'){
					t = c - 'A' + 10;
				}
				num += map.get(c) * Math.pow(BASE, str.length() - i - 1);
				num += t * Math.pow(BASE, str.length() - i - 1);
			}
			Integer.parseInt(str.substring(2, str.length() - 1), 16);
			System.out.print(num);
		}
	}

	@Test
	public void HJ6test() {
		Scanner in = new Scanner(System.in);
		// 注意 hasNext 和 hasNextLine 的区别
		while (in.hasNext()) { // 注意 while 处理多个 case
			long target = in.nextLong();
			int y = 2;
			while(target != 1){
				if(target % y == 0){
					target /=  y ;
					System.out.print(y+" ");
				}else{
					if(y > target/y){//这一步说明y就已经是target之下最大的因子了
						y = (int) target;
					}else{
						y++;
					}
				}
			}
		}
	}


	@Test
	public void HJ7test() {
		Scanner in = new Scanner(System.in);
		// 注意 hasNext 和 hasNextLine 的区别
		while (in.hasNext()) { // 注意 while 处理多个 case
			long target = in.nextLong();
			int y = 2;
			while(target != 1){
				if(target % y == 0){
					target /=  y ;
					System.out.print(y+" ");
				}else{
					if(y > target/y){//这一步说明y就已经是target之下最大的因子了
						y = (int) target;
					}else{
						y++;
					}
				}
			}
		}
	}

	@Test
	public void HJ8test() {
		Scanner in = new Scanner(System.in);
		// 注意 hasNext 和 hasNextLine 的区别
		while (in.hasNext()) { // 注意 while 处理多个 case
			float target = in.nextFloat();
			int a = (int) target;
			System.out.println(a);
		}
	}


}
