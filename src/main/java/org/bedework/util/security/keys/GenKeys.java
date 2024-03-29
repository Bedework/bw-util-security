/* ********************************************************************
    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.
*/
package org.bedework.util.security.keys;

import org.bedework.util.jmx.ConfBase;
import org.bedework.util.security.pki.PKITools;

/**
 * @author douglm
 *
 */
public class GenKeys extends ConfBase<GenKeysConfigImpl>
        implements GenKeysMBean {
  /** Following is some random text which we encode and decode to ensure
   *  generated keys work
   */
  String testText =
    "A variable of array type holds a reference to an object. ";

  public GenKeys(final String confDirName) {
    super(null, confDirName, "genkeys");
  }

  @Override
  public String getServiceName() {
    return serviceName;
  }

  @Override
  public void setPrivKeyFileName(final String val) {
    getConfig().setPrivKeyFileName(val);
  }

  @Override
  public String getPrivKeyFileName() {
    return getConfig().getPrivKeyFileName();
  }

  @Override
  public void setPublicKeyFileName(final String val) {
    getConfig().setPublicKeyFileName(val);
  }

  @Override
  public String getPublicKeyFileName() {
    return getConfig().getPublicKeyFileName();
  }

  /* ========================================================================
   * Operations
   * ======================================================================== */

  @Override
  public Msg genKeys() {
    final Msg infoLines = new Msg();

    try {
      final PKITools pki = new PKITools();

      if (getPrivKeyFileName() == null) {
        infoLines.add("Must provide a -privkey <file> parameter");
        return infoLines;
      }

      if (getPublicKeyFileName() == null) {
        infoLines.add("Must provide a -pubkey <file> parameter");
        return infoLines;
      }

      final PKITools.RSAKeys keys =
              pki.genRSAKeysIntoFiles(getPrivKeyFileName(),
                                      getPublicKeyFileName(),
                                      true);
      if (keys == null) {
        infoLines.add("Generation of keys failed");
        return infoLines;
      }

      // Now try the keys on the test text.

      final int numKeys = pki.countKeys(getPrivKeyFileName());

      //if (debug) {
      //  infoLines.add("Number of keys: " + numKeys);
      //}

      infoLines.add("test with---->" + testText);
      final String etext = pki.encryptWithKeyFile(getPublicKeyFileName(),
                                                  testText, numKeys - 1);
      infoLines.add("encrypts to-->" + etext);
      final String detext = pki.decryptWithKeyFile(getPrivKeyFileName(),
                                                   etext, numKeys - 1);
      infoLines.add("decrypts to-->" + detext);

      if (!testText.equals(detext)) {
        infoLines.add("Validity check failed: encrypt/decrypt failure");
      } else {
        infoLines.add("");
        infoLines.add("Validity check succeeded");
      }
    } catch (final Throwable t) {
      error(t);
      infoLines.add("Exception - check logs: " + t.getMessage());
    }

    return infoLines;
  }

  @Override
  public String loadConfig() {
    return loadConfig(GenKeysConfigImpl.class);
  }
}
