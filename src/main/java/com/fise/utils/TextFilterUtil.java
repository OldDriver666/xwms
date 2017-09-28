package com.fise.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TextFilterUtil {
	// 默认编码格式
	private static final String ENCODING = "gbk";
	// 敏感词库的路径
	private static final InputStream in = TextFilterUtil.class.getClassLoader()
			.getResourceAsStream("sensitive/keyWords.txt");
	// 脏字库
	private static Set<Character> sensitiveCharSet = null;
	// 敏感词库
	private static Set<String> sensitiveWordSet = null;
	/**
	 * 初始化敏感词库
	 */
	private static void init() {
		// 初始化容器
		sensitiveCharSet = new HashSet<>();
		sensitiveWordSet = new HashSet<>();
		// 读取文件 创建敏感词库
		readSensitiveWords();
	}
	/**
	 * 读取本地的敏感词文件
	 *
	 * @return
	 */
	private static void readSensitiveWords() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in, ENCODING));
			String line;
			while ((line = reader.readLine()) != null) {
				String word = line.trim();
				sensitiveWordSet.add(word);
				for (Character c : word.toCharArray()) {
					sensitiveCharSet.add(c);
				}
			}
		} catch (UnsupportedEncodingException e) {
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				reader = null;
			}
		}
		return;
	}
	/**
	 * 检查敏感词
	 *
	 * @return
	 */
	private static List<String> checkSensitiveWord(String text) {
		if (sensitiveWordSet == null || sensitiveCharSet == null) {
			init();
		}
		List<String> sensitiveWords = new ArrayList<>();
		for (int i = 0; i < text.length(); i++) {
			Character word = text.charAt(i);
			if (!sensitiveCharSet.contains(word)) {
				continue;
			}
			int j = i;
			while (j < text.length()) {
				if (!sensitiveCharSet.contains(word)) {
					break;
				}
				String key = text.substring(i, j + 1);
				if (sensitiveWordSet.contains(key)) {
					sensitiveWords.add(key);
				}
				j++;
			}
		}
		return sensitiveWords;
	}
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		
		File directory = new File("");//设定为当前文件夹
		try{
		    System.out.println(directory.getCanonicalPath());//获取标准的路径
		    System.out.println(directory.getAbsolutePath());//获取绝对路径
		}catch(Exception e){}

	}
}
