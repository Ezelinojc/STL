package com.example.STL.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class Formatar {

	
	public static Date data(String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date data1 = null;
		try {
			data1 = sdf.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data1;
	}
}
