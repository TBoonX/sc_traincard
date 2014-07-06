package main;


import java.util.HashMap;


public class Constants {
	public final static String BANKDRUECKEN = "Bankdrücken";
	public final static String t = "1";
	public final static HashMap<String, Integer> WORKOUTNAMES = new HashMap<String, Integer>() {
		{
			put(BANKDRUECKEN, 1);
			put(t, 2);
		};

	};

}
