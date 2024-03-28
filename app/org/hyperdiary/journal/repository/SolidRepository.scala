package org.hyperdiary.journal.repository

import com.google.inject.ImplementedBy
import org.hyperdiary.journal.models.{Entry, Journal, Person, Place, Residence}

@ImplementedBy(classOf[LocalSolidRepository])
trait SolidRepository {

  def getJournal(journalUri: String): Option[Journal]

  def getEntry(entryUri: String): Option[Entry]

  def getLabelLink(labelText: String): Option[String]

  def getPerson(personUri: String): Option[Person]
  
  def getResidence(residenceUri: String): Option[Residence]
  
  def getPlace(placeUri: String): Option[Place]

}
