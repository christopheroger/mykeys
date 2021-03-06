/**
 * 
 */
package org.dpr.mykeys.app;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509CRL;

import org.dpr.mykeys.app.KeyStoreInfo.StoreFormat;
import org.dpr.mykeys.app.KeyStoreInfo.StoreModel;
import org.dpr.mykeys.app.KeyStoreInfo.StoreType;

/**
 * @author Buck
 * 
 */
public class CommonsActions {

	public void exportCert(StoreFormat storeFormat, String path,
			char[] password, CertificateInfo certInfo) throws Exception {
		exportCert(null, storeFormat, path, password, certInfo, false);
	}

    public void exportCert(KeyStoreInfo ksInfo, StoreFormat pkcs12, String path, char[] password,
            CertificateInfo certInfo, boolean isExportCle) throws Exception 
    {
        exportCert( ksInfo,  pkcs12,  path,  password,
                 certInfo,  isExportCle, null);
        
    }
    
	public void exportCert(KeyStoreInfo ksInfoIn, StoreFormat storeFormat,
			String path, char[] passwordExport, CertificateInfo certInfo,
			boolean isExportCle,char[] privKeyPwd) throws Exception {
		StoreModel storeModel = StoreModel.P12STORE;
		KeyStoreInfo ksInfoOut = new KeyStoreInfo("store", path, storeModel,
				storeFormat);
		ksInfoOut.setPassword(passwordExport);
		KeyTools kt = new KeyTools();
		if (isExportCle && certInfo.getPrivateKey() == null) {
			CertificateInfo certInfoEx = new CertificateInfo();
			certInfoEx.setAlias(certInfo.getAlias());
			certInfoEx.setCertificate(certInfo.getCertificate());
			certInfoEx.setCertificateChain(certInfo.getCertificateChain());
			certInfoEx.setPassword(privKeyPwd);
			KeyStore kstore;
			boolean hasPrivateKey=false;
			kstore = kt.loadKeyStore(ksInfoIn.getPath(),
					ksInfoIn.getStoreFormat(), ksInfoIn.getPassword());
			if (kstore.isKeyEntry(certInfoEx.getAlias())) {
			    hasPrivateKey=true;

			}
			char pwd[]=ksInfoIn.getPassword();
			if(ksInfoIn.getStoreType().equals(StoreType.INTERNAL)){
			//	pwd=certInfoEx.getPassword();
				certInfoEx.setPassword(pwd);
			}
			certInfoEx.setPrivateKey(kt.getPrivateKey(certInfoEx.getAlias(),
					kstore, certInfoEx.getPassword()));

			certInfo = certInfoEx;
		}
		try {
			KeyStore ks = kt.createKeyStore(storeFormat, path, ksInfoOut.getPassword());
			// cloner le certinfo

			kt.addCertToKeyStoreNew(certInfo.getCertificate(), ksInfoOut,
					certInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void signData(KeyStoreInfo kInfo, char[] password,
			CertificateInfo certInfo, boolean isInclude) {
		KeyTools kt = new KeyTools();
		KeyStore ks;
		try {
			ks = kt.loadKeyStore(kInfo.getPath(), kInfo.getStoreFormat(),
					kInfo.getPassword());
			certInfo.setPrivateKey((PrivateKey) ks.getKey(certInfo.getAlias(),
					kInfo.getPassword()));
		} catch (KeyToolsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SignTools sTools = new SignTools();
		sTools.SignData(null, certInfo, "c:/dev/_test.sql", isInclude);

	}

	/**
	 * .
	 * 
	 * <BR>
	 * 
	 * 
	 * @param pwd
	 * @param string
	 * @param format2
	 * @return
	 * @throws Exception
	 * 
	 */
	public KeyStore createStore(StoreFormat format, String dir, char[] pwd)
			throws Exception {

		KeyTools kt = new KeyTools();

		KeyStore ks = kt.createKeyStore(format, dir, pwd);
		KSConfig.getUserCfg().addProperty(
				"store." + StoreModel.CERTSTORE + "." + format.toString(), dir);
		return ks;

	}

	// public KeyStore addCert(CertificateInfo ci, KeyStore ks, char[] pwd)
	// throws Exception {
	// KeyStoreInfo ksinfo=new Keys
	// ksinfo.s
	//
	// KeyStore kstore = loadKeyStore(ksInfo.getPath(), ksInfo
	// .getStoreFormat(), ksInfo.getPassword());
	// saveCertChain(kstore, cert, certInfo);
	// saveKeyStore(kstore, ksInfo);
	//
	//
	// }

	public void generateCrl(String aliasEmetteur, CrlInfo crlInfo)
			throws Exception {

		KeyTools ktools = new KeyTools();
		CertificateInfo certSign;
		try {
			certSign = ktools.getCertificateACByAlias(aliasEmetteur);
			X509CRL xCRL = CrlTools.generateCrl(certSign, crlInfo);
			CrlTools.saveCRL(xCRL, crlInfo.getPath());
		} catch (Exception e) {
			// log.error
			throw e;
		}

	}



}
