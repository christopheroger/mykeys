package org.dpr.mykeys.ihm.actions;

import org.apache.commons.lang.StringUtils;

public enum TypeAction {
	ADD_STORE("AddStore"), EXPORT_CERT("ExportCert"), IMPORT_STORE(
			"ImportStore"), OPEN_STORE("OpenStore"), ADD_CERT("AddCert"), IMPORT_CERT(
			"ImportCert"), CLOSE_STORE("CloseStore"), REMOVE_STORE(
			"RemoveStore"), DELETE_STORE("DeleteStore"), ADD_CERT_AC(
			"AddCertAC"), DELETE_CERT("DeleteCert");

	/** L'attribut qui contient la valeur associ� � l'enum */
	private final String value;

	/** Le constructeur qui associe une valeur � l'enum */
	private TypeAction(String value) {
		this.value = value;
	}

	/** La m�thode accesseur qui renvoit la valeur de l'enum */
	public String getValue() {
		return this.value;
	}

	/**
	 * Permet de recuperer un <code>EtatSignature</code> � partir de sa valeur.
	 * 
	 * <BR>
	 * 
	 * <pre>
	 * <b>Algorithme : </b>
	 * DEBUT
	 * 
	 * FIN
	 * </pre>
	 * 
	 * @param value
	 *            valeur de l'objet EtatSignature
	 * 
	 * @return EtatTransaction
	 */
	public static TypeAction getTypeAction(String value) {
		TypeAction[] typesAction = TypeAction.values();
		for (int i = 0; i < typesAction.length; i++) {
			TypeAction action = typesAction[i];
			if (StringUtils.equals(action.getValue(), value)) {
				return action;
			}
		}
		return null;
	}

}
