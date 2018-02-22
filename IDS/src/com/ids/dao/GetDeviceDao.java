package com.ids.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

public class GetDeviceDao {

	private static Map<String, NetworkInterface> deviceMap;

	public static String[] getDevice() {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		String[] strs = new String[devices.length];
		deviceMap = new HashMap<String, NetworkInterface>();
		int j = 1;
		for (int i = 0; i < devices.length; i++) {
			String string = devices[i].description;
			
			if (deviceMap.containsKey(string)) {
				String str = string + "_" + j;
				deviceMap.put(str, devices[i]);
				strs[i] = str;
				j++;
			} else {
				deviceMap.put(string, devices[i]);
				strs[i] = string;
			}

		}

		return strs;
	}

	public static Map<String, NetworkInterface> getDeviceMap() {
		return deviceMap;
	}

	public static void main(String[] args) {
		GetDeviceDao.getDevice();

		System.out.println(Arrays.toString(getDevice()));

		Set<String> s = deviceMap.keySet();
		for (String key : s) {
			NetworkInterface value = deviceMap.get(key);
			System.out.println(key + "---" + value);
		}
	}

}
