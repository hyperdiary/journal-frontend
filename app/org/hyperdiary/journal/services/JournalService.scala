package org.hyperdiary.journal.services

import org.hyperdiary.journal.models.{EntryHtml, EntryLink, Journal, JournalHtml, Paragraph}
import org.hyperdiary.journal.repository.SolidRepository
import play.twirl.api.Html

import javax.inject.{Inject, Singleton}

@Singleton
class JournalService @Inject()(solidRepository: SolidRepository) extends BaseService {
  
  def getJournal(journalId: String): Option[JournalHtml] = {
    val journalUri = s"http://krw.localhost:3000/journal/$journalId"
    solidRepository.getJournal(journalUri).flatMap( journal =>
      Some(JournalHtml(journal.title, getEntryLinks(journal), journal.subtitle))
    )
  }

  def getEntry(journalId: String, entryId: String): Option[EntryHtml] = {
    val entryUri = s"http://krw.localhost:3000/entry/$journalId.$entryId"
    solidRepository.getEntry(entryUri).flatMap { entry =>
      Some(EntryHtml(entry.title, getParagraphHtml(entry.paragraphs)))
    }
  }

  private def getParagraphHtml(paragraphs: List[Paragraph]): List[Html] = {
    paragraphs.map { paragraph =>
      val tokens = paragraph.text.split("!\\[")
      val result = tokens.map(token =>
        if (token.contains("]")) {
          val label = token.substring(0, token.indexOf("]"))
          val rest = token.substring(token.indexOf("]") + 1)
          solidRepository.getLabelLink(label) match {
            case Some(link) =>
              val patchedLink = updateUri(link) // TODO this is a temporary fix until deployed
              s"<a href=\"$patchedLink\">$label</a>$rest"
            case _ => s"<span style=\"color:red;\">$label</span>$rest"
          }
        } else {
          token
        })
      Html(result.mkString)
    }
  }

  private def getEntryLinks(journal: Journal): List[EntryLink] = {
    journal.entries.flatMap { entryUri =>
      solidRepository.getEntry(updateUri(entryUri)).flatMap { entry =>
        Some(EntryLink(entry.id, getJournalLink(journal.id, entry.id), entry.title))
      }
    }.sorted
  }


  private def getJournalLink(journalId: String, entryId: String): String = s"/journal/$journalId/entry/$entryId"

}
