package vulnerable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Verifier {


    public boolean verify(ECPoint genPoint, ECPoint publicKey, ECPoint V, BigInteger r, BigInteger n, BigInteger userId)
            throws IOException, NoSuchAlgorithmException {

        BigInteger c = makeChallenge(genPoint, publicKey, V, userId);
        ECPoint testV = genPoint.multiply(r).add(publicKey.multiply(c));

        // Write y/n to txt file so C code can see what the response is
        boolean output = V.getX().equals(testV.getX()) && V.getY().equals(testV.getY());
        PrintStream out = new PrintStream(new FileOutputStream("/home/madhav/Desktop/CapstoneProject/Demonstration/out.txt"));
        out.println(output? "y" : "n");
        out.close();

        return output;
    }

    // c = H(G|| V || A || UserID || OtherInfo)
    private static BigInteger makeChallenge(ECPoint genPoint, ECPoint publicKey, ECPoint V, BigInteger userID)
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
