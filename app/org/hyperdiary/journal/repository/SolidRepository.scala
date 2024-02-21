package org.hyperdiary.journal.repository

import com.google.inject.ImplementedBy
import org.hyperdiary.journal.models.{Entry, Journal}

@ImplementedBy(classOf[LocalSolidRepository])
trait SolidRepository {
  
  def getJournal(journalUri: String): Option[Journal]

  def getEntry(entryUri: String): Option[Entry]
  
  def getLabelLink(labelText: String): Option[String]
}
