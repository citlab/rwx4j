/**
 * Copyright 2010-2015 Complex and Distributed IT Systems, TU Berlin
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

package de.tu_berlin.cit.intercloud.xmpp.client.extension;

import java.net.URISyntaxException;

import org.apache.xmlbeans.XmlException;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.id.StanzaIdUtil;

import de.tu_berlin.cit.rwx4j.XmppURI;
import de.tu_berlin.cit.rwx4j.rest.ActionDocument;
import de.tu_berlin.cit.rwx4j.rest.RestDocument;
import de.tu_berlin.cit.rwx4j.rest.MethodDocument;

/**
 * TODO
 * 
 * @author Alexander Stanik <alexander.stanik@tu-berlin.de>
 */
public class RestIQ extends IQ {

	public static final String ELEMENT = "rest";
	public static final String NAMESPACE = "urn:xmpp:xml-rest";

	private final RestDocument doc;

	private XmppURI uri;
/*
	public RestIQ(XmppURI uri) {
		this(uri, ResourceDocument.Factory.newInstance());
	}
*/
	/**
	 * This constructor should only be called by the client builder
	 * @param uri
	 * @param doc
	 */
    public RestIQ(XmppURI uri, RestDocument doc) {
		this(doc);
		this.setStanzaId(StanzaIdUtil.newStanzaId());
		this.uri = uri;
		this.setType(Type.set);
		this.setTo(uri.getJID());
		
		if(this.doc.getRest() == null)
			this.doc.addNewRest().setPath(this.uri.getPath());
		
//		System.out.println("RestIQ was created with doc=" + this.doc.toString());

    }

	public RestIQ(RestDocument doc) {
		super(ELEMENT, NAMESPACE);
		this.doc = doc;
		try {
			this.uri = new XmppURI(doc.getRest().getPath());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected IQChildElementXmlStringBuilder getIQChildElementBuilder(
			IQChildElementXmlStringBuilder xml) {
		// set resource path
		xml.attribute("path", this.uri.getPath());
		xml.rightAngleBracket();

		try {
			if(this.doc.getRest().isSetMethod()) {
//				System.out.println("method toString:" + this.doc.getResource().getMethod().toString());
//				System.out.println("method toxmlText:" + this.doc.getResource().getMethod().xmlText());
//				System.out.println("method getDomNode.toString:" + this.doc.getResource().getMethod().getDomNode().toString());
				// parse method to method document
				MethodDocument methDoc;
				methDoc = MethodDocument.Factory.parse(this.doc.getRest().getMethod().getDomNode());
				xml.append(methDoc.toString());
				// do not call the toString method of a method directly, like
				// xml.append(this.doc.getResource().getMethod().toString());
			} else if(this.doc.getRest().isSetAction()) {
				// parse action to action document
				ActionDocument actDoc;
				actDoc = ActionDocument.Factory.parse(this.doc.getRest().getAction().getDomNode());
				xml.append(actDoc.toString());
				// do not call the toString method of an action directly, like
				// xml.append(this.doc.getResource().getAction().toString());
			}
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println("generated xml:" + xml);

		return xml;
	}

	public RestDocument getResourceDocument() {
		return this.doc;
	}


}
