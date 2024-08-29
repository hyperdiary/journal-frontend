package org.hyperdiary.journal.models

case class JournalHtml(title: String, entries: List[EntryLink], subtitle: Option[String])
