/**
 * 
 */
package org.dpr.mykeys.ihm.windows;

import static org.dpr.swingutils.ImageUtils.createImageIcon;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;

import org.dpr.mykeys.app.CertificateInfo;


/**
 * @author Buck
 *
 */
public class ListCertRenderer extends DefaultListCellRenderer {

	/**
	 * .
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
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param arg4
	 * @return
	 * 
	 * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList,
	 *      java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		Component retValue = super.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);
		// TODO Auto-generated method stub

		if (value instanceof CertificateInfo) {
			CertificateInfo cert = ((CertificateInfo) value);
			ImageIcon icon = null;
			if (cert.isContainsPrivateKey()) {
				icon = createImageIcon("certificatekey.png");
			} else {
				icon = createImageIcon("certificate2.png");
			}
			if (icon != null) {

				setIcon(icon);

			}
			setText(cert.getName());

		}
		// return super.getListCellRendererComponent(list, value, index,
		// isSelected, cellHasFocus);
		return retValue;
	}



}
