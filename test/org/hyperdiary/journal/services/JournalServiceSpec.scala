package org.hyperdiary.journal.services

import org.hyperdiary.journal.repository.TestSolidRepository
import org.scalatestplus.play.PlaySpec

class JournalServiceSpec extends PlaySpec {

  "JournalService getEntry" must {

    "" in {
      val dummyRepository = new TestSolidRepository()
      val service = new JournalService(dummyRepository)
      val entry = service.getEntry("J1","E1")
      //entry.get.title mustBe "Chapter 1: Childhood Memories"
      entry.get.paragraphs.length mustBe 42
    }
  }

}
