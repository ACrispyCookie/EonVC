package me.acrispycookie.eonvc.util.utility;

import org.bukkit.ChatColor;

public class Messages {
	
	private final static int CENTER_PX = 154;
	private final static int MAX_PX = 250;
	
	public static String getCenteredMessage(String message){
		message = ChatColor.translateAlternateColorCodes('&', message);

		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for(char c : message.toCharArray()){
			if(c == '§'){
				previousCode = true;
			}else if(previousCode){
				previousCode = false;
				if(c == 'l' || c == 'L'){
					isBold = true;
				}else isBold = false;
			}else{
				DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				messagePxSize++;
			}
		}

		int halvedMessageSize = messagePxSize / 2;
		int toCompensate = CENTER_PX - halvedMessageSize;
		int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
		int compensated = 0;
		StringBuilder sb = new StringBuilder();
		while(compensated < toCompensate){
			sb.append(" ");
			compensated += spaceLength;
		}
		return sb.toString() + message;
	}

}
