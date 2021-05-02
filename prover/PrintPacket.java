package prover;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

public class PrintPacket{
	
	private BigInteger ubuntuUserId = new BigInteger("050997");
    private BigInteger macUserId = new BigInteger("082096");
    private Prover prover;
	
    public static void main (String[] args) throws IOException, NoSuchAlgorithmException {
    	PrintPacket prn = new PrintPacket();
    	prn.printPacket();
	}
	
	private void printPacket() throws IOException, NoSuchAlgorithmException {
		this.prover = new Prover(ubuntuUserId);
       	System.out.println("packet -> "+prover.getPacket().toSSH());	
	
	}
}
