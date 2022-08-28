package com.gameofjess.javachess.helper.publicserver;

import com.gameofjess.javachess.helper.configuration.ConfigLoader;
import com.gameofjess.javachess.helper.configuration.PublicServerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class EncryptionManager {

    private static final Logger log = LogManager.getLogger(EncryptionManager.class);

    private PublicServerConfig config;

    public EncryptionManager() {
        try {
            config = (PublicServerConfig) new ConfigLoader().loadConfig(new File("encryption.json"), PublicServerConfig.class);
        } catch (IOException e) {
            log.error("Could not read config file. Proceeding to use default values!");
            config = new PublicServerConfig();
        }
    }

    public boolean getEncrypted() {
        return config.getUseEncryption();
    }

    public SSLContext getDefaultSSLContext() {
        SSLContext sslContext;
        try {
            Certificate letsEncrypt;
            try (InputStream inputStream = new URL("https://letsencrypt.org/certs/lets-encrypt-r3.pem").openStream()) {
                letsEncrypt = CertificateFactory.getInstance("X.509").generateCertificate(inputStream);
            } catch (CertificateException e) {
                throw new RuntimeException(e);
            }

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            keyStore.setCertificateEntry("lets-encrypt", letsEncrypt);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(keyStore, new char[0]);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(keyStore);

            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException | KeyStoreException |
                 CertificateException | UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        }
        return sslContext;
    }

    public SSLContext getInsecureSSLContext() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };

        SSLContext sslContext;

        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }

        return sslContext;
    }

    public SSLContext getSSLContextFromKeystore() {
        SSLContext sslContext;
        String pathToKeystore = config.getPathToKeystore();
        String keystorePassword = config.getKeystorePassword();

        if (pathToKeystore.equals("")) {
            throw new RuntimeException("Please configure your encryption setting before continuing!");
        }

        KeyStore ks;
        try {
            ks = KeyStore.getInstance("JKS");
            ks.load(Files.newInputStream(new File(pathToKeystore).toPath()), keystorePassword.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, keystorePassword.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException |
                 IOException | CertificateException | KeyManagementException e) {
            throw new RuntimeException("Invalid encryption configuration: " + e.getMessage());
        }

        return sslContext;
    }

}
