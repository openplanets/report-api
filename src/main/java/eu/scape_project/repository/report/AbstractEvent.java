package eu.scape_project.repository.report;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.openarchives.oai._2_0.oai_dc.OaiDcType;
import org.purl.dc.elements._1.ObjectFactory;
import org.purl.dc.elements._1.SimpleLiteral;
import org.w3c.util.DateParser;

public abstract class AbstractEvent implements Event {

	/**
	 * The logger.
	 */
	private static Logger logger = Logger.getLogger(AbstractEvent.class);

	@Override
	public String getOaiDcEvent() {
		logger.debug("getOaiDcEvent()");
		OaiDcType oaiDcType = new OaiDcType();

		SimpleLiteral identifier = new SimpleLiteral();
		identifier.getContent().add(getIdentifier());

		ObjectFactory dcObjFactory = new ObjectFactory();

		oaiDcType.getTitleOrCreatorOrSubject().add(
				dcObjFactory.createIdentifier(identifier));

		SimpleLiteral type = new SimpleLiteral();
		type.getContent().add(getType());

		oaiDcType.getTitleOrCreatorOrSubject().add(
				dcObjFactory.createType(type));

		SimpleLiteral date = new SimpleLiteral();
		date.getContent().add(DateParser.getIsoDateNoMillis(getDatetime()));

		oaiDcType.getTitleOrCreatorOrSubject().add(
				dcObjFactory.createDate(date));

		try {
			org.openarchives.oai._2_0.oai_dc.ObjectFactory oaidcObjFactory = new org.openarchives.oai._2_0.oai_dc.ObjectFactory();

			/*
			 * return AbstractEventCatalog .marshalToString(
			 * oaidcObjFactory.createDc(oaiDcType), true, true,
			 * "http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd"
			 * , OaiDcType.class, ElementType.class);
			 */
			return AbstractEventCatalog
					.marshalToString(
							oaidcObjFactory.createDc(oaiDcType),
							true,
							true,
							"http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd "
									+ "http://purl.org/dc/elements/1.1/ http://dublincore.org/schemas/xmls/qdc/2008/02/11/dc.xsd",
							"org.openarchives.oai._2_0.oai_dc:org.purl.dc.elements._1");

		} catch (JAXBException e) {
			logger.error(
					"Could not marshal oai_dc element into text - "
							+ e.getMessage(), e);
			return null;
		}

	}

}
