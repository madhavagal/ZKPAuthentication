package vulnerable;

import java.math.BigInteger;

public class ECPoint {

    private static BigInteger provenPrime;

    private final BigInteger xCoord;
    private final BigInteger yCoord;


    public ECPoint(BigInteger x, BigInteger y) {
        this.xCoord = x;
        this.yCoord = y;

        // calculate provenPrime
        this.provenPrime = new BigInteger("2").pow(256);
        this.provenPrime = this.provenPrime.subtract(new BigInteger("2").pow(32));
        this.provenPrime = this.provenPrime.subtract(new BigInteger("2").pow(9));
        this.provenPrime = this.provenPrime.subtract(new BigInteger("2").pow(8));
        this.provenPrime = this.provenPrime.subtract(new BigInteger("2").pow(7));
        this.provenPrime = this.provenPrime.subtract(new BigInteger("2").pow(6));
        this.provenPrime = this.provenPrime.subtract(new BigInteger("2").pow(4));
        this.provenPrime = this.provenPrime.subtract(BigInteger.ONE);

    }

    public String toString() {
        return "("+ this.xCoord.toString()+ ", " + this.yCoord.toString() + ")";
    }

    public BigInteger getX() { return xCoord; }

    public BigInteger getY() { return yCoord; }

    public ECPoint add(ECPoint that) {
        BigInteger lamAdd = that.yCoord.subtract(this.yCoord).multiply(modInverse(that.xCoord.subtract(this.xCoord)));
        lamAdd = lamAdd.mod(provenPrime);
        BigInteger newX = lamAdd.multiply(lamAdd).subtract(this.xCoord).subtract(that.xCoord).mod(provenPrime);
        BigInteger newY = lamAdd.multiply(this.xCoord.subtract(newX)).subtract(this.yCoord).mod(provenPrime);
        return new ECPoint(newX, newY);
    }

    public ECPoint multiply(BigInteger scalar) {
        String binary = scalar.toString(2);
        ECPoint q = new ECPoint(this.getX(), this.getY());
        for (int i = 1; i < binary.length(); ++i) {
            q = q.square();
            if (binary.charAt(i) == '1') {
                q = q.add(this);
            }
        }
        return q;
    }

    public ECPoint square() {
        BigInteger two = new BigInteger("2");
        BigInteger lam = new BigInteger("3").multiply(this.xCoord).multiply(this.xCoord).multiply(modInverse(two.multiply(this.yCoord))).mod(provenPrime);
        BigInteger newX = lam.multiply(lam).subtract(two.multiply(this.xCoord)).mod(provenPrime);
        BigInteger newY = lam.multiply(this.xCoord.subtract(newX)).subtract(this.yCoord).mod(provenPrime);
        return new ECPoint(newX, newY);
    }

    private static BigInteger modInverse(BigInteger var) {
        BigInteger lm = new BigInteger("1");
        BigInteger hm = new BigInteger("0");
        BigInteger low = var.mod(provenPrime);
        BigInteger high = provenPrime;
        // while low > 1
        while (low.compareTo(new BigInteger("1")) == 1) {
            BigInteger ratio = high.divide(low);
            BigInteger nm = hm.subtract(lm.multiply(ratio));
            BigInteger enew = high.subtract(low.multiply(ratio));
            hm = lm;
            high = low;
            lm = nm;
            low = enew;
        }
        return lm.mod(provenPrime);
    }
}
