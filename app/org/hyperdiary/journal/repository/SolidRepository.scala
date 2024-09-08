package org.hyperdiary.journal.repository

import com.google.inject.ImplementedBy
import org.apache.jena.rdf.model.Model
import org.hyperdiary.journal.models.{ Entry, Journal, Person, Place, Residence }

import scala.util.Try

@ImplementedBy(classOf[LocalSolidRepository])
trait SolidRepository {

  def getJournal(journalUri: String): Option[Journal]

  def getEntry(entryUri: String): Option[Entry]

  def getLabelLink(labelText: String): Option[String]

  def getPerson(personUri: String): Option[Person]

  def getResidence(residenceUri: String): Option[Residence]

  def getPlace(placeUri: String): Option[Place]

  def createLabel(labelsModel: Model): Try[String]

}
