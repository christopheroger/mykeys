package org.dpr.mykeys.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.asn1.x509.CRLNumber;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.x509.X509V2CRLGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;

public class CrlTools {
	final Log log = LogFactory.getLog(CrlTools.class);

	static String PUBKEYSTORE = "keystorePub.p12";

	static String TYPE_JKS = "JKS";

	static String TYPE_P12 = "PKCS12";

	static String EXT_JKS = ".jks";

	static String EXT_P12 = ".p12";

	static String X509_TYPE = "X.509";

	private static final int NUM_ALLOWED_INTERMEDIATE_CAS = 0;

	/**
	 * Chargement certificat X509 à partir d'un flux.
	 * 
	 * <BR>
	 * 
	 * 
	 * @param aCertStream
	 * @return
	 * @throws GeneralSecurityException
	 */
	private static X509Certificate loadX509Cert(InputStream aCertStream)
			throws GeneralSecurityException {
		// création d'une fabrique de certificat X509
		CertificateFactory cf = CertificateFactory.getInstance("X.509");

		// chargement du certificat
		X509Certificate cert = (X509Certificate) cf
				.generateCertificate(aCertStream);
		return cert;
	}

	/**
	 * get a random BigInteger
	 * 
	 * @param numBits
	 * @return
	 */
	public static BigInteger RandomBI(int numBits) {
		SecureRandom random = new SecureRandom();
		// byte bytes[] = new byte[20];
		// random.nextBytes(bytes);
		BigInteger bi = new BigInteger(numBits, random);
		return bi;

	}

	public X509CRL generateCrl(X509Certificate certSign, CrlInfo crlInfo,
			Key privateKey) throws KeyStoreException, NoSuchProviderException,
			NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException, InvalidKeyException, CRLException,
			IllegalStateException, SignatureException {

		Calendar calendar = Calendar.getInstance();

		X509V2CRLGenerator crlGen = new X509V2CRLGenerator();

		Date now = new Date();
		Date nextUpdate = calendar.getTime();

		// crlGen.setIssuerDN((X500Principal) certSign.getIssuerDN());
		crlGen.setIssuerDN(certSign.getSubjectX500Principal());
		String signAlgo = "SHA1WITHRSAENCRYPTION";
		crlGen.setThisUpdate(crlInfo.getThisUpdate());
		crlGen.setNextUpdate(crlInfo.getNextUpdate());
		crlGen.setSignatureAlgorithm(signAlgo);
		// BigInteger bi = new BigInteger("816384897");
		// crlGen.addCRLEntry(BigInteger.ONE, now,
		// CRLReason.privilegeWithdrawn);
		BigInteger bi = new BigInteger("155461028");
		crlGen.addCRLEntry(bi, new Date(), CRLReason.privilegeWithdrawn);

		crlGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,
				new AuthorityKeyIdentifierStructure(certSign));
		crlGen.addExtension(X509Extensions.CRLNumber, false, new CRLNumber(
				crlInfo.getNumber()));

		X509CRL crl = crlGen.generate((PrivateKey) privateKey, "BC");
		// OutputStream os = new FileOutputStream(new
		// File("./certificats/crlrevoke.crl"));
		// os.write(crl.getEncoded());
		return crl;

	}

	public void revokeCert(X509Certificate cert, X509CRL crl)
			throws KeyStoreException, NoSuchProviderException,
			NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException, InvalidKeyException, CRLException,
			IllegalStateException, SignatureException, KeyToolsException {

		// crl.

	}

	// public void timeStamp(KeyStoreInfo ksInfo, CertificateInfo certInfo){
	// TimeStampTokenGenerator ts = new TimeStampTokenGenerator(
	// }

	/**
	 * .
	 * 
	 * <BR>
	 * 
	 * @param certSign
	 * @param crlInfo
	 * @return
	 * @throws CertificateParsingException
	 * @throws SignatureException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws IllegalStateException
	 * @throws CRLException
	 * @throws InvalidKeyException
	 */
	public static X509CRL generateCrl(CertificateInfo certSign, CrlInfo crlInfo)
			throws CertificateParsingException, InvalidKeyException,
			CRLException, IllegalStateException, NoSuchProviderException,
			NoSuchAlgorithmException, SignatureException {

		X509V2CRLGenerator crlGen = new X509V2CRLGenerator();
		// crlGen.setIssuerDN((X500Principal) certSign.getIssuerDN());
		crlGen.setIssuerDN(certSign.getCertificate().getSubjectX500Principal());
		String signAlgo = "SHA256WITHRSAENCRYPTION";
		crlGen.setThisUpdate(crlInfo.getThisUpdate());
		crlGen.setNextUpdate(crlInfo.getNextUpdate());
		crlGen.setSignatureAlgorithm(signAlgo);

		crlGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,
				new AuthorityKeyIdentifierStructure(certSign.getCertificate()));
		crlGen.addExtension(X509Extensions.CRLNumber, false, new CRLNumber(
				crlInfo.getNumber()));

		X509CRL crl = crlGen.generate((PrivateKey) certSign.getPrivateKey(),
				"BC");
		return crl;
	}

	/**
	 * .
	 * 
	 * <BR>
	 * 
	 * 
	 * @param xCerts
	 * @throws IOException
	 * @throws CRLException
	 */
	public static void saveCRL(X509CRL crl, String crlFile)
			throws CRLException, IOException {

		OutputStream output = new FileOutputStream(crlFile);
		IOUtils.write(crl.getEncoded(), output);

	}
}
