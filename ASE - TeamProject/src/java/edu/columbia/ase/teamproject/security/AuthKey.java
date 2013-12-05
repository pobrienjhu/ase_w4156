package edu.columbia.ase.teamproject.security;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.io.BaseEncoding;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthKey.
 */
public class AuthKey {

    /** The id. */
    private final Long id;

    /** The provided mac. */
    private final byte[] providedMac;

    /**
     * Instantiates a new auth key.
     *
     * @param id
     *            the id
     * @param mac
     *            the mac
     */
    private AuthKey(Long id, byte[] mac) {
        this.id = Preconditions.checkNotNull(id);
        this.providedMac = mac;
    }

    /*
     * apiKey should be of the form: base64(id:base64(hmac(id, secret)))
     */
    /**
     * Instantiates a new auth key.
     *
     * @param apiKey
     *            the api key
     */
    public AuthKey(String apiKey) {
        String decoded = new String(BaseEncoding.base64().decode(apiKey));
        String[] parts = decoded.split(":", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException();
        }
        this.id = Long.valueOf(parts[0]);
        this.providedMac = BaseEncoding.base64().decode(parts[1]);
    }

    /**
     * Auth key for id and secret.
     *
     * @param id
     *            the id
     * @param secret
     *            the secret
     * @return the auth key
     */
    public static AuthKey authKeyForIdAndSecret(Long id, String secret) {
        Preconditions.checkNotNull(id);
        Preconditions.checkArgument(!secret.isEmpty());

        return new AuthKey(id, computeMac(id, secret));
    }

    /**
     * Gets the api key.
     *
     * @return the api key
     */
    public String getApiKey() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(id.toString());
        sb.append(':');
        sb.append(BaseEncoding.base64().encode(providedMac));
        return BaseEncoding.base64().encode(sb.toString().getBytes());
    }

    /**
     * Compute mac.
     *
     * @param id
     *            the id
     * @param secret
     *            the secret
     * @return the byte[]
     */
    private static byte[] computeMac(Long id, String secret) {
        Preconditions.checkNotNull(id);
        Preconditions.checkArgument(!secret.isEmpty());
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(id);
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            mac.init(secretKey);
            mac.update(buffer.array());
            return mac.doFinal();
        } catch (NoSuchAlgorithmException e) {
            Throwables.propagate(e);
        } catch (InvalidKeyException e) {
            Throwables.propagate(e);
        }
        return null;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Checks if is auth key valid.
     *
     * @param secret
     *            the secret
     * @return true, if is auth key valid
     */
    public boolean isAuthKeyValid(String secret) {
        byte[] expectedMac = computeMac(id, secret);
        if (expectedMac.length != providedMac.length) {
            return false;
        }

        int differences = 0;
        for (int i = 0; i < expectedMac.length; i++) {
            if (expectedMac[i] != providedMac[i]) {
                differences++;
            }
        }
        return differences == 0;

    }

}
