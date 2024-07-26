package org.hyperdiary.journal.services

import org.scalatestplus.play.PlaySpec

class LabelServiceSpec extends PlaySpec {

  "LabelService getLabel" must {

    "" in {
      val service = new LabelService
      val label = service.createLabel("water_skiing","http://dbpedia.org/resource/Water_skiing")
      label mustEqual("Hello")
    }
  }

}
