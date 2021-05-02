import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import java.net.InetAddress;
import java.util.Enumeration;
import java.net.NetworkInterface;
import java.net.Inet4Address;

public class Test {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
    	long ts = System.currentTimeMillis()/1000;
        System.out.println("Timestamp -> \n\n"+ts);
		InetAddress localhost = InetAddress.getLocalHost();
        System.out.println("System IP Address : \n\n" + (localhost.getHostAddress()).trim());
        NetworkInterface n = NetworkInterface.getByName("enp0s3");
        Enumeration ee = n.getInetAddresses();
        while (ee.hasMoreElements())
			{
				InetAddress i = (InetAddress) ee.nextElement();
				if(i instanceof Inet4Address)
					System.out.println(i.getHostAddress());
			}
	}
}
