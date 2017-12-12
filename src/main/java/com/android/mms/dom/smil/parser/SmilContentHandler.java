/*
 * Copyright 2014 Jacob Klinker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.mms.dom.smil.parser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.smil.SMILDocument;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.android.mms.dom.smil.SmilDocumentImpl;

import java.util.logging.Logger;

public class SmilContentHandler extends DefaultHandler {
    private static final Logger log = Logger.getLogger(SmilContentHandler.class.getName());

    private SMILDocument mSmilDocument;
    private Node mCurrentNode;

    /**
     * Resets this handler.
     *
     */
    public void reset() {
        mSmilDocument = new SmilDocumentImpl();
        mCurrentNode = mSmilDocument;
    }

    /**
     * Returns the SMILDocument.
     * @return The SMILDocument instance
     */
    public SMILDocument getSmilDocument() {
        return mSmilDocument;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        log.fine("SmilContentHandler.startElement. Creating element " + localName);

        Element element = mSmilDocument.createElement(localName);
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                    log.fine("Attribute " + i + " lname = " + attributes.getLocalName(i) +
                        " value = " + attributes.getValue(i));
                element.setAttribute(attributes.getLocalName(i),
                        attributes.getValue(i));
            }
        }

        log.fine("Appending " + localName + " to " + mCurrentNode.getNodeName());
        mCurrentNode.appendChild(element);

        mCurrentNode = element;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        log.fine("SmilContentHandler.endElement. localName " + localName);
        mCurrentNode = mCurrentNode.getParentNode();
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        log.fine("SmilContentHandler.characters. ch = " + new String(ch, start, length));
    }
}
