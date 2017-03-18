package com.uncc.prateek.xml_saxparserdemo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Xml;

public class PersonUtil {
	static public class PersonSAXParser extends DefaultHandler{
		private Person person = null;
		private StringBuilder xmlInnerText;
		
		public Person getPerson() {
			return person;
		}
		static Person parsePerson(InputStream xmlIn) throws IOException, SAXException{
			PersonSAXParser parser = new PersonSAXParser();
			Xml.parse(xmlIn, Xml.Encoding.UTF_8, parser);
			return parser.getPerson();
		}

		@Override
		public void startDocument() throws SAXException {
			xmlInnerText = new StringBuilder();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if(localName.equals("person")){
				person = new Person();
				try{
					person.setId(Integer.parseInt(attributes.getValue("id").trim()));
				} catch (NumberFormatException ex){
					//Invalid integer.
				}
			}
		}
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			xmlInnerText.append(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if(localName.equals("name")){
				person.setName(xmlInnerText.toString().trim());
			} else if(localName.equals("department")){
				person.setDept(xmlInnerText.toString().trim());
			} else if(localName.equals("age")){
				try{
					person.setAge(Integer.parseInt(xmlInnerText.toString().trim()));
				} catch(NumberFormatException ex){
					//Invalid integer.
				}
			} 
			xmlInnerText.setLength(0);
		}

		@Override
		public void endDocument() throws SAXException {
			super.endDocument();
		}
	}
	
	static public class PersonsSAXParser extends DefaultHandler{
		private ArrayList<Person> persons;
		private Person person = null;
		private StringBuilder xmlInnerText;
		
		public ArrayList<Person> getPersons() {
			return persons;
		}
		static ArrayList<Person> parsePersons(InputStream xmlIn) throws IOException, SAXException{
			PersonsSAXParser parser = new PersonsSAXParser();
			Xml.parse(xmlIn, Xml.Encoding.UTF_8, parser);
			return parser.getPersons();
		}

		@Override
		public void startDocument() throws SAXException {
			xmlInnerText = new StringBuilder();
			persons = new ArrayList<Person>();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if(localName.equals("person")){
				person = new Person();
				try{
					person.setId(Integer.parseInt(attributes.getValue("id").trim()));
				} catch (NumberFormatException ex){
					//Invalid integer.
				}
			}
		}
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			xmlInnerText.append(ch, start, length);
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if(localName.equals("name")){
				person.setName(xmlInnerText.toString().trim());
			} else if(localName.equals("department")){
				person.setDept(xmlInnerText.toString().trim());
			} else if(localName.equals("age")){
				try{
					person.setAge(Integer.parseInt(xmlInnerText.toString().trim()));
				} catch(NumberFormatException ex){
					//Invalid integer.
				}
			} else if(localName.equals("person")){
				persons.add(person);
			} 
			xmlInnerText.setLength(0);
		}

		@Override
		public void endDocument() throws SAXException {
			super.endDocument();
		}
	}
}