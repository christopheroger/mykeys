package org.dpr.mykeys.app;

import org.bouncycastle.asn1.x509.KeyUsage;

public class X509Constants {
	public static String[] keyUsageLabel = new String[] { "digitalSignature",
			"nonRepudiation", "keyEncipherment", "dataEncipherment",
			"keyAgreement", "keyCertSign", "cRLSign", "encipherOnly",
			"decipherOnly" };

	   public static String[] ExtendedkeyUsageLabel = new String[] { "code signing",
	             };
	   
	public static int[] keyUsageInt = new int[] { KeyUsage.digitalSignature,
			KeyUsage.nonRepudiation, KeyUsage.keyEncipherment,
			KeyUsage.dataEncipherment, KeyUsage.keyAgreement,
			KeyUsage.keyCertSign, KeyUsage.cRLSign, KeyUsage.encipherOnly,
			KeyUsage.decipherOnly };

}
