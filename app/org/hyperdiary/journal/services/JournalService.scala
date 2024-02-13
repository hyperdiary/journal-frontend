package org.hyperdiary.journal.services

import org.hyperdiary.journal.models.Entry
import org.hyperdiary.journal.repository.SolidRepository

import javax.inject.{Inject, Singleton}

@Singleton
class JournalService @Inject()(solidRepository: SolidRepository) {

  def getEntry(entryUri: String): Option[Entry] = solidRepository.getEntry(entryUri)
    

}
