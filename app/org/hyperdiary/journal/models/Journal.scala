package org.hyperdiary.journal.models

import org.apache.jena.rdf.model.{ Model, Resource }
import org.apache.jena.vocabulary.{ DCTerms, RDF }
import org.hyperdiary.journal.models.Entry.{ getEntryTitle, getParagraphs }
import org.hyperdiary.journal.vocabulary.{ DBpedia, HyperDiary }

import scala.jdk.CollectionConverters.*

case class Journal(id: String, title: String, entries: List[String], subtitle: Option[String])
object Journal {
  def fromModel(model: Model): Option[Journal] = model
    .listSubjectsWithProperty(RDF.`type`, HyperDiary.Journal)
    .asScala
    .toList
    .headOption
    .flatMap { journal =>
      Some(
        Journal(
          getJournalId(journal),
          getJournalTitle(journal),
          getJournalEntries(journal),
          getJournalSubtitle(journal)
        )
      )
    }

  private def getJournalId(journal: Resource): String =
    if (journal.hasProperty(DCTerms.identifier)) {
      val idObject = journal.getProperty(DCTerms.identifier).getObject
      if (idObject.isLiteral) {
        idObject.asLiteral().getString
      } else {
        // TODO log incorrect object type
        ""
      }
    } else {
      // TODO log missing title statement
      ""
    }

  private def getJournalTitle(journal: Resource): String =
    if (journal.hasProperty(DCTerms.title)) {
      val titleObject = journal.getProperty(DCTerms.title).getObject
      if (titleObject.isLiteral) {
        titleObject.asLiteral().getString
      } else {
        // TODO log incorrect object type
        ""
      }
    } else {
      // TODO log missing title statement
      ""
    }

  private def getJournalSubtitle(journal: Resource): Option[String] =
    if (journal.hasProperty(DBpedia.subtitle)) {
      val subtitleObject = journal.getProperty(DBpedia.subtitle).getObject
      if (subtitleObject.isLiteral) {
        Some(subtitleObject.asLiteral().getString)
      } else {
        // TODO log incorrect object type
        None
      }
    } else {
      None
    }

  private def getJournalEntries(journal: Resource): List[String] =
    if (journal.hasProperty(HyperDiary.hasEntry)) {
      journal.listProperties(HyperDiary.hasEntry).asScala.toList.map { entry =>
        entry.getObject.asResource().getURI
      }
    } else {
      List.empty
    }

}
