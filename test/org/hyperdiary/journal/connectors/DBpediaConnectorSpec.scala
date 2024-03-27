package org.hyperdiary.journal.connectors

import org.scalatestplus.play.PlaySpec

class DBpediaConnectorSpec extends PlaySpec {

  "" must {
    "" in {
      val result = DBpediaConnector.getLabel("http://dbpedia.org/resource/Ridgewood_High_School_(New_Jersey)")
      result mustEqual (Some("Ridgewood High School (New Jersey)"))
    }
  }


}
