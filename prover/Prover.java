package prover;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.ArrayList;

import java.net.InetAddress;
import java.util.Enumeration;
import java.net.NetworkInterface;
import java.net.Inet4Address;
import java.net.SocketException;
import java.lang.String;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Prover {

    private BigInteger privateKey;
    private ECPoint publicKey;
    private BigInteger userID;
    private BigInteger littleV;
    private ECPoint bigV;
    private BigInteger littleR;
    private BigInteger c;

    // define generator point and big prime n
    private BigInteger xCoord = new BigInteger("55066263022277343669578718895168534326250603453777594175500187360389116729240");
    private BigInteger yCoord = new BigInteger("32670510020758816978083085130507043184471273380659243275938904335757337482424");
    private ECPoint genPoint = new ECPoint(xCoord, yCoord);
    private BigInteger n = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141", 16);
		
	private long ts = System.currentTimeMillis()/1000;
    private BigInteger timestamp = BigInteger.valueOf(ts);
    private String ip;
    private BigInteger IP;
    
    private void findIP() throws SocketException{   
		//InetAddress localhost = InetAddress.getLocalHost();
        //System.out.println("System IP Address : \n\n" + (localhost.getHostAddress()).trim());
        NetworkInterface n = NetworkInterface.getByName("enp0s3");
        Enumeration ee = n.getInetAddresses();
        while (ee.hasMoreElements())
			{
				InetAddress i = (InetAddress) ee.nextElement();
				if(i instanceof Inet4Address){
					this.ip = i.getHostAddress();
					break;
				}
					
			}
		ip = ip.replace(".","999");
		this.IP = new BigInteger(ip);
	}

    public Prover(BigInteger userID) throws NoSuchAlgorithmException, SocketException {
        this.userID = userID;
        chooseKeys();
        computeV();
        findIP();
        this.c = makeChallenge(genPoint, publicKey, bigV, userID, timestamp, IP);
        computeR();
        
    }

    public Packet getPacket () throws IOException {
    	PrintStream debugout = new PrintStream(new FileOutputStream("/home/madhav/Desktop/debugProver.txt"));
		String debug = "Printing from Prover.java \n";
		debug += "genPoint -> "+this.genPoint.toString()+"\n";
		debug += "publicKey -> "+this.publicKey.toString()+"\n";
		debug += "V -> "+this.bigV.toString()+"\n";
		debug += "r -> "+this.littleR.toString()+"\n";
		debug += "n -> "+this.n.toString()+"\n";
		debug += "userId -> "+this.userID.toString()+"\n";
		debug += "timestamp -> "+this.timestamp.toString()+"\n";
		debug += "proverIP -> "+this.IP.toString()+"\n";
		debug += "challenge -> "+this.c.toString()+"\n";
        debugout.println(debug);
        debugout.close();
        return new Packet(this.genPoint, this.publicKey, this.littleR, this.bigV, this.n, this.c, this.userID, this.timestamp);
    }


    private void chooseKeys() {
        // choose private key between 1 and n-1
        this.privateKey = chooseRandom(this.n.subtract(BigInteger.ONE));
        this.publicKey = this.genPoint.multiply(this.privateKey);
    }

    private void computeV() {
        // choose v between 1 and n-1
        this.littleV = chooseRandom(this.n.subtract(BigInteger.ONE));
        this.bigV = this.genPoint.multiply(this.littleV);
    }

    private void computeR() {
        this.littleR = this.littleV.subtract(this.privateKey.multiply(this.c)).mod(this.n);
    }

    private BigInteger chooseRandom(BigInteger max) {
        SecureRandom random = new SecureRandom();
        return new BigInteger(max.toString(2).length(), random).mod(max).add(BigInteger.ONE);
    }

    // c = H(G|| V || A || UserID || OtherInfo)
    private static BigInteger makeChallenge(ECPoint genPoint, ECPoint publicKey, ECPoint V, BigInteger userID, BigInteger timestamp, BigInteger IP)
            throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        List<Byte> toHashList = new ArrayList<>();
        addByteArray(toHashList, genPoint.getX().toByteArray());
        addByteArray(toHashList, genPoint.getY().toByteArray());
        addByteArray(toHashList, V.getX().toByteArray());
        addByteArray(toHashList, V.getY().toByteArray());
        addByteArray(toHashList, publicKey.getX().toByteArray());
        addByteArray(toHashList, publicKey.getY().toByteArray());
        addByteArray(toHashList, userID.toByteArray());
        addByteArray(toHashList, timestamp.toByteArray());
        addByteArray(toHashList, IP.toByteArray());

        byte[] toHash = new byte[toHashList.size()];
        for(int i = 0; i < toHashList.size(); i++) {
            toHash[i] = toHashList.get(i).byteValue();
        }
        BigInteger c = new BigInteger(digest.digest(toHash));
        return c.abs(); // overflow, so take absolute value
    }

    private static void addByteArray(List<Byte> aryList, byte[] ary) {
        for (byte b : ary) {
            aryList.add(b);
        }
    }
}
