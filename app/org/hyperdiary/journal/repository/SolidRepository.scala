package org.hyperdiary.journal.repository

import org.hyperdiary.journal.models.Entry

trait SolidRepository {

  def getEntry(entryUri: String): Option[Entry]
}
