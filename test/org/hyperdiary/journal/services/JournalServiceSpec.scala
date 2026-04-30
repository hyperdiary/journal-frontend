package org.hyperdiary.journal.services

import org.hyperdiary.journal.config.Config
import org.hyperdiary.journal.repository.TestSolidRepository
import org.hyperdiary.journal.vocabulary.PersonalKnowledgeGraph
import org.scalatestplus.play.PlaySpec

class  JournalServiceSpec extends PlaySpec {

  "JournalService getEntry" must {

    "" ignore {
      val pkg = new PersonalKnowledgeGraph(Config("journal", "localhost"))
      val dummyRepository = new TestSolidRepository()
      val service = new JournalService(pkg)(dummyRepository)
      val entry = service.getEntry("J1", "E1")
      // entry.get.title mustBe "Chapter 1: Childhood Memories"
      entry.get.paragraphs.length mustBe 42
    }
  }

}
