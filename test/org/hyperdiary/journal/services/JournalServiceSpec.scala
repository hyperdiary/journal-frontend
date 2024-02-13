package org.hyperdiary.journal.services

import org.hyperdiary.journal.repository.DummySolidRepository
import org.scalatestplus.play.PlaySpec

class JournalServiceSpec extends PlaySpec {

  "JournalService getEntry" must {

    "" in {
      val dummyRepository = new DummySolidRepository()
      val service = new JournalService(dummyRepository)
      val entry = service.getEntry("http://krw.hyperdiary.io/entry/J1.E1")
      //entry.get.title mustBe "Chapter 1: Childhood Memories"
      entry.get.paragraphs.length mustBe 42
    }
  }

}
