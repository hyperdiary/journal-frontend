package org.hyperdiary.journal.repository

import org.apache.jena.riot.RDFDataMgr
import org.hyperdiary.journal.models.Entry

class DummySolidRepository extends SolidRepository {

  override def getEntry(entryUri: String): Option[Entry] = {
    val entryFilePath = getClass.getResource("/J1.E1.ttl").getPath
    val model = RDFDataMgr.loadModel(entryFilePath)
    Entry.fromModel(model)
  }
}
