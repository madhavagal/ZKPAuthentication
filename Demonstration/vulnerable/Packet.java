package vulnerable;

import vulnerable.ECPoint;

import java.math.BigInteger;

public class Packet {

    private ECPoint point;
    private ECPoint publicKey;
    private ECPoint V;
    private BigInteger r;
    private BigInteger n;
    private BigInteger c;

    public Packet(ECPoint genPoint, ECPoint publicKey, BigInteger r, ECPoint V, BigInteger n, BigInteger c) {
        this.point = genPoint;
        this.publicKey = publicKey;
        this.r = r;
        this.V = V;
        this.n = n;
        this.c = c;
    }

    public Packet(String genPoint, String publicKey, String r, String V, String n, String c) {
        this.r = new BigInteger(r);
        this.n = new BigInteger(n, 16);
        this.c = new BigInteger(c);

        this.point = makePointFromString(genPoint);
        this.publicKey = makePointFromString(publicKey);
        this.V = makePointFromString(V);
    }

    public String toString() {
        return this.point.toString() + " " + this.publicKey.toString() + " " + this.r.toString() + " " + this.V.toString() + " " + this.n.toString();
    }

    public static ECPoint makePointFromString(String s) {
        s = s.replace("(", "");
        s = s.replace(")", "");
        s = s.replace(",", "");
        String ary[] = s.split("\\s+");
        return new ECPoint(new BigInteger(ary[0]), new BigInteger(ary[1]));
    }

    public ECPoint getPoint() { return this.point; }

    public ECPoint getPublicKey() { return this.publicKey; }

    public ECPoint getV() { return this.V; }

    public BigInteger getR() { return this.r; }

    public BigInteger getN() { return this.n; }

    public BigInteger getC() { return this.c; }

    public String toSSH() {
        StringBuilder output = new StringBuilder();
        output.append(this.getPoint().getX().toString());
        output.append(",");
        output.append(this.getPoint().getY().toString());
        output.append(",");
        output.append(this.getPublicKey().getX().toString());
        output.append(",");
        output.append(this.getPublicKey().getY().toString());
        output.append(",");
        output.append(this.getV().getX().toString());
        output.append(",");
        output.append(this.getV().getY().toString());
        output.append(",");
        output.append(this.getR().toString());
        output.append(",");
        output.append(this.getN().toString(16));
        return output.toString();
    }
}
