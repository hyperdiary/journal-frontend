package org.hyperdiary.journal.models

case class EntryLink(id: String, uri: String, title: String)
object EntryLink {
  implicit val entryLinkOrdering: Ordering[EntryLink] = Ordering.by(_.id.stripPrefix("E").toInt)

}
