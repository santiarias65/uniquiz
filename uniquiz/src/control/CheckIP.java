package control;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CheckIP {
	public String obtenerIP() throws UnknownHostException {
		InetAddress ip = InetAddress.getLocalHost();
		return ip.getHostAddress();
	}
}
