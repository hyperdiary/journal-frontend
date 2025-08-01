package org.hyperdiary.journal.repository

import org.apache.jena.rdf.model.Model
import org.apache.jena.riot.RDFDataMgr
import org.hyperdiary.journal.models.{ Entry, Journal, Person, Place, Residence }

import scala.util.Try

class TestSolidRepository extends SolidRepository {

  override def getEntry(entryUri: String): Option[Entry] = {
    val entryFilePath = getClass.getResource("/J1.E1.ttl").getPath
    val model = RDFDataMgr.loadModel(entryFilePath)
    Entry.fromModel(model)
  }

  override def getJournal(journalUri: String): Option[Journal] = ???

  override def getLabelLink(labelText: String): Option[String] = ???

  override def getPerson(personUri: String): Option[Person] = ???

  override def getPlace(placeUri: String): Option[Place] = ???

  override def getResidence(residenceUri: String): Option[Residence] = ???

  override def createLabel(labelsModel: Model): Try[String] = ???
}
