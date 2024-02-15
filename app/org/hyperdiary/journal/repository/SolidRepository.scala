package org.hyperdiary.journal.repository

import com.google.inject.ImplementedBy
import org.hyperdiary.journal.models.Entry

@ImplementedBy(classOf[LocalSolidRepository]) 
trait SolidRepository {

  def getEntry(entryUri: String): Option[Entry]
  
  def getLabelLink(labelText: String): Option[String]
}
