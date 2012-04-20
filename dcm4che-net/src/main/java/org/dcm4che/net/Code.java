/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is part of dcm4che, an implementation of DICOM(TM) in
 * Java(TM), hosted at https://github.com/gunterze/dcm4che.
 *
 * The Initial Developer of the Original Code is
 * Agfa Healthcare.
 * Portions created by the Initial Developer are Copyright (C) 2012
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 * See @authors listed below
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */

package org.dcm4che.net;

import org.dcm4che.util.StringUtils;

/**
 * @author Gunter Zeilinger <gunterze@gmail.com>
 */
public class Code {

    private final String codeValue;
    private final String codingSchemeDesignator;
    private final String codingSchemeVersion;
    private final String codeMeaning;

    public Code(String codeValue, String codingSchemeDesignator,
            String codingSchemeVersion, String codeMeaning) {
        if (codeValue == null)
            throw new NullPointerException("codeValue");
        if (codingSchemeDesignator == null)
            throw new NullPointerException("codingSchemeDesignator");
        if (codeMeaning == null)
            throw new NullPointerException("codeMeaning");
        this.codeValue = codeValue;
        this.codingSchemeDesignator = codingSchemeDesignator;
        this.codingSchemeVersion = codingSchemeVersion;
        this.codeMeaning = codeMeaning;
    }

    public Code(String s) {
        String[] ss = StringUtils.split(s, '^');
        if (ss.length < 3 || ss.length > 4
                || ss[0].isEmpty() || ss[1].isEmpty() || ss[2].isEmpty())
            throw new IllegalArgumentException(s);
        this.codeValue = ss[0];
        this.codeMeaning = ss[1];
        this.codingSchemeDesignator = ss[2];
        this.codingSchemeVersion = ss.length > 3 && !ss[3].isEmpty() ? ss[3] : null;
    }

    public final String getCodeValue() {
        return codeValue;
    }

    public final String getCodingSchemeDesignator() {
        return codingSchemeDesignator;
    }

    public final String getCodingSchemeVersion() {
        return codingSchemeVersion;
    }

    public final String getCodeMeaning() {
        return codeMeaning;
    }

    @Override
    public int hashCode() {
        return 37 * (37 * (37 * 
            codeValue.hashCode() +
            codeMeaning.hashCode()) +
            codingSchemeDesignator.hashCode()) + 
            hashCode(codingSchemeVersion);
    }

    private int hashCode(String s) {
        return s == null ? 0 : s.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Code))
            return false;
        Code other = (Code) o;
        return codeValue.equals(other.codeValue)
                && codeMeaning.equals(other.codeMeaning)
                && codingSchemeDesignator.equals(other.codingSchemeDesignator)
                && equals(codingSchemeVersion, other.codingSchemeVersion);
    }

    private boolean equals(String s1, String s2) {
        return s1 == s2 || s1 != null && s1.equals(s2);
    }

    @Override
    public String toString() {
        String s = codeValue + '^' + codeMeaning + '^' + codingSchemeDesignator;
        return codingSchemeVersion == null ? s : s + '^' + codingSchemeVersion;
    }
}